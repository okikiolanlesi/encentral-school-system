package org.example.services;

import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import org.example.dto.CourseDto;
import org.example.dto.StudentDto;
import org.example.dto.StudentHasCourseDto;
import org.example.dto.TeacherDto;
import org.example.entity.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class CourseService {

    private JPAQueryFactory queryFactory;
    private EntityManager entityManager;
    QCourse qCourse = QCourse.course;
    StudentDto studentDto;
    TeacherDto teacherDto;
    CourseDto courseDto;
    StudentHasCourseDto studentHasCourseDto;

    public CourseService(EntityManager em){
        this.queryFactory = new JPAQueryFactory(em);
        this.entityManager = em;
        this.studentDto = new StudentDto(entityManager);
        this.teacherDto = new TeacherDto(entityManager);
        this.courseDto = new CourseDto(entityManager);
        this.studentHasCourseDto = new StudentHasCourseDto(entityManager);
    }

    public String registerCourses(Long studentId, List<Long> courses){
        String returnMessage = "Courses registered successfully";
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
//            Validate studentId
            Student student = studentDto.getAStudent(studentId);

            if(student == null){
                throw new Exception("Invalid student id passed: '" + studentId + "'.");
            }

//            Validate courses and register
            if(courses.size() < 5 || courses.size() >7){
                throw new Exception("You have to register between 5 to 7 courses");
            }
//            Make sure there are no duplicates in the list
            Boolean hasDuplicates = hasDuplicates(courses);
            if(hasDuplicates){
                throw new Exception("Found same course id passed more than once");
            }

            for(Long id: courses){
                Course course = courseDto.getACourse(id);
                if(course == null){
                    throw new Exception("Invalid course id passed: '" + id + "'.");
                }
                StudentHasCourse newEntry = new StudentHasCourse(student, course);
                studentHasCourseDto.create(newEntry);
            }

            transaction.commit();
        } catch (Exception e) {
            returnMessage = "Error creating comment; " + e.getMessage();
        } finally {
            if (transaction.isActive()) {
                transaction.rollback();
            }
        }
        return returnMessage;
    }

    public List<Course> getAll(){
        return courseDto.getAll();
    }

    public static <T> boolean hasDuplicates(List<T> list) {
        Set<T> uniqueItems = new HashSet<>();
        for (T element : list) {
            if (!uniqueItems.add(element)) {
                return true; // Duplicate found
            }
        }
        return false; // No duplicates found
    }
}
