package com.softserveinc.todosoap.dao.mappers;

import com.datastax.oss.driver.api.core.DriverException;
import com.datastax.oss.driver.api.core.cql.Row;
import com.softserveinc.todosoap.models.Todo;
import com.softservinc.todosoap.TaskStatus;
import org.springframework.data.cassandra.core.cql.RowMapper;

public class TodoRowMapper implements RowMapper<Todo> {

	@Override
	public Todo mapRow(Row row, int i) throws DriverException {

		Todo todo = new Todo();
		todo.setId(row.getUuid("id"));
		todo.setUserEmail(row.getString("useremail"));
		todo.setTaskText(row.getString("tasktext"));
		todo.setTaskStatus(TaskStatus.fromValue(row.getString("taskstatus")));
		todo.setTags(row.getList("tags", String.class));
		todo.setCreated(row.getLocalDate("created"));

		return todo;
	}
}
