package com.justDice.spring.mssql.service;

import java.util.List;

import org.springframework.scheduling.annotation.Async;

import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.AmazonSQSClientBuilder;
import com.amazonaws.services.sqs.model.Message;
import com.amazonaws.services.sqs.model.ReceiveMessageRequest;

public class SqsConsumerService {

    private final AmazonSQS sqs = AmazonSQSClientBuilder.defaultClient();
    private final String queueUrl = "your-queue-url";// I don't have AWS access so added this for reference

    @Async
    public void readMessagesFromQueue() {
        while (true) {
            ReceiveMessageRequest receiveMessageRequest = new ReceiveMessageRequest(queueUrl);
            List<Message> messages = sqs.receiveMessage(receiveMessageRequest).getMessages();

            for (Message message : messages) {
                System.out.println("Received message: " + message.getBody());
                sqs.deleteMessage(queueUrl, message.getReceiptHandle());
            }

            try {
                Thread.sleep(5000); 
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                throw new RuntimeException(e);
            }
        }
    }
}