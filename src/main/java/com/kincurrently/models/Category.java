package com.kincurrently.models;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "categories")
public class Category {

    @Id
    @GeneratedValue
    private long id;

    @Column(nullable = false)
    private String name;

    @ManyToMany(mappedBy = "categories")
    private List<Event> events;

    @ManyToMany(mappedBy = "categories")
    private List<Task> tasks;

    public Category(String name, List<Event> events, List<Task> tasks) {
        this.name = name;
        this.events = events;
        this.tasks = tasks;
    }

    public Category(Long id, String name, List<Event> events, List<Task> tasks) {
        this.name = name;
        this.events = events;
        this.tasks = tasks;
        this.id = id;
    }

    public Category() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Event> getEvents() {
        return events;
    }

    public void setEvents(List<Event> events) {
        this.events = events;
    }

    public List<Task> getTasks() {
        return tasks;
    }

    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
    }
}
