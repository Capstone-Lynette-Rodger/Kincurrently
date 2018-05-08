package com.kincurrently.models;


import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;

import static javax.persistence.TemporalType.DATE;

@Entity
@Table(name = "event_comments")
public class EventComment {

    @Column(nullable = false)
    private String commentBody;

    @Id
    @GeneratedValue
    private long id;

    @ManyToOne
    @JoinColumn(name = "event_id")
    private Event event;

    @Column(nullable=false)
    @Temporal(DATE)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date created_on;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public EventComment(String commentBody, Event event, User user, Date created_on) {
        this.commentBody = commentBody;
        this.event = event;
        this.user = user;
        this.created_on = created_on;
    }

    public EventComment(Long id, String commentBody, Event event, User user, Date created_on) {
        this.id = id;
        this.commentBody = commentBody;
        this.event = event;
        this.user = user;
        this.created_on = created_on;
    }

    public EventComment() {
    }

    public String getCommentBody() {
        return commentBody;
    }

    public void setCommentBody(String commentBody) {
        this.commentBody = commentBody;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Date getCreated_on() {
        return created_on;
    }

    public void setCreated_on(Date created_on) {
        this.created_on = created_on;
    }
}
