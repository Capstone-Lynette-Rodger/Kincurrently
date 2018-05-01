package com.kincurrently.models;

import javax.persistence.*;

@Entity
@Table(name="statuses")
public class Status {

    @Id
    @GeneratedValue
    private long id;

    @Column(nullable=false)
    private String status;

    public Status() {
    }

    public Status(String status, Long id) {
        this.status = status;
        this.id = id;
    }

    public Status(String status) {
        this.status = status;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
