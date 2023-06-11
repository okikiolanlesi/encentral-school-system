package org.example.dto;

import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.example.entity.*;

import java.util.List;

public class CourseDto {
    private EntityManager entityManager;
    private JPAQueryFactory queryFactory;

    QStudent qStudent = QStudent.student;
    QTeacher qTeacher = QTeacher.teacher;
    QCourse qCourse = QCourse.course;
    QStudentHasCourse qStudentHasCourse = QStudentHasCourse.studentHasCourse;


    public CourseDto(EntityManager entityManager){
        this.entityManager = entityManager;
        this.queryFactory = new JPAQueryFactory(entityManager);
    }

    public Course getACourse(Long id){
        Course course = queryFactory.selectFrom(qCourse).where(qCourse.id.eq(id)).fetchOne();
        return course ;
    }

    public void create(Course course){
        entityManager.persist(course);
    }

    public Course getCourseByTitle(String title){
        Course course = queryFactory.selectFrom(qCourse).where(qCourse.title.eq(title)).fetchOne();

        return course;
    }

    public List<Course> getAll(){
        List<Course> courses = queryFactory.selectFrom(qCourse).fetch();
        return courses;
    }
}
