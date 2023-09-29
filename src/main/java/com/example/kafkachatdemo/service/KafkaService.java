package com.example.kafkachatdemo.service;

import com.example.kafkachatdemo.model.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.PartitionOffset;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaService {

    @Autowired
    private KafkaTemplate<String, Message> kafkaTemplate;

    @Value(value = "${message.topic.private.name}")
    private String privateTopicName;

    public void sendMessage(String key, String sender, String recipient, String body) {
        Message message = Message.builder().sender(sender).recipient(recipient).body(body).build();
        kafkaTemplate.send(privateTopicName, key, message);

    }

    @KafkaListener(topics = "${message.topic.private.name}", containerFactory = "mobileContainerFactory",  topicPartitions = @org.springframework.kafka.annotation.TopicPartition(topic = "${message.topic.private.name}", partitionOffsets = @PartitionOffset(partition = "0", initialOffset = "0")))
    public void listenerMobile(Message message) {
        System.out.println(message.getSender() + " -> " + message.getRecipient() + " : " + message.getBody());
    }

    @KafkaListener(topics = "firsttopiclol", containerFactory = "desktopContainerFactory")
    public void listenerDesktop(String message) {
        System.out.println(message);
    }


}
