package com.company.facility.common.web;

import com.company.facility.common.exception.ConflictException;
import com.company.facility.common.exception.ResourceNotFoundException;
import java.time.OffsetDateTime;
import java.util.HashMap;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<Map<String, Object>> handleNotFound(ResourceNotFoundException ex) {
		return buildError(HttpStatus.NOT_FOUND, ex.getMessage(), null);
	}

	@ExceptionHandler(ConflictException.class)
	public ResponseEntity<Map<String, Object>> handleConflict(ConflictException ex) {
		return buildError(HttpStatus.CONFLICT, ex.getMessage(), null);
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<Map<String, Object>> handleValidation(MethodArgumentNotValidException ex) {
		Map<String, String> fields = new HashMap<>();
		for (FieldError error : ex.getBindingResult().getFieldErrors()) {
			fields.put(error.getField(), error.getDefaultMessage());
		}
		return buildError(HttpStatus.BAD_REQUEST, "Validation failed", fields);
	}

	private ResponseEntity<Map<String, Object>> buildError(
		HttpStatus status,
		String message,
		Map<String, String> fields
	) {
		Map<String, Object> body = new HashMap<>();
		body.put("timestamp", OffsetDateTime.now());
		body.put("status", status.value());
		body.put("error", status.getReasonPhrase());
		body.put("message", message);
		if (fields != null && !fields.isEmpty()) {
			body.put("fields", fields);
		}
		return ResponseEntity.status(status).body(body);
	}
}
