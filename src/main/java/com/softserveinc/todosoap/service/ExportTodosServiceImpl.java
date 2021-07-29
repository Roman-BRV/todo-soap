package com.softserveinc.todosoap.service;

import com.softserveinc.todosoap.repository.ExportTodosRepository;
import com.softserveinc.todosoap.models.ExportTodosClaim;
import com.softserveinc.todosoap.service.rabbitmq.ExportPublisher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.NoSuchElementException;
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
		log.info("New ExportTodosClaim created - {}. ID - {} sent into AMPQ as message.", claim.getCreated(), claim.getId());

		return claim.getId().toString();
	}

	@Override
	public String checkStatus(String id) {

		Optional<ExportTodosClaim> claimOptional = exportTodosRepository.findById(UUID.fromString(id));
		ExportTodosClaim claim = claimOptional
				.orElseThrow(() -> new NoSuchElementException("ExportTodosClaim with ID - " + id + " not found."));
		log.info("Status ExportTodosClaim ID - {} is - {}.", claim.getId(), claim.getStatus());

		return claim.getStatus();
	}

	@Override
	public String downloadFileLink(String id) {

		Optional<ExportTodosClaim> claimOptional = exportTodosRepository.findById(UUID.fromString(id));
		ExportTodosClaim claim = claimOptional
				.orElseThrow(() -> new NoSuchElementException("ExportTodosClaim with ID - " + id + " not found."));

		if(claim.getStatus().equals("COMPLETED")){
			log.info("Status ExportTodosClaim ID - {} is - {}. File {} created.", claim.getId(), claim.getStatus(), claim.getResultPath());
			return claim.getResultPath();
		} else {
			log.warn("Status ExportTodosClaim ID - {} is - {}. File not created.", claim.getId(), claim.getStatus());
			throw new NoSuchElementException("File for ExportTodosClaim with ID - " + id + " not generated yet.");
		}
	}
}
