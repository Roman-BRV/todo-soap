package com.softserveinc.todosoap.service;

import com.datastax.driver.core.utils.UUIDs;
import com.softserveinc.todosoap.dao.TodoTaskDAO;
import com.softserveinc.todosoap.models.Todo;
import com.softservinc.todosoap.TaskStatus;
import com.softservinc.todosoap.TodoTask;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class TodoTaskServiceImpl implements TodoTaskService {

	private TodoTaskDAO todoTaskDAO;

	@Autowired
	public TodoTaskServiceImpl(TodoTaskDAO todoTaskDAO){
		this.todoTaskDAO = todoTaskDAO;
	}

	private Todo mapToModel(TodoTask todoTask){

		Todo model = new Todo();
		model.setId(UUIDs.timeBased());
		model.setUserEmail(todoTask.getUserEmail());
		model.setTaskText(todoTask.getTaskText());
		model.setTaskStatus(todoTask.getTaskStatus());
		model.setTags(new ArrayList<>(List.of("tag")));
		model.setCreated(LocalDate.now());

		return model;
	}

	private TodoTask mapToResponse(Todo todo){

		TodoTask response = new TodoTask();
		if(todo.getId() == null){
			response.setId("empty");
		}else {
			response.setId(todo.getId().toString());
		}
		response.setUserEmail(todo.getUserEmail());
		response.setTaskText(todo.getTaskText());
		response.setTaskStatus(todo.getTaskStatus());

		return response;
	}
	public TodoTask add(String userEmail, String taskText){

		TodoTask todoTask = new TodoTask();
		todoTask.setUserEmail(userEmail);
		todoTask.setTaskText(taskText);
		todoTask.setTaskStatus(TaskStatus.ACTIVE);

		Todo response = todoTaskDAO.add(mapToModel(todoTask));
		return mapToResponse(response);
	}

	public TodoTask findByUserEmailAndText(String userEmail, String taskText){

		Todo response = todoTaskDAO.findByUserEmailAndText(userEmail, taskText);
		return mapToResponse(response);
	}

	public TodoTask update(String userEmail, String oldTaskText, String newTaskText, TaskStatus taskStatus){

		Todo oldTask = todoTaskDAO.findByUserEmailAndText(userEmail, oldTaskText);
		Todo newTask = oldTask;
		newTask.setTaskText(newTaskText);
		newTask.setTaskStatus(taskStatus);

		Todo response = todoTaskDAO.update(newTask);
		return mapToResponse(response);
	}

	@Override
	public TodoTask remove(String userEmail, String taskText) {

		Todo removedTodo = todoTaskDAO.findByUserEmailAndText(userEmail, taskText);
		if(todoTaskDAO.remove(removedTodo.getId())){
			return mapToResponse(removedTodo);
		}else{
			TodoTask unexistTodoTask = new TodoTask();
			unexistTodoTask.setUserEmail(userEmail);
			unexistTodoTask.setTaskText(taskText);
			unexistTodoTask.setTaskStatus(TaskStatus.ACTIVE);
			return unexistTodoTask;
		}
	}
}
