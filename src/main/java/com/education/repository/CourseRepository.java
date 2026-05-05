package com.education.repository;

import com.education.entity.Course;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface CourseRepository extends CrudRepository<Course, Integer> {

    @Query("SELECT c FROM Course c WHERE LOWER(c.name) LIKE LOWER(CONCAT('%', ?1, '%'))")
    List<Course> findByNameContaining(String name);

    @Query("SELECT c FROM Course c WHERE c.price =?1")
    List<Course> findByPrice(Double price);

    @Query("SELECT c FROM Course c WHERE c.duration =?1")
    List<Course> findByDuration(Integer duration);

    @Query("SELECT c FROM Course c WHERE c.price BETWEEN ?1 AND ?2")
    List<Course> findByPriceBetween(Double priceAfter, Double priceBefore);

    @Query("SELECT c FROM Course c WHERE c.createdDate BETWEEN ?1 AND ?2")
    List<Course> findByCreatedDateBetween(LocalDateTime createdDateAfter, LocalDateTime createdDateBefore);
}
