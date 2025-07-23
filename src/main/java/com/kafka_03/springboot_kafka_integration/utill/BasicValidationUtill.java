package com.kafka_03.springboot_kafka_integration.utill;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Set;

import org.apache.kafka.clients.admin.AdminClient;

import com.kafka_03.springboot_kafka_integration.config.DTO.KafkaTopicDTO;
import com.kafka_03.springboot_kafka_integration.enums.ErrorResponseCode;

public class BasicValidationUtill {

	public static ResponseResult createTopicValidations(KafkaTopicDTO kafkaTopicDTO) {
		if (kafkaTopicDTO.getTopicName() == null || kafkaTopicDTO.getTopicName().trim().isEmpty()) {
			return ResponseResult.builder().code(ErrorResponseCode.TOPIC_NAME_REQUIRED.getCode())
					.message(ResponseResultMessageUtill.getMessage(ErrorResponseCode.TOPIC_NAME_REQUIRED.getCode()))
					.data(new ArrayList<>()).build();
		}

		if (kafkaTopicDTO.getPartitions() == null) {
			kafkaTopicDTO.setPartitions(1);
		}

		if (kafkaTopicDTO.getReplicationFactor() == null) {
			kafkaTopicDTO.setReplicationFactor((short) 1);
		}

		// Return null for success (no error response needed)
		return null;
	}

	public static ResponseResult validTopicDeletion(AdminClient adminClient, String topicName) {

		if (topicName == null || topicName.trim().isEmpty()) {
			return ResponseResult.builder().code(ErrorResponseCode.TOPIC_NAME_REQUIRED.getCode())
					.message(ResponseResultMessageUtill.getMessage(ErrorResponseCode.TOPIC_NAME_REQUIRED.getCode()))
					.data(new ArrayList<>()).build();
		}

		return null;
	}

}
