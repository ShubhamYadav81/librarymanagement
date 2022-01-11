package com.library.management.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
import com.library.management.dto.UserDto;
import com.library.management.feign.BookClient;
import com.library.management.feign.UserClient;
import com.library.management.service.LibraryService;

@RestController
@RequestMapping("/library")

public class LibraryUserController {
	@Autowired
	LibraryService libraryServices;
	@Autowired
	UserClient userClient;
	@Autowired
	BookClient bookClient;

	@GetMapping("/users")
	public	List<UserDto> getListOfUser() {
		return userClient.getAllUser();
	}

	@GetMapping("/users/{username}")
	public ResponseEntity<UserDto> getUserByUsername(@PathVariable String username) {
		UserDto userDto = userClient.getUser(username);
		List<Integer> bookIds = libraryServices.listOfBookIdIssued(username);
		List<BookDto> issuedBooks = new ArrayList<>();
		userDto.setBookDto(issuedBooks);
		if (!bookIds.isEmpty()) {
			bookIds.forEach(bookId -> issuedBooks.add(bookClient.getBookById(bookId)));
		}
		return new ResponseEntity<>(userDto, HttpStatus.OK);
	}

	@PostMapping("/users")
	@ResponseStatus(HttpStatus.CREATED)
	public void addUser(@RequestBody UserDto userDto){
		userClient.add(userDto);
	}

	@PutMapping("/users/{username}")
	public UserDto updateUser(@PathVariable String username, @RequestBody UserDto userDto){
		return  userClient.updateDetails(username, userDto);
	}

	@DeleteMapping("/users/{username}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deleteUser(@PathVariable String username){
		userClient.delete(username);
	}
}
