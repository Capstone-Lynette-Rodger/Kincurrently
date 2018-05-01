package com.kincurrently.models;


import javax.persistence.*;

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


    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public EventComment(String commentBody, Event event, User user) {
        this.commentBody = commentBody;
        this.event = event;
        this.user = user;
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
}
