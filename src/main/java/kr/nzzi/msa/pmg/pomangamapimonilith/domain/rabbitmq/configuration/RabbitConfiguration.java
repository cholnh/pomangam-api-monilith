package kr.nzzi.msa.pmg.pomangamapimonilith.domain.rabbitmq.configuration;

import kr.nzzi.msa.pmg.pomangamapimonilith.domain.rabbitmq.event.RabbitMqEvent;
import kr.nzzi.msa.pmg.pomangamapimonilith.domain.rabbitmq.handler.RabbitMqExceptionHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.config.RetryInterceptorBuilder;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class RabbitConfiguration {

    private final String PRODUCT_EXCHANGE = "product.exchange";

    private final int MAX_TRY_COUNT = 3;
    private final int INITIAL_INTERVAL = 3000;
    private final int MULTIPLIER = 3;
    private final int MAX_INTERVAL = 10000;

    @Bean
    TopicExchange productMessageExchange() {
        return ExchangeBuilder
                .topicExchange(PRODUCT_EXCHANGE)
                .durable(true)
                .build();
    }

    @Bean
    TopicExchange productSaveExchange() {
        return ExchangeBuilder
                .topicExchange(PRODUCT_EXCHANGE)
                .durable(true)
                .build();
    }

    @Bean
    Binding productMessageBinding() {
        return BindingBuilder.bind(productMessageEvent()).to(productMessageExchange()).with(RabbitMqEvent.PRODUCT_MESSAGE_EVENT);
    }

    @Bean
    Binding productSaveBinding() {
        return BindingBuilder.bind(productSaveEvent()).to(productSaveExchange()).with(RabbitMqEvent.PRODUCT_SAVE_EVENT);
    }

    @Bean
    public Queue productMessageEvent() {
        return QueueBuilder
                .durable(RabbitMqEvent.PRODUCT_MESSAGE_EVENT)
                .build();
    }

    @Bean
    public Queue productSaveEvent() {
        return QueueBuilder
                .durable(RabbitMqEvent.PRODUCT_SAVE_EVENT)
                .build();
    }

    @Bean
    public RabbitTemplate amqpTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate();
        rabbitTemplate.setConnectionFactory(connectionFactory);
        rabbitTemplate.setMandatory(true);
        rabbitTemplate.setChannelTransacted(true);
        rabbitTemplate.setReplyTimeout(60000);
        rabbitTemplate.setMessageConverter(messageConverter());
        return rabbitTemplate;
    }

    @Bean
    public SimpleRabbitListenerContainerFactory rabbitListenerContainerFactory(
            ConnectionFactory connectionFactory) {
        final SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory);
        factory.setDefaultRequeueRejected(false);
        factory.setMessageConverter(messageConverter());
        factory.setChannelTransacted(true);
        factory.setAdviceChain(RetryInterceptorBuilder
                .stateless()
                .maxAttempts(MAX_TRY_COUNT)
                .recoverer(new RabbitMqExceptionHandler())
                .backOffOptions(INITIAL_INTERVAL, MULTIPLIER, MAX_INTERVAL)
                .build());
        return factory;
    }

    @Bean
    MessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }
}
