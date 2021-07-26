package com.softserveinc.todosoap.soap;

import com.softserveinc.todosoap.service.TodoTaskService;
import com.softservinc.todosoap.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import java.util.List;

@Endpoint
public class TodoTaskEndpoint {

	private static final String NAMESPACE_URI = "http://todosoap.softservinc.com/";

	private final TodoTaskService todoTaskService;

	@Autowired
	public TodoTaskEndpoint(TodoTaskService todoTaskService){
		this.todoTaskService = todoTaskService;
	}

	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "addTodoTaskRequest")
	@ResponsePayload
	public AddTodoTaskResponse addTodoTask(@RequestPayload AddTodoTaskRequest request){
		AddTodoTaskResponse response = new AddTodoTaskResponse();
		response.setTodoTask(todoTaskService.add(request.getTaskText(), request.getTags()));
		return response;
	}

	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "findTodoTaskByTextRequest")
	@ResponsePayload
	public FindTodoTaskByTextResponse findTodoTask(@RequestPayload FindTodoTaskByTextRequest request){
		FindTodoTaskByTextResponse response = new FindTodoTaskByTextResponse();
		response.setTodoTask(todoTaskService.findByText(request.getTaskText()));
		return response;
	}

	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "updateTodoTaskRequest")
	@ResponsePayload
	public UpdateTodoTaskResponse updateTodoTask(@RequestPayload UpdateTodoTaskRequest request){
		UpdateTodoTaskResponse response = new UpdateTodoTaskResponse();
		response.setTodoTask(todoTaskService.update(request.getOldTaskText(), request.getNewTaskText(), request.getTaskStatus(), request.getTags()));
		return response;
	}

	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "removeTodoTaskRequest")
	@ResponsePayload
	public RemoveTodoTaskResponse removeTodoTask(@RequestPayload RemoveTodoTaskRequest request){
		RemoveTodoTaskResponse response = new RemoveTodoTaskResponse();
		response.setResponse(todoTaskService.removeByText(request.getTaskText()));
		return response;
	}

	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "getTodoTasksByStatusRequest")
	@ResponsePayload
	public GetTodoTasksByStatusResponse getTodoTasksByStatus(@RequestPayload GetTodoTasksByStatusRequest request){
		List<TodoTask> todoTasks = todoTaskService.getTodoTasksByStatus(request.getTaskStatus());

		GetTodoTasksByStatusResponse response = new GetTodoTasksByStatusResponse();
		response.getTodoTask().addAll(todoTasks);
		return response;
	}

	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "getTodoTasksByTagRequest")
	@ResponsePayload
	public GetTodoTasksByTagResponse getTodoTasksByTag(@RequestPayload GetTodoTasksByTagRequest request){
		List<TodoTask> todoTasks = todoTaskService.getTodoTasksByTag(request.getTag());

		GetTodoTasksByTagResponse response = new GetTodoTasksByTagResponse();
		response.getTodoTask().addAll(todoTasks);
		return response;
	}

	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "getAllTodoTasksOrderByCreatedRequest")
	@ResponsePayload
	public GetAllTodoTasksOrderByCreatedResponse getAllTodoTasksOrderByCreated(){
		List<TodoTask> todoTasks = todoTaskService.getAllTodoTasksOrderByCreated();

		GetAllTodoTasksOrderByCreatedResponse response = new GetAllTodoTasksOrderByCreatedResponse();
		response.getTodoTask().addAll(todoTasks);
		return response;
	}
}
