package com.strr.api.constant;

public interface Constant {
    String KAFKA_TOPIC = "new-topic";
    String KAFKA_GROUP = "new-group";

    String RABBITMQ_EXCHANGE_DIRECT = "new-exchange-direct";
    String RABBITMQ_EXCHANGE_FANOUT = "new-exchange-fanout";
    String RABBITMQ_EXCHANGE_TOPIC = "new-exchange-topic";
    String RABBITMQ_EXCHANGE_HEADER = "new-exchange-header";

    String RABBITMQ_QUEUE_DIRECT = "new-queue-direct";
    String RABBITMQ_QUEUE_FANOUT_ONE = "new-queue-fanout-one";
    String RABBITMQ_QUEUE_FANOUT_TWO = "new-queue-fanout-two";
    String RABBITMQ_QUEUE_TOPIC_ONE = "new-queue-topic-one";
    String RABBITMQ_QUEUE_TOPIC_TWO = "new-queue-topic-two";
    String RABBITMQ_QUEUE_TOPIC_THREE = "new-queue-topic-three";
    String RABBITMQ_QUEUE_HEADER_TITLE = "new-queue-header-title";
    String RABBITMQ_QUEUE_HEADER_AUTHOR = "new-queue-header-author";
}
