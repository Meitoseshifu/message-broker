package ua.happy;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import lombok.SneakyThrows;

import java.nio.charset.StandardCharsets;

@SuppressWarnings(value = "all")
public class MessageProducerApp2 {
    /**
     * Create exchange bobocode-topic
     */
    @SneakyThrows
    public static void main(String[] args) {
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("192.168.0.189");

        try(Connection connection = connectionFactory.newConnection();
            Channel channel = connection.createChannel()) {

            String exchangeName = "bobocode-topic";
            channel.exchangeDeclare(exchangeName, BuiltinExchangeType.TOPIC, true); // durable - зберігає повідомлення на диск і вони мають появитися навіть після рестарту сервака

            //String queueName = "test-queue";
            //String message = "What drawbacks in Streams?";

            //channel.basicPublish("", queueName, null, message.getBytes(StandardCharsets.UTF_8));
        }

    }
}
