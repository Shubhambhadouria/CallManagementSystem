package com.kapturecx.exceptions;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(CallException.class)
	public ResponseEntity<ErrorFormatDetails> CallExceptionHandler(CallException se, WebRequest req) {

		ErrorFormatDetails err = new ErrorFormatDetails();
		err.setTimestamp(LocalDateTime.now());
		err.setMessage(se.getMessage());
		err.setDetails(req.getDescription(false));

		return new ResponseEntity<ErrorFormatDetails>(err, HttpStatus.NOT_FOUND);

	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ErrorFormatDetails> myMANVExceptionHandler(MethodArgumentNotValidException me) {
		ErrorFormatDetails err = new ErrorFormatDetails(LocalDateTime.now(), "Validation Error",
				me.getBindingResult().getFieldError().getDefaultMessage());
		return new ResponseEntity<>(err, HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(Exception.class)
	public ResponseEntity<ErrorFormatDetails> AllExceptionHandler(Exception se, WebRequest req) {

		ErrorFormatDetails err = new ErrorFormatDetails();
		err.setTimestamp(LocalDateTime.now());
		err.setMessage(se.getMessage());
		err.setDetails(req.getDescription(false));

		return new ResponseEntity<ErrorFormatDetails>(err, HttpStatus.NOT_FOUND);

	}

}
