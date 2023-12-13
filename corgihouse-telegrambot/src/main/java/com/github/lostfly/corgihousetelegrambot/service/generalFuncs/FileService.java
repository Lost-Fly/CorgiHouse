package com.github.lostfly.corgihousetelegrambot.service.generalFuncs;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

import static com.github.lostfly.corgihousetelegrambot.constants.GlobalConstants.*;

@Slf4j
@Component
public class FileService {

    public void downloadPhotoByFilePath(String filePath, String directory, Long chat_id, Long pet_id, String botToken) throws IOException {
        java.io.File downloadedFile = new java.io.File(directory);

        java.io.File localFile = new java.io.File(downloadedFile, PET_IMG_FILE_NAME + chat_id.toString() +
                '_' + pet_id.toString() + PET_IMG_FILE_EXTENSION);

        String link = TG_DOWNLOAD_FILE_LINK + botToken + "/" + filePath;

        try (InputStream in = new URL(link).openStream()) {
            Files.copy(in, localFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
        }

    }

    public File givePhotoByFilePath(Long chat_id, Long pet_id) throws IOException {
        java.io.File downloadedFile = new java.io.File(PHOTO_STORAGE_DIR);

        return new File(downloadedFile, PET_IMG_FILE_NAME + chat_id.toString() + '_'
                + pet_id.toString() + PET_IMG_FILE_EXTENSION);
    }

    public File giveCorgiPhotoByFilePath(Long pet_id) throws IOException {
        java.io.File downloadedFile = new java.io.File(CORGI_STORAGE_DIR);

        return new File(downloadedFile, pet_id.toString() + PET_IMG_FILE_EXTENSION);
    }

}
