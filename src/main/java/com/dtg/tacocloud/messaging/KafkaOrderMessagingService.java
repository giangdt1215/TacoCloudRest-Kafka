package com.dtg.tacocloud.messaging;

import com.dtg.tacocloud.model.TacoOrder;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaOrderMessagingService implements OrderMessagingService{

    private KafkaTemplate<String, TacoOrder> kafkaTemplate;

    public KafkaOrderMessagingService(KafkaTemplate<String, TacoOrder> kafkaTemplate){
        this.kafkaTemplate = kafkaTemplate;
    }

    @Override
    public void sendOrder(TacoOrder order) {
        kafkaTemplate.send("tacocloud.orders.topic", order);
    }
}
