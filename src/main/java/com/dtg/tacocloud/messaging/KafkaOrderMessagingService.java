package com.dtg.tacocloud.messaging;

import com.dtg.tacocloud.model.TacoOrder;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
public class RabbitOrderMessagingService implements OrderMessagingService{

    private RabbitTemplate rabbit;

    public RabbitOrderMessagingService(RabbitTemplate rabbit){
        this.rabbit = rabbit;
    }

    @Override
    public void sendOrder(TacoOrder order) {
        //Use send() method of RabbitTemplate
//        MessageConverter converter = rabbit.getMessageConverter();
//        MessageProperties props = new MessageProperties();
//        props.setHeader("X_ORDER_SOURCE", "WEB");
//        Message message = converter.toMessage(order, props);
//        rabbit.send("tacocloud.order", message);

        //Use convertAndSend() method of RabbitTemplate, use MessagePostProcessor for setting Header
        rabbit.convertAndSend("tacocloud.order.queue", order, message -> {
            MessageProperties props = message.getMessageProperties();
            props.setHeader("X_ORDER_SOURCE", "WEB");
            return message;
        });
    }
}
