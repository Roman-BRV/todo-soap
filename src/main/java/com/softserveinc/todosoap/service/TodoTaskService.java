package com.softserveinc.todosoap.service;

import com.softservinc.todosoap.TaskStatus;
import com.softservinc.todosoap.TodoTask;

import java.util.List;

public interface TodoTaskService {

	TodoTask add(String taskText, List<String> tags);

	TodoTask findByText(String taskText);

	TodoTask update(String oldTaskText, String newTaskText, TaskStatus taskStatus, List<String> tags);

	boolean removeByText(String taskText);

	List<TodoTask> getTodoTasksByStatus(TaskStatus taskStatus);

	List<TodoTask> getTodoTasksByTag(String tag);

	List<TodoTask> getAllTodoTasksOrderByCreated();
}
