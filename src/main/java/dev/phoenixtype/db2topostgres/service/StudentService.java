package dev.phoenixtype.db2topostgres.service;


import dev.phoenixtype.db2topostgres.model.StudentRecord;
import dev.phoenixtype.db2topostgres.repository.StudentsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StudentService {

    private final StudentsRepository studentRepository;

    public StudentRecord saveStudent(StudentRecord student) {
        return studentRepository.save(student);
    }

    public void insertStudents(List<StudentRecord> students) {
        studentRepository.saveAll(students);
    }
}
