package com.justDice.spring.mssql.service;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.AmazonSQSClientBuilder;
import com.amazonaws.services.sqs.model.SendMessageRequest;
import com.justDice.spring.mssql.repository.CalendarEntryRepository;

@Service
public class SqsService {

   @Autowired
	CalendarEntryRepository calendarRepository;
	
    private final AmazonSQS sqs = AmazonSQSClientBuilder.defaultClient();
    private final String queueUrl = "your-queue-url";// I don't have AWS access so added this for reference

    @Scheduled(fixedRate = 300000) // every 5 minutes . Can be changed based on requirement
    public void sendActiveEntriesToQueue() {
    	calendarRepository.findByStopDateAfter(LocalDateTime.now()).forEach(entry -> {
            String message = entry.toString(); 
            SendMessageRequest sendMsgRequest = new SendMessageRequest()
                    .withQueueUrl(queueUrl)
                    .withMessageBody(message);
            sqs.sendMessage(sendMsgRequest);
        });
    }
}