# Design: ASSET-001

## Overview
The demo adds a single bounded context `asset` with a standard Spring Boot layering:
- Controller: API contract and validation
- Service: business rules (duplicate check, lookup)
- Repository: JPA persistence
- Model/DTO: persistence model and API payloads

## Data Model
- `asset`
  - `id` (PK, auto increment)
  - `asset_tag` (unique, non-null)
  - `name` (non-null)
  - `status` (enum string)
  - `department` (optional)
  - `created_at` (non-null)

## Error Handling
- Duplicate `assetTag` -> HTTP 409
- Missing/invalid params -> HTTP 400
- Asset not found -> HTTP 404

## Test Strategy
- Service unit tests for duplicate/not-found branches
- Controller tests for API behavior and status codes

## Traceability
- Spec ID: `ASSET-001`
- Test names include `ASSET_001` prefix for quick grep and CI trace.
