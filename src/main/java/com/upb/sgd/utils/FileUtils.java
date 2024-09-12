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

    // Método para convertir el tamaño del archivo a la unidad más adecuada con máximo 5 caracteres
    public static String getFileSize(Path filePath) throws IOException {
        long bytes = Files.size(filePath);
        double size = bytes; // Inicializamos con bytes
        String unit = " B"; // Unidad por defecto: bytes
        DecimalFormat df = new DecimalFormat("0.0"); // Usaremos 1 decimal por defecto

        // Convertimos el tamaño a la unidad más apropiada
        if (bytes >= 1024) {
            size = bytes / 1024.0;
            unit = " KB";
        }
        if (size >= 1024) {
            size = size / 1024.0;
            unit = " MB";
        }
        if (size >= 1024) {
            size = size / 1024.0;
            unit = " GB";
        }

        // Ajuste final si excede los 99 GB
        if (size >= 100 && " GB".equals(unit)) {
            return "99+GB"; // Limitar a 99+ GB
        }

        // Limitar el tamaño a 99 en cualquier unidad
        if (size >= 100) {
            df = new DecimalFormat("00"); // Sin decimales si es >= 100
        }

        // Formatear el tamaño y unirlo con la unidad
        return df.format(size) + unit;
    }
}
