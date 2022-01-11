package com.library.management.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.library.management.dao.LibraryRepository;
import com.library.management.exception.BookAlreadyIssuedException;
import com.library.management.exception.BookNotFoundException;
import com.library.management.exception.CountOfBookIsThreeExcpetion;
import com.library.management.exception.UserNotFound;
import com.library.management.model.Library;

@Service
public class LibraryService {

	@Autowired
	LibraryRepository libraryRepository;

	public void issuedBookToUser(int bookId, String username)
			throws CountOfBookIsThreeExcpetion, BookAlreadyIssuedException {

		if (libraryRepository.countByUsername(username) > 2) {
			throw new CountOfBookIsThreeExcpetion("can't issue more than 3 books");
		} else if (libraryRepository.existsByUsernameAndBookid(username, bookId)) {
			throw new BookAlreadyIssuedException("Book already issued to you");
		}
		Library library = new Library();
		library.setUsername(username);
		library.setBookid(bookId);
		libraryRepository.save(library);
	}

	public void releasedBook(int bookId, String username) throws BookNotFoundException, UserNotFound {

		isUserNamePresent(username);
		isBookPresent(bookId);
		libraryRepository.deleteByUsernameAndBookid(username, bookId);
	}

	public boolean isUserNamePresent(String username) throws UserNotFound {
		if (!libraryRepository.existsByUsername(username)) {
			return true;
		}
		throw new UserNotFound("username not found");
	}

	public boolean isBookPresent(int bookId) throws BookNotFoundException {
		if (libraryRepository.existsByBookid(bookId)) {
			return true;
		}
		throw new BookNotFoundException("book not found");
	}

	public List<Integer> listOfBookIdIssued(String username) {
		return libraryRepository.listOfBookIssued(username);
	}

}
