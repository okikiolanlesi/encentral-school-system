package org.example.entity;


import jakarta.persistence.*;

import java.time.LocalDateTime;
import com.google.common.base.Objects;

@Entity
@Table(name = "teacher")
public class Teacher {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Column(name="created_at")

    private LocalDateTime createdAt;

    @Column(name="updated_at")
    private LocalDateTime updatedAt;

    public Teacher(){

    }

    public Teacher(String name){
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }



    public String getName() {
        return name;
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
        return Objects.hashCode(createdAt, updatedAt, name);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Teacher)) {
            return false;
        }
        Teacher other = (Teacher) obj;
        return Objects.equal(getCreatedAt(), other.getCreatedAt()) &&
                Objects.equal(getUpdatedAt(), other.getUpdatedAt()) &&
                Objects.equal(getName(), other.getName());
    }

    @Override
    public String toString() {
        return "{ " +
                "name=" + name +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                " }";
    }
}
