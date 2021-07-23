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
				"AND tasktext = ? limit 1 ALLOW FILTERING ;";
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

	@Override
	public List<Todo> getTodoTasksByStatus(String userEmail, TaskStatus taskStatus) {

		String query = "SELECT * " +
				"FROM todosoap.todo " +
				"WHERE useremail = ? " +
				"AND taskstatus = ? ALLOW FILTERING ;";
		return cqlTemplate.query(query, new TodoRowMapper(), userEmail, taskStatus.value());
	}

	@Override
	public List<Todo> getTodoTasksByTag(String userEmail, String tag) {

		String query = "SELECT * " +
				"FROM todosoap.todo " +
				"WHERE useremail = ? " +
				"AND tags contains ? ALLOW FILTERING ;";
		return cqlTemplate.query(query, new TodoRowMapper(), userEmail, tag);
	}

	@Override
	public List<Todo> getAllTodoTasksByUserEmail(String userEmail) {

		String query = "SELECT * " +
				"FROM todosoap.todo " +
				"WHERE useremail = ? ALLOW FILTERING ;";
		return cqlTemplate.query(query, new TodoRowMapper(), userEmail);
	}
}
