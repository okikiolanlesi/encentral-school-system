package org.example.services;

import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import org.example.dto.StudentHasCourseDto;
import org.example.entity.*;

import java.util.List;

public class StudentHasCourseService {

    private JPAQueryFactory queryFactory;
    private EntityManager entityManager;
    private StudentHasCourseDto studentHasCourseDto;

    public StudentHasCourseService(EntityManager em){
        this.queryFactory = new JPAQueryFactory(em);
        this.entityManager = em;
        this.studentHasCourseDto = new StudentHasCourseDto(entityManager);
    }

    public String create(StudentHasCourse studentHasCourse){
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            studentHasCourseDto.create(studentHasCourse);
            transaction.commit();
        }catch (Exception e){
            System.out.println(e.getMessage());
            transaction.rollback();
        }
        return "New entry created successfully";
    }

}
