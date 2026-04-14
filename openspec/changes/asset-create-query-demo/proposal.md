# Proposal: ASSET-001 asset create/query demo

## Intent
Establish an OpenSpec-driven demo that proves requirement-to-code traceability in this project.

## Scope
- Add a minimal asset API:
  - `POST /api/v1/assets`
  - `GET /api/v1/assets/{id}`
  - `GET /api/v1/assets`
- Keep implementation lightweight and testable.
- Add CI checks to enforce OpenSpec artifact completeness.

## Out of Scope
- Asset assignment workflows
- File attachments and OSS integration
- Complex approval flows

## Acceptance Criteria
- OpenSpec artifacts for this change are complete and reviewable.
- The three APIs are implemented and covered by tests.
- CI fails when required OpenSpec artifacts are missing.
