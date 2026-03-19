package com.example.Saleswebsite.entity;

import com.example.Saleswebsite.security.HashConverter;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "users")
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(unique = true, nullable = false)
    @Convert(converter = HashConverter.class)
    private String username;

    @Column(nullable = false)
    @Convert(converter = HashConverter.class)
    private String password;

    @Convert(converter = HashConverter.class)
    private String email;

    @Column(name = "full_name")
    private String fullName;
}
