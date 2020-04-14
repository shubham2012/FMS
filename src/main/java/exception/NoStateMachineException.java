package exception;

import java.util.UUID;

public class NoStateMachineException extends Exception {

    public NoStateMachineException(UUID id) {
        super(String.format("State machine not found with id: ", id));
    }
}
