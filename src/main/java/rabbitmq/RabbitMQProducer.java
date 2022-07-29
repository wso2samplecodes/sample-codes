package rabbitmq;

/**
 * Created by ramilu on 2/1/16.
 */

import com.rabbitmq.client.Connection;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.*;

public class RabbitMQProducer {
    public static void main(String []args) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setUsername("guest");
        factory.setPassword("guest");
        factory.setVirtualHost("/");
        factory.setHost("127.0.0.1");
        factory.setPort(1883);
        Connection conn = factory.newConnection();
        Channel channel = conn.createChannel();
        String exchangeName = "myExchange";
        String routingKey = "testRoute";
        byte[] messageBodyBytes = "Hello, world!".getBytes();
        channel.basicPublish(exchangeName, routingKey
                ,MessageProperties.PERSISTENT_TEXT_PLAIN, messageBodyBytes) ;
        channel.close();
        conn.close();
    }
}
