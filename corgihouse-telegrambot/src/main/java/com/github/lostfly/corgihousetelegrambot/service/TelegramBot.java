package com.github.lostfly.corgihousetelegrambot.service;

import com.github.lostfly.corgihousetelegrambot.config.BotConfig;
import com.github.lostfly.corgihousetelegrambot.model.Pet;
import com.github.lostfly.corgihousetelegrambot.model.PetRepository;
import com.github.lostfly.corgihousetelegrambot.model.User;
import com.github.lostfly.corgihousetelegrambot.model.UserRepository;
import com.vdurmont.emoji.EmojiParser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.interfaces.BotApiObject;
import org.telegram.telegrambots.meta.api.methods.GetFile;
import org.telegram.telegrambots.meta.api.methods.commands.SetMyCommands;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.PhotoSize;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.commands.BotCommand;
import org.telegram.telegrambots.meta.api.objects.commands.scope.BotCommandScope;
import org.telegram.telegrambots.meta.api.objects.commands.scope.BotCommandScopeDefault;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.io.File;
import java.util.Optional;

@Slf4j
@Component
public class TelegramBot extends TelegramLongPollingBot {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PetRepository petRepository;

    final BotConfig config;

    static final String HELP_TEXT = "Этот бот умеет - , чтобы пользоваться этим тыкай сюда - . Или обратитесь к @eveprova";

    public TelegramBot(BotConfig config){

        this.config = config;
        List<BotCommand> listofCommands = new ArrayList<>();
        listofCommands.add(new BotCommand("/start", "Начать"));
        listofCommands.add(new BotCommand("/help", "Помощь"));
        listofCommands.add(new BotCommand("/register", "Регистрация пользователя"));
        try {
            this.execute(new SetMyCommands(listofCommands, new BotCommandScopeDefault(), null));
        }
        catch (TelegramApiException e){
            log.error("Error setting bot command list " + e.getMessage());
        }

    }

    @Override
    public String getBotUsername() {
        return config.getBotName();
    }

    @Override
    public String getBotToken() {
        return config.getToken();
    }

    @Override
    public void onUpdateReceived(Update update) {

        if(update.hasMessage()){
            String messageText = update.getMessage().getText();
            long chatId = update.getMessage().getChatId();


            // DOWNLOAD AND SEND IMAGES
            if (update.hasMessage() && update.getMessage().hasPhoto()) {
                // Получаем объект, представляющий сообщение
                PhotoSize photo = update.getMessage().getPhoto().stream()
                        .sorted((p1, p2) -> Integer.compare(p2.getFileSize(), p1.getFileSize()))
                        .findFirst()
                        .orElse(null);

                if (photo != null) {
                    try {
                        // Получаем объект, представляющий файл фотографии
                        GetFile getFileRequest = new GetFile();
                        getFileRequest.setFileId(photo.getFileId());
                        org.telegram.telegrambots.meta.api.objects.File file = execute(getFileRequest);

                        // Скачиваем фото на компьютер
                        java.io.File localFile = downloadPhotoByFilePath(file.getFilePath(), "downloaded_photos", chatId);

                        sendPhoto(chatId, localFile);


                        System.out.println("Фото сохранено: " + localFile.getAbsolutePath());
                    } catch (TelegramApiException | IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            switch(messageText){
                case "/start":
                    startCommandReceived(chatId, update.getMessage().getChat().getFirstName());
                    break;
                case "/help":
                    helpCommandReceived(chatId);
                    break;
                case "/register":
                    registerUser(update.getMessage());

                    registerUserCommandReceived(chatId, update.getMessage().getChat().getFirstName());
                    break;
                case "/register_pet":

                    registerPet(update.getMessage());

                    registerPetCommandReceived(chatId, update.getMessage().getChat().getFirstName());
                    break;
                case "/test_pet":
                case "Питомцы":

                    testPet(update.getMessage());

                    break;
                case "Профиль":

                    testProfile(update.getMessage());

                    break;

                default:
                    sendMessage(chatId, "Пока я в разработке, но скоро смогу понять тебя!");
                    break;
            }

        }

    }

    private void testProfile(Message message) {
        var chatId = message.getChatId();

        User user = userRepository.findByChatId(chatId);

        StringBuilder user_info_message = new StringBuilder();

        user_info_message.append("ID пользователя: ").append(user.getChatId()).append("\n");
        user_info_message.append("Логин: ").append(user.getUserName()).append("\n");
        user_info_message.append("Имя: ").append(user.getFirstName()).append("\n");
        user_info_message.append("Фамилия: ").append(user.getLastName()).append("\n");
        user_info_message.append("Телефон: ").append(user.getPhoneNumber()).append("\n");
        user_info_message.append("Дата Регистрации: ").append(user.getRegisteredAt()).append("\n\n");

        // Отправьте сообщение с информацией о всех животных
        sendMessage(chatId, user_info_message.toString());

    }

    private void testPet(Message message) {
        var chatId = message.getChatId();

        ArrayList<Pet> pets = petRepository.findAllByOwnerId(chatId);

        // Проверьте, если список животных пустой
        if (pets.isEmpty()) {
            // Если список пуст, отправьте сообщение о том, что нет питомцев
            sendMessage(chatId, "У вас пока что нет питомцев! Хотите зарегистрировать?");
        } else {
            // Создайте StringBuilder для сбора информации о животных
            StringBuilder pet_info_message = new StringBuilder();

            // Переберите каждое животное и добавьте информацию о нем в StringBuilder
            for (Pet pet : pets) {
                pet_info_message.append("ID животного: ").append(pet.getPetId()).append("\n");
                pet_info_message.append("Имя животного: ").append(pet.getPetName()).append("\n");
                pet_info_message.append("Тип животного: ").append(pet.getAnimalType()).append("\n");
                pet_info_message.append("Порода животного: ").append(pet.getPetBreed()).append("\n");
                pet_info_message.append("ID владельца: ").append(pet.getOwnerId()).append("\n\n");
            }

            // Отправьте сообщение с информацией о всех животных
            sendMessage(chatId, pet_info_message.toString());
        }
    }


    private void sendPhoto(Long chatId, File imageFile) throws TelegramApiException{
        SendPhoto sendPhoto = new SendPhoto();
        sendPhoto.setChatId(chatId.toString());
        sendPhoto.setPhoto(new InputFile(imageFile));
        execute(sendPhoto);
    }

    private File downloadPhotoByFilePath(String filePath, String directory, Long id) throws IOException {
        java.io.File downloadedFile = new java.io.File(directory);
        if (!downloadedFile.exists()) {
            downloadedFile.mkdirs();
        }
        java.io.File localFile = new java.io.File(downloadedFile, "pet_image_" + Long.toString(id) + ".jpg");

        String link = "https://api.telegram.org/file/bot" + getBotToken() + "/" + filePath;

        try (InputStream in = new URL(link).openStream()) {
            Files.copy(in, localFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
        }
        return localFile;
    }



    private void registerUser(Message message) {

        if (userRepository.findById(message.getChatId()).isEmpty()){
            var chatId = message.getChatId();
            var chat = message.getChat();
            User user = new User();

            user.setChatId(chatId);
            user.setFirstName(chat.getFirstName());
            user.setLastName(chat.getLastName());
            user.setUserName(chat.getUserName());
            user.setRegisteredAt(new Timestamp(System.currentTimeMillis()));


            userRepository.save(user);

            log.info("user saved: " + user);
        }

    }

    private void registerPet(Message message) {

        if (petRepository.findById(message.getChatId()).isEmpty()){


            var chatId = message.getChatId();
            var chat = message.getChat();
            Pet pet = new Pet();

            pet.setOwnerId(chatId);
            pet.setPetBreed("Корги");
            pet.setAnimalType("Собака");
            pet.setPetName("Булочка");

            var petId = petRepository.count();

            pet.setPetId(petId + 1);

            petRepository.save(pet);

            log.info("pet saved: " + pet);
        }

    }

    private void registerUserCommandReceived(long chatId, String name ) {
        String answer = "Раздел регистрации! Сначала введите имя: ";
        sendMessage(chatId, answer);
        log.info("Register user " + name + " " + chatId);
    }

    private void registerPetCommandReceived(long chatId, String name ) {
        String answer = "Регистрация питомца! Сначала введите имя: ";
        sendMessage(chatId, answer);
        log.info("Register pet " + name + " " + chatId);
    }

    private void startCommandReceived(long chatId, String name) {
        String answer = EmojiParser.parseToUnicode("Привет, " + name + ", рад тебя видеть!" + " :blush:");
        sendMessage(chatId, answer);
        log.info("Replied to user " + name + " " + chatId);
    }

    private void helpCommandReceived(long chatId)  {
        String answer = HELP_TEXT;
        sendMessage(chatId, answer);
    }

    private void sendMessage(long chatId, String textToSend)  {
        SendMessage message = new SendMessage();
        message.setChatId(String.valueOf(chatId));
        message.setText(textToSend);
        try {
            execute(message);
        }
        catch (TelegramApiException e){
            log.error("Error occurred" + e.getMessage());
        }
    }

}
