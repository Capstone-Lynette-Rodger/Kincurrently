package com.kincurrently.models;


import javax.persistence.*;
import java.util.List;

@Entity
@Table(name="messageRecipients")
public class MessageRecipient {

    @Id
    @Column
    private long id;

    @ManyToMany(mappedBy = "messageRecipients")
    private List<Message> messages;

    @ManyToOne
    @JoinColumn(name = "receiver_id")
    private User user;

    public MessageRecipient(long id, List<Message> messages) {
        this.id = id;
        this.messages = messages;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public List<Message> getMessages() {
        return messages;
    }

    public void setMessages(List<Message> messages) {
        this.messages = messages;
    }
}
