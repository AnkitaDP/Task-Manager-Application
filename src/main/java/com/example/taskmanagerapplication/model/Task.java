package com.example.taskmanagerapplication.model;

import com.example.taskmanagerapplication.enums.Priority;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    @Column(nullable = false)
    private String title;
    private String description;
    private boolean isCompleted;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Priority priority;
    @Column(nullable = false)
    private LocalDateTime deadline;
    @CreationTimestamp
    private LocalDateTime createdAt;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User_ user;
    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;
}
