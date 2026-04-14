package com.company.facility.asset.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.company.facility.asset.dto.CreateAssetRequest;
import com.company.facility.asset.model.Asset;
import com.company.facility.asset.model.AssetStatus;
import com.company.facility.asset.repository.AssetRepository;
import com.company.facility.common.exception.ConflictException;
import com.company.facility.common.exception.ResourceNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
class AssetServiceTests {

	@Autowired
	private AssetService assetService;

	@Autowired
	private AssetRepository assetRepository;

	@BeforeEach
	void clearData() {
		assetRepository.deleteAll();
	}

	@Test
	void ASSET_001_createDuplicateAssetTag_shouldThrowConflict() {
		Asset existed = new Asset();
		existed.setAssetTag("AT-001");
		existed.setName("Laptop");
		existed.setStatus(AssetStatus.IN_USE);
		existed.setCreatedAt(java.time.OffsetDateTime.now());
		assetRepository.save(existed);

		CreateAssetRequest request = new CreateAssetRequest();
		request.setAssetTag("AT-001");
		request.setName("Laptop-2");
		request.setStatus(AssetStatus.IDLE);

		assertThrows(ConflictException.class, () -> assetService.create(request));
	}

	@Test
	void ASSET_001_getById_missing_shouldThrowNotFound() {
		assertThrows(ResourceNotFoundException.class, () -> assetService.getById(999L));
	}

	@Test
	void ASSET_001_createAndGet_shouldSucceed() {
		CreateAssetRequest request = new CreateAssetRequest();
		request.setAssetTag("AT-100");
		request.setName("Monitor");
		request.setStatus(AssetStatus.IDLE);
		request.setDepartment("IT");

		var created = assetService.create(request);
		var found = assetService.getById(created.id());
		assertEquals("AT-100", found.assetTag());
		assertEquals("Monitor", found.name());
	}
}
