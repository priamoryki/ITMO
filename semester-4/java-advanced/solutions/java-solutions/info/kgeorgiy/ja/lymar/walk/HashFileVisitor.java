package info.kgeorgiy.ja.lymar.walk;

import java.io.BufferedInputStream;
import java.io.BufferedWriter;
import java.io.IOException;
import java.math.BigInteger;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;

/**
 * @author Pavel Lymar
 */
public class HashFileVisitor extends SimpleFileVisitor<Path> {
    private final int BUFFER_MAX_SIZE = 2048;
    private final BufferedWriter writer;

    public HashFileVisitor(BufferedWriter writer) {
        this.writer = writer;
    }

    @Override
    public FileVisitResult visitFile(Path path, BasicFileAttributes attributes) throws IOException {
        String hash = RecursiveWalk.DEFAULT_HASH;
        try (BufferedInputStream stream = new BufferedInputStream(Files.newInputStream(path))) {
            // :NOTE: digest as a field
            int bufferSize;
            byte[] buffer = new byte[BUFFER_MAX_SIZE];
            while ((bufferSize = stream.read(buffer)) != -1) {
                RecursiveWalk.result.update(buffer, 0, bufferSize);
            }
            hash = String.format("%040x", new BigInteger(1, RecursiveWalk.result.digest()));
        } catch (IOException ignored) {
        }
        // :NOTE: create a method
        RecursiveWalk.writeResult(writer, path.toString(), hash);
        return FileVisitResult.CONTINUE;
    }

    @Override
    public FileVisitResult visitFileFailed(Path path, IOException exc) throws IOException {
        RecursiveWalk.writeResult(writer, path.toString());
        return FileVisitResult.CONTINUE;
    }
}
