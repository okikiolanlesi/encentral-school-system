package org.example;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import org.example.dto.CourseDto;
import org.example.dto.StudentDto;
import org.example.dto.TeacherDto;
import org.example.entity.Course;
import org.example.entity.Student;
import org.example.entity.Teacher;


public class Seeder {
    public static void seedAll(){
        // Set up the EntityManagerFactory
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("schoolsystem");
        // Create an EntityManager
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            entityManager.persist(new Student("OkikiStudent"));
            entityManager.persist(new Student("OlaStudent"));

            entityManager.persist(new Teacher("OkikiTeacher"));
            entityManager.persist(new Teacher("OlaTeacher"));

            entityManager.persist(new Course("MATH"));
            entityManager.persist(new Course("ENGLISH"));
            entityManager.persist(new Course("FINE ART"));
            entityManager.persist(new Course("FURTHER MATHS"));
            entityManager.persist(new Course("AGRIC"));
            entityManager.persist(new Course("BIOLOGY"));
            entityManager.persist(new Course("CHEMISTRY"));
            entityManager.persist(new Course("PHYSICS"));
            entityManager.persist(new Course("ECONOMICS"));
            entityManager.persist(new Course("GOVERNMENT"));

            transaction.commit();
        }catch (Exception e){
            System.out.println(e.getMessage());
            transaction.rollback();
        }
    }
    public static void main(String[] args){
      seedAll();
    }
}
