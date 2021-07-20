package com.softserveinc.todosoap.donain.models;

import com.softserveinc.todosoap.soap.xmladapters.LocalDateAdapter;
import com.softserveinc.todosoap.soap.xmladapters.TaskStatusAdapter;

import javax.xml.bind.annotation.*;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.time.LocalDate;

@XmlRootElement(name = "todoTask")
@XmlType(propOrder = {
		"id",
		"userEmail",
		"taskText",
		"createDate",
		"accomplishDate",
		"taskStatus"
})
public class TodoTask {

	private long id;
	private String userEmail;
	private String taskText;
	private LocalDate createDate;
	private LocalDate accomplishDate;
	private TaskStatusEnum taskStatus;

	public long getId() {
		return id;
	}

	@XmlElement(name = "id")
	public void setId(long id) {
		this.id = id;
	}

	public String getUserEmail() {
		return userEmail;
	}

	@XmlElement(name = "userEmail")
	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}

	public String getTaskText() {
		return taskText;
	}

	@XmlElement(name = "taskText")
	public void setTaskText(String taskText) {
		this.taskText = taskText;
	}

	public LocalDate getCreateDate() {
		return createDate;
	}

	@XmlElement(name = "createDate")
	@XmlJavaTypeAdapter(LocalDateAdapter.class)
	public void setCreateDate(LocalDate createDate) {
		this.createDate = createDate;
	}

	public LocalDate getAccomplishDate() {
		return accomplishDate;
	}

	@XmlElement(name = "accomplishDate")
	@XmlJavaTypeAdapter(LocalDateAdapter.class)
	public void setAccomplishDate(LocalDate accomplishDate) {
		this.accomplishDate = accomplishDate;
	}

	public TaskStatusEnum getTaskStatus() {
		return taskStatus;
	}

	@XmlElement(name = "taskStatus")
	@XmlJavaTypeAdapter(TaskStatusAdapter.class)
	public void setTaskStatus(TaskStatusEnum taskStatus) {
		this.taskStatus = taskStatus;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof TodoTask)) return false;

		TodoTask todoTask = (TodoTask) o;

		if (id != todoTask.id) return false;
		if (userEmail != null ? !userEmail.equals(todoTask.userEmail) : todoTask.userEmail != null) return false;
		if (taskText != null ? !taskText.equals(todoTask.taskText) : todoTask.taskText != null) return false;
		if (createDate != null ? !createDate.equals(todoTask.createDate) : todoTask.createDate != null) return false;
		if (accomplishDate != null ? !accomplishDate.equals(todoTask.accomplishDate) : todoTask.accomplishDate != null)
			return false;
		return taskStatus == todoTask.taskStatus;
	}

	@Override
	public int hashCode() {
		int result = (int) (id ^ (id >>> 32));
		result = 31 * result + (userEmail != null ? userEmail.hashCode() : 0);
		result = 31 * result + (taskText != null ? taskText.hashCode() : 0);
		result = 31 * result + (createDate != null ? createDate.hashCode() : 0);
		result = 31 * result + (accomplishDate != null ? accomplishDate.hashCode() : 0);
		result = 31 * result + (taskStatus != null ? taskStatus.hashCode() : 0);
		return result;
	}

	@Override
	public String toString() {
		return "TodoTask{" +
				"id=" + id +
				", userEmail='" + userEmail + '\'' +
				", taskText='" + taskText + '\'' +
				", createDate=" + createDate +
				", accomplishDate=" + accomplishDate +
				", taskStatus=" + taskStatus +
				'}';
	}
}
