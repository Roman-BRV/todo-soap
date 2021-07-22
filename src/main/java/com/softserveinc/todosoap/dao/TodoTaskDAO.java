package com.softserveinc.todosoap.dao;


import com.softserveinc.todosoap.models.Todo;

import java.util.UUID;

public interface TodoTaskDAO {

	Todo add(Todo todoTask);

	Todo findById(UUID id);

	Todo findByUserEmailAndText(String userEmail, String taskText);

	Todo update(Todo todoTask);

	boolean remove(UUID id);
}
