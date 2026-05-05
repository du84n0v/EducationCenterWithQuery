package com.education.repository;

import com.education.entity.Student;
import com.education.enums.Gender;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface StudentRepository extends CrudRepository<Student, Integer> {

    @Query("FROM Student")
    List<Student> findAllStudent();

    @Query("FROM Student s WHERE s.id = ?1")
    Student getById(Integer id);

    @Query("FROM Student s WHERE LOWER(s.name) LIKE LOWER(?1) ")
    List<Student> getAllByName(String name);

    @Query("FROM Student s WHERE s.surname = ?1")
    List<Student> getAllBySurname(String surname);

    @Query("FROM Student s WHERE s.level =?1")
    List<Student> getAllByLevel(Integer level);

    @Query("FROM Student s WHERE s.gender =?1")
    List<Student> getAllByGender(Gender gender);

    @Query("FROM Student s WHERE s.age =?1")
    List<Student> getAllByAge(Integer age);

    @Query("FROM Student s WHERE s.createdDate = ?1")
    List<Student> getAllByCreatedDate(LocalDateTime date);

    @Query("FROM Student s WHERE s.createdDate BETWEEN ?1 AND ?2")
    List<Student> getAllByCreatedDateBetween(LocalDateTime start, LocalDateTime end);
}
