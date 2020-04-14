package domain;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class CreateStateMachineRequest {

    private List<Transition> transitions;

    public CreateStateMachineRequest() {
        this.transitions = new ArrayList<>();
    }

    public void addTransition(String from, String action, String to){
        Transition transition = new Transition(from, action, to);
        transitions.add(transition);
    }

}
