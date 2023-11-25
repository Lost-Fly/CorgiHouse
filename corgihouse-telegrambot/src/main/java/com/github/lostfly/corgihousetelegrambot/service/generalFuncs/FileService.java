package com.github.lostfly.corgihousetelegrambot.service.generalFuncs;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

@Slf4j
@Component
public class FileService {


    public File downloadPhotoByFilePath(String filePath, String directory, Long id, String botToken) throws IOException {
        java.io.File downloadedFile = new java.io.File(directory);
        if (!downloadedFile.exists()) {
            downloadedFile.mkdirs();
        }
        java.io.File localFile = new java.io.File(downloadedFile, "pet_image_" + id + ".jpg");

        String link = "https://api.telegram.org/file/bot" + botToken + "/" + filePath;

        try (InputStream in = new URL(link).openStream()) {
            Files.copy(in, localFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
        }
        return localFile;
    }


}
