package com.softserveinc.todosoap.models;

import com.softservinc.todosoap.TaskStatus;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Table
public class Todo {

	@PrimaryKey
	private UUID id;
	private String userEmail;
	private String taskText;
	private TaskStatus taskStatus;
	private List<String> tags;
	private Instant created;

	public void addTag(String tag) {
		this.tags.add(tag);
	}

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public String getUserEmail() {
		return userEmail;
	}

	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
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
				", userEmail='" + userEmail + '\'' +
				", todoTask='" + taskText + '\'' +
				", taskStatus=" + taskStatus +
				", tags=" + tags +
				", created=" + created +
				'}';
	}
}
