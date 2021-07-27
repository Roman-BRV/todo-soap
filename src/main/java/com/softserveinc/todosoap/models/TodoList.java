package com.softserveinc.todosoap.models;

import javax.xml.bind.annotation.*;
import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "todoList")
public class TodoList {

	@XmlElement(name = "todo")
	private List<Todo> todos;

	public List<Todo> getTodos() {
		return todos;
	}

	public void setTodos(List<Todo> todos) {
		this.todos = todos;
	}
}
