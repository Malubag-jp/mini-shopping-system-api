package com.srllc.bootcamp_project.mini_shopping_system.domain.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "role_table")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "role_name", length = 20, nullable = false, unique = true)
    private String roleName;

    @Column(name = "date_created", updatable = false)
    private LocalDateTime dateCreated;

    @Column(name = "date_modified")
    private LocalDateTime dateModified;

    @PrePersist
    protected void onCreate() {
        this.dateCreated=LocalDateTime.now();
        this.dateModified=LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        this.dateModified=LocalDateTime.now();
    }
}
