//package com.github.lostfly.corgihousetelegrambot.service;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.telegram.telegrambots.meta.api.objects.Update;
//
//import java.util.HashMap;
//import java.util.Map;
//import java.util.function.Function;
//
//public class CommandProcessor {
//    @Autowired
//    private UserFuncs userFuncs;
//
//    public void frt() {
//
//
//        // Создаем словарь команд и связанных с ними функций
//        Map<String, Function<Update, String>> commandMap = new HashMap<>();
//        Map<String, Function<Update, String>> globalContextCommandMap = new HashMap<>();
//
//        commandMap.put("/start", update -> generalFuncs.startCommandReceived(update.getChatId(), update.getMessage().getChat().getFirstName()));
//        commandMap.put("/help", update -> HELP_TEXT);
//        commandMap.put("/register", userRegistration::initializeRegistration);
//        commandMap.put("/register_pet", petRegistration::registerPet);
//        commandMap.put("Питомцы", update -> petsFuncs.showPets(update.getChatId()));
//        commandMap.put("Профиль", update -> userFuncs.showProfile(update.getChatId()));
//
//        globalContextCommandMap.put(GLOBAL_CONTEXT_USER_REGISTRATION, userRegistration::continueRegistration);
//        globalContextCommandMap.put(GLOBAL_CONTEXT_PET_REGISTRATION, update -> INDEV_TEXT);
//
//        // Обработка команд
//        Update update = ...; // Здесь вы должны получить объект Update
//        String chatId = update.getChatId();
//
//        if (update.hasMessage() && Objects.equals(sessionRepository.findByChatId(chatId).getGlobalFunctionContext(), GLOBAL_CONTEXT_DEFAULT)) {
//            String messageText = update.getMessage().getText();
//            Function<Update, String> commandHandler = commandMap.get(messageText);
//
//            if (commandHandler != null) {
//                String response = commandHandler.apply(update);
//                sendMessage(chatId, response);
//            } else {
//                sendMessage(chatId, INDEV_TEXT);
//            }
//        } else {
//            String globalContext = sessionRepository.findByChatId(chatId).getGlobalFunctionContext();
//            Function<Update, String> globalContextHandler = globalContextCommandMap.get(globalContext);
//
//            if (globalContextHandler != null) {
//                String response = globalContextHandler.apply(update);
//                sendMessage(chatId, response);
//            } else {
//                sessionRepository.setGlobalContextByChatId(GLOBAL_CONTEXT_DEFAULT, chatId);
//                log.error("No global context found -  " + update.getMessage().getText());
//                sendMessage(chatId, INDEV_TEXT);
//            }
//        }
//    }
//}
