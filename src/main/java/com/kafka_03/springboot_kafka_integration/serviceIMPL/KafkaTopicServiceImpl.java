package com.kafka_03.springboot_kafka_integration.serviceIMPL;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Set;

import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.CreateTopicsResult;
import org.apache.kafka.clients.admin.DeleteTopicsResult;
import org.apache.kafka.clients.admin.NewTopic;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kafka_03.springboot_kafka_integration.config.DTO.KafkaTopicDTO;
import com.kafka_03.springboot_kafka_integration.controller.KafkaTopicController;
import com.kafka_03.springboot_kafka_integration.service.KafkaTopicService;
import com.kafka_03.springboot_kafka_integration.utill.BasicValidationUtill;
import com.kafka_03.springboot_kafka_integration.utill.ResponseResult;

@Service
public class KafkaTopicServiceImpl implements KafkaTopicService {

	private static final Logger LOGGER = LoggerFactory.getLogger(KafkaTopicServiceImpl.class);

	@Autowired
	private AdminClient adminClient;

	@Override
	public ResponseResult createTopic(KafkaTopicDTO kafkaTopicDTO) {
		// Step 1: Run validation and preprocessing
		ResponseResult validationResult = BasicValidationUtill.createTopicValidations(kafkaTopicDTO);
		if (!"200".equals(validationResult.getCode())) {
			return validationResult;
		}

		try {
			// Step 2: Create topic after validation
			NewTopic newTopic = new NewTopic(kafkaTopicDTO.getTopicName(), kafkaTopicDTO.getPartitions(),
					kafkaTopicDTO.getReplicationFactor().shortValue());

			CreateTopicsResult result = adminClient.createTopics(Collections.singleton(newTopic));
			result.all().get();

			return ResponseResult.builder().code("200").message("Topic created successfully")
					.data(kafkaTopicDTO.getTopicName()).build();
		} catch (Exception e) {
			return ResponseResult.builder().code("500").message("Failed to create topic: " + e.getMessage()).data(new ArrayList<>())
					.build();
		}
	}

	@Override
	public ResponseResult getAllTopics() {
		try {
			Set<String> topics = adminClient.listTopics().names().get();
			return ResponseResult.builder().code("200").message("Topic list fetched successfully").data(topics).build();
		} catch (Exception e) {
			return ResponseResult.builder().code("500").message("Failed to fetch topic list: " + e.getMessage())
					.data(null).build();
		}
	}

	@Override
	public ResponseResult deleteTopic(String topicName) {
		ResponseResult validationResult = BasicValidationUtill.validTopicDeletion(adminClient, topicName);

		if (!"200".equals(validationResult.getCode())) {
			return validationResult;
		}

		try {
			DeleteTopicsResult result = adminClient.deleteTopics(Collections.singletonList(topicName));
			result.all().get();

			return ResponseResult.builder().code("200").message("Topic deleted successfully").data(topicName).build();
		} catch (Exception e) {
			return ResponseResult.builder().code("500").message("Failed to delete topic: " + e.getMessage()).data(new ArrayList<>())
					.build();
		}
	}

}
