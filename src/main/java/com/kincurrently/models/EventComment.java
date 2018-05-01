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

    public EventComment(String commentBody, Event event) {
        this.commentBody = commentBody;
        this.event = event;
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
}
