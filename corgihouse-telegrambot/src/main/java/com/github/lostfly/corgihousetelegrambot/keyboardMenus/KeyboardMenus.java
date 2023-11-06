package com.github.lostfly.corgihousetelegrambot.keyboardMenus;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
public class KeyboardMenus {

    private static final String PROFILE = "Профиль";
    private static final String PETS = "Питомцы";
    private static final String MY_MEETINGS = "Мои встречи";
    private static final String SEARCH_MEETINGS = "Поиск встреч";

    public ReplyKeyboardMarkup mainKeyboard() {
        ReplyKeyboardMarkup mainMarkup = new ReplyKeyboardMarkup();

        List<KeyboardRow> keyboardRows = new ArrayList<>();
        KeyboardRow mainRow1 = new KeyboardRow();
        KeyboardRow mainRow2 = new KeyboardRow();

        mainRow1.add(PROFILE);
        mainRow1.add(SEARCH_MEETINGS);
        keyboardRows.add(mainRow1);

        mainRow2.add(PETS);
        mainRow2.add(MY_MEETINGS);
        keyboardRows.add(mainRow2);

        mainMarkup.setKeyboard(keyboardRows);

        return mainMarkup;
    }

}
