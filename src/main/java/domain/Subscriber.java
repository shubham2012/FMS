package domain;

import lombok.Data;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

@Data
public class Subscriber {

    private String name;
    private UUID id;
    private Set<String> eventList;

    public Subscriber(String name, Set<String> eventList) {
        this.id = UUID.randomUUID();
        this.name = name;
        if (Objects.isNull(eventList)) {
            this.eventList = new HashSet<>();
        } else {
            this.eventList = eventList;
        }
    }
}
