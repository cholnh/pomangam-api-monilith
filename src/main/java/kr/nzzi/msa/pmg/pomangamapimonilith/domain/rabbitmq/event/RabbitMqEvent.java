package kr.nzzi.msa.pmg.pomangamapimonilith.domain.rabbitmq.event;

public interface RabbitMqEvent {
    String PRODUCT_MESSAGE_EVENT = "product.message";
    String PRODUCT_SAVE_EVENT = "product.save";
}
