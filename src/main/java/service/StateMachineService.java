package service;

import domain.AddTransitionRequest;
import domain.CreateStateMachineRequest;
import domain.DoTransitionResponse;
import domain.StateMachine;
import domain.Subscriber;
import exception.NoStateMachineException;
import exception.WrongStateException;

import java.util.UUID;

public interface StateMachineService {

    void addSubscriber(UUID stateMachineId, Subscriber subscriber);

    StateMachine addStateMachine(CreateStateMachineRequest request) throws Exception;

    StateMachine addTransitionToStateMachine(AddTransitionRequest addTransitionRequest) throws NoStateMachineException;

    DoTransitionResponse doTransition(UUID id, String currentState, String event) throws NoStateMachineException, WrongStateException;

}
