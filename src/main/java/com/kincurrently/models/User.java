package com.kincurrently.models;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
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

    @Column(nullable=false)
    @NotBlank(message = "First name field cannot be blank.")
    @Size(max=150, message = "First name cannot be more than 150 characters.")
    private String firstName;

    @Column(nullable=false)
    @NotBlank(message = "Last name field cannot be blank.")
    @Size(max=150, message = "Last name cannot be more than 150 characters.")
    private String lastName;

    @Column
    @Size(max=150, message = "Family title cannot be more than 150 characters.")
    private String title;

    @Column(nullable=false)
    @Temporal(DATE)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @NotNull(message = "Birthdate field cannot be blank.")
    private Date birthdate;

//    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
//    private List<Task> tasks;
//
    @ManyToOne(optional = false)
    @JoinColumn (name = "family_id")
    private Family family;

    public User() {
    }


    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
    private List<EventComment> eventComments;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
    private List<Event> events;


    public User(User copy) {
        this.id = copy.id;
        this.username = copy.username;
        this.email = copy.email;
        this.password = copy.password;
        this.firstName = copy.firstName;
        this.lastName = copy.lastName;
        this.birthdate = copy.birthdate;
        this.family = copy.family;
        this.title = copy.title;
        this.eventComments = copy.eventComments;
        this.events = copy.events;
    }
    public User(String username, String email, String password, String firstName, String lastName, Date birthdate, Family family, String title, List<EventComment> eventComments, List<Event> events) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthdate = birthdate;
        this.family = family;
        this.title = title;
        this.eventComments = eventComments;
        this.events = events;
    }

    public User(Long id, String username, String email, String password, String firstName, String lastName, Date birthdate, Family family, String title, List<EventComment> eventComments, List<Event> events) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthdate = birthdate;
        this.family = family;
        this.title = title;
        this.eventComments = eventComments;
        this.events = events;

    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Date getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(Date birthdate) {
        this.birthdate = birthdate;
    }

    public Family getFamily() {
        return family;
    }

    public void setFamily(Family family) {
        this.family = family;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<EventComment> getEventComments() {
        return eventComments;
    }

    public void setEventComments(List<EventComment> eventComments) {
        this.eventComments = eventComments;
    }

    public List<Event> getEvents() {
        return events;
    }

    public void setEvents(List<Event> events) {
        this.events = events;
    }
}
