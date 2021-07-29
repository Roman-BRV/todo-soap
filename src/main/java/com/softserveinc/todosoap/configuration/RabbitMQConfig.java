package com.softserveinc.todosoap.configuration;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

@Configuration
public class RabbitMQConfig {

	private final Environment env;

	@Autowired
	public RabbitMQConfig(Environment env) {
		this.env = env;
	}

	@Bean
	Queue queue(){
		return new Queue(env.getProperty("rabbitmq.export-todos.queue-name"), false);
	}

	@Bean
	TopicExchange exchange(){
		return new TopicExchange(env.getProperty("rabbitmq.export-todos.exchange-name"));
	}

	@Bean
	Binding binding(Queue queue, TopicExchange exchange){
		return BindingBuilder.bind(queue).to(exchange).with(env.getProperty("rabbitmq.export-todos.routing-key"));
	}

	@Bean
	Queue deadLetterQueue(){
		return new Queue(env.getProperty("rabbitmq.export-todos.dead-letter-queue-name"), false);
	}

	@Bean
	DirectExchange deadLetterExchange(){
		return new DirectExchange(env.getProperty("rabbitmq.export-todos.dead-letter-exchange-name"));
	}

	@Bean
	Binding deadLetterBinding(Queue deadLetterQueue, DirectExchange deadLetterExchange){
		return BindingBuilder.bind(deadLetterQueue).to(deadLetterExchange).with(env.getProperty("rabbitmq.export-todos.dead-letter-routing-key"));
	}

	@Bean
	SimpleMessageListenerContainer container(ConnectionFactory connectionFactory){
		SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
		container.setConnectionFactory(connectionFactory);
		container.setQueueNames(env.getProperty("rabbitmq.export-todos.queue-name"), env.getProperty("rabbitmq.export-todos.dead-letter-queue-name"));
		return container;
	}
}
