package core;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;



public class Alphabet {

    public static final Map<Character, String> TEXT_TO_CAESAR;
    public static final Map<String, Character> CAESAR_TO_TEXT;

    static {
        Map<Character, String> textToCaesar = new HashMap<>();
        Map<String, Character> caesarToText = new HashMap<>();

        textToCaesar.put('А', ".-");
        textToCaesar.put('Б', "-...");
        textToCaesar.put('Я', ".-.-");


        for (Map.Entry<Character, String> entry : textToCaesar.entrySet()) {
             caesarToText.put(entry.getValue(), entry.getKey());
        }

        TEXT_TO_CAESAR = Collections.unmodifiableMap(textToCaesar);
        CAESAR_TO_TEXT = Collections.unmodifiableMap(caesarToText);
    }

    private Alphabet() {}
}
