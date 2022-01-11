package com.library.management.exception;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpClientErrorException;

import feign.FeignException;

@RestControllerAdvice
public class GlobalHandler {

	@ExceptionHandler(HttpClientErrorException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public String handleClientException(HttpClientErrorException exe)
	{
		return exe.getMessage();
	}
	
	@ExceptionHandler(BookAlreadyIssuedException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public Map<String, String> handlesUserAlredyExist(BookAlreadyIssuedException bookOrUserNotFoundException,
			HttpServletRequest request) {

		Map<String, String> response = new HashMap<>();

		response.put("service", "library");
		response.put("timestamp", new Date().toString());
		response.put("error", bookOrUserNotFoundException.getMessage());
		response.put("status", HttpStatus.NOT_FOUND.name());
		return response;
	}
	
	@ExceptionHandler(CountOfBookIsThreeExcpetion.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public Map<String, String> handlesCountOfBookException(CountOfBookIsThreeExcpetion countOfBookIsThreeExcpetion,
			HttpServletRequest request) {

		Map<String, String> response = new HashMap<>();

		response.put("service", "library");
		response.put("timestamp", new Date().toString());
		response.put("error", countOfBookIsThreeExcpetion.getMessage());
		response.put("status", HttpStatus.NOT_FOUND.name());
		return response;
	}
	@ExceptionHandler(BookNotFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public Map<String, String> handlesBookNotFound(BookNotFoundException bookNotFound,
			HttpServletRequest request) {

		Map<String, String> response = new HashMap<>();

		response.put("service", "library");
		response.put("timestamp", new Date().toString());
		response.put("error", bookNotFound.getMessage());
		response.put("status", HttpStatus.NOT_FOUND.name());
		return response;
	}
	@ExceptionHandler(UserNotFound.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public Map<String, String> handlesUserNotFound(UserNotFound userNotFound,
			HttpServletRequest request) {

		Map<String, String> response = new HashMap<>();

		response.put("service", "library");
		response.put("timestamp", new Date().toString());
		response.put("error", userNotFound.getMessage());
		response.put("status", HttpStatus.NOT_FOUND.name());
		return response;
	}
	
	@ExceptionHandler(FeignException.class)
	public ResponseEntity<String> handleFeignClientError(FeignException exc) {
	    return new ResponseEntity<>(exc.contentUTF8(), HttpStatus.valueOf(exc.status()));
	}
}
