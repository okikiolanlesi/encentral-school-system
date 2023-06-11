package org.example;


import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import org.example.entity.Course;
import org.example.entity.Student;
import org.example.entity.StudentHasCourse;
import org.example.entity.Teacher;
import org.example.services.CourseService;
import org.example.services.StudentHasCourseService;
import org.example.services.StudentService;
import org.example.services.TeacherService;
import org.junit.jupiter.api.*;
import org.testcontainers.containers.wait.strategy.Wait;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.containers.GenericContainer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class SchoolSystem {
    private static final String PERSISTENCE_UNIT_NAME = "schoolsystemtest";


    @Container
    public static GenericContainer databaseContainer = new GenericContainer<>("\"h2:mem\"")
            .withExposedPorts(8082)
            .waitingFor(Wait.forListeningPort())
            .withEnv("TZ", "UTC");
    private static EntityManagerFactory entityManagerFactory;
    private static EntityManager entityManager;

    private static StudentService studentService;
    private static TeacherService teacherService;
    private static StudentHasCourseService studentHasCourseService;
    private static CourseService courseService;

    @BeforeAll
    static void setUp() {
        String jdbcUrl = "jdbc:h2://" + databaseContainer.getHost() + ":" + databaseContainer.getMappedPort(8082) + "/testdb";

        entityManagerFactory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME, getJpaProperties(jdbcUrl));
        entityManager = entityManagerFactory.createEntityManager();

        studentService = new StudentService(entityManager);
        teacherService = new TeacherService(entityManager);
        courseService = new CourseService(entityManager);
        studentHasCourseService = new StudentHasCourseService(entityManager);

        Seeder.seedAll();
    }

    @AfterAll
    public static void teardown(){
        EntityTransaction transaction =   entityManager.getTransaction();
        try{
            transaction.begin();

            entityManager.createQuery("DELETE FROM Student ").executeUpdate();

            entityManager.createQuery("DELETE FROM Course ").executeUpdate();

            entityManager.createQuery("DELETE FROM StudentHasCourse ").executeUpdate();

            entityManager.createQuery("DELETE FROM Teacher ").executeUpdate();


            transaction.commit();

        }catch (Exception e){
            transaction.rollback();
        }
    }


    @Test
    public void registerUser(){
        String response = studentService.register("testName");
        assertEquals("Student registered successfuly", response);
    }

    @Test
    public void registerCourses(){
        Student student = studentService.getAllStudents().get(0);
        List<Course> courses = courseService.getAll();
        List<Long> ids = new ArrayList<Long>();
        for(int i = 0; i < 7; i++){
            ids.add(courses.get(i).getId());
        }
        String response = courseService.registerCourses(student.getId(), ids);
        assertEquals("Courses registered successfully", response);
    }

    @Test
    public void testGetAllStudents(){
        List<Student> response = studentService.getAllStudents();
        assertTrue(response instanceof List, "response is not list");
    }

    @Test
    public void testGetAllTeachers(){
        List<Teacher> response = teacherService.getAllTeachers();
        assertTrue(response instanceof List, "response is not list");
    }

    @Test
    public void testGetListOfCoursesRegisteredFor() throws Exception {
        Student student = studentService.getAllStudents().get(0);
        List<StudentHasCourse> response = studentService.getAllCoursesForAStudent(student.getId());
        assertTrue(response instanceof List, "response is not list");
    }

    @Test
    public void testGetTeacherAssignedToStudent() throws Exception {
        Student student = studentService.getAllStudents().get(0);
        String response = studentService.getAssignedTeacherAsJSON(student.getId());
        assertTrue(response instanceof String, "response is not json string");
    }

    private static Map<String, String> getJpaProperties(String jdbcUrl) {
        Map<String, String> properties = new HashMap<>();
        properties.put("javax.persistence.jdbc.url", jdbcUrl);
        properties.put("javax.persistence.jdbc.user", "sa");
        properties.put("javax.persistence.jdbc.password", "");
        properties.put("javax.persistence.jdbc.driver", "org.h2.Driver");
        return properties;
    }
}
