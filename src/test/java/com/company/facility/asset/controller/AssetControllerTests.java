package com.company.facility.asset.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.company.facility.asset.dto.CreateAssetRequest;
import com.company.facility.asset.model.AssetStatus;
import com.company.facility.asset.repository.AssetRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class AssetControllerTests {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;

	@Autowired
	private AssetRepository assetRepository;

	@BeforeEach
	void clearData() {
		assetRepository.deleteAll();
	}

	@Test
	void ASSET_001_createAndQuery_shouldSucceed() throws Exception {
		CreateAssetRequest request = new CreateAssetRequest();
		request.setAssetTag("AT-API-001");
		request.setName("Printer");
		request.setStatus(AssetStatus.IN_USE);
		request.setDepartment("OPS");

		String body = objectMapper.writeValueAsString(request);

		String created = mockMvc
			.perform(post("/api/v1/assets").contentType(MediaType.APPLICATION_JSON).content(body))
			.andExpect(status().isCreated())
			.andExpect(jsonPath("$.assetTag").value("AT-API-001"))
			.andReturn()
			.getResponse()
			.getContentAsString();

		long id = objectMapper.readTree(created).get("id").asLong();

		mockMvc
			.perform(get("/api/v1/assets/{id}", id))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.name").value("Printer"));

		mockMvc
			.perform(get("/api/v1/assets").param("page", "0").param("size", "10").param("keyword", "AT-API"))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.content[0].assetTag").value("AT-API-001"));
	}

	@Test
	void ASSET_001_create_withInvalidPayload_shouldReturn400() throws Exception {
		String invalidBody = """
			{
			  "assetTag": "",
			  "name": "",
			  "status": null
			}
			""";

		mockMvc
			.perform(post("/api/v1/assets").contentType(MediaType.APPLICATION_JSON).content(invalidBody))
			.andExpect(status().isBadRequest())
			.andExpect(jsonPath("$.message").value("Validation failed"));
	}
}
