package org.example.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

import com.google.common.base.Objects;

@Entity
@Table(name = "course")
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    public Course() {

    }

    public Course(String title) {
        this.title = title;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
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
        return Objects.hashCode(createdAt, updatedAt, title);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Course)) {
            return false;
        }
        Course other = (Course) obj;
        return Objects.equal(getCreatedAt(), other.getCreatedAt()) &&
                Objects.equal(getUpdatedAt(), other.getUpdatedAt()) &&
                Objects.equal(getTitle(), other.getTitle());
    }

    @Override
    public String toString() {
        return "{ " +
                "id=" + id +
                ", title=" + title +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                " }";
    }
}