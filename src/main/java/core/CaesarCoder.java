package core;

import exception.CaesarException;
import model.ProcessingResult;
import service.ValidationService;

public class CaesarCoder {

    private final ValidationService validationService;


    public CaesarCoder() {
        this.validationService = new ValidationService();
    }

    public ProcessingResult encodeText(String text) throws CaesarException {

        validationService.validateTextForEncoding(text);


        String upperText = text.toUpperCase();
        StringBuilder result = new StringBuilder();

        for (int i = 0; i < upperText.length(); i++) {
            char currentChar = upperText.charAt(i);
            result.append(Alphabet.TEXT_TO_CAESAR.get(currentChar));

            if (i < upperText.length() - 1) {
                result.append(" ");
            }
        }

        String encoded = result.toString();

        return new ProcessingResult(true, "Текст успешно закодирован",
                getPreview(text), getPreview(encoded));
    }


    public ProcessingResult decodeText(String morseCode) throws CaesarException {

        validationService.validateMorseCode(morseCode);


        StringBuilder result = new StringBuilder();
        String[] symbols = morseCode.trim().split(" ");

        for (String symbol : symbols) {
            if (Alphabet.CAESAR_TO_TEXT.containsKey(symbol)) {
                result.append(Alphabet.CAESAR_TO_TEXT.get(symbol));
            } else if (symbol.equals("/")) {
                result.append(" ");
            }
        }

        String decoded = result.toString();
        return new ProcessingResult(true, "Код успешно декодирован",
                getPreview(morseCode), getPreview(decoded));
    }

    public String getPreview(String text) {

        if (text.length() <= 100) {
            return text;
        }
        return text.substring(0, 97) + "...";
    }
}
