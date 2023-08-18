package me.theentropyshard.ptlauncher.utils;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.List;
import java.util.Scanner;

public class IOUtils {
    /**
     * Reads given file to UTF-8 string
     */
    public static String readFile(Path path) throws IOException {
        List<String> lines = Files.readAllLines(path, StandardCharsets.UTF_8);
        return String.join("\n", lines);
    }

    /**
     * Returns true if file is empty
     */
    public static boolean isFileEmpty(Path path) throws IOException {
        return Files.size(path) == 0L;
    }

    /**
     * Writes UTF-8 string to file
     */
    public static void writeString(Path path, String s) throws IOException {
        byte[] bytes = s.getBytes(StandardCharsets.UTF_8);
        Files.write(path, bytes, StandardOpenOption.WRITE);
    }

    /**
     * Appends UTF-8 string to file
     */
    public static void appendString(Path path, String s) throws IOException {
        byte[] bytes = s.getBytes(StandardCharsets.UTF_8);
        Files.write(path, bytes, StandardOpenOption.APPEND);
    }

    /**
     * Reads given InputStream to UTF-8 string
     */
    public static String inputStreamToString(InputStream inputStream) {
        StringBuilder builder = new StringBuilder();
        Scanner scanner = new Scanner(inputStream, StandardCharsets.UTF_8.name());
        while (scanner.hasNextLine()) {
            builder.append(scanner.nextLine()).append("\n");
        }
        scanner.close();
        return builder.toString();
    }

    private IOUtils() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }
}
