package dev.phoenixtype.db2topostgres.controller;


import dev.phoenixtype.db2topostgres.model.StudentRecord;
import dev.phoenixtype.db2topostgres.service.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/student")
@RequiredArgsConstructor
public class StudentController {

    private final StudentService studentService;

    @PostMapping("/insert")
    public ResponseEntity<StudentRecord> insertStudent(@RequestBody StudentRecord student) {
        StudentRecord savedStudent = studentService.saveStudent(student);
        return new ResponseEntity<>(savedStudent, HttpStatus.CREATED);
    }

    @PostMapping
    public ResponseEntity<String> insertStudents(@RequestBody List<StudentRecord> students) {
        studentService.insertStudents(students);
        return ResponseEntity.ok("StudentRecord inserted successfully.");
    }
}
