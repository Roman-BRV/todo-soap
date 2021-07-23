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

	private TodoTaskService todoTaskService;

	@Autowired
	public TodoTaskEndpoint(TodoTaskService todoTaskService){
		this.todoTaskService = todoTaskService;
	}

	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "addTodoTaskRequest")
	@ResponsePayload
	public AddTodoTaskResponse addTodoTask(@RequestPayload AddTodoTaskRequest request){
		AddTodoTaskResponse response = new AddTodoTaskResponse();
		response.setTodoTask(todoTaskService.add(request.getUserEmail(), request.getTaskText(), request.getTags()));
		return response;
	}

	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "findTodoTaskRequest")
	@ResponsePayload
	public FindTodoTaskResponse findTodoTask(@RequestPayload FindTodoTaskRequest request){
		FindTodoTaskResponse response = new FindTodoTaskResponse();
		response.setTodoTask(todoTaskService.findByUserEmailAndText(request.getUserEmail(), request.getTaskText()));
		return response;
	}

	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "updateTodoTaskRequest")
	@ResponsePayload
	public UpdateTodoTaskResponse findTodoTask(@RequestPayload UpdateTodoTaskRequest request){
		UpdateTodoTaskResponse response = new UpdateTodoTaskResponse();
		response.setTodoTask(todoTaskService.update(request.getUserEmail(), request.getOldTaskText(), request.getNewTaskText(), request.getTaskStatus(), request.getTags()));
		return response;
	}

	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "removeTodoTaskRequest")
	@ResponsePayload
	public RemoveTodoTaskResponse removeTodoTask(@RequestPayload RemoveTodoTaskRequest request){
		RemoveTodoTaskResponse response = new RemoveTodoTaskResponse();
		response.setResponse(todoTaskService.remove(request.getUserEmail(), request.getTaskText()));
		return response;
	}

	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "getTodoTasksByStatusRequest")
	@ResponsePayload
	public GetTodoTasksByStatusResponse getTodoTasksByStatus(@RequestPayload GetTodoTasksByStatusRequest request){
		List<TodoTask> todoTasks = todoTaskService.getTodoTasksByStatus(request.getUserEmail(), request.getTaskStatus());

		GetTodoTasksByStatusResponse response = new GetTodoTasksByStatusResponse();
		response.getTodoTask().addAll(todoTasks);
		return response;
	}

	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "getTodoTasksByTagRequest")
	@ResponsePayload
	public GetTodoTasksByTagResponse getTodoTasksByTag(@RequestPayload GetTodoTasksByTagRequest request){
		List<TodoTask> todoTasks = todoTaskService.getTodoTasksByTag(request.getUserEmail(), request.getTag());

		GetTodoTasksByTagResponse response = new GetTodoTasksByTagResponse();
		response.getTodoTask().addAll(todoTasks);
		return response;
	}

	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "getTodoTasksByCreatedOrderRequest")
	@ResponsePayload
	public GetTodoTasksByCreatedOrderResponse getTodoTasksByCreatedOrder(@RequestPayload GetTodoTasksByCreatedOrderRequest request){
		List<TodoTask> todoTasks = todoTaskService.getTodoTasksByCreatedOrder(request.getUserEmail());

		GetTodoTasksByCreatedOrderResponse response = new GetTodoTasksByCreatedOrderResponse();
		response.getTodoTask().addAll(todoTasks);
		return response;
	}
}
