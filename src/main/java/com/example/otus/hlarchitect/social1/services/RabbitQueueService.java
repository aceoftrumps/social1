package com.example.otus.hlarchitect.social1.services;

public interface RabbitQueueService {

    String RECEIVER = "newsReceiver";

    void addMessage(String userName, String message);

    void addNewQueue(String queueName);

    void addQueueToListener(String listenerId,String queueName);

    void removeQueueFromListener(String listenerId,String queueName);

}
