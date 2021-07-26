package com.softserveinc.todosoap.rest;

import com.softserveinc.todosoap.service.ExportDBService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/exportdb")
public class TodoTaskRestController {

	private static final String DEFAULT_USER_EMAIL = "guest@useremail.com";

	private final ExportDBService exportDBService;

	@Autowired
	public TodoTaskRestController(ExportDBService exportDBService) {
		this.exportDBService = exportDBService;
	}

	@PostMapping("/claim")
	public String claimExportDB(){
		return exportDBService.claimExportDB(DEFAULT_USER_EMAIL);
	}

	@GetMapping("/status/{id}")
	public String checkStatusExportDB(@PathVariable String id){
		return exportDBService.checkStatusExportDB(DEFAULT_USER_EMAIL, id);
	}

	@GetMapping("/download/{id}")
	public String downloadExportDB(@PathVariable String id){
		return exportDBService.downloadLinkExportDB(DEFAULT_USER_EMAIL, id);
	}
}
