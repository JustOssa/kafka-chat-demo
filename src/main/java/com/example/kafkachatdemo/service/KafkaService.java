package com.example.kafkachatdemo.service;

import com.example.kafkachatdemo.model.Greeting;
import com.example.kafkachatdemo.model.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.util.HtmlUtils;

@Service
public class KafkaService {

    @Autowired
    private KafkaTemplate<String, Message> kafkaTemplate;

    @Autowired
    SimpMessagingTemplate template;

    @Value(value = "${message.topic.private.name}")
    private String privateTopicName;

    public void sendMessage(String sender, String recipient, String body) {
        Message message = Message.builder().sender(sender).recipient(recipient).body(body).build();
        kafkaTemplate.send(privateTopicName, "5", message); // key (optional) is used to determine partition
    }

    /*    @KafkaListener(
                topics = "${message.topic.private.name}",
                containerFactory = "mobileContainerFactory",
                topicPartitions = @TopicPartition(
                        topic = "${message.topic.private.name}",
                        partitionOffsets = @PartitionOffset(partition = "0", initialOffset = "0")   // 5
                )
        )*/
    @KafkaListener(topicPattern = "private.chat.*", containerFactory = "mobileContainerFactory",  properties = "metadata.max.age.ms:1000")
    public void listenerMobile(Message message) {
        System.out.println("Received private message: " + message.getSender() + " -> " + message.getRecipient() + " : " + message.getBody());
    }

    @KafkaListener(topics = "private", containerFactory = "mobileContainerFactory")
    // @SendTo("/topic/greetings")
    public void listenerDesktop(Message message) {
        System.out.println("wslni");
        template.convertAndSend("/topic/greetings", message);
        //return new Greeting("Hello, " + HtmlUtils.htmlEscape(message.getSender()) + "!");
    }

}
