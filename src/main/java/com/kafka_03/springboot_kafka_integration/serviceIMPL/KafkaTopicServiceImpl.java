package com.kafka_03.springboot_kafka_integration.serviceIMPL;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutionException;

import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.Config;
import org.apache.kafka.clients.admin.ConfigEntry;
import org.apache.kafka.clients.admin.CreateTopicsResult;
import org.apache.kafka.clients.admin.DeleteTopicsResult;
import org.apache.kafka.clients.admin.DescribeConfigsResult;
import org.apache.kafka.clients.admin.DescribeTopicsResult;
import org.apache.kafka.clients.admin.NewPartitions;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.admin.TopicDescription;
import org.apache.kafka.common.Node;
import org.apache.kafka.common.config.ConfigResource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.kafka_03.springboot_kafka_integration.config.DTO.KafkaTopicDTO;
import com.kafka_03.springboot_kafka_integration.config.DTO.PartitionUpdateDTO;
import com.kafka_03.springboot_kafka_integration.enums.ErrorResponseCode;
import com.kafka_03.springboot_kafka_integration.enums.SuccessResponseCode;
import com.kafka_03.springboot_kafka_integration.service.KafkaTopicService;
import com.kafka_03.springboot_kafka_integration.utill.BasicValidationUtill;
import com.kafka_03.springboot_kafka_integration.utill.ResponseResult;
import com.kafka_03.springboot_kafka_integration.utill.ResponseResultMessageUtill;

@Service
public class KafkaTopicServiceImpl implements KafkaTopicService {

	private static final Logger LOGGER = LoggerFactory.getLogger(KafkaTopicServiceImpl.class);

	@Autowired
	private AdminClient adminClient;

	@Override
	public ResponseResult createTopic(KafkaTopicDTO kafkaTopicDTO) {
		LO
		// Step 1: Run validation and preprocessing
		var validationResult = BasicValidationUtill.createTopicValidations(kafkaTopicDTO);
		// Only runs if validation failed
		if (validationResult != null) {
			return validationResult;
		}

		try {
			// Step 2: Create topic after validation
			NewTopic newTopic = new NewTopic(kafkaTopicDTO.getTopicName(), kafkaTopicDTO.getPartitions(),
					kafkaTopicDTO.getReplicationFactor().shortValue());

			CreateTopicsResult result = adminClient.createTopics(Collections.singleton(newTopic));
			result.all().get();

			return ResponseResult.builder().code(SuccessResponseCode.TOPIC_CREATED.getCode())
					.message(ResponseResultMessageUtill.getMessage(SuccessResponseCode.TOPIC_CREATED.getCode()))
					.data(kafkaTopicDTO.getTopicName()).build();

		} catch (Exception e) {
			return ResponseResult.builder().code(ErrorResponseCode.TOPIC_CREATION_FAILED.getCode())
					.message(ResponseResultMessageUtill.getMessage(ErrorResponseCode.TOPIC_CREATION_FAILED.getCode())
							+ ": " + e.getMessage())
					.data(new ArrayList<>()).build();
		}
	}

	@Override
	public ResponseResult getAllTopics() {
		LOGGER.info("getAllTopics method started !!!");
		try {
			Set<String> topics = adminClient.listTopics().names().get();
			LOGGER.info("topics fetched:{}", topics);
			return ResponseResult.builder().code(SuccessResponseCode.TOPIC_LIST_FETCHED.getCode())
					.message(ResponseResultMessageUtill.getMessage(SuccessResponseCode.TOPIC_LIST_FETCHED.getCode()))
					.data(topics).build();
		} catch (Exception e) {
			return ResponseResult.builder().code(ErrorResponseCode.TOPIC_LIST_FETCHING_ERROR.getCode())
					.message(
							ResponseResultMessageUtill.getMessage(ErrorResponseCode.TOPIC_LIST_FETCHING_ERROR.getCode())
									+ ": " + e.getMessage())
					.data(new ArrayList<>()).build();
		}
	}

	@Override
	public ResponseResult deleteTopic(String topicName) {

		var validationResult = BasicValidationUtill.validTopicDeletion(adminClient, topicName);
		if (validationResult != null) {
			return validationResult;
		}
		try {
			Set<String> existingTopics = adminClient.listTopics().names().get();
			if (!existingTopics.contains(topicName)) {
				return ResponseResult.builder().code(ErrorResponseCode.TOPIC_DOES_NOT_EXIST.getCode())
						.message(
								ResponseResultMessageUtill.getMessage(ErrorResponseCode.TOPIC_DOES_NOT_EXIST.getCode()))
						.data(Collections.emptyList()).build();
			}
			DeleteTopicsResult result = adminClient.deleteTopics(Collections.singletonList(topicName));
			result.all().get();

			return ResponseResult.builder().code(SuccessResponseCode.TOPIC_DELETION_SUCCESS.getCode())
					.message(
							ResponseResultMessageUtill.getMessage(SuccessResponseCode.TOPIC_DELETION_SUCCESS.getCode()))
					.data(new ArrayList<>()).build();

		} catch (Exception e) {
			return ResponseResult.builder().code(ErrorResponseCode.TOPIC_DELETION_FAILED.getCode())
					.message(ResponseResultMessageUtill.getMessage(ErrorResponseCode.TOPIC_DELETION_FAILED.getCode())
							+ ": " + e.getMessage())
					.data(new ArrayList<>()).build();
		}
	}

	@Override
	public ResponseResult getTopicDetails(String topicName) {
		try {
			// Describe topic
			DescribeTopicsResult describeTopicsResult = adminClient
					.describeTopics(Collections.singletonList(topicName));
			TopicDescription topicDescription = describeTopicsResult.values().get(topicName).get();

			// Get Configs
			ConfigResource configResource = new ConfigResource(ConfigResource.Type.TOPIC, topicName);
			DescribeConfigsResult describeConfigsResult = adminClient
					.describeConfigs(Collections.singleton(configResource));
			Config config = describeConfigsResult.all().get().get(configResource);

			// Extract config entries into a Map
			Map<String, String> configMap = new HashMap<>();
			for (ConfigEntry entry : config.entries()) {
				configMap.put(entry.name(), entry.value());
			}

			// Prepare data to return
			Map<String, Object> topicData = new HashMap<>();
			topicData.put("topicName", topicDescription.name());
			topicData.put("partitions", topicDescription.partitions().size());
			topicData.put("replicationFactor", topicDescription.partitions().get(0).replicas().size());
			topicData.put("config", configMap);

			return ResponseResult.builder().code("200").message("Topic details fetched successfully").data(topicData)
					.build();

		} catch (ExecutionException e) {
			return ResponseResult.builder().code("500")
					.message("Failed to fetch topic details: " + e.getCause().getMessage()).data(null).build();
		} catch (Exception e) {
			return ResponseResult.builder().code("500").message("Internal server error: " + e.getMessage()).data(null)
					.build();
		}
	}

	@Override
	public ResponseResult getPartitionDetails(String topicName) {
		try {
			DescribeTopicsResult describeTopicsResult = adminClient
					.describeTopics(Collections.singletonList(topicName));
			TopicDescription description = describeTopicsResult.values().get(topicName).get();

			List<Map<String, Object>> partitionList = new ArrayList<>();
			description.partitions().forEach(p -> {
				Map<String, Object> partitionInfo = new HashMap<>();
				partitionInfo.put("partitionId", p.partition());
				partitionInfo.put("leader", p.leader().id());
				partitionInfo.put("replicas", p.replicas());
				partitionInfo.put("isr", p.isr());

				partitionList.add(partitionInfo);
			});

			// Create response map with partition count and list
			Map<String, Object> responseMap = new HashMap<>();
			responseMap.put("partitionCount", partitionList.size());
			responseMap.put("partitions", partitionList);

			return ResponseResult.builder().code("200").message("Partition details fetched").data(responseMap).build();

		} catch (Exception e) {
			return ResponseResult.builder().code("500").message("Error fetching partition details: " + e.getMessage())
					.data(null).build();
		}
	}

	@Override
	public ResponseResult getBrokerDetails() {
		try {
			Collection<Node> nodes = adminClient.describeCluster().nodes().get();
			List<Map<String, Object>> brokerList = new ArrayList<>();

			for (Node node : nodes) {
				Map<String, Object> brokerInfo = new HashMap<>();
				brokerInfo.put("id", node.id());
				brokerInfo.put("host", node.host());
				brokerInfo.put("port", node.port());
				brokerList.add(brokerInfo);
			}

			return ResponseResult.builder().code("200").message("Broker details fetched successfully").data(brokerList)
					.build();

		} catch (Exception e) {
			return ResponseResult.builder().code("500").message("Error fetching broker details: " + e.getMessage())
					.data(null).build();
		}
	}

	@Override
	public ResponseResult increasePartitions(PartitionUpdateDTO dto) {
		try {
			Map<String, NewPartitions> newPartitionMap = new HashMap<>();
			newPartitionMap.put(dto.getTopicName(), NewPartitions.increaseTo(dto.getNewPartitionCount()));

			adminClient.createPartitions(newPartitionMap).all().get();

			return ResponseResult.builder().code("200").message("Partitions increased successfully").data(dto).build();

		} catch (ExecutionException e) {
			return ResponseResult.builder().code("500")
					.message("Failed to increase partitions: " + e.getCause().getMessage()).data(null).build();
		} catch (Exception e) {
			return ResponseResult.builder().code("500").message("Internal server error: " + e.getMessage()).data(null)
					.build();
		}
	}

}
