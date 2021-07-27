package com.softserveinc.todosoap.rest;

import com.softserveinc.todosoap.service.ExportTodosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/todos/exports")
public class TodosExportController {

	private final ExportTodosService exportTodosService;

	@Autowired
	public TodosExportController(ExportTodosService exportTodosService) {
		this.exportTodosService = exportTodosService;
	}

	@PostMapping()
	public String claimExport(){
		return exportTodosService.claimExport();
	}

	@GetMapping("/{id}/status")
	public String checkStatus(@PathVariable String id){
		return exportTodosService.checkStatus(id);
	}

	@GetMapping("/{id}/file")
	public String downloadFile(@PathVariable String id){
		return exportTodosService.downloadFileLink(id);
	}
}
