package com.softserveinc.todosoap.rest;

import com.softserveinc.todosoap.service.ExportDBService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/exportdb")
public class TodoTaskRestController {

	private final ExportDBService exportDBService;

	@Autowired
	public TodoTaskRestController(ExportDBService exportDBService) {
		this.exportDBService = exportDBService;
	}

	@PostMapping("/claim")
	public String claimExportDB(){
		return exportDBService.claimExportDB();
	}

	@GetMapping("/status/{id}")
	public String checkStatusExportDB(@PathVariable String id){
		return exportDBService.checkStatusExportDB(id);
	}

	@GetMapping("/download/{id}")
	public String downloadExportDB(@PathVariable String id){
		return exportDBService.downloadLinkExportDB(id);
	}
}
