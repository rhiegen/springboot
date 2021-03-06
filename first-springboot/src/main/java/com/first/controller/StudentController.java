package com.first.controller;

import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.first.exception.ApiRequestException;
import com.first.model.Student;
import com.first.repository.StudentRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@RestController
@RequestMapping("/student")
public class StudentController {

	private final StudentRepository studentDAO;

	@Autowired
	public StudentController(StudentRepository studentDAO) {
		this.studentDAO = studentDAO;
	}

	/*Adding pageable
	 * */
	@GetMapping
	public ResponseEntity<?> listAll(Pageable pageable) {
		return new ResponseEntity<>(studentDAO.findAll(pageable), HttpStatus.OK);

	}

	@GetMapping(path = "/{id}")
	public ResponseEntity<?> getStudentById(@PathVariable("id") Long id,
			@AuthenticationPrincipal UserDetails userDetails) { // annotation to capture user credentials, 
		//it's unnecessary for the system to work, just curiosity
		//System.out.println("xxxxx "+ userDetails); 
		verifyIfStudentExists(id);
		PasswordEncoderGenerator();
		Optional<Student> student = studentDAO.findById(id);
		return new ResponseEntity<>(student, HttpStatus.OK);

	}

	/*
	 * Adding variable validation
	 * */
	@PostMapping
	@Transactional(rollbackFor = Exception.class)
	public ResponseEntity<?> save(@Valid @RequestBody Student student) {
		return new ResponseEntity<>(studentDAO.save(student), HttpStatus.CREATED);
	}

	@DeleteMapping(path = "/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<?> delete(@PathVariable Long id) {
		verifyIfStudentExists(id);
		studentDAO.deleteById(id);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@PutMapping
	public ResponseEntity<?> update(@RequestBody Student student) {
		verifyIfStudentExists(student.getId());
		studentDAO.save(student);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@GetMapping(path = "/findByName/{name}")
	public ResponseEntity<?> findStudentByName(@PathVariable String name) {
		return new ResponseEntity<>(studentDAO.findByName(name), HttpStatus.OK);
	}

	@GetMapping(path = "/findLike/{name}")
	public ResponseEntity<?> findStudentByLike(@PathVariable String name) {
		return new ResponseEntity<>(studentDAO.findByNameIgnoreCaseContaining(name), HttpStatus.OK);
	}

	/*Adding id verification for the student object
	 * */
	private void verifyIfStudentExists(Long id) {
		Optional<Student> student = studentDAO.findById(id);
		if (student.get() == null) {
			throw new ApiRequestException("estudante não encontrado para o id:  "+id);
		}
	}

	public void PasswordEncoderGenerator() {
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		String hashedPassword = passwordEncoder.encode("admin");
		System.out.println("admin  " + hashedPassword);
		String hashedPassword2 = passwordEncoder.encode("user");
		System.out.println("user   " + hashedPassword2);

		
		
	}
}
