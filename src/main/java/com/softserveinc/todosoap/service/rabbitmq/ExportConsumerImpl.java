package com.softserveinc.todosoap.service.rabbitmq;

import com.softserveinc.todosoap.dao.ExportTodosDAO;
import com.softserveinc.todosoap.dao.TodoTaskDAO;
import com.softserveinc.todosoap.models.TodoList;
import com.softserveinc.todosoap.util.ObjectToXMLConverter;
import com.softserveinc.todosoap.util.XMLFileSaver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;

@Component
public class ExportConsumerImpl implements ExportConsumer {

	private static final Logger log = LoggerFactory.getLogger(ExportConsumerImpl.class);

	private final TodoTaskDAO todoTaskDAO;
	private final ExportTodosDAO exportTodosDAO;

	@Autowired
	public ExportConsumerImpl(TodoTaskDAO todoTaskDAO, ExportTodosDAO exportTodosDAO) {
		this.todoTaskDAO = todoTaskDAO;
		this.exportTodosDAO = exportTodosDAO;
	}

	@Override
	public void receiveMessage(String claimId) {

		if(!exportTodosDAO.changeStatus(claimId, "IN PROGRESS")){
			log.warn("Status not changed!");//throw
		}
		log.info("Status changed to " + "IN PROGRESS");

		TodoList todos = new TodoList();
		todos.setTodos(todoTaskDAO.getAllTodoTasks());
		String xmlTodos = ObjectToXMLConverter.objectToXMLConvert(todos);
		log.info("Todos export converted in XML ({} symbols).", xmlTodos.length());
		File xmlFile = XMLFileSaver.stringToXMLFile(xmlTodos);

		if(!exportTodosDAO.completeClaim(claimId, "COMPLETED", xmlFile.getAbsolutePath())){
			log.warn("Status not changed!");//throw
		}
		log.info("Status - COMPLETED. Todos export saved in XML file - {}.", xmlFile.getAbsolutePath());
	}
}
