package com.softserveinc.todosoap.service;

import com.softserveinc.todosoap.repository.TodoTaskRepository;
import com.softserveinc.todosoap.models.Todo;
import com.softservinc.todosoap.TaskStatus;
import com.softservinc.todosoap.TodoTask;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class TodoTaskServiceImpl implements TodoTaskService {

	private final TodoTaskRepository todoTaskRepository;

	@Autowired
	public TodoTaskServiceImpl(TodoTaskRepository todoTaskRepository){
		this.todoTaskRepository = todoTaskRepository;
	}

	public TodoTask add(String taskText, List<String> tags){

		TodoTask todoTask = new TodoTask();
		todoTask.setTaskText(taskText);
		todoTask.setTaskStatus(TaskStatus.ACTIVE);
		todoTask.getTags().addAll(tags);

		Todo modelTodo = mapToModel(todoTask);
		modelTodo.setId(UUID.randomUUID());
		modelTodo.setCreated(Instant.now());
		return mapToResponse(todoTaskRepository.save(modelTodo));
	}

	public TodoTask findByText(String taskText){

		Todo response = todoTaskRepository.findByTaskText(taskText);
		return mapToResponse(response);
	}

	public TodoTask update(String oldTaskText, String newTaskText, TaskStatus taskStatus, List<String> tags){

		Todo newTask = todoTaskRepository.findByTaskText(oldTaskText);
		newTask.setTaskText(newTaskText);
		newTask.setTaskStatus(taskStatus);
		newTask.setTags(tags);

		Todo response = todoTaskRepository.save(newTask);
		return mapToResponse(response);
	}

	@Override
	public boolean removeByText(String taskText) {

		Todo removedTodo = todoTaskRepository.findByTaskText(taskText);
		if(removedTodo != null){
			todoTaskRepository.delete(removedTodo);
			return true;
		} else {
			return false;
		}
	}

	@Override
	public List<TodoTask> getTodoTasksByStatus(TaskStatus taskStatus) {

		List<Todo> todos = new ArrayList<>(todoTaskRepository.findByTaskStatus(taskStatus));
		return todos.stream()
				.map(this::mapToResponse)
				.collect(Collectors.toList());
	}

	@Override
	public List<TodoTask> getTodoTasksByTag(String tag) {

		List<Todo> todos = new ArrayList<>(todoTaskRepository.findByTagsContaining(tag));
		return todos.stream()
				.map(this::mapToResponse)
				.collect(Collectors.toList());
	}

	@Override
	public List<TodoTask> getAllTodoTasksOrderByCreated() {

		List<Todo> todos = new ArrayList<>(todoTaskRepository.findAll());
		return todos.stream()
				.sorted(Comparator.comparing(Todo::getCreated))
				.map(this::mapToResponse)
				.collect(Collectors.toList());
	}

	public List<Todo>  getAllTodoTasks(){

		return todoTaskRepository.findAll();
	}

	private Todo mapToModel(TodoTask todoTask){

		Todo model = new Todo();
		if(todoTask.getId() != null){
			model.setId(UUID.fromString(todoTask.getId()));
		}
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
		response.setTaskText(todo.getTaskText());
		response.setTaskStatus(todo.getTaskStatus());
		response.getTags().addAll(todo.getTags());

		return response;
	}
}
