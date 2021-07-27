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

	@Value("${rabbitmq.exportdb.exchange-name}")
	private String exchangeName;

	@Value("${rabbitmq.exportdb.routing-key}")
	private String exportdbRoutingKey;

	private final RabbitTemplate rabbitTemplate;

	@Autowired
	public ExportPublisherImpl(RabbitTemplate rabbitTemplate) {
		this.rabbitTemplate = rabbitTemplate;
	}

	@Override
	public void publishClaim(String claimId) {

		rabbitTemplate.convertAndSend(exchangeName, exportdbRoutingKey, claimId);
		log.info("Sent message: claimID - {} in exchange - {} with routing key - {}.", claimId, exchangeName, exportdbRoutingKey);
	}
}
