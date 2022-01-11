package com.library.management.dto;


import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class UserDto {
	
	private String username;
	private String email;
	private String name;
	private List<BookDto> bookDto;
}
