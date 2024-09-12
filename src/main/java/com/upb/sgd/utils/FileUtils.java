package com.upb.sgd.utils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class FileUtils {

    public static byte[] readFileToByteArray(Path filePath) throws IOException {
        return Files.readAllBytes(filePath);
    }

    public static void writeByteArrayToFile(Path filePath, byte[] data) throws IOException {
        Files.write(filePath, data);
    }

    public static String SetFileVersionName(String original, String toInsert) {
        int dotIndex = original.lastIndexOf(".");

        if (dotIndex == -1) {
            return original;
        }

        String baseName = original.substring(0, dotIndex);
        String extension = original.substring(dotIndex);

        return baseName + toInsert + extension;
    }
}
