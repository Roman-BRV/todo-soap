package com.softserveinc.todosoap.service;

import com.softservinc.todosoap.TaskStatus;
import com.softservinc.todosoap.TodoTask;

public interface TodoTaskService {

	TodoTask add(String userEmail, String taskText);

	TodoTask findByUserEmailAndText(String userEmail, String taskText);

	TodoTask update(String userEmail, String oldTaskText, String newTaskText, TaskStatus taskStatus);

	TodoTask remove(String userEmail, String taskText);
}
