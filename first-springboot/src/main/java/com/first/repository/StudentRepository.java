package com.first.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.first.model.Student;

public interface StudentRepository extends CrudRepository<Student,Long> {
	List<Student> findByName(String name);
	List<Student> findByNameIgnoreCaseContaining(String name);


}
