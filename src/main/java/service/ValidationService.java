package service;

import core.Alphabet;
import exception.CaesarException;

public class ValidationService {

    public void validateTextForEncoding(String text) throws CaesarException {

        if (text == null || text.trim().isEmpty()) {
            throw new CaesarException("Текст для кодирования не может быть пустым");
        }

        String upperText = text.toUpperCase();
        for (int i = 0; i < upperText.length(); i++) {
            char c = upperText.charAt(i);
            if (!Alphabet.TEXT_TO_CAESAR.containsKey(c)) {
                throw new CaesarException(String.format("Неподдерживаемый символ '%c' в позиции %d. Выберите \"3. Справка по алфавиту Морзе\" и проверьте содержимое словаря!", c, (i + 1)));
            }
        }
    }

    public void validateMorseCode(String morseCode)  throws CaesarException {

        if (morseCode == null || morseCode.trim().isEmpty()) {
            throw new CaesarException("Код Морзе не может быть пустым!");
        }


        String[] symbols = morseCode.trim().split(" ");

        for (int i = 0; i < symbols.length; i++) {
            String symbol = symbols[i];

            if (!symbol.equals("/") && !Alphabet.CAESAR_TO_TEXT.containsKey(symbol)) {
                throw new CaesarException(
                        String.format("Некорректный код Морзе '%s' в позиции %d", symbol, (i + 1)));
            }
        }
    }
}
