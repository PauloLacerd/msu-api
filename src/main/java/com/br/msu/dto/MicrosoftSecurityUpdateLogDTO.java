package com.br.msu.dto;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import com.br.msu.entity.MicrosoftSecurityUpdateLog;
import com.google.gson.annotations.SerializedName;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class MicrosoftSecurityUpdateLogDTO {

	@SerializedName(value = "ID")
	private String id;

	@SerializedName(value = "DocumentTitle")
	private String documentTitle;

	@SerializedName(value = "InitialReleaseDate")
	private String initialReleaseDate;

	@SerializedName(value = "CurrentReleaseDate")
	private String currentReleaseDate;

	@SerializedName(value = "CvrfUrl")
	private String cvrfUrl;

	public static List<MicrosoftSecurityUpdateLog> convertListDTOToListEntity(List<MicrosoftSecurityUpdateLogDTO> objectDTOList) {
		return objectDTOList
			.stream()
			.map(entityLog -> {
				return MicrosoftSecurityUpdateLog
					.builder()
					.id_ms_log(entityLog.getId())
					.documentTitle(entityLog.getDocumentTitle())
					.initialReleaseDate(LocalDate.parse(entityLog.getInitialReleaseDate().split("T")[0]))
					.currentReleaseDate(LocalDate.parse(entityLog.getCurrentReleaseDate().split("T")[0]))
					.cvrfUrl(entityLog.getCvrfUrl())
					.build();
			})
			.collect(Collectors.toList());
	}
}
