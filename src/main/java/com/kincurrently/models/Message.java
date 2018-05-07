package com.kincurrently.models;


import javax.persistence.*;
import java.util.List;

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

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name="messages_messageRecipients",
            joinColumns={@JoinColumn(name="message_id")},
            inverseJoinColumns={@JoinColumn(name="recipient_id")}
    )
    private List<MessageRecipient> messageRecipients;

    public Message(User user, String body, List<MessageRecipient> messageRecipients) {
        this.user = user;
        this.body = body;
        this.messageRecipients = messageRecipients;
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

    public List<MessageRecipient> getMessageRecipients() {
        return messageRecipients;
    }

    public void setMessageRecipients(List<MessageRecipient> messageRecipients) {
        this.messageRecipients = messageRecipients;
    }
}
