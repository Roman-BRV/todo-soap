package com.softserveinc.todosoap.models;

import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

import java.time.Instant;
import java.util.UUID;

@Table
public class ExportDBClaim {

	@PrimaryKey
	private UUID id;
	private String userEmail;
	private Instant created;
	private String status;
	private String resultPath;

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

	public Instant getCreated() {
		return created;
	}

	public void setCreated(Instant created) {
		this.created = created;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getResultPath() {
		return resultPath;
	}

	public void setResultPath(String resultPath) {
		this.resultPath = resultPath;
	}

	@Override
	public String toString() {
		return "ExportDBClaim{" +
				"id=" + id +
				", userEmail='" + userEmail + '\'' +
				", created=" + created +
				", status='" + status + '\'' +
				", resultPath='" + resultPath + '\'' +
				'}';
	}
}
