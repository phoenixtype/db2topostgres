package dev.phoenixtype.db2topostgres.repository;


import dev.phoenixtype.db2topostgres.model.StudentRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentsRepository extends JpaRepository<StudentRecord, Integer> {
}
