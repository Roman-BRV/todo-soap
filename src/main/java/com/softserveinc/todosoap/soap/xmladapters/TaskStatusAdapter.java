package com.softserveinc.todosoap.soap.xmladapters;

import com.softserveinc.todosoap.donain.models.TaskStatusEnum;

import javax.xml.bind.annotation.adapters.XmlAdapter;

public class TaskStatusAdapter extends XmlAdapter<String, TaskStatusEnum> {

	@Override
	public TaskStatusEnum unmarshal(String taskStatusTitle) throws Exception {
		TaskStatusEnum status = TaskStatusEnum.ACTIVE;
		if(taskStatusTitle.equals("Completed")){
			status = TaskStatusEnum.COMPLETED;
		}
		return status;
	}

	@Override
	public String marshal(TaskStatusEnum taskStatus) throws Exception {
		return taskStatus.getTitle();
	}
}
