# Delta for Assets

## ADDED Requirements

### Requirement: ASSET-001 create asset API
The system SHALL expose a create asset API for office asset registration.

#### Scenario: ASSET-001 create succeeds
- GIVEN a request contains `assetTag`, `name`, and `status`
- WHEN the client calls `POST /api/v1/assets`
- THEN the response status is `201`
- AND the response contains persisted asset data

#### Scenario: ASSET-001 duplicate assetTag
- GIVEN an existing asset has the same `assetTag`
- WHEN the client calls `POST /api/v1/assets`
- THEN the response status is `409`

### Requirement: ASSET-001 query asset APIs
The system SHALL expose query APIs by id and paginated list.

#### Scenario: ASSET-001 query by id
- GIVEN an existing asset id
- WHEN the client calls `GET /api/v1/assets/{id}`
- THEN the response status is `200`

#### Scenario: ASSET-001 query list
- GIVEN existing assets in storage
- WHEN the client calls `GET /api/v1/assets`
- THEN the response status is `200`
- AND a paged result is returned
