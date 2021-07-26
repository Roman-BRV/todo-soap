package com.softserveinc.todosoap.dao;


import com.softserveinc.todosoap.models.Todo;
import com.softservinc.todosoap.TaskStatus;

import java.util.List;
import java.util.UUID;

public interface TodoTaskDAO {

	Todo add(Todo todoTask);

	Todo findById(UUID id);

	Todo findByText(String taskText);

	Todo update(Todo todoTask);

	boolean remove(UUID id);

    List<Todo> getTodoTasksByStatus(TaskStatus taskStatus);

	List<Todo> getTodoTasksByTag(String tag);

    List<Todo> getAllTodoTasks();
}
