package it.emix.product.adapter;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.producer.ProducerConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.expression.common.LiteralExpression;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.core.MessageHandler;
import org.springframework.integration.kafka.outbound.KafkaProducerMessageHandler;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

import it.emix.product.constants.KafkaConstants;

public class ProductAdapterWithJava {

	@Bean
	@ServiceActivator(inputChannel = "toKafka")
	public MessageHandler handler() throws Exception {
	    KafkaProducerMessageHandler<String, String> handler =
	            new KafkaProducerMessageHandler<>(kafkaTemplate());
	    handler.setTopicExpression(new LiteralExpression("someTopic"));
	    handler.setMessageKeyExpression(new LiteralExpression("someKey"));
	    return handler;
	}

	@Bean
	public KafkaTemplate<String, String> kafkaTemplate() {
	    return new KafkaTemplate<>(producerFactory());
	}

	@Bean
	public ProducerFactory<String, String> producerFactory() {
	    Map<String, Object> props = new HashMap<>();
	    props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, KafkaConstants.KAFKA_BROKER);
	    // set more properties
	    return new DefaultKafkaProducerFactory<>(props);
	}
}
