package dev.elder.ms_user.producer;

import dev.elder.ms_user.producer.dto.UserCreatedEvent;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
public class UserCreatedProducer {

    private final RabbitTemplate rabbitTemplate;

    public UserCreatedProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void publish(String routingKey, UserCreatedEvent event) {
        rabbitTemplate.convertAndSend(
                "user.exchange",
                routingKey,
                event
        );
    }

}
