package com.kincurrently.models;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "events")
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Value("${google-api-key")
    private String api;

    public String getApi() {
        return api;
    }

    @ManyToOne
    @JoinColumn(name = "family_id")
    private Family family;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "event")
    private List<EventComment> eventComments;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToMany
    @JoinTable(
            name="events_categories",
            joinColumns = {@JoinColumn(name="event_id")},
            inverseJoinColumns = {@JoinColumn(name="category_id")}
    )
    private List<Category> categories;

    @Column(nullable = false)
    @NotBlank(message = "Title field cannot be blank.")
    @Size(max=50, message = "Title cannot be more than 100 characters.")
    private String title;

    @Column(nullable = false, columnDefinition = "TEXT")
    @NotBlank(message = "Description field cannot be blank.")
    private String description;

    @Column
    private String location;

    @Column
    private String address;

    @Column(nullable = false)
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "yyyy-MM-DD")
//    @NotNull(message = "Events need to have a start date")
    private java.util.Date start_date;

    @Column
    @Temporal(TemporalType.TIME)
    @DateTimeFormat(pattern = "hh:mm")
    private java.util.Date start_time;

    @Column
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "yyyy-MM-DD")
    private java.util.Date end_date;

    @Column
    @Temporal(TemporalType.TIME)
    @DateTimeFormat(pattern = "hh:mm")
    private java.util.Date end_time;


    public Event(Family family, String title, String description, String location, Date start_date, Date start_time, Date end_date, Date end_time, List<EventComment> eventComments, List<Category> categories, User user, String address) {
        this.family = family;
        this.title = title;
        this.description = description;
        this.location = location;
        this.start_date = start_date;
        this.start_time = start_time;
        this.end_date = end_date;
        this.end_time = end_time;
        this.eventComments = eventComments;
        this.categories = categories;
        this.user = user;
        this.address = address;
    }

    public Event(String title, String description, String location, Date start_date, Date start_time, Date end_date, Date end_time, List<EventComment> eventComments, List<Category> categories, User user, String address) {
        this.title = title;
        this.description = description;
        this.location = location;
        this.start_date = start_date;
        this.start_time = start_time;
        this.end_date = end_date;
        this.end_time = end_time;
        this.eventComments = eventComments;
        this.categories = categories;
        this.user = user;
        this.address = address;
    }

    public Event() {
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public List<Category> getCategories() {
        return categories;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }

    public List<EventComment> getEventComments() {
        return eventComments;
    }

    public void setEventComments(List<EventComment> eventComments) {
        this.eventComments = eventComments;
    }

    public Date getStart_time() {
        return start_time;
    }

    public void setStart_time(Date start_time) {
        this.start_time = start_time;
    }

    public Date getEnd_time() {
        return end_time;
    }

    public void setEnd_time(Date end_time) {
        this.end_time = end_time;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Family getFamily() {
        return family;
    }

    public void setFamily(Family family) {
        this.family = family;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Date getStart_date() {
        return start_date;
    }

    public void setStart_date(Date start_date) {
        this.start_date = start_date;
    }

    public Date getEnd_date() {
        return end_date;
    }

    public void setEnd_date(Date end_date) {
        this.end_date = end_date;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }


}
