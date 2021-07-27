package com.softserveinc.todosoap.service;

import com.softserveinc.todosoap.dao.ExportTodosDAO;
import com.softserveinc.todosoap.models.ExportDBClaim;
import com.softserveinc.todosoap.service.rabbitmq.ExportPublisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.UUID;

@Service
public class ExportTodosServiceImpl implements ExportTodosService {

	private final ExportTodosDAO exportTodosDAO;
	private final ExportPublisher exportPublisher;

	@Autowired
	public ExportTodosServiceImpl(ExportTodosDAO exportTodosDAO, ExportPublisher exportPublisher) {
		this.exportTodosDAO = exportTodosDAO;
		this.exportPublisher = exportPublisher;
	}

	@Override
	public String claimExport() {

		ExportDBClaim claim = new ExportDBClaim();
		claim.setId(UUID.randomUUID());
		claim.setCreated(Instant.now());
		claim.setStatus("ACCEPTED");
		claim.setResultPath("");

		exportTodosDAO.create(claim);
		exportPublisher.publishClaim(claim.getId().toString());
		return claim.getId().toString();
	}

	@Override
	public String checkStatus(String id) {

		return exportTodosDAO.checkStatus(UUID.fromString(id));
	}

	@Override
	public String downloadFileLink(String id) {

		ExportDBClaim claim = exportTodosDAO.findById(UUID.fromString(id));
		if(claim.getStatus().equals("COMPLETED")){
			return claim.getResultPath();
		} else {
			return "";//throw not found exception
		}
	}
}
