package com.library.management;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.library.management.controller.LibraryUserBookController;
import com.library.management.dto.BookDto;
import com.library.management.dto.UserDto;
import com.library.management.feign.BookClient;
import com.library.management.feign.UserClient;
import com.library.management.service.LibraryService;

@WebMvcTest(LibraryUserBookController.class)
class LibraryUserBookControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private UserClient userClient;
	@MockBean 
	private BookClient bookClient;
	@MockBean
	LibraryService libraryServices;

	private BookDto bookDto;
	private UserDto userDto;

	@BeforeEach
	void setupBookDtoAndUserDto() {
		bookDto = new BookDto();
		bookDto.setId(1);
		bookDto.setAuthor("shubham");
		bookDto.setName("java");
		bookDto.setPublisher("shubham yadav");

		userDto = new UserDto();
		userDto.setUsername("shubham");
		userDto.setName("shubham yadav");
		userDto.setEmail("shubhamyadav@email.com");

	}
	@Test
	void testGetUserByUsernameWhenNoBooksIssued() throws Exception {
		
	    when(userClient.getUser(anyString())).thenReturn(new UserDto());
	    when(bookClient.getBookById(anyInt())).thenReturn(new BookDto());
	    doNothing().when(libraryServices).issuedBookToUser(anyInt(),anyString());
	 
	    MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/library/users/shubham/books/1");
	    mockMvc.perform(requestBuilder).andExpect(status().isCreated());
	    
	    verify(libraryServices,times(1)).issuedBookToUser(anyInt(),anyString());
	    
	}
	@Test
	void testGetUserByUsernameWhenRelesedd() throws Exception {
		
	    when(userClient.getUser(anyString())).thenReturn(new UserDto());
	    when(bookClient.getBookById(anyInt())).thenReturn(new BookDto());
	    doNothing().when(libraryServices).issuedBookToUser(anyInt(),anyString());
	 
	    MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/library/users/shubham/books/1");
	    mockMvc.perform(requestBuilder).andExpect(status().isNoContent());
	    
	    verify(libraryServices,times(1)).releasedBook(1, "shubham");
	    
	}



	
}
