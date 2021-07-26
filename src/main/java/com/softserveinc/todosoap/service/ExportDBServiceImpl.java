package com.softserveinc.todosoap.service;

import com.softserveinc.todosoap.dao.ExportDBDAO;
import com.softserveinc.todosoap.models.ExportDBClaim;
import com.softserveinc.todosoap.service.rabbitmq.ExportPublisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.UUID;

@Service
public class ExportDBServiceImpl implements ExportDBService{

	private final ExportDBDAO exportDBDAO;
	private final ExportPublisher exportPublisher;

	@Autowired
	public ExportDBServiceImpl(ExportDBDAO exportDBDAO, ExportPublisher exportPublisher) {
		this.exportDBDAO = exportDBDAO;
		this.exportPublisher = exportPublisher;
	}

	@Override
	public String claimExportDB() {

		ExportDBClaim claim = new ExportDBClaim();
		claim.setId(UUID.randomUUID());
		claim.setCreated(Instant.now());
		claim.setStatus("ACCEPTED");
		claim.setResultPath("");

		exportDBDAO.create(claim);
		exportPublisher.publishClaim(claim.getId().toString());
		return claim.getId().toString();
	}

	@Override
	public String checkStatusExportDB(String id) {

		return exportDBDAO.checkStatus(UUID.fromString(id));
	}

	@Override
	public String downloadLinkExportDB(String id) {

		ExportDBClaim claim = exportDBDAO.findById(UUID.fromString(id));
		if(claim.getStatus().equals("COMPLETED")){
			return claim.getResultPath();
		} else {
			return "";//throw not found exception
		}
	}
}
