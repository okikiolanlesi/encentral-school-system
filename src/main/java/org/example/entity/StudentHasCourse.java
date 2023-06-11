package org.example.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import com.google.common.base.Objects;

@Entity
@Table(name = "student_has_course")
public class StudentHasCourse {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "course_id", referencedColumnName = "id")
    private Course course;

    @ManyToOne
    @JoinColumn(name = "student_id", referencedColumnName = "id")
    private Student student;

    @Column(name="created_at")
    private LocalDateTime createdAt;

    @Column(name="updated_at")
    private LocalDateTime updatedAt;

    public StudentHasCourse(){

    }

    public StudentHasCourse(Student student, Course course){
        this.student = student;
        this.course = course;
    }

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public Student getStudent() {
        return student;
    }
    public void setStudent(Student student) {
        this.student = student;
    }

    public Course getCourse() {
        return course;
    }
    public void setCourse(Course course) {
        this.course = course;
    }

    public void setCreatedAt(LocalDateTime dateTime) {
        this.createdAt = dateTime;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setUpdatedAt(LocalDateTime dateTime) {
        this.updatedAt = dateTime;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }


    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(createdAt, updatedAt, course, student);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof StudentHasCourse)) {
            return false;
        }
        StudentHasCourse other = (StudentHasCourse) obj;
        return Objects.equal(getCreatedAt(), other.getCreatedAt()) &&
                Objects.equal(getUpdatedAt(), other.getUpdatedAt()) &&
                Objects.equal(getStudent(), other.getStudent());
    }

    @Override
    public String toString() {
        return "{ " +
                "id=" + id +
                ", student=" + student +
                ", course=" + course +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                " }";
    }
}
