package com.softserveinc.todosoap.service.rabbitmq;

import com.softserveinc.todosoap.models.ExportTodosClaim;
import com.softserveinc.todosoap.repository.ExportTodosRepository;
import com.softserveinc.todosoap.repository.TodoTaskRepository;
import com.softserveinc.todosoap.models.TodoList;
import com.softserveinc.todosoap.util.ObjectToXMLConverter;
import com.softserveinc.todosoap.util.XMLFileSaver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.Optional;
import java.util.UUID;

@Component
public class ExportConsumerImpl implements ExportConsumer {

	private static final Logger log = LoggerFactory.getLogger(ExportConsumerImpl.class);

	private final TodoTaskRepository todoTaskRepository;
	private final ExportTodosRepository exportTodosRepository;

	@Autowired
	public ExportConsumerImpl(TodoTaskRepository todoTaskRepository, ExportTodosRepository exportTodosRepository) {
		this.todoTaskRepository = todoTaskRepository;
		this.exportTodosRepository = exportTodosRepository;
	}

	@Override
	public void receiveMessage(String claimId) {

		Optional<ExportTodosClaim> claimOptional = exportTodosRepository.findById(UUID.fromString(claimId));
		ExportTodosClaim claim = claimOptional.orElseThrow(RuntimeException::new);
		claim.setStatus("IN PROGRESS");
		exportTodosRepository.save(claim);
		log.info("Status changed to " + "IN PROGRESS");

		TodoList todos = new TodoList();
		todos.setTodos(todoTaskRepository.findAll());
		String xmlTodos = ObjectToXMLConverter.objectToXMLConvert(todos);
		log.info("Todos export converted in XML ({} symbols).", xmlTodos.length());
		File xmlFile = XMLFileSaver.stringToXMLFile(xmlTodos);

		claim.setStatus("COMPLETED");
		claim.setResultPath(xmlFile.getAbsolutePath());
		exportTodosRepository.save(claim);
		log.info("Status - COMPLETED. Todos export saved in XML file - {}.", xmlFile.getAbsolutePath());
	}
}
