package com.softserveinc.todosoap.service;

import com.softservinc.todosoap.TaskStatus;
import com.softservinc.todosoap.TodoTask;

import java.util.List;

public interface TodoTaskService {

	TodoTask add(String userEmail, String taskText, List<String> tags);

	TodoTask findByUserEmailAndText(String userEmail, String taskText);

	TodoTask update(String userEmail, String oldTaskText, String newTaskText, TaskStatus taskStatus, List<String> tags);

	boolean remove(String userEmail, String taskText);

	List<TodoTask> getTodoTasksByStatus(String userEmail, TaskStatus taskStatus);

	List<TodoTask> getTodoTasksByTag(String userEmail, String tag);

	List<TodoTask> getTodoTasksByCreatedOrder(String userEmail);

	String getAllTodoTasks();
}
