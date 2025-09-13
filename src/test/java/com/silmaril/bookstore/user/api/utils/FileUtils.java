package com.silmaril.bookstore.user.api.utils;

import java.io.File;
import java.io.FileInputStream;
import java.nio.file.Files;
import java.util.Objects;

public class FileUtils {
    private FileUtils() {
    }

    public static String readFileFromClassLoader(String name) {
        try {
            File file =
                    new File(
                            Objects.requireNonNull(
                                            Thread.currentThread().getContextClassLoader().getResource(name))
                                    .toURI());
            return Files.readString(file.toPath());
        } catch (Exception ex) {
            throw new RuntimeException("Error reading file [" + name + "]", ex);
        }
    }

    public static byte[] readBytesFromClassLoader(String fileName) {
        try (FileInputStream fileInput =
                     new FileInputStream(
                             new File(
                                     Objects.requireNonNull(
                                                     Thread.currentThread().getContextClassLoader()
                                                             .getResource(fileName))
                                             .toURI()));) {
            return fileInput.readAllBytes();
        } catch (Exception ex) {
            throw new RuntimeException("Error reading file [" + fileName + "]", ex);
        }
    }
}