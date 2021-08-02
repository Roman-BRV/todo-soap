package com.softserveinc.todosoap.rest;

import com.softserveinc.todosoap.service.ExportTodosService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.NoSuchElementException;

@RestController
@RequestMapping("/todos/exports")
public class TodosExportController {

	private static final Logger log = LoggerFactory.getLogger(TodosExportController.class);

	private final ExportTodosService exportTodosService;

	@Autowired
	public TodosExportController(ExportTodosService exportTodosService) {
		this.exportTodosService = exportTodosService;
	}

	@PostMapping()
	public ResponseEntity<String> claimExport(){

		try {
			String claimId = exportTodosService.claimExport();
			return new ResponseEntity<>(claimId, HttpStatus.CREATED);
		} catch (Exception e) {
			log.error("ExportTodosClaim not created. HttpStatus - {}.", HttpStatus.INTERNAL_SERVER_ERROR);
			return new ResponseEntity<>("", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/{id}/status")
	public ResponseEntity<String> checkStatus(@PathVariable String id){

		try {
			String claimStatus = exportTodosService.checkStatus(id);
			return new ResponseEntity<>(claimStatus, HttpStatus.OK);
		} catch (NoSuchElementException e) {
			log.warn("ExportTodosClaim with ID - {} not found. HttpStatus - {}.", id, HttpStatus.NOT_FOUND);
			return new ResponseEntity<>("", HttpStatus.NOT_FOUND);
		} catch (Exception e) {
			log.error("Getting status ExportTodosClaim with ID - {} was failed. HttpStatus - {}.", id, HttpStatus.INTERNAL_SERVER_ERROR);
			return new ResponseEntity<>("", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/{id}/file")
	public ResponseEntity<String> downloadFileLink(@PathVariable String id){

		try {
			String filePath = exportTodosService.downloadFileLink(id);
			return new ResponseEntity<>(filePath, HttpStatus.OK);
		} catch (NoSuchElementException e) {
			log.warn("File by ExportTodosClaim with ID - {} not found. HttpStatus - {}.", id, HttpStatus.NOT_FOUND);
			return new ResponseEntity<>("", HttpStatus.NOT_FOUND);
		} catch (Exception e) {
			log.error("Getting file by ExportTodosClaim with ID - {} was failed. HttpStatus - {}.", id, HttpStatus.INTERNAL_SERVER_ERROR);
			return new ResponseEntity<>("", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
