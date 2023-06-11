package org.example.dto;

import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.example.entity.*;

import java.util.List;

public class TeacherDto {
    private EntityManager entityManager;
    private JPAQueryFactory queryFactory;

    QTeacher qTeacher = QTeacher.teacher;


    public TeacherDto(EntityManager entityManager){
        this.entityManager = entityManager;
        this.queryFactory = new JPAQueryFactory(entityManager);

    }

    public Teacher getRandomTeacher(){
        Long count = queryFactory.selectFrom(qTeacher).fetchResults().getTotal();

        if(count == 0){
            return null;
        }

        int randomIndex = (int) (Math.random() * count);

        Teacher randomTeacher = queryFactory.selectFrom(qTeacher).offset(randomIndex)
                .limit(1)
                .fetchOne();

        return randomTeacher;
    }

    public void create(Teacher teacher){
        entityManager.persist(teacher);
    }

    public List<Teacher> getAll(){
        List<Teacher> teachers = queryFactory.selectFrom(qTeacher).fetch();
        return teachers;
    }
}
