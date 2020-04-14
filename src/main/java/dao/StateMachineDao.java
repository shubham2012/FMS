package dao;

import domain.StateMachine;
import lombok.Data;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Data
public class StateMachineDao {

    private Map<UUID, Map<String, Map<String, String>>> stateMachines;

    public StateMachineDao() {
        this.stateMachines = new ConcurrentHashMap<>();
    }

    public void addStateMachines(UUID id, Map<String, Map<String, String>> stateMachine) {
        this.stateMachines.put(id, stateMachine);
    }

    public void addStateMachines(StateMachine stateMachine) {
        this.stateMachines.put(stateMachine.getId(), stateMachine.getStates());
    }

    public Map<UUID, Map<String, Map<String, String>>> getAllStateMachines() {
        return stateMachines;
    }

    public StateMachine getStateMachine(UUID stateMachineId){
        if (!stateMachines.containsKey(stateMachineId))
            return null;
        return new StateMachine(stateMachineId, stateMachines.get(stateMachineId));
    }
}
