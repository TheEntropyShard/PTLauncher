package me.theentropyshard.ptlauncher.utils;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;

public class PathUtils {
    public static Path createFile(Path path) throws IOException {
        if (!Files.exists(path)) {
            Files.createFile(path);
        }

        return path;
    }

    public static Path createDirectory(Path path) throws IOException {
        if (!Files.exists(path)) {
            Files.createDirectories(path);
        }

        return path;
    }

    public static void deleteDirectory(Path path) throws IOException {
        Files.walkFileTree(
                path,
                new SimpleFileVisitor<Path>() {
                    @Override
                    public FileVisitResult postVisitDirectory(Path dir, IOException e) throws IOException {
                        Files.delete(dir);
                        return FileVisitResult.CONTINUE;
                    }

                    @Override
                    public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                        Files.delete(file);
                        return FileVisitResult.CONTINUE;
                    }
                }
        );
    }

    private PathUtils() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }
}
