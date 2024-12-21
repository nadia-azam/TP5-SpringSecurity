package org.example.tp5sring.entities;

import jakarta.persistence.*;
import lombok.Data;


import java.util.HashSet;
import java.util.Set;

@Entity
@Data
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name; // Example: ROLE_USER, ROLE_ADMIN

    @ManyToMany(mappedBy = "roles")
    private Set<User> users = new HashSet<>();


    public Role() {

    }


    public Role(String roleAdmin) {
        this.name = roleAdmin;
    }


    // Constructeur sans arguments



}