package org.example.dto;

import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.example.entity.*;

public class StudentHasCourseDto {
    private EntityManager entityManager;
    private JPAQueryFactory queryFactory;

    public StudentHasCourseDto(EntityManager entityManager){
        this.entityManager = entityManager;
        this.queryFactory = new JPAQueryFactory(entityManager);
    }

    public void create(StudentHasCourse studentHasCourse){
        entityManager.persist(studentHasCourse);
    }

}
