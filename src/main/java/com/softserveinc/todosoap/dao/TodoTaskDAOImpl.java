package com.softserveinc.todosoap.dao;

import com.softserveinc.todosoap.dao.mappers.TodoRowMapper;
import com.softserveinc.todosoap.models.Todo;
import com.softservinc.todosoap.TaskStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.cassandra.core.CassandraOperations;
import org.springframework.data.cassandra.core.cql.CqlTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public class TodoTaskDAOImpl implements TodoTaskDAO{

	private final CassandraOperations cassandraTemplate;

	private final CqlTemplate cqlTemplate;

	@Autowired
	public TodoTaskDAOImpl(CassandraOperations cassandraTemplate, CqlTemplate cqlTemplate) {
		this.cassandraTemplate = cassandraTemplate;
		this.cqlTemplate = cqlTemplate;
	}

	@Override
	public Todo add(Todo todo) {
		return cassandraTemplate.insert(todo);
	}

	@Override
	public Todo findById(UUID id) {
		return cassandraTemplate.selectOneById(id,Todo.class);
	}

	@Override
	public Todo findByText(String taskText) {

		String query = "SELECT * FROM todosoap.todo WHERE tasktext = ? limit 1 ALLOW FILTERING;";
		return cqlTemplate.queryForObject(query, new TodoRowMapper(), taskText);
	}

	@Override
	public Todo update(Todo todoTask) {
		return cassandraTemplate.update(todoTask);
	}

	@Override
	public boolean remove(UUID id) {
		return cassandraTemplate.deleteById(id, Todo.class);
	}

	@Override
	public List<Todo> getTodoTasksByStatus(TaskStatus taskStatus) {

		String query = "SELECT * FROM todosoap.todo WHERE taskstatus = ? ALLOW FILTERING;";
		return cqlTemplate.query(query, new TodoRowMapper(), taskStatus.value());
	}

	@Override
	public List<Todo> getTodoTasksByTag(String tag) {

		String query = "SELECT * FROM todosoap.todo WHERE tags contains ? ALLOW FILTERING;";
		return cqlTemplate.query(query, new TodoRowMapper(), tag);
	}

	@Override
	public List<Todo> getAllTodoTasks() {

		String query = "SELECT * FROM todosoap.todo ALLOW FILTERING;";
		return cqlTemplate.query(query, new TodoRowMapper());
	}
}
