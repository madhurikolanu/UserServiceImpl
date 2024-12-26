package com.apis.configs;

import com.apis.dtos.SendEmailRequestDto;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaTemplate;

@Configuration
public class KafkaProducerConfig {

    private KafkaTemplate<String, String> kafkaTemplate;

    KafkaProducerConfig(KafkaTemplate<String, String> kafkaTemplate){
        this.kafkaTemplate = kafkaTemplate;
    }
   public void publishEventToKafka(String topicName, String messageNeedsToBeSend){
        kafkaTemplate.send(topicName, messageNeedsToBeSend);
    }

}
