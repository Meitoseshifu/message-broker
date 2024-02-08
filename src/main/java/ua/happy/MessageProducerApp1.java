package ua.happy;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import lombok.SneakyThrows;

import java.nio.charset.StandardCharsets;

@SuppressWarnings(value = "all")
public class MessageProducerApp1 {

    /**
     * Спочатку запускаєм консюмера шоб той створив чергу і вичитував наше повідомлення
     */
    @SneakyThrows
    public static void main(String[] args) {
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("192.168.0.189");

        try(Connection connection = connectionFactory.newConnection();
            Channel channel = connection.createChannel()) {

            String queueNameAndRoutingKey = "test-queue";
            String message = "test queue stub message";

            channel.basicPublish("", queueNameAndRoutingKey, null, message.getBytes(StandardCharsets.UTF_8));
        }

    }
}
