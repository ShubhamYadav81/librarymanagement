package com.library.management;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.assertj.core.util.Arrays;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.library.management.controller.LibraryUserController;
import com.library.management.dto.BookDto;
import com.library.management.dto.UserDto;
import com.library.management.feign.BookClient;
import com.library.management.feign.UserClient;
import com.library.management.service.LibraryService;

@WebMvcTest(LibraryUserController.class)
 class LibraryUserControllerTest {
	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private UserClient userClient;
	@MockBean 
	private BookClient bookClient;
	@MockBean
	private LibraryService libraryServices;
	@MockBean
	private ModelMapper modelMapper;

	private UserDto userDto;
	private BookDto bookDto;
	@BeforeEach
	void setUp() {
		userDto = new UserDto();
		userDto.setUsername("shubham");
		userDto.setName("shubham yadav");
		userDto.setEmail("shubhamyadav@email.com");
		
		bookDto=new BookDto();
		bookDto.setId(1);
		bookDto.setName("shubham");
	}
	@Test
	void shouldReturnOkStatusWhenUserListGet() throws Exception {

		when(userClient.getAllUser()).thenReturn(new ArrayList<>());

		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/library/users");
		mockMvc.perform(requestBuilder).andExpect(status().isOk());
		verify(userClient, times(1)).getAllUser();

	}

	@Test
	void shouldReturnokStatusWhenUserIsEmpty() throws Exception {
	    when(userClient.getUser(anyString())).thenReturn(new UserDto());
	    
	    when(libraryServices.listOfBookIdIssued(anyString())).thenReturn(new ArrayList<>());
	    MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/library/users/shubham");
	    mockMvc.perform(requestBuilder).andExpect(status().isOk());
	    
	    verify(userClient, times(1)).getUser(anyString());
	    verify(libraryServices, times(1)).listOfBookIdIssued(anyString());
	    
	}

	@Test
	void shouldReturnokStatusWhenUserIssuedBook() throws Exception {
	    when(userClient.getUser(anyString())).thenReturn(new UserDto());
	    List<Integer> s1=new ArrayList<Integer>();
	    s1.add(1);
	    when(libraryServices.listOfBookIdIssued(anyString())).thenReturn(s1);
	    when(bookClient.getBookById(anyInt())).thenReturn(bookDto);
	
	    MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/library/users/shubham");
	    mockMvc.perform(requestBuilder).andExpect(status().isOk());
	    
	    verify(userClient, times(1)).getUser(anyString());
	    verify(libraryServices, times(1)).listOfBookIdIssued(anyString());
	    
	}
	@Test
	void shouldReturnOkStatusWhenDetailUpdated()throws Exception
	{
		ObjectMapper mapper = new ObjectMapper();
		String bookDtoJson = mapper.writeValueAsString(bookDto);
		
		when(userClient.updateDetails(anyString(), any(UserDto.class))).thenReturn(userDto);
		
		MockHttpServletRequestBuilder requestBuilders = MockMvcRequestBuilders.put("/library/users/shubham")
				.contentType(MediaType.APPLICATION_JSON).content(bookDtoJson);
		
		mockMvc.perform(requestBuilders).andExpect(status().isOk());
		
		verify(userClient, times(1)).updateDetails(anyString(), any(UserDto.class));

	}
	@Test
	void shouldReturnNotContentStatusBookDelete()throws Exception
	{
		
		
		doNothing().when(userClient).delete(anyString());
		
		MockHttpServletRequestBuilder requestBuilders = MockMvcRequestBuilders.delete("/library/users/shubham");
		mockMvc.perform(requestBuilders).andExpect(status().isNoContent());
		
		verify(userClient, times(1)).delete(anyString());

	}
	@Test
	void shouldReturnCreatedStatusWhenUserAdded() throws Exception {
		
		ObjectMapper mapper = new ObjectMapper();
		String bookDtoJson = mapper.writeValueAsString(userDto);
		
		doNothing().when(userClient).add(userDto);

		MockHttpServletRequestBuilder requestBuilders = MockMvcRequestBuilders.post("/library/users")
				.contentType(MediaType.APPLICATION_JSON).content(bookDtoJson);
		mockMvc.perform(requestBuilders).andExpect(status().isCreated());
		
		verify(userClient, times(1)).add(any(UserDto.class));

	}
	
}
