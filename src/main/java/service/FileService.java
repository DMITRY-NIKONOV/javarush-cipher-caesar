package service;

import exception.CaesarException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

public class FileService {


    public String readFile(String filePath) throws CaesarException {
        try {
            Path path = Path.of(filePath);
            if (!Files.exists(path)) {
                throw new CaesarException("Файл не существует " + filePath);
            }
            if (!Files.isReadable(path)) {
                throw new CaesarException("Нет прав на чтение файла " + filePath);
            }
            return Files.readString(path);

        } catch (IOException e) {
            throw new CaesarException("Ошибка чтения файла: " + e.getMessage(), e);
        }
    }


    public void writeFile(String content, String filePath) throws CaesarException {

        try {
            Path path = Path.of(filePath);
            Path parentDir = path.getParent();

            if (parentDir != null && !Files.exists(parentDir)) {
                Files.createDirectories(parentDir);
            }

            Files.writeString(path, content, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);

        } catch (IOException e) {
            throw new CaesarException("Ошибка записи файла: " + e.getMessage(), e);
        }
    }


    public boolean fileExists(String filePath) {

        return Files.exists(Path.of(filePath));
    }
}
