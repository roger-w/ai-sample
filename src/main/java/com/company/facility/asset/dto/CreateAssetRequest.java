package com.company.facility.asset.dto;

import com.company.facility.asset.model.AssetStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class CreateAssetRequest {

	@NotBlank
	@Size(max = 64)
	private String assetTag;

	@NotBlank
	@Size(max = 128)
	private String name;

	@NotNull
	private AssetStatus status;

	@Size(max = 128)
	private String department;

	public String getAssetTag() {
		return assetTag;
	}

	public void setAssetTag(String assetTag) {
		this.assetTag = assetTag;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public AssetStatus getStatus() {
		return status;
	}

	public void setStatus(AssetStatus status) {
		this.status = status;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}
}
