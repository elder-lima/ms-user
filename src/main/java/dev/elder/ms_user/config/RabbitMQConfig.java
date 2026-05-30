package dev.elder.ms_user.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    @Bean
    public Queue userCreatedQueue() {
        return new Queue("user.created.queue");
    }

    @Bean
    public Queue userLoggedQueue() {
        return new Queue("user.logged.queue");
    }

    @Bean
    public TopicExchange userExchange() {
        return new TopicExchange("user.exchange");
    }

    @Bean
    public Binding userCreatedBinding(
            Queue userCreatedQueue,
            TopicExchange userExchange
    ) {
        return BindingBuilder
                .bind(userCreatedQueue)
                .to(userExchange)
                .with("user.created");
    }

    @Bean
    public Binding userLoggedBinding(
            Queue userLoggedQueue,
            TopicExchange userExchange
    ) {
        return BindingBuilder
                .bind(userLoggedQueue)
                .to(userExchange)
                .with("user.logged");
    }

    @Bean
    public Jackson2JsonMessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }

}
