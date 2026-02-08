package exception;

public class CaesarException extends Exception{



    public CaesarException(String message) {

        super(message);
    }


    public CaesarException(String message, Throwable cause) {

        super(message, cause);
    }
}
