package com.mongodb.mongodb.Student;

import com.mongodb.mongodb.Student.Models.Address;
import com.mongodb.mongodb.Student.Models.Student;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class StudentService {
    private final StudentRepository studentRepository;

    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    public Optional<Student> findStudentById(String id) {
        return studentRepository.findById(id);
    }

    public Optional<Student> findStudentByEmail(String email) {
        return studentRepository.findStudentByEmail(email);
    }

    public Student createStudent(Student student) {
        List<String> verifyStudentInputs = verifyStudentInputs(student);
        List<String> verifyAddress = verifyAddress(student.getAddress());

        if (!verifyStudentInputs.isEmpty()) {
            throw new IllegalStateException("Student inputs are not valid");
        } else if (!verifyAddress.isEmpty()) {
            throw new IllegalStateException("Address inputs are not valid");
        }

        studentRepository.insert(student);

        return studentRepository.save(student);
    }

    public Student updateStudent(String id, Student student) {
        Student studentToUpdate = studentRepository.findById(id).orElseThrow(() -> new IllegalStateException("Student with id " + id + " does not exist"));

        if (student.getFirstName() != null) studentToUpdate.setFirstName(student.getFirstName());
        if (student.getLastName() != null) studentToUpdate.setLastName(student.getLastName());
        if (student.getEmail() != null) studentToUpdate.setEmail(student.getEmail());
        if (student.getPassword() != null) studentToUpdate.setPassword(student.getPassword());
        if (student.getGender() != null) studentToUpdate.setGender(student.getGender());
        if (student.getFavoriteSubjects() != null && student.getFavoriteSubjects().size() > 0) studentToUpdate.setFavoriteSubjects(student.getFavoriteSubjects());
        if (student.getTotalSpentInBooks() != null) studentToUpdate.setTotalSpentInBooks(student.getTotalSpentInBooks());
        if (student.getAddress() != null) studentToUpdate.setAddress(student.getAddress());

        return studentRepository.save(studentToUpdate);
    }

    public void deleteStudent(String id) {
        boolean exists = studentRepository.existsById(id);

        if (!exists) {
            throw new IllegalStateException("Student with id " + id + " does not exist");
        }

        studentRepository.deleteById(id);
    }

    public List<String> verifyStudentInputs(Student student) {
        List<String> errors = new ArrayList<>();

        if (student.getFirstName().isEmpty()) {
            errors.add("First name is required");
        }

        if (student.getLastName().isEmpty()) {
            errors.add("Last name is required");
        }

        if (student.getEmail().isEmpty()) {
            errors.add("Email is required");
        }

        String regexPattern = "^(.+)@(\\S+)$";
        if (!student.getEmail().matches(regexPattern)) {
            errors.add("Email is not valid");
        }

        if (student.getPassword().isEmpty()) {
            errors.add("Password is required");
        }

        return errors;
    }

    public List<String> verifyAddress(Address address) {
        List<String> errors = new ArrayList<>();

        if (address.getCity().isEmpty()) {
            errors.add("City is required");
        }

        if (address.getCountry().isEmpty()) {
            errors.add("Country is required");
        }

        if (address.getStreet().isEmpty()) {
            errors.add("Street is required");
        }

        if (address.getZipCode().isEmpty()) {
            errors.add("Zip code is required");
        }

        return errors;
    }
}




