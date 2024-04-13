package com.imsoftware.students.service.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.function.Function;
import java.util.stream.Collectors;

import com.imsoftware.students.repository.StudentRepository;
import org.springframework.stereotype.Service;

import com.imsoftware.students.domain.StudentDTO;
import com.imsoftware.students.entity.Student;
import com.imsoftware.students.entity.Subject;
import com.imsoftware.students.service.IStudentService;

@Service
public class StudentServiceImpl implements IStudentService {

	private final StudentRepository studentRepository;

	public StudentServiceImpl(StudentRepository studentRepository) {
		super();
		this.studentRepository = studentRepository;
	}

	@Override
	public Collection<StudentDTO> findAll() {
		return studentRepository.findAll().stream().map(new Function<Student, StudentDTO>() {
			@Override
			public StudentDTO apply(Student student) {
				List<String> programmingLanguagesKnowAbout = student.getSubjects().stream()
						.map(pl -> new String(pl.getName())).collect(Collectors.toList());
				return new StudentDTO(student.getName(), programmingLanguagesKnowAbout);
			}

		}).collect(Collectors.toList());
		
	}

	@Override
	public Collection<StudentDTO> findAllAndShowIfHaveAPopularSubject() {
		// TODO Obtener la lista de todos los estudiantes
		// e indicar la materia más concurrida existentes en la BD 
		// e indicar si el estudiante cursa o no la materia más concurrida registrado en la BD.
		
		// Obtener la lista de todos los estudiantes.
		List<Student> students = studentRepository.findAll();
		
		if (students.isEmpty()) {
			return null;
		} else {
			List<Subject> ls = new ArrayList<>();
			students.forEach(e -> {
				ls.addAll(e.getSubjects());
			});
			final Subject sub = ls.stream()
				.max(Comparator.comparing(Subject::getName))
				.orElseThrow(NoSuchElementException::new);
			
			System.out.println("Materia mas concurrida -> " + sub.getName());
			
			return students
					.stream().map(new Function<Student, StudentDTO>() {
				@Override
				public StudentDTO apply(Student student) {
					Boolean currentPopularSubject = 
							student.getSubjects().stream().anyMatch(e -> {
								return e.getName().equals(sub.getName());
							});
					
					return new StudentDTO(student.getName(), currentPopularSubject);
				}

			}).collect(Collectors.toList());
		}
	}

}
