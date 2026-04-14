package com.company.facility.asset.dto;

import com.company.facility.asset.model.Asset;
import com.company.facility.asset.model.AssetStatus;
import java.time.OffsetDateTime;

public record AssetResponse(
	Long id,
	String assetTag,
	String name,
	AssetStatus status,
	String department,
	OffsetDateTime createdAt
) {
	public static AssetResponse from(Asset asset) {
		return new AssetResponse(
			asset.getId(),
			asset.getAssetTag(),
			asset.getName(),
			asset.getStatus(),
			asset.getDepartment(),
			asset.getCreatedAt()
		);
	}
}
