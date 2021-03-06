package com.kincurrently.models;

import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.List;

@Entity
@Table(name="families")
public class Family {
    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable=false)
    @Size(max=200, message = "Family name cannot be more than 200 characters.")
    private String name;

    @Column(nullable=false, unique=true)
    @NotBlank(message = "Family code cannot be blank.")
    @Size(max=200, message = "Family code cannot be more than 200 characters.")
    private String code;

    @OneToMany(mappedBy = "family", fetch = FetchType.EAGER)
    private List<User> users;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "family")
    private List<Event> events;

    public Family() {
    }

    public Family(String name, String code, List<User> users, List<Event> events) {
        this.name = name;
        this.code = code;
        this.users = users;
        this.events = events;
    }

    public Family(Long id, String name, String code, List<User> users, List<Event> events) {
        this.id = id;
        this.name = name;
        this.code = code;
        this.users = users;
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

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public List<Event> getEvents() {
        return events;
    }

    public void setEvents(List<Event> events) {
        this.events = events;
    }
}
