package service;

import dao.StateMachineDao;
import dao.SubscriberDao;
import domain.AddTransitionRequest;
import domain.CreateStateMachineRequest;
import domain.DoTransitionResponse;
import domain.Result;
import domain.StateMachine;
import domain.Subscriber;
import domain.Transition;
import exception.NoStateMachineException;
import exception.WrongStateException;

import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

public class StateMachineServiceImpl implements StateMachineService {

    private StateMachineDao stateMachineDao;

    private SubscriberServiceImpl subscriberService;

    private SubscriberDao subscriberDao;

    public StateMachineServiceImpl() {
        this.stateMachineDao = new StateMachineDao();
        this.subscriberService = new SubscriberServiceImpl();
        this.subscriberDao = new SubscriberDao();
    }

    public void addSubscriber(UUID stateMachineId, Subscriber subscriber) {
        subscriberService.addSubscriber(stateMachineId, subscriber, subscriberDao);
    }

    public StateMachine addStateMachine(CreateStateMachineRequest request) throws Exception {
        validate(request);
        StateMachine stateMachine = new StateMachine();
        for (Transition transition : request.getTransitions()) {
            stateMachine.addStates(transition);
        }
        stateMachineDao.addStateMachines(stateMachine);
        return stateMachine;
    }

    public StateMachine addTransitionToStateMachine(AddTransitionRequest addTransitionRequest) throws NoStateMachineException {
        StateMachine stateMachine = stateMachineDao.getStateMachine(addTransitionRequest.getId());
        if (Objects.isNull(stateMachine)){
            throw new NoStateMachineException(addTransitionRequest.getId());
        }
        stateMachine.addStates(addTransitionRequest.getTransition());
        return stateMachine;
    }

    public synchronized DoTransitionResponse doTransition(UUID id, String currentState, String event) throws NoStateMachineException, WrongStateException {
        Map<String, Map<String, String>> stateMachine = stateMachineDao.getStateMachine(id).getStates();
        if (Objects.isNull(stateMachine)) {
            throw new NoStateMachineException(id);
        }
        Map<String, String> fromStateTransitions = stateMachine.get(currentState);
        if (Objects.isNull(fromStateTransitions)) {
            throw new WrongStateException("State does not exists", currentState);
        }
        String destinationState = fromStateTransitions.get(event);
        if (Objects.isNull(destinationState)) {
            throw new WrongStateException("There is transition exists for action ", event);
        }
        subscriberService.sendNotificationForStateMachineSubscribers(id, event, subscriberDao);
        return new DoTransitionResponse(destinationState, Result.SUCCESS, "");
    }


    /**
     * All the validations goes here
     *
     * @param request
     * @return
     */
    private void validate(CreateStateMachineRequest request) throws Exception {
        if (Objects.isNull(request)){
            throw new Exception("Request is Empty");
        }
        Optional<Transition> optionalOfStart = request.getTransitions().stream()
                .filter(x -> x.getFromState().equals("Start"))
                .findAny();
        Optional<Transition> optionalOfComplete = request.getTransitions().stream()
                .filter(x -> x.getFromState().equals("Completed"))
                .findAny();
        if (!optionalOfStart.isPresent()) {
            throw new WrongStateException("Please add Start transition to the state machine");
        }
        if (!optionalOfComplete.isPresent()) {
            throw new WrongStateException("Please add Complete transition to the state machine");
        }
    }


}
