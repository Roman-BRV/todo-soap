package com.softserveinc.todosoap.dao;

import com.softserveinc.todosoap.models.ExportDBClaim;

import java.util.UUID;

public interface ExportDBDAO {

	void create(ExportDBClaim claim);

	String checkStatusExportDB(String userEmail, UUID id);

	ExportDBClaim findById(UUID id);
}
