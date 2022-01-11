package com.library.management.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.library.management.dto.BookDto;
import com.library.management.feign.BookClient;

@RestController
@RequestMapping("/library")

public class LibraryBookController {

	@Autowired
	private BookClient bookClient;

	@GetMapping("/book")
	public List<BookDto> getListOfBook() {
		return bookClient.getAllBook();
	}

	@GetMapping("/book/{bookId}")
	public BookDto getBook(@PathVariable int bookId) {
		return bookClient.getBookById(bookId);
	}

	@PostMapping("/book")
	@ResponseStatus(HttpStatus.CREATED)
	public void addBook(@RequestBody BookDto bookDto) {
		bookClient.add(bookDto);
	}

	@PutMapping("/book/{bookId}")
	public BookDto updateBookDetail(@PathVariable int bookId, @RequestBody BookDto bookDto) {
		return bookClient.updateDetails(bookId, bookDto);
	}

	@DeleteMapping("/book/{bookId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deleteBookDetail(@PathVariable int bookId) {
		bookClient.deleteById(bookId);
	}
}
