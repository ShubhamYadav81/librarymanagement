package com.library.management.feign;

import java.util.List;

import org.springframework.cloud.loadbalancer.annotation.LoadBalancerClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.library.management.dto.BookDto;

@FeignClient(name = "book")
@LoadBalancerClient(name="book")
public interface BookClient {
	
	@GetMapping("book")
	List<BookDto> getAllBook();
	
	@GetMapping("book/{bookId}")
	BookDto getBookById(@PathVariable int bookId);
	
	@PostMapping("book")
	void add(@RequestBody BookDto bookDto);
	
	@DeleteMapping("book/{bookId}")
	void deleteById(@PathVariable int bookId);
	
	@PutMapping("book/{bookId}")
	BookDto updateDetails(@PathVariable int bookId,@RequestBody BookDto bookDto);
	

}
