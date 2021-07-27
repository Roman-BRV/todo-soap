package com.softserveinc.todosoap.dao;

import com.softserveinc.todosoap.models.ExportDBClaim;

import java.util.UUID;

public interface ExportTodosDAO {

	void create(ExportDBClaim claim);

	String checkStatus(UUID id);

	ExportDBClaim findById(UUID id);

    boolean changeStatus(String claimId, String status);

	boolean completeClaim(String claimId, String status, String filepath);
}
