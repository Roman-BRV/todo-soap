package com.softserveinc.todosoap.dao;

import com.softserveinc.todosoap.dao.mappers.TodoRowMapper;
import com.softserveinc.todosoap.models.Todo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.cassandra.core.CassandraOperations;
import org.springframework.data.cassandra.core.cql.CqlTemplate;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public class TodoTaskDAOImpl implements TodoTaskDAO{

	@Autowired
	private CassandraOperations cassandraTemplate;

	@Autowired
	private CqlTemplate cqlTemplate;

	@Override
	public Todo add(Todo todo) {
		return cassandraTemplate.insert(todo);
	}

	@Override
	public Todo findById(UUID id) {
		return cassandraTemplate.selectOneById(id,Todo.class);
	}

	@Override
	public Todo findByUserEmailAndText(String userEmail, String taskText) {
		String query = "SELECT * " +
				"FROM todosoap.todo " +
				"WHERE useremail = ? " +
				"AND tasktext = ? ALLOW FILTERING ;";
		return cqlTemplate.queryForObject(query, new TodoRowMapper(), userEmail, taskText);
//		Select.Where select = QueryBuilder.select().from(DB_TABLE).where(QueryBuilder.eq("useremail", userEmail)).and(QueryBuilder.eq("tasktext", taskText));
//		return cassandraTemplate.selectOne(select, Todo.class);
	}

	@Override
	public Todo update(Todo todoTask) {
		return cassandraTemplate.update(todoTask);
	}

	@Override
	public boolean remove(UUID id) {
		return cassandraTemplate.deleteById(id, Todo.class);
	}
}
