package com.br.msu.entity;

import static jakarta.persistence.GenerationType.AUTO;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(schema = "public", name = "ms_security_update_logs")
public class MicrosoftSecurityUpdateLog {

	@Id
	@Column(name = "id_log")
	@GeneratedValue(strategy = AUTO)
	private Long id;

	@Column(name = "id_ms_log")
	private String id_ms_log;

	@Column(name = "document_Title")
	private String documentTitle;

	@Column(name = "initial_Release_Date")
	private LocalDate initialReleaseDate;

	@Column(name = "current_Release_Date")
	private LocalDate currentReleaseDate;

	@Column(name = "cvrfUrl")
	private String cvrfUrl;

}
