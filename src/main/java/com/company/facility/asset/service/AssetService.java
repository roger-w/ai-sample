package com.company.facility.asset.service;

import com.company.facility.asset.dto.AssetResponse;
import com.company.facility.asset.dto.CreateAssetRequest;
import com.company.facility.asset.model.Asset;
import com.company.facility.asset.repository.AssetRepository;
import com.company.facility.common.exception.ConflictException;
import com.company.facility.common.exception.ResourceNotFoundException;
import java.time.OffsetDateTime;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AssetService {

	private final AssetRepository assetRepository;

	public AssetService(AssetRepository assetRepository) {
		this.assetRepository = assetRepository;
	}

	@Transactional
	public AssetResponse create(CreateAssetRequest request) {
		if (assetRepository.existsByAssetTag(request.getAssetTag())) {
			throw new ConflictException("Asset tag already exists: " + request.getAssetTag());
		}

		Asset asset = new Asset();
		asset.setAssetTag(request.getAssetTag());
		asset.setName(request.getName());
		asset.setStatus(request.getStatus());
		asset.setDepartment(request.getDepartment());
		asset.setCreatedAt(OffsetDateTime.now());

		return AssetResponse.from(assetRepository.save(asset));
	}

	@Transactional(readOnly = true)
	public AssetResponse getById(Long id) {
		Asset asset = assetRepository
			.findById(id)
			.orElseThrow(() -> new ResourceNotFoundException("Asset not found: " + id));
		return AssetResponse.from(asset);
	}

	@Transactional(readOnly = true)
	public Page<AssetResponse> list(String keyword, Pageable pageable) {
		Page<Asset> page;
		if (keyword == null || keyword.isBlank()) {
			page = assetRepository.findAll(pageable);
		} else {
			page = assetRepository.findByNameContainingIgnoreCaseOrAssetTagContainingIgnoreCase(
				keyword,
				keyword,
				pageable
			);
		}
		return page.map(AssetResponse::from);
	}
}
