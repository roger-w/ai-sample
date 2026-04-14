package com.company.facility.asset.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.OffsetDateTime;

@Entity
@Table(name = "asset")
public class Asset {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "asset_tag", nullable = false, unique = true, length = 64)
	private String assetTag;

	@Column(nullable = false, length = 128)
	private String name;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false, length = 32)
	private AssetStatus status;

	@Column(length = 128)
	private String department;

	@Column(name = "created_at", nullable = false)
	private OffsetDateTime createdAt;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

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

	public OffsetDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(OffsetDateTime createdAt) {
		this.createdAt = createdAt;
	}
}
