package com.company.facility.asset.repository;

import com.company.facility.asset.model.Asset;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AssetRepository extends JpaRepository<Asset, Long> {

	boolean existsByAssetTag(String assetTag);

	Page<Asset> findByNameContainingIgnoreCaseOrAssetTagContainingIgnoreCase(
		String nameKeyword,
		String tagKeyword,
		Pageable pageable
	);
}
