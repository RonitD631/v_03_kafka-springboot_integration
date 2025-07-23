package com.kafka_03.springboot_kafka_integration.enums;

public enum ErrorResponseCode {
	
	TOPIC_CREATION_FAILED("ERR5001"),
	TOPIC_NAME_REQUIRED("ERR5002"), 
	TOPIC_LIST_FETCHING_ERROR("ERR5003"),
	TOPIC_DELETION_FAILED("ERR5004"),
	TOPIC_DOES_NOT_EXIST("ERR5005");

	 private final String code;

	 ErrorResponseCode(String code) {
	        this.code = code;
	    }

	    public String getCode() {
	        return code;
	    }
}
