package org.example.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import org.example.dto.StudentDto;
import org.example.dto.TeacherDto;
import org.example.entity.Student;
import org.example.entity.StudentHasCourse;
import org.example.entity.Teacher;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;


import java.util.List;

public class StudentService {
    private JPAQueryFactory queryFactory;
    private EntityManager entityManager;
    StudentDto studentDto;
    TeacherDto teacherDto;

    public StudentService(EntityManager em){
        this.queryFactory = new JPAQueryFactory(em);
        this.entityManager = em;
        this.studentDto = new StudentDto(entityManager);
        this.teacherDto = new TeacherDto(entityManager);
    }

    public String register(String name){
        String returnMessage = "Student registered successfuly";
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();

//          Get a teacher
            Teacher teacher = teacherDto.getRandomTeacher();
            if(teacher == null){
                throw new Exception("Unable to register students, no teachers available at the moment");
            }

//            Register student
            Student student = new Student(name);
            student.setTeacher(teacher);
            studentDto.create(student);

            transaction.commit();
        } catch (Exception e) {

            returnMessage = "Error registering user; " + e.getMessage();

        } finally {
            if (transaction.isActive()) {
                transaction.rollback();
            }
        }
        return returnMessage;
    }

    public List<Student> getAllStudents(){
        List<Student> students = studentDto.getAll();
        return students;
    }

    public List<StudentHasCourse> getAllCoursesForAStudent(Long studentId) throws Exception{
        Student student = studentDto.getAStudentDetails(studentId);

        if(student == null){
            throw new Exception("Invalid student id");
        }

        return student.getCourses();
    }

    public String getAssignedTeacherAsJSON(Long studentId) throws Exception{
        Student student = studentDto.getAStudentDetails(studentId);

        if(student == null){
            throw new Exception("Invalid student id");
        }

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        String json = objectMapper.writeValueAsString(student.getTeacher());

        return json;
    }
}
