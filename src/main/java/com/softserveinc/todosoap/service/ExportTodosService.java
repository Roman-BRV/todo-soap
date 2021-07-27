package com.softserveinc.todosoap.service;

public interface ExportTodosService {

	String claimExport();

	String checkStatus(String id);

	String downloadFileLink(String id);
}
