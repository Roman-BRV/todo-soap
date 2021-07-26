package com.softserveinc.todosoap.service.rabbitmq;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class ExportPublisherImpl implements ExportPublisher {

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
	}
}
