package com.education.repository;

import com.education.entity.StudentCourse;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface StudentCourseRepository extends CrudRepository<StudentCourse, Integer> {

    @Query("SELECT sc FROM StudentCourse sc WHERE sc.id =?1")
    StudentCourse getById(Integer id);

    @Query("SELECT sc FROM StudentCourse sc WHERE sc.studentId =?1 AND sc.createdDate BETWEEN ?2 AND ?3")
    List<StudentCourse> findByStudentIdAndCreatedDateBetween(Integer studentId, LocalDateTime start, LocalDateTime end);

    @Query("SELECT sc FROM StudentCourse sc WHERE sc.studentId =?1 ORDER BY sc.createdDate DESC")
    List<StudentCourse> findByStudentIdOrderByCreatedDateDesc(Integer studentId);

    @Query("SELECT sc FROM StudentCourse sc WHERE sc.studentId =?1 AND sc.courseId =?2 ORDER BY sc.createdDate DESC")
    List<StudentCourse> findByStudentIdAndCourseIdOrderByCreatedDateDesc(Integer studentId, Integer courseId);

    @Query("SELECT sc FROM StudentCourse sc")
    List<StudentCourse> getAll();

    @Query("SELECT sc FROM StudentCourse sc WHERE sc.studentId =?1 ORDER BY sc.createdDate DESC limit 1")
    StudentCourse findFirstByStudentIdOrderByCreatedDateDesc(Integer studentId);

    @Query("SELECT sc FROM StudentCourse sc WHERE sc.studentId =?1 ORDER BY sc.mark DESC LIMIT 3")
    List<StudentCourse> findByStudentIdOrderByMarkDesc(Integer studentId);

    @Query("SELECT sc FROM StudentCourse sc WHERE sc.studentId =?1 ORDER BY sc.createdDate DESC LIMIT 1")
    StudentCourse findFirstByStudentIdOrderByCreatedDate(Integer studentId);

    @Query("SELECT sc FROM StudentCourse sc WHERE sc.studentId =?1 AND sc.courseId =?2 ORDER BY sc.mark DESC LIMIT 1")
    StudentCourse findFirstByStudentIdAndCourseIdOrderByMark(Integer studentId, Integer courseId);

    @Query("SELECT AVG(sc.mark) FROM StudentCourse sc WHERE sc.studentId =?1")
    Double findStudentAvgMark(Integer studentId);

    @Query("SELECT AVG(sc.mark) FROM StudentCourse sc WHERE sc.studentId =?1 AND sc.courseId =?2")
    Double findStudentAvgMarkOnCourse(Integer studentId, Integer courseId);

    @Query("SELECT COUNT(sc) FROM StudentCourse sc WHERE sc.studentId =?1 AND sc.mark > ?2")
    Long countByStudentIdAndMarkGreaterThan(Integer studentId, Double targetMark);

    @Query("SELECT sc.mark FROM StudentCourse sc WHERE sc.courseId =?1 ORDER BY sc.mark DESC LIMIT 1")
    Double findTopMarkOnCourse(Integer courseId);

    @Query("SELECT AVG(sc.mark) FROM StudentCourse sc WHERE sc.courseId =?1")
    Double findAvgMarkOnCourse(Integer courseId);

    @Query("SELECT COUNT(sc) FROM StudentCourse sc WHERE sc.courseId =?1")
    Long countByCourseId(Integer courseId);
}
