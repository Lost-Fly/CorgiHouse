package com.github.lostfly.corgihousetelegrambot.service.generalFuncs;

import com.github.lostfly.corgihousetelegrambot.service.TelegramBot;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.GetFile;
import org.telegram.telegrambots.meta.api.objects.PhotoSize;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

import static com.github.lostfly.corgihousetelegrambot.constants.GlobalConstants.CORGI_STORAGE_DIR;
import static com.github.lostfly.corgihousetelegrambot.constants.GlobalConstants.PHOTO_STORAGE_DIR;

@Slf4j
@Component
public class FileService {

    public void downloadPhotoByFilePath(String filePath, String directory, Long chat_id, Long pet_id, String botToken) throws IOException {
        java.io.File downloadedFile = new java.io.File(directory);
        if (!downloadedFile.exists()) {
            downloadedFile.mkdirs();
        }

        java.io.File localFile = new java.io.File(downloadedFile, "pet_image_" + chat_id.toString() + '_' + pet_id.toString() + ".jpg");

        String link = "https://api.telegram.org/file/bot" + botToken + "/" + filePath;

        try (InputStream in = new URL(link).openStream()) {
            Files.copy(in, localFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
        }

    }

    public File givePhotoByFilePath(Long chat_id, Long pet_id) throws IOException {
        java.io.File downloadedFile = new java.io.File(PHOTO_STORAGE_DIR);

        java.io.File localFile = new java.io.File(downloadedFile, "pet_image_" + chat_id.toString() + '_' + pet_id.toString() + ".jpg");

        return localFile;
    }

    public File giveCorgiPhotoByFilePath(Long pet_id) throws IOException {
        java.io.File downloadedFile = new java.io.File(CORGI_STORAGE_DIR);

        java.io.File localFile = new java.io.File(downloadedFile, pet_id.toString() + ".jpg");

        return localFile;
    }

}
