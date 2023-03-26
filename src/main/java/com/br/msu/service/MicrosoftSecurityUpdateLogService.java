package com.br.msu.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.ExampleMatcher.StringMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.br.msu.dto.MicrosoftSecurityUpdateLogDTO;
import com.br.msu.entity.MicrosoftSecurityUpdateLog;
import com.br.msu.microsoftAPI.rest.MicrosoftApiRest;
import com.br.msu.repository.IMicrosoftSecurityUpdateLogRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@EnableScheduling
public class MicrosoftSecurityUpdateLogService {

	@Autowired
	private IMicrosoftSecurityUpdateLogRepository repository;

	@Autowired
	private MicrosoftApiRest microsoftApi;

	public Page<MicrosoftSecurityUpdateLog> getall(int page, int size, MicrosoftSecurityUpdateLog filterLogs) {

		PageRequest pageRequest = PageRequest.of(page, size);

		MicrosoftSecurityUpdateLog microsoftSecurityUpdateLog = new MicrosoftSecurityUpdateLog();
		microsoftSecurityUpdateLog.setId_ms_log(filterLogs.getId_ms_log());
		microsoftSecurityUpdateLog.setDocumentTitle(filterLogs.getDocumentTitle());
		microsoftSecurityUpdateLog.setInitialReleaseDate(filterLogs.getInitialReleaseDate());
		microsoftSecurityUpdateLog.setCurrentReleaseDate(filterLogs.getCurrentReleaseDate());

		ExampleMatcher matcher = ExampleMatcher.matching()
			.withIgnoreCase(true)
			.withStringMatcher(StringMatcher.CONTAINING);

		Example<MicrosoftSecurityUpdateLog> filterSecurityLogs = Example.of(microsoftSecurityUpdateLog, matcher);

		return repository.findAll(filterSecurityLogs, pageRequest);
	}

	@Scheduled(fixedRate = 300000)
	public List<MicrosoftSecurityUpdateLog> teste() {
		log.info("Executing scheduled method");

		List<MicrosoftSecurityUpdateLogDTO> microsoftSecurityUpdateLogsDTOList = microsoftApi.getMicrosoftSecurityUpdates();
		List<MicrosoftSecurityUpdateLog> securityLogsList = repository.findAll();

		List<MicrosoftSecurityUpdateLogDTO> newSecurityLogsList = microsoftSecurityUpdateLogsDTOList
			.stream()
			.filter(securityLogDTO -> !securityLogsList.stream().anyMatch(securityLogEntity -> securityLogEntity.getId_ms_log().equalsIgnoreCase(securityLogDTO.getId())))
			.collect(Collectors.toList());

		List<MicrosoftSecurityUpdateLog> microsoftSecurityUpdateLogsEntityList = MicrosoftSecurityUpdateLogDTO.convertListDTOToListEntity(newSecurityLogsList);
		repository.saveAll(microsoftSecurityUpdateLogsEntityList);

		log.info("{} more new security records were inserted", microsoftSecurityUpdateLogsEntityList.size());

		return microsoftSecurityUpdateLogsEntityList;
	}

}
