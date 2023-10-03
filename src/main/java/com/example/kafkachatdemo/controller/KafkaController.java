package com.example.kafkachatdemo.controller;

import com.example.kafkachatdemo.model.Greeting;
import com.example.kafkachatdemo.model.Message;
import com.example.kafkachatdemo.service.KafkaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.HtmlUtils;

@RestController
@RequestMapping("/mobile")
public class KafkaController {

    @Autowired
    private KafkaService kafkaService;


    @MessageMapping("/hello")
    // @SendTo("/topic/greetings")
    public void greeting(Message message) throws Exception {
        kafkaService.sendMessage(message.getSender(), message.getRecipient(), message.getBody());
        // return new Greeting("Hello, " + HtmlUtils.htmlEscape(message.getSender()) + "!");
    }

    @PostMapping("/sendMessage")
    public void sendMessage(@RequestParam String key, @RequestParam String sender, @RequestParam String recipient, @RequestParam String body) {
        kafkaService.sendMessage(sender, recipient, body);
    }
}
