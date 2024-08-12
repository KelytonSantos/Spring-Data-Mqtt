package com.mqtt.mqtt;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class Subscriber {

    private static final String BROKER = "tcp://localhost:1883";
    private static final String CLIENT = "pc";
    private static final String TOPIC = "java/exemplo";
    private MqttClient client;

    @Autowired
    private MessageRepository repo;

    public Subscriber() {
    }

    public void connect(String usuario, String senha) {
        try {
            client = new MqttClient(BROKER, CLIENT, new MemoryPersistence());
            MqttConnectOptions connectionOptions = new MqttConnectOptions();
            connectionOptions.setCleanSession(true);
            connectionOptions.setUserName(usuario);
            connectionOptions.setPassword(senha.toCharArray());
            connectionOptions.setCleanSession(true);

            System.out.println("Connecting....");
            client.connect(connectionOptions);

            if (client.isConnected()) {
                System.out.println("Connected!");
            } else {
                System.out.println("Caiu no else");
            }

        } catch (MqttException e) {
            e.printStackTrace();
        }
    }

    public void start() {
        try {
            if (client != null && client.isConnected()) {
                client.subscribe(TOPIC, (topic, message) -> {
                    // Configure a mensagem antes de salvar
                    Message msg = new Message();
                    msg.setTopic(topic);
                    msg.setPayload(new String(message.getPayload()));
                    msg.setQos(message.getQos());

                    System.out.println("Received message: " + new String(msg.getPayload()));
                    System.out.println("Topico setado: " + msg.getTopic());
                    System.out.println("Mensagem: " + new String(msg.getPayload()));
                    System.out.println("Quality: " + message.getQos());

                    // Salve a mensagem no repositório
                    try {
                        repo.save(msg);
                        System.out.println("Message saved successfully.");
                    } catch (Exception e) {
                        System.err.println("Error saving message to repository: " + e.getMessage());
                        e.printStackTrace();
                    }
                });
            } else {
                System.out.println("Client is not connected.");
            }
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }

    public void print() {
        try {
            client.subscribe(TOPIC, (topic, message) -> {
                System.out.println(new String(message.getPayload()));
            });
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }
}
