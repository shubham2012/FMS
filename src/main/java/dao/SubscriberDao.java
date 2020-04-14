package dao;

import domain.Subscriber;
import lombok.Data;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Data
public class SubscriberDao {

    private Map<UUID, Set<Subscriber>> stateMachineSubscribers;

    public SubscriberDao() {
        this.stateMachineSubscribers = new HashMap<>();
    }

    public void addSubscriber(UUID stateMachineId, Subscriber subscriber){
        if (stateMachineSubscribers.containsKey(stateMachineId)) {
            Set<Subscriber> subscribers = stateMachineSubscribers.get(stateMachineId);
            subscribers.add(subscriber);
            stateMachineSubscribers.put(stateMachineId, subscribers);
        } else {
            stateMachineSubscribers.put(stateMachineId, Stream.of(subscriber).collect(Collectors.toSet()));
        }
    }

    public Map<UUID, Set<Subscriber>> getAllStateMachineSubscribers() {
        return stateMachineSubscribers;
    }

    public Set<Subscriber> getSubscribersForStateMachine(UUID stateMachineId) {
        return stateMachineSubscribers.get(stateMachineId);
    }
}
