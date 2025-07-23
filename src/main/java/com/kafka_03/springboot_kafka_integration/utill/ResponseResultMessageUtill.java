package com.kafka_03.springboot_kafka_integration.utill;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.kafka.common.protocol.MessageUtil;

public class ResponseResultMessageUtill {

	 private static final Properties props = new Properties();

	    static {
	        try (InputStream input = MessageUtil.class.getClassLoader().getResourceAsStream("messages.properties")) {
	            if (input != null) {
	                props.load(input);
	            } else {
	                throw new RuntimeException("messages.properties file not found in classpath");
	            }
	        } catch (IOException e) {
	            throw new RuntimeException("Failed to load messages from messages.properties", e);
	        }
	    }

	    public static String getMessage(String code) {
	        return props.getProperty(code, "Message not found for code: " + code);
	    }
}
