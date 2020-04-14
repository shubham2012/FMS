package service;

import domain.AddTransitionRequest;
import domain.CreateStateMachineRequest;
import domain.DoTransitionResponse;
import domain.StateMachine;
import domain.Subscriber;
import domain.Transition;
import exception.NoStateMachineException;
import exception.WrongStateException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

class StateMachineServiceImplTest {

    StateMachine stateMachine;
    private StateMachineService service = new StateMachineServiceImpl();
    private CreateStateMachineRequest request;

    private

    @BeforeEach
    void setUp() throws Exception {
        request = new CreateStateMachineRequest();
        request.addTransition("Start", "FulFillOrder", "Created");
        request.addTransition("Created", "Activate", "Activated");
        request.addTransition("Created", "Cancel", "Cancelled");
        request.addTransition("Activated", "MakeProgress", "InProgress");
        request.addTransition("Activated", "Cancel", "Cancelled");
        request.addTransition("InProgress", "Cancel", "Cancelled");
        request.addTransition("InProgress", "Complete", "Completed");
        request.addTransition("Completed", null, null);
        stateMachine = service.addStateMachine(request);
        Subscriber subscriber1 = new Subscriber("Ramesh", Stream.of("ALL").collect(Collectors.toSet()));
        Subscriber subscriber2 = new Subscriber("Suresh", Stream.of("Activate").collect(Collectors.toSet()));
        Subscriber subscriber3 = new Subscriber("Nilesh", Stream.of("Cancel").collect(Collectors.toSet()));
        Subscriber subscriber4 = new Subscriber("Kamlesh", Stream.of("Complete").collect(Collectors.toSet()));
        service.addSubscriber(stateMachine.getId(), subscriber1);
        service.addSubscriber(stateMachine.getId(), subscriber2);
        service.addSubscriber(stateMachine.getId(), subscriber3);
        service.addSubscriber(stateMachine.getId(), subscriber4);
    }

    @Test
    void test_add_state_machine() throws Exception {
        CreateStateMachineRequest request = new CreateStateMachineRequest();
        request.addTransition("Start", "FulFillOrder", "Created");
        request.addTransition("Created", "Activate", "Activated");
        request.addTransition("Created", "Cancel", "Cancelled");
        request.addTransition("Activated", "MakeProgress", "InProgress");
        request.addTransition("Activated", "Cancel", "Cancelled");
        request.addTransition("InProgress", "Complete", "Completed");
        request.addTransition("Completed", null, null);
        StateMachine stateMachine = service.addStateMachine(request);
        Assertions.assertNotNull(stateMachine.getId());
        Assertions.assertEquals("Cancelled", stateMachine.getStates().get("Created").get("Cancel"));
    }

    @Test
    void test_add_state_machine_with_no_Start_state() {
        CreateStateMachineRequest request = new CreateStateMachineRequest();
        request.addTransition("Start", "FulFillOrder", "Created");
        request.addTransition("Created", "Activate", "Activated");
        request.addTransition("Created", "Cancel", "Cancelled");
        Assertions.assertThrows(WrongStateException.class, () ->
                service.addStateMachine(request));
    }

    @Test
    void test_add_state_machine_with_no_Complete_state() {
        CreateStateMachineRequest request = new CreateStateMachineRequest();
        request.addTransition("Created", "Activate", "Activated");
        request.addTransition("Created", "Cancel", "Cancelled");
        request.addTransition("Completed", null, null);
        Assertions.assertThrows(WrongStateException.class, () ->
                service.addStateMachine(request));
    }

    @Test
    void test_add_transition_to_state_machine() throws NoStateMachineException {
        Transition transition = new Transition("InProgress", "Cancel", "Cancelled");
        AddTransitionRequest addTransition = new AddTransitionRequest(stateMachine.getId(), transition);
        StateMachine stateMachine = service.addTransitionToStateMachine(addTransition);
        Assertions.assertEquals("Cancelled", stateMachine.getStates().get("InProgress").get("Cancel"));
    }

    @Test
    void test_add_transition_to_state_machine_with_wrong_state_machine() {
        Transition transition = new Transition("InProgress", "Cancel", "Cancelled");
        AddTransitionRequest addTransition = new AddTransitionRequest(UUID.randomUUID(), transition);
        Assertions.assertThrows(NoStateMachineException.class, () ->
                service.addTransitionToStateMachine(addTransition));
    }

    @Test
    void test_do_transition() throws Exception {
        System.out.println(stateMachine);
        DoTransitionResponse doTransitionResponse = service.doTransition(stateMachine.getId(), "Created", "Cancel");
        Assertions.assertEquals("Cancelled", doTransitionResponse.getDestinationState());
        DoTransitionResponse doTransitionResponse1 = service.doTransition(stateMachine.getId(), "InProgress", "Complete");
        Assertions.assertEquals("Completed", doTransitionResponse1.getDestinationState());
    }

    @Test
    void test_do_transition_with_wrong_transition() {
        System.out.println(stateMachine);
        Assertions.assertThrows(WrongStateException.class, () ->
                service.doTransition(stateMachine.getId(), "Created", "Complete"));
    }

}