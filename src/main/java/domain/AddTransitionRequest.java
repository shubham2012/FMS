package domain;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;

@Data
@AllArgsConstructor
public class AddTransitionRequest {

    private UUID id;

    private Transition transition;

}
