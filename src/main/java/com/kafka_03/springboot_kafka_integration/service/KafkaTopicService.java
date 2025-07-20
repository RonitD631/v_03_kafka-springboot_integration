package com.kafka_03.springboot_kafka_integration.service;

import com.kafka_03.springboot_kafka_integration.config.DTO.KafkaTopicDTO;
import com.kafka_03.springboot_kafka_integration.utill.ResponseResult;

public interface KafkaTopicService {

	ResponseResult createTopic(KafkaTopicDTO kafkaTopicDTO);

	ResponseResult getAllTopics();

	ResponseResult deleteTopic(String topicName);

}
