package com.softserveinc.todosoap.service;

public interface ExportDBService {

	String claimExportDB();

	String checkStatusExportDB(String id);

	String downloadLinkExportDB(String id);
}
