package exception;

import lombok.Data;

@Data
public class WrongStateException extends Exception {

    public WrongStateException(String message, String ... args) {
        super(message + " " + String.join(" ", args));
    }

}
