package kr.nzzi.msa.pmg.pomangamapimonilith.domain.rabbitmq.test.listener;

import kr.nzzi.msa.pmg.pomangamapimonilith.domain.rabbitmq.event.RabbitMqEvent;
import kr.nzzi.msa.pmg.pomangamapimonilith.domain.rabbitmq.test.ProductEvent;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@AllArgsConstructor
public class ProductListener {

    @RabbitListener(queues = RabbitMqEvent.PRODUCT_MESSAGE_EVENT)
    public void productMessageHandler(final Message message) {
        log.info("productMessageHandler");
        log.info(message.toString());
    }

    @RabbitListener(queues = RabbitMqEvent.PRODUCT_SAVE_EVENT)
    public void productSaveHandler(final ProductEvent event) {
        log.info("productSaveHandler");
        log.info(event.toString());
    }
}
