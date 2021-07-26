package com.softserveinc.todosoap.service.rabbitmq;

import com.softserveinc.todosoap.dao.ExportDBDAO;
import com.softserveinc.todosoap.dao.TodoTaskDAO;
import com.softserveinc.todosoap.models.Todo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ExportConsumerImpl implements ExportConsumer {

	private static final String FILEPATH = "C:\\\\";

	private final TodoTaskDAO todoTaskDAO;
	private final ExportDBDAO exportDBDAO;

	@Autowired
	public ExportConsumerImpl(TodoTaskDAO todoTaskDAO, ExportDBDAO exportDBDAO) {
		this.todoTaskDAO = todoTaskDAO;
		this.exportDBDAO = exportDBDAO;
	}

	@Override
	public void receiveMessage(String claimId){

		if(!exportDBDAO.changeStatus(claimId, "IN PROGRESS")){
			System.out.println("Status not changed!");//throw
		}
		System.out.println("Status changed!");

		List<Todo> todos = todoTaskDAO.getAllTodoTasks();
		//save in file
		try {
			Thread.sleep(30000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		if(!exportDBDAO.completeClaim(claimId, "COMPLETED", FILEPATH)){
			System.out.println("Status not changed!");//throw
		}
		System.out.println("Status changed! Filepath - " + FILEPATH);
		System.out.println(todos);
	}
}
