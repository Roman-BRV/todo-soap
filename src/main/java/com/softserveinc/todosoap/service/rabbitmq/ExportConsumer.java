package com.softserveinc.todosoap.service.rabbitmq;

public interface ExportConsumer {

	void processMessage(String claimId);

	void processFailedMessage(String claimId);
}
