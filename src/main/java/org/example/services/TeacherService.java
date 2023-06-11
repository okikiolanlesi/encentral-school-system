package org.example.services;

import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import org.example.dto.TeacherDto;
import org.example.entity.*;

import java.util.List;

public class TeacherService {

    private JPAQueryFactory queryFactory;
    private EntityManager entityManager;
    private TeacherDto teacherDto;

    public TeacherService(EntityManager em){
        this.queryFactory = new JPAQueryFactory(em);
        this.entityManager = em;
        this.teacherDto = new TeacherDto(entityManager);

    }

    public String create(Teacher teacher){
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            entityManager.persist(teacher);
            transaction.commit();
        }catch (Exception e){
            System.out.println(e.getMessage());
            transaction.rollback();
        }
        return "Teacher created successfully";
    }

    public List<Teacher> getAllTeachers(){
        return teacherDto.getAll();
    }

}