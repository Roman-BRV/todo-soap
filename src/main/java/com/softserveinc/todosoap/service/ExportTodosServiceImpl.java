package com.softserveinc.todosoap.service;

import com.softserveinc.todosoap.repository.ExportTodosRepository;
import com.softserveinc.todosoap.models.ExportTodosClaim;
import com.softserveinc.todosoap.service.rabbitmq.ExportPublisher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Service
public class ExportTodosServiceImpl implements ExportTodosService {

	private static final Logger log = LoggerFactory.getLogger(ExportTodosServiceImpl.class);

	private final ExportTodosRepository exportTodosRepository;
	private final ExportPublisher exportPublisher;

	@Autowired
	public ExportTodosServiceImpl(ExportTodosRepository exportTodosRepository, ExportPublisher exportPublisher) {
		this.exportTodosRepository = exportTodosRepository;
		this.exportPublisher = exportPublisher;
	}

	@Override
	public String claimExport() {

		ExportTodosClaim claim = new ExportTodosClaim();
		claim.setId(UUID.randomUUID());
		claim.setCreated(Instant.now());
		claim.setStatus("ACCEPTED");
		claim.setResultPath("");

		exportTodosRepository.save(claim);
		exportPublisher.publishClaim(claim.getId().toString());
		return claim.getId().toString();
	}

	@Override
	public String checkStatus(String id) {

		Optional<ExportTodosClaim> claimOptional = exportTodosRepository.findById(UUID.fromString(id));
		ExportTodosClaim claim = claimOptional.orElseThrow(RuntimeException::new);
		return claim.getStatus();
	}

	@Override
	public String downloadFileLink(String id) {

		Optional<ExportTodosClaim> claimOptional = exportTodosRepository.findById(UUID.fromString(id));
		ExportTodosClaim claim = claimOptional.orElseThrow(RuntimeException::new);
		if(claim.getStatus().equals("COMPLETED")){
			return claim.getResultPath();
		} else {
			return "";//throw not found exception
		}
	}
}
