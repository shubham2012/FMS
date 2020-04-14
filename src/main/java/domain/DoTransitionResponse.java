package domain;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class DoTransitionResponse {
    private String destinationState;
    private Result result;
    private String message;
}
