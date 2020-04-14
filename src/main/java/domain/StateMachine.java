package domain;

import lombok.Data;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Data
public class StateMachine {

    private UUID id;

    private Map<String, Map<String, String>> states;

    public StateMachine() {
        this.id = UUID.randomUUID();
        states = new ConcurrentHashMap<>();
    }

    public StateMachine(UUID id, Map<String, Map<String, String>> states) {
        this.id = id;
        this.states = states;
    }

    public void addStates(Transition transition){
        Map<String, String> toStates = states.getOrDefault(transition.getFromState(), new HashMap<>());
        toStates.put(transition.getEvent(), transition.getToState());
        states.put(transition.getFromState(), toStates);
    }

}
