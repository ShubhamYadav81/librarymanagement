package com.library.management;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.library.management.dao.LibraryRepository;
import com.library.management.exception.BookAlreadyIssuedException;
import com.library.management.exception.BookNotFoundException;
import com.library.management.exception.CountOfBookIsThreeExcpetion;
import com.library.management.exception.UserNotFound;
import com.library.management.model.Library;
import com.library.management.service.LibraryService;

@ExtendWith(MockitoExtension.class)
@ExtendWith(SpringExtension.class)
class LibraryServicesTest {
	@InjectMocks 
	LibraryService libraryServices;
	
	@MockBean
	LibraryRepository libraryRepository;
	
	@Test
	void shouldReturnNothingWhenBookIssued()throws CountOfBookIsThreeExcpetion, BookAlreadyIssuedException
	{
		when(libraryRepository.countByUsername("shubham")).thenReturn(2);
		libraryServices.issuedBookToUser(1,"shubham");	
		verify(libraryRepository,times(1)).save(any(Library.class));
	}
	@Test
	void shouldThrowErrorWhenBookNotIssued()throws CountOfBookIsThreeExcpetion
	{
		when(libraryRepository.countByUsername("shubham")).thenReturn(4);
		assertThrows(CountOfBookIsThreeExcpetion.class,()->{libraryServices.issuedBookToUser(1,"shubham");});
	}
	/*@Test
	void shouldReturnNothingWhenBookReleased()throws BookAlreadyIssuedException, BookNotFoundException, UserNotFound
	{
		when(libraryRepository.existsByBookid(1)).thenReturn(true);
		when(libraryRepository.existsByUsername("shubham")).thenReturn(true);
		libraryServices.isBookPresent(1);
		libraryServices.isUserNamePresent("shubham");
		libraryServices.releasedBook(1, "shubham");
		verify(libraryRepository,times(1)).deleteByUsernameAndBookid("shubham", 1);
	}*/
	@Test
	void shouldThrowBookAndUserNotFoundExceptionWhenBookNotReleased()throws BookNotFoundException
	{
		when(libraryRepository.existsByBookid(1)).thenReturn(false);
		when(libraryRepository.existsByUsername("shubham")).thenReturn(false);
		assertThrows(BookNotFoundException.class, ()->{libraryServices.releasedBook(1,"shubham");});
	}
	@Test
	void shouldReturnListOfBookIdWhenBookIssued()
	{
		List<Integer> bookIdList=new ArrayList<>();
		bookIdList.add(1);
		when(libraryRepository.listOfBookIssued("shubham")).thenReturn(bookIdList);
		libraryServices.listOfBookIdIssued("shubham");
		assertEquals(1, bookIdList.get(0));
	}
}
