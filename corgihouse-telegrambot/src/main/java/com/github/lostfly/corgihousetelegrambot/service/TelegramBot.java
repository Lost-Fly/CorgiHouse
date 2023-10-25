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
import org.telegram.telegrambots.meta.api.methods.GetFile;
import org.telegram.telegrambots.meta.api.methods.commands.SetMyCommands;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.PhotoSize;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.commands.BotCommand;
import org.telegram.telegrambots.meta.api.objects.commands.scope.BotCommandScopeDefault;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.io.File;
import java.util.Objects;

import static com.github.lostfly.corgihousetelegrambot.constants.GlobalConstants.*;

@Slf4j
@Component
public class TelegramBot extends TelegramLongPollingBot {

    @Autowired
    private UserRegistration userRegistration;

    @Autowired
    private PetRegistration petRegistration;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PetRepository petRepository;

    public static String globalFunctionContext = GLOBAL_CONTEXT_DEFAULT;

    final BotConfig config;

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

        long chatId = update.getMessage().getChatId();

        if(update.hasMessage() && Objects.equals(globalFunctionContext, GLOBAL_CONTEXT_DEFAULT)){
            String messageText = update.getMessage().getText();

            if (update.getMessage().hasPhoto()) {
                downloadAndSendImage(update);
            }

            switch(messageText){
                case "/start":
                    startCommandReceived(chatId, update.getMessage().getChat().getFirstName());
                    break;
                case "/help":
                    helpCommandReceived(chatId);
                    break;
                case "/register":
                    sendMessage(chatId,userRegistration.initializeRegistration(update));
                    break;
                case "/register_pet":
                    petRegistration.registerPet(update.getMessage());
                    break;
                case "Питомцы":
                    showPets(update.getMessage());
                    break;
                case "Профиль":
                    showProfile(update.getMessage());
                    break;
                default:
                    sendMessage(chatId, INDEV_TEXT);
                    break;
            }
        }
        else {
            switch (globalFunctionContext){
                case GLOBAL_CONTEXT_USER_REGISTRATION:
                    sendMessage(chatId,userRegistration.continueRegistration(update));
                    break;
                default:
                    globalFunctionContext=GLOBAL_CONTEXT_DEFAULT;
                    log.error("No global context found -  " + update.getMessage().getText());
                    sendMessage(chatId, INDEV_TEXT);
                    break;
            }
        }
    }

    private void showProfile(Message message) {
        var chatId = message.getChatId();

        User user = userRepository.findByChatId(chatId);

        StringBuilder user_info_message = new StringBuilder();

        user_info_message.append("ID пользователя: ").append(user.getChatId()).append("\n");
        user_info_message.append("Логин: ").append(user.getUserName()).append("\n");
        user_info_message.append("Имя: ").append(user.getFirstName()).append("\n");
        user_info_message.append("Фамилия: ").append(user.getLastName()).append("\n");
        user_info_message.append("Телефон: ").append(user.getPhoneNumber()).append("\n");
        user_info_message.append("Дата Регистрации: ").append(user.getRegisteredAt()).append("\n\n");


        sendMessage(chatId, user_info_message.toString());

    }

    private void showPets(Message message) {
        var chatId = message.getChatId();

        ArrayList<Pet> pets = petRepository.findAllByOwnerId(chatId);

        if (pets.isEmpty()) {
            sendMessage(chatId, NO_PETS_TEXT);
        } else {

            StringBuilder pet_info_message = new StringBuilder();


            for (Pet pet : pets) {
                pet_info_message.append("ID животного: ").append(pet.getPetId()).append("\n");
                pet_info_message.append("Имя животного: ").append(pet.getPetName()).append("\n");
                pet_info_message.append("Тип животного: ").append(pet.getAnimalType()).append("\n");
                pet_info_message.append("Порода животного: ").append(pet.getPetBreed()).append("\n");
                pet_info_message.append("ID владельца: ").append(pet.getOwnerId()).append("\n\n");
            }


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



    private void startCommandReceived(long chatId, String name) {
        String MEETING_REPLY = EmojiParser.parseToUnicode("Привет, " + name + ", рад тебя видеть!" + " :blush:");
        sendMessage(chatId, MEETING_REPLY);
        log.info("Replied to user " + name + " " + chatId);
    }

    private void helpCommandReceived(long chatId)  {
        String answer = HELP_TEXT;
        sendMessage(chatId, answer);
    }

    private void downloadAndSendImage(Update update){

        long chatId = update.getMessage().getChatId();

        PhotoSize photo = update.getMessage().getPhoto().stream()
                .sorted((p1, p2) -> Integer.compare(p2.getFileSize(), p1.getFileSize()))
                .findFirst()
                .orElse(null);

        if (photo != null) {
            try {

                GetFile getFileRequest = new GetFile();
                getFileRequest.setFileId(photo.getFileId());
                org.telegram.telegrambots.meta.api.objects.File file = execute(getFileRequest);

                java.io.File localFile = downloadPhotoByFilePath(file.getFilePath(), "downloaded_photos", chatId);

                sendPhoto(chatId, localFile);

                System.out.println("Фото сохранено: " + localFile.getAbsolutePath());
            } catch (TelegramApiException | IOException e) {
                e.printStackTrace();
            }
        }
    }

    void sendMessage(long chatId, String textToSend)  {
        SendMessage message = new SendMessage();
        message.setChatId(String.valueOf(chatId));
        message.setText(textToSend);


        ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup();
        List<KeyboardRow> keyboardRows =new ArrayList<>();
        KeyboardRow row1 = new KeyboardRow();

        row1.add("Профиль");
        row1.add("Поиск встреч");
        keyboardRows.add(row1);

        KeyboardRow row2 = new KeyboardRow();

        row2.add("Питомцы");
        row2.add("Мои встречи");
        keyboardRows.add(row2);

        keyboardMarkup.setKeyboard(keyboardRows);

        message.setReplyMarkup(keyboardMarkup);


        try {
            execute(message);
        }
        catch (TelegramApiException e){
            log.error("Error occurred" + e.getMessage());
        }
    }

}
