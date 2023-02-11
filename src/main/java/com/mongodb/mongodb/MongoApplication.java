package com.mongodb.mongodb;

import com.mongodb.mongodb.Student.Models.Address;
import com.mongodb.mongodb.Student.Models.Gender;
import com.mongodb.mongodb.Student.Models.Student;
import com.mongodb.mongodb.Student.StudentRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.math.BigDecimal;
import java.util.List;

@SpringBootApplication
public class MongoApplication {

	public static void main(String[] args) {
		SpringApplication.run(MongoApplication.class, args);
	}

	@Bean
	CommandLineRunner runner(StudentRepository studentRepository, MongoTemplate mongoTemplate) {
		return args -> {
			Address address = new Address(
					"USA",
					"New York",
					"Wall Street",
					"10005"
			);

			Student student = new Student(
					"John",
					"Doe",
					"johndoe@gmail.com",
					"password",
					Gender.MALE,
					address,
					List.of("Math", "Computer Science"),
					BigDecimal.TEN
			);

//			usingMongoTemplateAndQuery(studentRepository, mongoTemplate, student);

			studentRepository.findStudentByEmail(student.getEmail())
					.ifPresentOrElse(foundStudent -> {
						System.out.println("Student already exists: " + foundStudent);
					}, () -> {
						System.out.println("Inserting student: " + student);
						studentRepository.insert(student);
					});
		};
	}

	private static void usingMongoTemplateAndQuery(StudentRepository studentRepository, MongoTemplate mongoTemplate, Student student) {
		Query query = new Query();
		query.addCriteria(Criteria.where("email").is(student.getEmail()));

		List<Student> students = mongoTemplate.find(query, Student.class);

		if (students.size() > 1) {
			throw new IllegalStateException("Found one student or more with the same email");
		}

		if (students.isEmpty()) {
			System.out.println("Inserting student: " + student);
			studentRepository.insert(student);
		} else {
			System.out.println("Student already exists: " + student);
		}
	}
}
