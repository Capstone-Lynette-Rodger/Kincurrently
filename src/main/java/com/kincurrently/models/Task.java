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
@Table(name="tasks")
public class Task {

    @Id
    @GeneratedValue
    private long id;

    @Column(nullable=false)
    @NotBlank(message = "Title field cannot be blank.")
    @Size(max=255, message = "Title cannot be more than 255 characters.")
    private String title;

    @Column(nullable=false)
    @Temporal(DATE)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date created_on;

    @Column(nullable=false)
    @Temporal(DATE)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @NotNull(message = "Completed by field cannot be blank.")
    private Date completed_by;

    @Column(columnDefinition = "TEXT")
    @Size(max=750, message = "Description cannot be more than 750 characters.")
    private String description;

    @ManyToOne(optional = false)
    @JoinColumn (name = "creator_id")
    private User creator;

    @ManyToOne(optional = false)
    @JoinColumn (name = "designated_user_id")
    private User designated_user;

    @OneToOne
    private Status status;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "task")
    private List<TaskComment> comments;

    @ManyToMany
    @JoinTable(
            name="tasks_categories",
            joinColumns = {@JoinColumn(name="task_id")},
            inverseJoinColumns = {@JoinColumn(name="category_id")}
    )
    private List<Category> categories;

    public Task() {
    }

    public Task(String title, Date created_on, Date completed_by, String description, User creator, User designated_user, Status status, List<TaskComment> comments, Long id, List<Category> categories) {
        this.title = title;
        this.created_on = created_on;
        this.completed_by = completed_by;
        this.description = description;
        this.creator = creator;
        this.status = status;
        this.id = id;
        this.comments = comments;
        this.designated_user = designated_user;
        this.categories = categories;
    }

    public Task(String title, Date created_on, Date completed_by, String description, User creator, User designated_user, Status status, List<TaskComment> comments, List<Category> categories) {
        this.title = title;
        this.created_on = created_on;
        this.completed_by = completed_by;
        this.description = description;
        this.creator = creator;
        this.status = status;
        this.comments = comments;
        this.designated_user = designated_user;
        this.categories = categories;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getCreated_on() {
        return created_on;
    }

    public void setCreated_on(Date created_on) {
        this.created_on = created_on;
    }

    public Date getCompleted_by() {
        return completed_by;
    }

    public void setCompleted_by(Date completed_by) {
        this.completed_by = completed_by;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public User getCreator() {
        return creator;
    }

    public void setCreator(User creator) {
        this.creator = creator;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public List<TaskComment> getComments() {
        return comments;
    }

    public void setComments(List<TaskComment> comments) {
        this.comments = comments;
    }

    public User getDesignated_user() {
        return designated_user;
    }

    public void setDesignated_user(User designated_user) {
        this.designated_user = designated_user;
    }

    public List<Category> getCategories() {
        return categories;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }
}
