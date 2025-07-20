package com.kafka_03.springboot_kafka_integration.utill;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ResponseResult {

	private String code;
	private String message;
	private Object data;

}
