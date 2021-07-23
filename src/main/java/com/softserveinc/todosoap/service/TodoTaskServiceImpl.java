package com.softserveinc.todosoap.service;

import com.softserveinc.todosoap.dao.TodoTaskDAO;
import com.softserveinc.todosoap.models.Todo;
import com.softservinc.todosoap.TaskStatus;
import com.softservinc.todosoap.TodoTask;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class TodoTaskServiceImpl implements TodoTaskService {

	private TodoTaskDAO todoTaskDAO;

	@Autowired
	public TodoTaskServiceImpl(TodoTaskDAO todoTaskDAO){
		this.todoTaskDAO = todoTaskDAO;
	}

	public TodoTask add(String userEmail, String taskText, List<String> tags){

		TodoTask todoTask = new TodoTask();
		todoTask.setUserEmail(userEmail);
		todoTask.setTaskText(taskText);
		todoTask.setTaskStatus(TaskStatus.ACTIVE);
		todoTask.getTags().addAll(tags);

		Todo modelTodo = mapToModel(todoTask);
		modelTodo.setId(UUID.randomUUID());
		modelTodo.setCreated(Instant.now());
		return mapToResponse(todoTaskDAO.add(modelTodo));
	}

	public TodoTask findByUserEmailAndText(String userEmail, String taskText){

		Todo response = todoTaskDAO.findByUserEmailAndText(userEmail, taskText);
		return mapToResponse(response);
	}

	public TodoTask update(String userEmail, String oldTaskText, String newTaskText, TaskStatus taskStatus, List<String> tags){

		Todo oldTask = todoTaskDAO.findByUserEmailAndText(userEmail, oldTaskText);
		Todo newTask = oldTask;
		newTask.setTaskText(newTaskText);
		newTask.setTaskStatus(taskStatus);
		newTask.setTags(tags);

		Todo response = todoTaskDAO.update(newTask);
		return mapToResponse(response);
	}

	@Override
	public boolean remove(String userEmail, String taskText) {

		Todo removedTodo = todoTaskDAO.findByUserEmailAndText(userEmail, taskText);
		return todoTaskDAO.remove(removedTodo.getId());
	}

	@Override
	public List<TodoTask> getTodoTasksByStatus(String userEmail, TaskStatus taskStatus) {

		List<Todo> todos = todoTaskDAO.getTodoTasksByStatus(userEmail, taskStatus);
		return todos.stream()
				.map(this::mapToResponse)
				.collect(Collectors.toList());
	}

	@Override
	public List<TodoTask> getTodoTasksByTag(String userEmail, String tag) {

		List<Todo> todos = todoTaskDAO.getTodoTasksByTag(userEmail, tag);
		return todos.stream()
				.map(this::mapToResponse)
				.collect(Collectors.toList());
	}

	@Override
	public List<TodoTask> getTodoTasksByCreatedOrder(String userEmail) {

		List<Todo> todos = todoTaskDAO.getAllTodoTasksByUserEmail(userEmail);
		return todos.stream()
				.sorted(Comparator.comparing(Todo::getCreated))
				.map(this::mapToResponse)
				.collect(Collectors.toList());
	}

	private Todo mapToModel(TodoTask todoTask){

		Todo model = new Todo();
		if(todoTask.getId() != null){
			model.setId(UUID.fromString(todoTask.getId()));
		}
		model.setUserEmail(todoTask.getUserEmail());
		model.setTaskText(todoTask.getTaskText());
		model.setTaskStatus(todoTask.getTaskStatus());
		model.setTags(todoTask.getTags());

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
		response.getTags().addAll(todo.getTags());

		return response;
	}
}
