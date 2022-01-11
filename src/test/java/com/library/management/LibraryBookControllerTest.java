package com.library.management;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.library.management.controller.LibraryBookController;
import com.library.management.dto.BookDto;
import com.library.management.feign.BookClient;

@WebMvcTest(LibraryBookController.class)
class LibraryBookControllerTest {
	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private BookClient bookClient;

	private BookDto bookDto;

	@BeforeEach
	void setUp() {
		bookDto = new BookDto();
		bookDto.setId(1);
		bookDto.setAuthor("shubham");
		bookDto.setName("java");
		bookDto.setPublisher("shubham yadav");
	}

	@Test
	void shouldReturnOkStatusWhenBookListGet() throws Exception {

		when(bookClient.getAllBook()).thenReturn(new ArrayList<>());

		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/library/book");
		mockMvc.perform(requestBuilder).andExpect(status().isOk());
		verify(bookClient, times(1)).getAllBook();

	}

	@Test
	void shouldReturnOkStatusWhenBookRecordGet() throws Exception {

		when(bookClient.getBookById(0)).thenReturn(new BookDto());

		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/library/book/1");
		mockMvc.perform(requestBuilder).andExpect(status().isOk());
		verify(bookClient, times(1)).getBookById(1);

	}

	@Test
	void shouldReturnCreatedStatusWhenBookAdded() throws Exception {
		
		ObjectMapper mapper = new ObjectMapper();
		String bookDtoJson = mapper.writeValueAsString(bookDto);
		
		doNothing().when(bookClient).add(any(BookDto.class));

		MockHttpServletRequestBuilder requestBuilders = MockMvcRequestBuilders.post("/library/book")
				.contentType(MediaType.APPLICATION_JSON).content(bookDtoJson);
		mockMvc.perform(requestBuilders).andExpect(status().isCreated());
		
		verify(bookClient, times(1)).add(any(BookDto.class));

	}
	
	@Test
	void shouldReturnOkStatusWhenDetailUpdated()throws Exception
	{
		ObjectMapper mapper = new ObjectMapper();
		String bookDtoJson = mapper.writeValueAsString(bookDto);
		
		when(bookClient.updateDetails(anyInt(), any(BookDto.class))).thenReturn(bookDto);
		
		MockHttpServletRequestBuilder requestBuilders = MockMvcRequestBuilders.put("/library/book/1")
				.contentType(MediaType.APPLICATION_JSON).content(bookDtoJson);
		
		mockMvc.perform(requestBuilders).andExpect(status().isOk());
		
		verify(bookClient, times(1)).updateDetails(anyInt(), any(BookDto.class));

	}
	@Test
	void shouldReturnNotContentStatusBookDelete()throws Exception
	{
		
		
		doNothing().when(bookClient).deleteById(anyInt());
		
		MockHttpServletRequestBuilder requestBuilders = MockMvcRequestBuilders.delete("/library/book/1");
		mockMvc.perform(requestBuilders).andExpect(status().isNoContent());
		
		verify(bookClient, times(1)).deleteById(anyInt());

	}

}
