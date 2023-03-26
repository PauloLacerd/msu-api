package com.br.msu.microsoftAPI.rest;

import static org.springframework.http.HttpHeaders.CONTENT_TYPE;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import java.lang.reflect.Type;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.br.msu.dto.MicrosoftSecurityUpdateLogDTO;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class MicrosoftApiRest {

	@Value("${msu-api.urlApiMicrosoftSecurityUpdates}")
	private String urlApi;

	public List<MicrosoftSecurityUpdateLogDTO> getMicrosoftSecurityUpdates() {

		log.info("Fetching Microsoft Security Update Logs...");

		WebClient webclient = WebClient.builder()
			.baseUrl(urlApi)
			.defaultHeader(CONTENT_TYPE, APPLICATION_JSON_VALUE)
			.build();

		String response = webclient
			.get()
			.uri("/updates")
			.retrieve()
			.bodyToMono(String.class)
			.block();

		List<MicrosoftSecurityUpdateLogDTO> convertedResponseList = parseStringJsonToMicrosoftSecurityUpdateLogDTO(response);

		log.info("{} records were found", convertedResponseList.size());

		return convertedResponseList;
	}

	private List<MicrosoftSecurityUpdateLogDTO> parseStringJsonToMicrosoftSecurityUpdateLogDTO(String json) {
		Gson gson = new Gson();
		JsonObject jsonObject = new Gson().fromJson(json, JsonObject.class);

		Type typeList = new TypeToken<List<MicrosoftSecurityUpdateLogDTO>>(){}.getType();
		List<MicrosoftSecurityUpdateLogDTO> convertedResponseList = gson.fromJson(jsonObject.get("value"), typeList);

		return convertedResponseList;
	}
}
