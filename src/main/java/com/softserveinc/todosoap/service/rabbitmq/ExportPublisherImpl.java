package com.softserveinc.todosoap.service.rabbitmq;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ExportPublisherImpl implements ExportPublisher {

	private static final Logger log = LoggerFactory.getLogger(ExportPublisherImpl.class);

	private final RabbitTemplate rabbitTemplate;

	@Value("${rabbitmq.export-todos.exchange-name}")
	private String exchangeName;

	@Value("${rabbitmq.export-todos.routing-key}")
	private String routingKey;

	@Autowired
	public ExportPublisherImpl(RabbitTemplate rabbitTemplate) {
		this.rabbitTemplate = rabbitTemplate;
	}

	@Override
	public void publishClaim(String claimId) {

		rabbitTemplate.convertAndSend(exchangeName, routingKey, claimId);
		log.info("Sent message: claimID - {} in exchange - {}.", claimId, exchangeName);
	}
}
