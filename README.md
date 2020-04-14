# FMS
## Finite State Machine

Problem:
Finite State Machine
Implement a generic multi-threaded event driven finite state machine.
A finite state machine is a machine that can be in exactly one of a finite number of states at any given time. The FSM can change from one state to another in response to some external inputs; the change from one state to another is called a transition. An FSM is defined by a list of its states, its initial state, and the conditions for each transition.
A finite-state machine (FSM) is event driven if the transition from one state to another is triggered by an event or a message.
A message can be sent by any registered message producer. Multiple subscribers should be able to register with the FSM. The FSM should change state on any valid message sent by a message producer. Any changes of state should be listened to by registered subscribers.
The FSM should be thread safe. Assume that all producers and subscribers of messages are on different threads.
A Subscriber should be able to listen on,
1. Successful transition to any one of its interested states
2. Successful transition to any state
The FSM should have the following capabilities:
a. Has one Start State and multiple end states.
b. Easy way to define states and transitions between states. c. Validate State machine constructed.
d. Error handling in case of invalid state transitions.

Sample Happy testcase: FSM in Created state and Activate Event Occurs, changes the current state to Activated.
Sample Error Test case: FSM in Activated State and Activate Event occurs, appropriate error handling to be supported.
