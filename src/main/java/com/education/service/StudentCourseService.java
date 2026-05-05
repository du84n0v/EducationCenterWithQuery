package com.education.service;

import com.education.dto.*;
import com.education.entity.StudentCourse;
import com.education.repository.StudentCourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.LinkedList;
import java.util.List;

@Service
public class StudentCourseService {

    @Autowired
    private StudentCourseRepository repository;

    public StudentCourseDTO createStudentCourse(StudentCourseDTO dto) {
        StudentCourse studentCourse = new StudentCourse();
        studentCourse.setStudentId(dto.getStudentId());
        studentCourse.setCourseId(dto.getCourseId());
        studentCourse.setMark(dto.getMark());
        studentCourse.setCreatedDate(LocalDateTime.now());
        dto.setCreatedDate(studentCourse.getCreatedDate());
        repository.save(studentCourse);
        return dto;
    }

    public Boolean updateById(Integer id, StudentCourseDTO dto) {
        StudentCourse studentCourse = repository.getById(id);
        if(studentCourse == null){
            return false;
        }
        studentCourse.setStudentId(dto.getStudentId());
        studentCourse.setCourseId(dto.getCourseId());
        studentCourse.setMark(dto.getMark());
        repository.save(studentCourse);
        return true;

    }

    public StudentCourseDTO getStudentCourseById(Integer id) {
        StudentCourse sc = repository.getById(id);
        return (sc == null ? null : entityToDTO(sc));
    }

    public StudentCourseDTO entityToDTO(StudentCourse studentCourse){
        return new StudentCourseDTO(studentCourse.getStudentId(),
                studentCourse.getCourseId(),
                studentCourse.getMark(),
                studentCourse.getCreatedDate());
    }

    public StudentCourseDetailed getDetailInfo(Integer id) {
        StudentCourse sc = repository.getById(id);
        if(sc == null){
            return null;
        }
        StudentCourseDetailed result = new StudentCourseDetailed();
        result.setId(sc.getId());
        result.setMark(sc.getMark());
        result.setCreatedDate(sc.getCreatedDate());
        result.setStudentShort(new StudentShortInfo(sc.getStudentId(),
                sc.getStudent().getName(),
                sc.getStudent().getSurname()));
        result.setCourseShort(new CourseShortInfo(sc.getCourseId(),
                sc.getCourse().getName()));
        return result;
    }

    public Boolean deleteStudentCourseById(Integer id) {
        StudentCourse sc = repository.getById(id);
        if(sc == null){
            return false;
        }
        repository.deleteById(id);
        return true;
    }

    public List<StudentCourseDTO> getAll() {
        List<StudentCourse> optional = repository.getAll();
        List<StudentCourseDTO> result = new LinkedList<>();
        for (StudentCourse studentCourse : optional) {
            result.add(entityToDTO(studentCourse));
        }
        return result;
    }

    public List<StudentMarkDto> getStudentMarkOnDate(Integer studentId, LocalDate date) {
        LocalDateTime start = date.atStartOfDay();
        LocalDateTime end = date.atTime(LocalTime.MAX);
        return entityToStudentMark(repository.findByStudentIdAndCreatedDateBetween(studentId, start, end));
    }

    public List<StudentMarkDto> getStudentMarkBetweenDate(Integer studentId, LocalDateTime start, LocalDateTime end) {
        return entityToStudentMark(repository.findByStudentIdAndCreatedDateBetween(studentId, start, end));
    }

    public List<StudentMarkDto> entityToStudentMark(List<StudentCourse> list){
        List<StudentMarkDto> result = new LinkedList<>();
        for (StudentCourse ls : list) {
            result.add(new StudentMarkDto(ls.getCourse().getName(), ls.getMark(), ls.getCreatedDate()));
        }
        return result;
    }

    public List<StudentMarkDto> getStudentMarkDesc(Integer studentId) {
        return entityToStudentMark(repository.findByStudentIdOrderByCreatedDateDesc(studentId));
    }

    public List<StudentMarkDto> getStudentMarkOnCourse(Integer studentId, Integer courseId) {
        return entityToStudentMark(repository.findByStudentIdAndCourseIdOrderByCreatedDateDesc(studentId, courseId));
    }

    public StudentMarkDto getStudentLastMark(Integer studentId) {
        List<StudentMarkDto> result = entityToStudentMark(List.of(repository.findFirstByStudentIdOrderByCreatedDateDesc(studentId)));
        return (result.isEmpty() ? null : result.getFirst());
    }

    public List<StudentMarkDto> getStudentTop3Mark(Integer studentId) {
        List<StudentCourse> list = repository.findByStudentIdOrderByMarkDesc(studentId);

        List<StudentCourse> top3 = new LinkedList<>();
        for(int i = 0; i < Math.max(3, list.size()); ++ i){
            top3.add(list.get(i));
        }
        return entityToStudentMark(top3);
    }

    public StudentMarkDto getStudentFirstMark(Integer studentId) {
        List<StudentMarkDto> result = entityToStudentMark(List.of(repository.findFirstByStudentIdOrderByCreatedDate(studentId)));
        return (result.isEmpty() ? null : result.getFirst());
    }

    public StudentMarkDto getFirstCourseFirstMark(Integer studentId) {
        List<StudentMarkDto> response = entityToStudentMark(List.of(repository.findFirstByStudentIdOrderByCreatedDate(studentId)));
        return (response.isEmpty() ? null : response.getFirst());
    }

    public StudentMarkDto getStudentTopMarkOnCourse(Integer studentId, Integer courseId) {
        List<StudentMarkDto> response = entityToStudentMark(List.of(repository.findFirstByStudentIdAndCourseIdOrderByMark(studentId, courseId)));
        return (response.isEmpty() ? null : response.getFirst());
    }

    public Double getStudentAvgMark(Integer studentId) {
        return repository.findStudentAvgMark(studentId);
    }

    public Double getStudentAvgMarkOnCourse(Integer studentId, Integer courseId) {
        return repository.findStudentAvgMarkOnCourse(studentId, courseId);
    }

    public Long getMarkCountGreaterThenTarget(Integer studentId, Double targetMark) {
        return repository.countByStudentIdAndMarkGreaterThan(studentId, targetMark);
    }

    public Double getTopMarkOnCourse(Integer courseId) {
        return repository.findTopMarkOnCourse(courseId);
    }

    public Double getAvgMarkOnCourse(Integer courseId) {
        return repository.findAvgMarkOnCourse(courseId);
    }

    public Long getMarkCountOnCourse(Integer courseId) {
        return repository.countByCourseId(courseId);
    }
}
