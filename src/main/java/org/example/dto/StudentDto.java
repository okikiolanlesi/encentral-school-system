package org.example.dto;

import com.google.common.base.Optional;
import com.google.common.base.Preconditions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.example.entity.*;

import java.util.List;

public class StudentDto {


    private EntityManager entityManager;
    private JPAQueryFactory queryFactory;

    QStudent qStudent = QStudent.student;
    QTeacher qTeacher = QTeacher.teacher;
    QCourse  qCourse = QCourse.course;
    QStudentHasCourse qStudentHasCourse = QStudentHasCourse.studentHasCourse;


    public StudentDto(EntityManager entityManager){
        this.entityManager = entityManager;
        this.queryFactory = new JPAQueryFactory(entityManager);
    }

    public Student getAStudentDetails(Long id){
        List<Student> students = queryFactory.selectFrom(qStudent)
                .leftJoin(qStudent.teacher, qTeacher)
                .leftJoin(qStudent.courses, qStudentHasCourse)
//                .leftJoin(qStudentHasCourse.course, qCourse)
                .where(qStudent.id.eq(id))
                .fetchJoin()
                .fetch();

        if(students.size() < 1){
            return null;
        }
        return students.get(0);
    }

    public Student getAStudent(Long id){
        Student students = queryFactory.selectFrom(qStudent)
                .where(qStudent.id.eq(id))
                .fetchFirst();

        return students;
    }

    @Transactional
    public void create(Student student){
        entityManager.persist(student);
    }

    public List<Student> getAll(){
        List<Student> students = queryFactory.selectFrom(qStudent).fetch();
        return students;
    }
}
