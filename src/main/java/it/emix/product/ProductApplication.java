 package it.emix.product;

import java.util.concurrent.ExecutionException;

import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import it.emix.product.adapter.ProductAdapterWithJava;
import it.emix.product.constants.KafkaConstants;
import it.emix.product.product.ProductCreator;

@SpringBootApplication
public class ProductApplication {

	public static void main(String[] args) throws Exception {
		ConfigurableApplicationContext ctx = SpringApplication.run(ProductApplication.class, args);
		// runProducer();
		ProductAdapterWithJava product = ctx.getBean(ProductAdapterWithJava.class);
		product.handler();
	}

	 static void runProducer() {
		 Producer<Long, String> producer = ProductCreator.createProducer();
		         for (int index = 0; index < KafkaConstants.MESSAGE_COUNT; index++) {
		             ProducerRecord<Long, String> record = new ProducerRecord<Long, String>(KafkaConstants.TOPIC_NAME,
		             "This is record " + index);
		             try {
		             RecordMetadata metadata = producer.send(record).get();
		                         System.out.println("Record sent with key " + index + " to partition " + metadata.partition()
		                         + " with offset " + metadata.offset());
		                  } 
		             catch (ExecutionException e) {
		                      System.out.println("Error in sending record");
		                      System.out.println(e);
		                   } 
		              catch (InterruptedException e) {
		                       System.out.println("Error in sending record");
		                       System.out.println(e);
		                   }
		          }
		     }
}
