package com.mongodb.mongodb.Student;

import com.mongodb.mongodb.Student.Models.Student;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/v1/student")
@AllArgsConstructor
public class StudentController {
    private final StudentService studentService;
    @GetMapping
    public List<Student> fetchAllStudents() {
        return studentService.getAllStudents();
    }

    @GetMapping("{id}")
    public Optional<Student> findStudentById(@PathVariable("id") String id) {
        return studentService.findStudentById(id);
    }

    @GetMapping("email/{email}")
    public Optional<Student> findStudentByEmail(@PathVariable("email") String email) {
        return studentService.findStudentByEmail(email);
    }

    @PostMapping
    public Student createStudent(@RequestBody Student student) {
        return studentService.createStudent(student);
    }

    @PatchMapping("{id}")
    public Student updateStudent(@PathVariable("id") String id, @RequestBody Student student) {
        return studentService.updateStudent(id, student);
    }

    @DeleteMapping("{id}")
    public void deleteStudent(@PathVariable("id") String id) {
        studentService.deleteStudent(id);
    }
}
