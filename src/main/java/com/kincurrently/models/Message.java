package com.kincurrently.models;


import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

import static javax.persistence.TemporalType.DATE;

@Entity
@Table(name="messages")
public class Message {

    @Id
    @GeneratedValue
    private long id;

    @ManyToOne
    @JoinColumn(name = "creator_id")
    private User user;

    @Column(columnDefinition = "TEXT")
    private String body;

    @Column
    private boolean messageRead = false;

    public void readMessage() {
        this.messageRead = true;
    }

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name="messages_messageRecipients",
            joinColumns={@JoinColumn(name="message_id")},
            inverseJoinColumns={@JoinColumn(name="recipient_id")}
    )
    private List<User> messageRecipients;

    public Message(User user, String body, List<User> messageRecipients) {
        this.user = user;
        this.body = body;
        this.messageRecipients = messageRecipients;
    }

    public boolean isMessageRead() {
        return messageRead;
    }

    public void setMessageRead(boolean messageRead) {
        this.messageRead = messageRead;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public List<User> getMessageRecipients() {
        return messageRecipients;
    }

    public void setMessageRecipients(List<User> messageRecipients) {
        this.messageRecipients = messageRecipients;
    }

}
