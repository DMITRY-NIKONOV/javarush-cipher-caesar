import core.Alphabet;
import core.CaesarCoder;
import exception.CaesarException;
import model.ProcessingResult;
import service.FileService;

import java.util.Map;
import java.util.Scanner;

public class CaesarApp {

    private final CaesarCoder caesarCoder;
    private final FileService fileService;
    private final Scanner scanner;

    public CaesarApp() {
        this.caesarCoder = new CaesarCoder();
        this.fileService = new FileService();
        this.scanner = new Scanner(System.in);
    }


    public static void main(String[] args) {
        CaesarApp app = new CaesarApp();
        app.run();
    }

    public void run() {

        printWelcomeMessage();

        while (true) {
            showMainMenu();
            String choice = scanner.nextLine();

            switch (choice) {
                case "1" -> processEncodeFile();
                case "2" -> processDecodeFile();
                case "3" -> showAlphabetInfo();
                case "0" -> System.out.println("\nДо свидания!");
                default -> System.out.println("Неверный выбор! Попробуйте снова.");
            }
        }
    }

    private void printWelcomeMessage() {
        System.out.println("ШИФР ЦЕЗАРЯ v.1.0");
        System.out.println("Профессиональный кодер-декодер");
        System.out.println("=".repeat(30));

    }

    private void showMainMenu() {
        System.out.println("ГЛАВНОЕ МЕНЮ: ");
        System.out.println("1. Закодировать файл (текст -> шифр)");
        System.out.println("2. Декодировать файл (шифр -> текст)");
        System.out.println("3. Справка по алфавиту шифра");
        System.out.println("0. Выход");
        System.out.print("Выберите действие: ");
    }

    private void processEncodeFile() {

        System.out.println("Кодирование файла:");
        try {
            String inputFile = getInputFilePath();
            String outputFile = getOutputFilePath();

            String context = fileService.readFile(inputFile);

            ProcessingResult result = caesarCoder.encodeText(context);

            fileService.writeFile(result.getOutputPreview(), outputFile);

            displaySuccessResult(result, inputFile, outputFile);
        } catch (CaesarException e) {
            displayError(e.getMessage());
        }
    }

    private void processDecodeFile() {

        System.out.println("\n ДЕКОДИРОВАНИЕ ФАЙЛА");

        try {
            String inputFile = getInputFilePath();
            String outputFile = getOutputFilePath();

            String content = fileService.readFile(inputFile);
            ProcessingResult result = caesarCoder.decodeText(content);
            fileService.writeFile(result.getOutputPreview(), outputFile);

            displaySuccessResult(result, inputFile, outputFile);

        } catch (CaesarException e) {
            displayError(e.getMessage());
        }
    }

    private String getInputFilePath() {
        System.out.print("Введите путь к исходному файлу (с шифром Цезаря) и его имя: ");
        return scanner.nextLine().trim();
    }

    private String getOutputFilePath() {
        System.out.print("Введите путь для результата и имя файла в который запишем результат: ");
        return scanner.nextLine().trim();
    }

    private void displaySuccessResult(ProcessingResult result, String inputFile, String outputFile) {
        System.out.println("\n " + result.getMessage());
        System.out.println("Исходный файл: " + inputFile);
        System.out.println("Результат: " + outputFile);

        System.out.println("\nПревью:");
        System.out.println("Вход: " + result.getInputPreview());
        System.out.println("Выход: " + result.getOutputPreview());
    }

    private void displayError(String message) {
        System.out.println("\n Ошибка: " + message + "\n");
    }

    private void showAlphabetInfo() {
        System.out.println("\n ШИФР ЦЕЗАРЯ");
        System.out.println("────────────────");

        System.out.println("\nРУССКИЕ БУКВЫ:");
        int count = 0;
        for (char c = 'А'; c <= 'Я'; c++) {
            String caesar = Alphabet.TEXT_TO_CAESAR.get(c);
            if (caesar != null) {
                System.out.printf("  %c → %s%n", c, caesar);
                count++;
            }
        }
        System.out.println("В словаре всего " + count + " РУССКИХ БУКВ");

        System.out.println("\nЦИФРЫ:");
        count = 0;
        for (char c = '0'; c <= '9'; c++) {
            String caesar = Alphabet.TEXT_TO_CAESAR.get(c);
            if (caesar != null) {
                System.out.printf("  %c → %s%n", c, caesar);
                count++;
            }
        }
        System.out.println("В словаре всего " + count + " ЦИФР");


        System.out.println("\nЗНАКИ ПРЕПИНАНИЯ И СИМВОЛЫ:");

        count = 0;
        for (Map.Entry<Character, String> entry : Alphabet.TEXT_TO_CAESAR.entrySet()) {
            char c = entry.getKey();
            if (!(c >= 'А' && c <= 'Я') && !(c >= '0' && c <= '9')) {
                String displayChar = c == ' ' ? "[ПРОБЕЛ]" : String.valueOf(c);
                System.out.printf("  %s → %s%n", displayChar, entry.getValue());
                count++;
            }
        }
        System.out.println("В словаре всего " + count + " ЗНАКОВ ПРЕПИНАНИЯ И СИМВОЛОВ");

        System.out.println("\nПримеры кодирования:");
        System.out.println("  'SOS' → ... --- ...");
        System.out.println("  'ПРИВЕТ' → .--. .-. .. .-- . -");
        System.out.println("\nПравила:");
        System.out.println("• Символы разделяются пробелами");
        System.out.println("• Слова разделяются знаком '/'");
        System.out.println("• Поддерживаются русские буквы, цифры, базовые знаки препинания");
    }
}
