package info.kgeorgiy.ja.lymar.walk;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @author Pavel Lymar
 */
public class RecursiveWalk {
    // :NOTE: final, DEFAULT_HASH, 40 zeros
    public final static String DEFAULT_HASH = "0".repeat(40);
    public static MessageDigest result;

    public static void writeResult(BufferedWriter writer, String path) throws IOException {
        writer.write(DEFAULT_HASH + " " + path + System.lineSeparator());
    }

    public static void writeResult(BufferedWriter writer, String path, String hash) throws IOException {
        writer.write(hash + " " + path + System.lineSeparator());
    }

    public static void main(String[] args) {
        try {
            result = MessageDigest.getInstance("SHA-1");
        } catch (NoSuchAlgorithmException e) {
            System.err.println("SHA-1 is not found: " + e.getMessage());
            return;
        }

        if (args == null || args.length != 2 || args[0] == null || args[1] == null) {
            // :NOTE: err, usage
            System.err.println("Invalid program input. Usage: RecursiveWalk [input path] [output path]");
            return;
        }

        Path inputPath = null, outputPath = null;
        try {
            inputPath = Paths.get(args[0]);
        } catch (InvalidPathException e) {
            System.err.println("Invalid input path: " + e.getMessage());
        }
        try {
            outputPath = Paths.get(args[1]);
        } catch (InvalidPathException e) {
            System.err.println("Invalid output path: " + e.getMessage());
        }

        if (inputPath == null || outputPath == null) {
            return;
        }

        Path parent = outputPath.getParent();
        if (parent == null) {
            System.err.println("Invalid parent path of output file");
            return;
        }

        // :NOTE: npe
        try {
            if (Files.notExists(parent)) {
                Files.createDirectories(parent);
            }
        } catch (IOException e) {
            System.err.println("Can't create output directory: " + e.getMessage());
        }

        try (BufferedReader reader = Files.newBufferedReader(inputPath);
             BufferedWriter writer = Files.newBufferedWriter(outputPath)) {
            String path;
            while ((path = reader.readLine()) != null) {
                try {
                    Files.walkFileTree(Paths.get(path), new HashFileVisitor(writer));
                } catch (IOException | InvalidPathException e) {
                    // :NOTE: logs
                    System.err.println(e.getMessage());
                    writeResult(writer, path);
                }
            }
        } catch (IOException e) {
            System.err.println("Error with input or output file: " + e.getMessage());
        }
    }
}
