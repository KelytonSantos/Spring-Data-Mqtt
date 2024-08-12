package com.mqtt.mqtt;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class MqttApplication {

	public static void main(String[] args) {
		ApplicationContext context = SpringApplication.run(MqttApplication.class, args);

		Subscriber subscriber = context.getBean(Subscriber.class);
		System.out.println("func connect");
		subscriber.connect("saint", "200274");

		System.out.println("Start");
		subscriber.start();
	}
}

/*
 * @SpringBootApplication
 * public class MqttApplication {
 * 
 * public static void main(String[] args) {
 * SpringApplication.run(MqttApplication.class, args);
 * 
 * Subscriber subscriber = new Subscriber();
 * System.out.println("func connect");
 * subscriber.connect("saint", "200274");
 * 
 * System.out.println("Start");
 * subscriber.start();
 * }
 * }
 */