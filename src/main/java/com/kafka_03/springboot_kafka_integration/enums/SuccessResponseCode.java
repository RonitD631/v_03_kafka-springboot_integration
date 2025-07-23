package com.kafka_03.springboot_kafka_integration.enums;

public enum SuccessResponseCode {

	 TOPIC_LIST_FETCHED("SUC3003"),
	 TOPIC_CREATED("SUC2001"),
	 TOPIC_DELETION_SUCCESS("SUC2002");
	
	 private final String code;

	 SuccessResponseCode(String code) {
	        this.code = code;
	    }

	    public String getCode() {
	        return code;
	    }
}
