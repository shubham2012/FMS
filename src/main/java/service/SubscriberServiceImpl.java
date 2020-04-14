package service;

import dao.SubscriberDao;
import domain.Subscriber;

import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class SubscriberServiceImpl {

    Logger logger = Logger.getLogger(SubscriberServiceImpl.class.getName());


    public void addSubscriber(UUID stateMachineId, Subscriber subscriber, SubscriberDao subscriberDao) {
        subscriberDao.addSubscriber(stateMachineId, subscriber);
    }

    public void sendNotificationForStateMachineSubscribers(UUID stateMachineId, String event, SubscriberDao subscriberDao) {
        Set<Subscriber> subscribersForStateMachine = subscriberDao.getSubscribersForStateMachine(stateMachineId);
        if (Objects.nonNull(subscribersForStateMachine)) {
            List<Subscriber> collect = subscribersForStateMachine.stream()
                    .filter(x -> x.getEventList().contains(event) || x.getEventList().contains("ALL"))
                    .collect(Collectors.toList());
            collect.forEach(x -> {
                // send notification by putting it into queue or any way you want to plan
                logger.info(String.format("Sending Notification to subscriber: %s for event: %s", x.getName(), event));
            });
        }
    }

}
