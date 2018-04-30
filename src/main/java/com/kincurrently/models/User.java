package com.kincurrently.models;

import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.List;

import static javax.persistence.TemporalType.DATE;

@Entity
@Table(name="users")
public class User {
    @Id
    @GeneratedValue
    private long id;

    @Column(nullable=false, unique=true)
    @NotBlank(message = "Username field cannot be blank.")
    @Size(max=50, message = "Username cannot be more than 50 characters.")
    private String username;

    @Column(nullable=false, unique=true)
    @NotBlank(message = "Email field cannot be blank.")
    @Size(max=150, message = "Email cannot be more than 150 characters.")
    private String email;

    @Column(nullable=false)
    @NotBlank(message = "Password field cannot be blank.")
    @Size(max=150, message = "Password cannot be more than 150 characters.")
    private String password;

    @Column
    @Size(max=150, message = "First name cannot be more than 150 characters.")
    private String firstName;

    @Column
    @Size(max=150, message = "Last name cannot be more than 150 characters.")
    private String lastName;

    @Column(nullable=false)
    @Temporal(DATE)
    @NotBlank(message = "Birthdate field cannot be blank.")
    private Date birthdate;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
    private List<Task> tasks;

    public User() {
    }
}
