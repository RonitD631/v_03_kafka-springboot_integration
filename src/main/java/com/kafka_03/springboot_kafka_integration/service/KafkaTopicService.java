package com.kafka_03.springboot_kafka_integration.service;

import com.kafka_03.springboot_kafka_integration.config.DTO.KafkaTopicDTO;
import com.kafka_03.springboot_kafka_integration.config.DTO.PartitionUpdateDTO;
import com.kafka_03.springboot_kafka_integration.utill.ResponseResult;

public interface KafkaTopicService {

	ResponseResult createTopic(KafkaTopicDTO kafkaTopicDTO);

	ResponseResult getAllTopics();

	ResponseResult deleteTopic(String topicName);

	ResponseResult getTopicDetails(String topicName);

	ResponseResult getPartitionDetails(String topicName);

	ResponseResult getBrokerDetails();

	ResponseResult increasePartitions(PartitionUpdateDTO dto);

}
