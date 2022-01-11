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


import com.library.management.dto.UserDto;

@FeignClient(name = "user" )
@LoadBalancerClient(name="user")
public interface UserClient {
	
	@GetMapping("users")
	List<UserDto> getAllUser();
	
	@GetMapping("users/{username}")
	UserDto getUser(@PathVariable String username);
	
	@PostMapping("users")
	void add(@RequestBody UserDto userDto);
	
	@DeleteMapping("users/{username}")
	void delete(@PathVariable String username);
	
	@PutMapping("users/{username}")
	UserDto updateDetails(@PathVariable String username,@RequestBody UserDto userDto);
}
