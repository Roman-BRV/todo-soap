package com.softserveinc.todosoap.service;

public interface ExportDBService {

	String claimExportDB(String userEmail);

	String checkStatusExportDB(String userEmail, String id);

	String downloadLinkExportDB(String userEmail, String id);
}
