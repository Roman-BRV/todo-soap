package com.softserveinc.todosoap.models;

import com.softservinc.todosoap.TaskStatus;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

import javax.xml.bind.annotation.*;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Table
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "todo")
@XmlType(propOrder = {"id", "taskText", "taskStatus", "tags", "created"})
public class Todo {

	@PrimaryKey
	private UUID id;
	private String taskText;
	@XmlSchemaType(name = "string")
	private TaskStatus taskStatus;
	private List<String> tags;
	private Instant created;

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public String getTaskText() {
		return taskText;
	}

	public void setTaskText(String taskText) {
		this.taskText = taskText;
	}

	public TaskStatus getTaskStatus() {
		return taskStatus;
	}

	public void setTaskStatus(TaskStatus taskStatus) {
		this.taskStatus = taskStatus;
	}

	public List<String> getTags() {
		return tags;
	}

	public void setTags(List<String> tags) {
		this.tags = tags;
	}

	public Instant getCreated() {
		return created;
	}

	public void setCreated(Instant created) {
		this.created = created;
	}

	@Override
	public String toString() {
		return "Todo{" +
				"id=" + id +
				", todoTask='" + taskText + '\'' +
				", taskStatus=" + taskStatus +
				", tags=" + tags +
				", created=" + created +
				'}';
	}
}
