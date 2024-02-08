package ua.happy;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import lombok.SneakyThrows;

import java.nio.charset.StandardCharsets;
import java.util.Scanner;

@SuppressWarnings(value = "all")
public class MessageProducerApp3 {
    private static Scanner in = new Scanner(System.in);
    /**
     * match to MessageConsumerApp3
     */
    @SneakyThrows
    public static void main(String[] args) {
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("192.168.0.189");

        try (Connection connection = connectionFactory.newConnection();
            Channel channel = connection.createChannel()) {

            String exchangeName = "bobocode-topic";
            channel.exchangeDeclare(exchangeName, BuiltinExchangeType.TOPIC, true);

            // message types: announcement, message
            // teams: petros, hoverla, blyznytsia, breskul, svydovets
            // Створити с-му в якій я зможу слати повідомлення, вказуючи його тип
            // (просте повідомлення чи оголошення) і вказуючи якій команді я його шлю
            // тобто routingKeyPattern: messageType.team - announcement.breskul,
            // message.hoverla (це для продюсера) а для консюмера можна створити свою
            // чергу і як routingKeyPattern: announcement.* (приходитимуть всі announcement
            // незалежно від того яка команда) або *.petros і отримувати все для петроса
            // не залежно від того чи це announcement чи message
            //String message = "Hello everyone. This is announcement!";
            //String message1 = "Hello everyone. This is message!";

            while (true) {
                String message = readMessage();
                channel.basicPublish(exchangeName, "announcement.petros", null,
                        message.getBytes(StandardCharsets.UTF_8));
            }
        }
    }

    private static String readMessage() {
        System.out.println(" > ");
        return in.nextLine();
    }
}
