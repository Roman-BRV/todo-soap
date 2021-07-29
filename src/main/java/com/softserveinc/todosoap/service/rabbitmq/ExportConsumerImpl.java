package com.softserveinc.todosoap.service.rabbitmq;

import com.softserveinc.todosoap.models.ExportTodosClaim;
import com.softserveinc.todosoap.repository.ExportTodosRepository;
import com.softserveinc.todosoap.repository.TodoTaskRepository;
import com.softserveinc.todosoap.models.TodoList;
import com.softserveinc.todosoap.util.ObjectToXMLConverter;
import com.softserveinc.todosoap.util.XMLFileSaver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.AmqpRejectAndDontRequeueException;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;

@Component
public class ExportConsumerImpl implements ExportConsumer {

	private static final Logger log = LoggerFactory.getLogger(ExportConsumerImpl.class);

	private final RabbitTemplate rabbitTemplate;

	private final TodoTaskRepository todoTaskRepository;
	private final ExportTodosRepository exportTodosRepository;

	@Value("${rabbitmq.export-todos.dead-letter-exchange-name}")
	private String deadLetterExchangeName;

	@Value("${rabbitmq.export-todos.dead-letter-routing-key}")
	private String deadLetterRoutingKey;

	@Autowired
	public ExportConsumerImpl(TodoTaskRepository todoRepository, ExportTodosRepository exportRepository, RabbitTemplate template) {
		this.todoTaskRepository = todoRepository;
		this.exportTodosRepository = exportRepository;
		this.rabbitTemplate = template;
	}

	@Override
	@RabbitListener(queues = "${rabbitmq.export-todos.queue-name}")
	public void processMessage(String claimId) {

		try {
			Optional<ExportTodosClaim> claimOptional = exportTodosRepository.findById(UUID.fromString(claimId));
			ExportTodosClaim claim = claimOptional.orElseThrow(NoSuchElementException::new);
			claim.setStatus("IN PROGRESS");
			exportTodosRepository.save(claim);

			TodoList todos = new TodoList();
			todos.setTodos(todoTaskRepository.findAll());
			String xmlTodos = ObjectToXMLConverter.objectToXMLConvert(todos);
			log.info("Todos export converted in XML ({} symbols).", xmlTodos.length());
			File xmlFile = XMLFileSaver.stringToXMLFile(xmlTodos);

			claim.setStatus("COMPLETED");
			claim.setResultPath(xmlFile.getAbsolutePath());
			exportTodosRepository.save(claim);
			log.info("Status - COMPLETED. Todos export saved in XML file - {}.", xmlFile.getAbsolutePath());
		} catch (Exception e) {
			rabbitTemplate.convertAndSend(deadLetterExchangeName, deadLetterRoutingKey, claimId);
			log.warn("Export Todos was failed in cause: {} Sent message - {}.", e.getMessage(), deadLetterExchangeName);
		}
	}

	@Override
	@RabbitListener(queues = "${rabbitmq.export-todos.dead-letter-queue-name}")
	public void processFailedMessage(String claimId){

		Optional<ExportTodosClaim> claimOptional = exportTodosRepository.findById(UUID.fromString(claimId));
		ExportTodosClaim claim = claimOptional
				.orElseThrow(() -> new AmqpRejectAndDontRequeueException("There aren't any ExportTodosClaim with ID - " + claimId));
		claim.setStatus("FAILED");
		exportTodosRepository.save(claim);
		log.error("Export Todos by claim - {} was failed. Changed status to FAILED. Stopped attempt to export.", claim);
		throw new AmqpRejectAndDontRequeueException("Stopped attempt by ExportTodosClaim with ID - " + claimId);
	}
}
