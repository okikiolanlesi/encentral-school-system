package org.example;

import jakarta.persistence.EntityManager;
import org.example.entity.Course;
import org.example.entity.Student;
import org.example.entity.Teacher;
import org.example.services.CourseService;
import org.example.services.StudentHasCourseService;
import org.example.services.StudentService;
import org.example.services.TeacherService;
import org.h2.engine.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class UserFlow {
    private StudentService studentService;
    private CourseService courseService;
    private StudentHasCourseService studentHasCourseService;
    private TeacherService teacherService;

    public UserFlow(EntityManager entityManager){
        this.studentService = new StudentService(entityManager);
        this.courseService = new CourseService(entityManager);
        this.studentHasCourseService = new StudentHasCourseService(entityManager);
        this.teacherService = new TeacherService(entityManager);
    }

    public void registerStudent(){
        System.out.println("Please provide a name for the student to be registered: ");
        Scanner in = new Scanner(System.in);
        String name = in.nextLine();
        System.out.println("name sipppps=================================" + name);
        String output = studentService.register(name);

        System.out.println(output);
    }

    public void registerCourses(){
        System.out.println("Please provide the student id: ");

        Scanner in = new Scanner(System.in);

        Long studentId = in.nextLong();


       List<Course> courses = courseService.getAll();
        System.out.println("Here's list of courses offered\nPlease input the id of courses you would like to register for seperated by space\ne.g 1 2 3 4 5 6");
        for(Course course: courses){
            System.out.println(course);
        }

        in = new Scanner(System.in);
        String[] courseIds = in.nextLine().split(" ");
        Boolean validInput = false;
        List<Long> courseIdsLong = new ArrayList<Long>();

        while(!validInput){
            validInput = true;
            for(String id: courseIds){
                try{
                  Long idLong = Long.parseLong(id);
                    courseIdsLong.add(idLong);
                }catch (Exception e){
                    validInput = false;
                    courseIdsLong = new ArrayList<Long>();
                    return;
                }
            }

            if(!validInput) {
                System.out.println("Please enter valid input or enter q to exit the program");
                String newInput = in.nextLine();
                if(newInput.equals("q")){
                    System.exit(1);
                }
                courseIds = newInput.split(" ");
            }
        }

       String response = courseService.registerCourses(studentId, courseIdsLong);

        System.out.println(response);
    }

    public void getAllStudents(){
        List<Student> students = studentService.getAllStudents();
        for(Student student : students){
            System.out.println(student);
            System.out.println("=============================");
        }
    }

    public void getAllTeachers(){
        List<Teacher> teachers = teacherService.getAllTeachers();
        System.out.println(teachers);
        for(Teacher teacher : teachers){
            System.out.println(teacher);
            System.out.println("=============================");

        }
    }

    public void getListOfAllCoursesRegisteredForAStudent(){
        System.out.println("Please provide the student id: ");

        Scanner in = new Scanner(System.in);

        Long studentId = in.nextLong();

        try{
            System.out.println(studentService.getAllCoursesForAStudent(studentId));
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    public void getTeacherAssignedToStudent(){
        System.out.println("Please provide the student id: ");

        Scanner in = new Scanner(System.in);

        Long studentId = in.nextLong();

        try{
            System.out.println(studentService.getAssignedTeacherAsJSON(studentId));
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
}
