package com.upb.sgd.utils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.DecimalFormat;

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

    // Método para convertir el tamaño del archivo a MB con máximo 5 caracteres
    public static String getFileSizeInMB(Path filePath) throws IOException {
        long bytes = Files.size(filePath);
        double megabytes = bytes / (1024.0 * 1024.0);

        DecimalFormat df;
        if (megabytes < 10) {
            df = new DecimalFormat("0.00"); // 2 decimales para valores menores a 10 MB
        } else {
            df = new DecimalFormat("0.0");  // 1 decimal para valores mayores o iguales a 10 MB
        }

        String formattedSize = df.format(megabytes) + " MB";

        // Asegurar que no exceda los 5 caracteres
        if (formattedSize.length() > 5) {
            formattedSize = formattedSize.substring(0, 5);
        }

        return formattedSize;
    }
}
