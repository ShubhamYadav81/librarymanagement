package com.library.management.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.library.management.exception.BookAlreadyIssuedException;
import com.library.management.exception.BookNotFoundException;
import com.library.management.exception.CountOfBookIsThreeExcpetion;
import com.library.management.exception.UserNotFound;
import com.library.management.feign.BookClient;
import com.library.management.feign.UserClient;
import com.library.management.service.LibraryService;

@RestController
@RequestMapping("library/users/{username}/books/{bookId}")

public class LibraryUserBookController {


	@Autowired
	RestTemplate restTemplate;
	@Autowired
	LibraryService libraryServices;
	@Autowired
	BookClient bookClient;
	@Autowired
	UserClient userClient;

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public void issuedBook(@PathVariable String username, @PathVariable int bookId) throws CountOfBookIsThreeExcpetion, BookAlreadyIssuedException {
		userClient.getUser(username);
		bookClient.getBookById(bookId);
		libraryServices.issuedBookToUser(bookId, username);
	}

	@DeleteMapping
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void releasedBook(@PathVariable String username, @PathVariable int bookId)
			throws BookAlreadyIssuedException, BookNotFoundException, UserNotFound {
		userClient.getUser(username);
		bookClient.getBookById(bookId);
		libraryServices.releasedBook(bookId, username);
	}

}
