package com.br.msu.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.br.msu.entity.MicrosoftSecurityUpdateLog;

public interface IMicrosoftSecurityUpdateLogRepository extends JpaRepository<MicrosoftSecurityUpdateLog, Long> {
	
}
