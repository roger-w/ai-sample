# Assets Specification

## Requirement: Asset registration
The system SHALL allow authorized users to register an office asset with a unique asset tag.

### Scenario: Create asset successfully
- GIVEN a valid request with `assetTag`, `name`, and `status`
- WHEN the client calls `POST /api/v1/assets`
- THEN the system creates a new asset record
- AND returns HTTP 201 with the created asset payload

### Scenario: Reject duplicate asset tag
- GIVEN an existing asset with the same `assetTag`
- WHEN the client calls `POST /api/v1/assets`
- THEN the system rejects the request
- AND returns HTTP 409 with an error message

## Requirement: Asset query
The system SHALL allow users to query asset details by ID and list assets with pagination.

### Scenario: Query by id
- GIVEN an existing asset id
- WHEN the client calls `GET /api/v1/assets/{id}`
- THEN the system returns HTTP 200 with the asset details

### Scenario: Query by id not found
- GIVEN a non-existing asset id
- WHEN the client calls `GET /api/v1/assets/{id}`
- THEN the system returns HTTP 404

### Scenario: Paginated list
- GIVEN multiple assets are stored
- WHEN the client calls `GET /api/v1/assets?page=0&size=10&keyword=pc`
- THEN the system returns HTTP 200 with a paginated result
