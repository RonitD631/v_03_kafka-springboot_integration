package com.kafka_03.springboot_kafka_integration.config.DTO;

import lombok.Data;

@Data
public class KafkaTopicDTO {

	
    private String topicName;

   
    private Integer partitions;

   
    private Short replicationFactor;
    
    
    
}
