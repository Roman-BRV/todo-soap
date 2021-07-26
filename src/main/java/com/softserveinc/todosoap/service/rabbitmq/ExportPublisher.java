package com.softserveinc.todosoap.service.rabbitmq;

public interface ExportPublisher {

	void publishClaim(String claimId);
}
