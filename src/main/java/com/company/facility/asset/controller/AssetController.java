package com.company.facility.asset.controller;

import com.company.facility.asset.dto.AssetResponse;
import com.company.facility.asset.dto.CreateAssetRequest;
import com.company.facility.asset.service.AssetService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/assets")
public class AssetController {

	private final AssetService assetService;

	public AssetController(AssetService assetService) {
		this.assetService = assetService;
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public AssetResponse create(@Valid @RequestBody CreateAssetRequest request) {
		return assetService.create(request);
	}

	@GetMapping("/{id}")
	public AssetResponse getById(@PathVariable Long id) {
		return assetService.getById(id);
	}

	@GetMapping
	public Page<AssetResponse> list(
		@RequestParam(defaultValue = "0") int page,
		@RequestParam(defaultValue = "10") int size,
		@RequestParam(required = false) String keyword
	) {
		Pageable pageable = PageRequest.of(page, size);
		return assetService.list(keyword, pageable);
	}
}
