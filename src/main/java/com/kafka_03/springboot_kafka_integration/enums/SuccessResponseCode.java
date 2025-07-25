package com.kafka_03.springboot_kafka_integration.enums;

public enum SuccessResponseCode {

	 TOPIC_LIST_FETCHED("SUC3003"),
	 TOPIC_CREATED("SUC2001"),
	 TOPIC_DELETION_SUCCESS("SUC2002"),
	 PARTION_DETAILS_SUCCESS("SUC2003"),
	 TOPIC_DETAILS_SUCCESS("SUC2004"),
	 BROKER_DETAILS_SUCCESS("SUC2005"),
	 PARTITION_INCREASE_SUCCESS("SUC2006");
	
	 private final String code;

	 SuccessResponseCode(String code) {
	        this.code = code;
	    }

	    public String getCode() {
	        return code;
	    }
}
