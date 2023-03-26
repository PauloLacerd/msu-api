package com.br.msu.rest;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.br.msu.entity.MicrosoftSecurityUpdateLog;
import com.br.msu.service.MicrosoftSecurityUpdateLogService;

@RestController
@RequestMapping(path = "/microsoftlogs")
public class MicrosoftSecurityUpdateLogRest {
	
	@Autowired
	private MicrosoftSecurityUpdateLogService service;

	@GetMapping(path = "/getall")
	public Page<MicrosoftSecurityUpdateLog> getall(
    @RequestParam(value = "id_ms_log", required = false) String id_ms_log,
    @RequestParam(value = "documentTitle", required = false) String documentTitle,
    @RequestParam(value = "initialReleaseDate", required = false) String initialReleaseDate,
    @RequestParam(value = "currentReleaseDate", required = false) String currentReleaseDate,
    @RequestParam( value = "page", required = false, defaultValue = "0") int page,
    @RequestParam(value = "size", required = false, defaultValue = "10") int size) {

		MicrosoftSecurityUpdateLog filterLogs = MicrosoftSecurityUpdateLog
			.builder()
			.id_ms_log(id_ms_log)
			.documentTitle(documentTitle)
			.initialReleaseDate(initialReleaseDate.isBlank() ? null : LocalDate.parse(initialReleaseDate))
			.currentReleaseDate(currentReleaseDate.isBlank() ? null : LocalDate.parse(currentReleaseDate))
			.build();

		return service.getall(page, size, filterLogs);
	}
}
