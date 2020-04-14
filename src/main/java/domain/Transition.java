package domain;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Transition {
    private String fromState;
    private String event;
    private String toState;
}
