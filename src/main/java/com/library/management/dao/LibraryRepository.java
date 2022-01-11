package com.library.management.dao;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.library.management.model.Library;

import java.util.*;

public interface LibraryRepository extends CrudRepository<Library,Integer> {
	
	int countByUsername(String username);
	
	boolean existsByUsernameAndBookid(String username,int id);
	
	boolean existsByUsername(String username);
	
	boolean existsByBookid(int id);
	
	@Query("select lib.bookid from Library lib where lib.username=:username")
	List<Integer> listOfBookIssued(String username);
	
	@Transactional
	void deleteByUsernameAndBookid(String username,int id);
}
