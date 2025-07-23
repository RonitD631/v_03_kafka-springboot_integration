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
//		try {
//			if (topicName == null || topicName.trim().isEmpty()) {
//				return ResponseResult.builder().code("400").message("Topic name must not be empty")
//						.data(Collections.emptyList()).build();
//			}
//
//			Set<String> existingTopics = adminClient.listTopics().names().get();
//
//			if (!existingTopics.contains(topicName)) {
//				return ResponseResult.builder().code("404").message("Topic '" + topicName + "' does not exist")
//						.data(Collections.emptyList()).build();
//			}
//
//			return ResponseResult.builder().code("200").message("Validation Success").data(null).build();
//
//		} catch (Exception e) {
//			return ResponseResult.builder().code("500").message("Error validating topic existence: " + e.getMessage())
//					.data(null).build();
//		}
//	}
