package org.example;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import java.util.Scanner;

public class Main {
    private static final Logger logger = LogManager.getLogger(Main.class);

    public static void app(){
        // Set up the EntityManagerFactory
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("schoolsystem");
        // Create an EntityManager
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        UserFlow userFlow = new UserFlow(entityManager);

        Scanner in = new Scanner(System.in);
        System.out.println("Welcome to the school system application");
        System.out.println("Select an option to continue e.g 1");
        System.out.println("Or enter 99 to quit");
        System.out.println("1. Register a student to the school\n2. Register courses for the student\n3. Get the list of every student in the school\n4. Get the list of courses registered for a student\n5. Get all the teachers in the school\n6. Get the teacher assigned to a student");

        Integer selectedOption = in.nextInt();

        if(selectedOption == 99){
            System.exit(99);
        }

        while(selectedOption > 7 || selectedOption < 1){
            System.out.println("Select a valid option to continue e.g 1");
            System.out.println("Or enter 99 to exit");
            selectedOption = in.nextInt();
            if(selectedOption == 99){
                System.exit(1);
            }
        }

        switch (selectedOption) {
            case 1:
                userFlow.registerStudent();
                break;
            case 2:
                userFlow.registerCourses();
                break;
            case 3:
                userFlow.getAllStudents();
                break;
            case 4:
                userFlow.getListOfAllCoursesRegisteredForAStudent();
                break;
            case 5:
                userFlow.getAllTeachers();
                break;
            case 6:
                userFlow.getTeacherAssignedToStudent();
                break;
            default:
                System.out.println("Invalid number");
                break;
        }

        System.out.println("\nWould you like to perform another operation?\n1. Yes\n2. No");
        Integer restart = in.nextInt();

        while(restart == 1 && restart == 2){
            System.out.println("Select a valid option to continue e.g 1");
            restart = in.nextInt();
        }
        if(restart == 1){
            app();
        }else{
            in.close();
        }
    }
    public static void main(String[] args){
       app();
    }
}
