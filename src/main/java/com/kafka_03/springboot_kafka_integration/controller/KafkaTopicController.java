package com.kafka_03.springboot_kafka_integration.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kafka_03.springboot_kafka_integration.config.DTO.KafkaTopicDTO;
import com.kafka_03.springboot_kafka_integration.service.KafkaTopicService;
import com.kafka_03.springboot_kafka_integration.utill.ResponseResult;

@RestController
@RequestMapping("/v3/kafka-service")
public class KafkaTopicController {

	private static final Logger LOGGER = LoggerFactory.getLogger(KafkaTopicController.class);

	@Autowired
	private KafkaTopicService kafkaTopicService;

	@PostMapping("/create")
	public ResponseResult createTopic(@RequestBody KafkaTopicDTO kafkaTopicDTO) {
		return kafkaTopicService.createTopic(kafkaTopicDTO);
	}

	@GetMapping("/list")
	public ResponseResult listTopics() {
		return kafkaTopicService.getAllTopics();
	}

	@DeleteMapping("/delete/{topicName}")
	public ResponseResult deleteTopic(@PathVariable String topicName) {
		return kafkaTopicService.deleteTopic(topicName);
	}

}
