#!/usr/bin/env bash
set -euo pipefail

ROOT_DIR="$(cd "$(dirname "$0")/.." && pwd)"
OPENSPEC_DIR="${ROOT_DIR}/openspec"
CHANGES_DIR="${OPENSPEC_DIR}/changes"

if [[ ! -d "${OPENSPEC_DIR}" ]]; then
  echo "ERROR: openspec directory does not exist."
  exit 1
fi

if [[ ! -d "${CHANGES_DIR}" ]]; then
  echo "ERROR: openspec/changes directory does not exist."
  exit 1
fi

validate_change() {
  local change_dir="$1"
  local missing=0
  local required_files=("proposal.md" "design.md" "tasks.md")

  for file in "${required_files[@]}"; do
    if [[ ! -f "${change_dir}/${file}" ]]; then
      echo "ERROR: missing ${file} in ${change_dir}"
      missing=1
    fi
  done

  if [[ ! -d "${change_dir}/specs" ]]; then
    echo "ERROR: missing specs directory in ${change_dir}"
    missing=1
  else
    local spec_count
    spec_count=$(find "${change_dir}/specs" -type f -name "*.md" | wc -l | tr -d ' ')
    if [[ "${spec_count}" -eq 0 ]]; then
      echo "ERROR: no spec markdown files found in ${change_dir}/specs"
      missing=1
    fi
  fi

  return "${missing}"
}

has_changes=0
failed=0
for change_dir in "${CHANGES_DIR}"/*; do
  if [[ -d "${change_dir}" ]] && [[ "$(basename "${change_dir}")" != "archive" ]]; then
    has_changes=1
    if ! validate_change "${change_dir}"; then
      failed=1
    fi
  fi
done

if [[ "${has_changes}" -eq 0 ]]; then
  echo "ERROR: no active changes found under openspec/changes"
  exit 1
fi

if [[ "${failed}" -eq 1 ]]; then
  echo "OpenSpec validation failed."
  exit 1
fi

echo "OpenSpec validation passed."
