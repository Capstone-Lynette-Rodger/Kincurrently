package com.kincurrently.models;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;

import static javax.persistence.TemporalType.DATE;

@Entity
@Table(name="task_comments")
public class TaskComment {

    @Id
    @GeneratedValue
    private long id;

    @Column(nullable=false)
    @Temporal(DATE)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date created_on;

    @Column(nullable = false, columnDefinition = "TEXT")
    @NotBlank(message = "Comment cannot be blank.")
    private String commentBody;

    @ManyToOne (optional = false)
    @JoinColumn (name = "task_id")
    private Task task;

    @ManyToOne (optional = false)
    @JoinColumn (name = "user_id")
    private User user;

    public TaskComment() {
    }

    public TaskComment(Date created_on, String commentBody, Task task, User user, Long id) {
        this.created_on = created_on;
        this.commentBody = commentBody;
        this.task = task;
        this.user = user;
        this.id = id;
    }

    public TaskComment(Date created_on, String commentBody, Task task, User user) {
        this.created_on = created_on;
        this.commentBody = commentBody;
        this.task = task;
        this.user = user;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Date getCreated_on() {
        return created_on;
    }

    public void setCreated_on(Date created_on) {
        this.created_on = created_on;
    }

    public String getCommentBody() {
        return commentBody;
    }

    public void setCommentBody(String commentBody) {
        this.commentBody = commentBody;
    }

    public Task getTask() {
        return task;
    }

    public void setTask(Task task) {
        this.task = task;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
