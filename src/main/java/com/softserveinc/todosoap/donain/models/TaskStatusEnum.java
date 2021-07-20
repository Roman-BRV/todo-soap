package com.softserveinc.todosoap.donain.models;

public enum TaskStatusEnum {

	ACTIVE("Active"), COMPLETED("Completed");

	private String title;

	TaskStatusEnum(String title) {
		this.title = title;
	}

	public String getTitle() {
		return title;
	}

	@Override
	public String toString() {
		return super.toString();
	}
}
