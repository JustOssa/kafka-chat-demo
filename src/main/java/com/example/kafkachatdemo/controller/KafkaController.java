package com.example.kafkachatdemo.controller;

import com.example.kafkachatdemo.service.KafkaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/mobile")
public class KafkaController {

    @Autowired
    private KafkaService kafkaService;

    @PostMapping("/sendMessage")
    public void sendMessage(@RequestParam String key, @RequestParam String sender, @RequestParam String recipient, @RequestParam String body) {
        kafkaService.sendMessage(key, sender, recipient, body);
    }
}
