package com.softserveinc.todosoap.repository;


import com.softserveinc.todosoap.models.Todo;
import com.softservinc.todosoap.TaskStatus;
import org.springframework.data.cassandra.repository.AllowFiltering;
import org.springframework.data.cassandra.repository.CassandraRepository;

import java.util.List;
import java.util.UUID;

public interface TodoTaskRepository extends CassandraRepository<Todo, UUID> {

	@AllowFiltering
	Todo findByTaskText(String taskText);

    @AllowFiltering
	List<Todo> findByTaskStatus(TaskStatus taskStatus);

    @AllowFiltering
	List<Todo> findByTagsContaining(String tag);
}
