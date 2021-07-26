package com.softserveinc.todosoap.service.rabbitmq;

public interface ExportConsumer {

	void receiveMessage(String claimId);
}
