package com.example.otus.hlarchitect.social1.services.impl;

import com.example.otus.hlarchitect.social1.services.NewsCacheService;
import com.example.otus.hlarchitect.social1.services.RabbitQueueService;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.AbstractMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.RabbitListenerEndpointRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Log4j2
@Service
public class RabbitQueueServiceImpl implements RabbitQueueService {

    @Autowired
    private RabbitAdmin rabbitAdmin;
    @Autowired
    private RabbitListenerEndpointRegistry rabbitListenerEndpointRegistry;
    @Autowired
    private RabbitTemplate rabbitTemplate;
    @Autowired
    private NewsCacheService newsCacheService;

    @Override
    public void addMessage(String userName, String message){
        rabbitTemplate.convertAndSend("amq.topic", userName, message);
    }


    @RabbitListener(id = RECEIVER)
    public void receiver(Message message, String newsMessage) {
        final String newsPoster = message.getMessageProperties().getConsumerQueue();
        log.info("received Message from rabbit : " + message);
        newsCacheService.putNewsItemToAllFollowers(newsPoster, newsMessage);
    }


    @Override
    public void addNewQueue(String queueName) {
        Queue queue = new Queue(queueName, true, false, false);
        Binding binding = new Binding(
                queueName,
                Binding.DestinationType.QUEUE,
                "amq.topic",
                queueName,
                null
        );
        rabbitAdmin.declareQueue(queue);
        rabbitAdmin.declareBinding(binding);
        this.addQueueToListener(RECEIVER,queueName);
    }

    @Override
    public void addQueueToListener(String listenerId, String queueName) {
        log.info("adding queue : " + queueName + " to listener with id : " + listenerId);
        if (!checkQueueExistOnListener(listenerId,queueName)) {
            this.getMessageListenerContainerById(listenerId).addQueueNames(queueName);
            log.info("queue ");
        } else {
            log.info("given queue name : " + queueName + " not exist on given listener id : " + listenerId);
        }
    }

    @Override
    public void removeQueueFromListener(String listenerId, String queueName) {
        log.info("removing queue : " + queueName + " from listener : " + listenerId);
        if (checkQueueExistOnListener(listenerId,queueName)) {
            this.getMessageListenerContainerById(listenerId).removeQueueNames(queueName);
            log.info("deleting queue from rabbit management");
            this.rabbitAdmin.deleteQueue(queueName);
        } else {
            log.info("given queue name : " + queueName + " not exist on given listener id : " + listenerId);
        }
    }

    private Boolean checkQueueExistOnListener(String listenerId, String queueName) {
        try {
            log.info("checking queueName : " + queueName + " exist on listener id : " + listenerId);
            log.info("getting queueNames");
            String[] queueNames = this.getMessageListenerContainerById(listenerId).getQueueNames();
            if (queueNames != null) {
                log.info("checking " + queueName + " exist on active queues");
                for (String name : queueNames) {
                    log.info("name : " + name + " with checking name : " + queueName);
                    if (name.equals(queueName)) {
                        log.info("queue name exist on listener, returning true");
                        return Boolean.TRUE;
                    }
                }
                return Boolean.FALSE;
            } else {
                log.info("there is no queue exist on listener");
                return Boolean.FALSE;
            }
        } catch (Exception e) {
            log.error("Error on checking queue exist on listener");
            log.error("error message : " + ExceptionUtils.getMessage(e));
            log.error("trace : " + ExceptionUtils.getStackTrace(e));
            return Boolean.FALSE;
        }
    }

    private AbstractMessageListenerContainer getMessageListenerContainerById(String listenerId) {
        log.info("getting message listener container by id : " + listenerId);
        return ((AbstractMessageListenerContainer) this.rabbitListenerEndpointRegistry
                .getListenerContainer(listenerId)
        );
    }
}
