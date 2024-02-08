package ua.happy;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;
import lombok.SneakyThrows;

import java.nio.charset.StandardCharsets;

public class MessageConsumer {

    @SneakyThrows
    public static void main(String[] args) {
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("93.175.204.87");

        try (Connection connection = connectionFactory.newConnection();
             Channel channel = connection.createChannel()) {

            String queueName = "paliychuk-queue";
            channel.queueDeclare(queueName, true, false, false, null);
            //String exchangeName = "bobocode-topic";
            String exchangeName = "message-fanout";
            channel.queueBind(queueName, exchangeName, "announcement.*");
            channel.queueBind(queueName, exchangeName, "*.petros");

            DeliverCallback deliverCallback = (consumerTag, message) -> {
                String messageStr = new String(message.getBody(), StandardCharsets.UTF_8);
                System.out.println(messageStr);
            };

            channel.basicConsume(queueName, true, deliverCallback, consumerTag -> {
            });
        }
    }
}
