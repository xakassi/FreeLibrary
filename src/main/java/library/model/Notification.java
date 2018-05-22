package library.model;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Table(name = "notification")
public class Notification implements Comparable<Notification> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne(optional = false, cascade = CascadeType.REFRESH)
    @JoinColumn(name = "userid")
    private User user;

    @Column(nullable = false)
    private String notice;

    @Column(name ="notifdate", nullable = false)
    private Date date;

    public Notification(int id, User user, String notice, Date date) {
        this.id = id;
        this.user = user;
        this.notice = notice;
        this.date = date;
    }

    public Notification(User user, String notice, Date date) {
        this.user = user;
        this.notice = notice;
        this.date = date;
    }

    public Notification() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setNotice(String notice) {
        this.notice = notice;
    }

    public void setDate(Date date) {
        this.date = date;
    }


    public String getNotice() {
        return notice;
    }

    public Date getDate() {
        return date;
    }

    @Override
    public String toString() {
        return new StringBuilder("Notification for user \'").append(user.getLogin()).append("\':\n")
                .append(notice).append("\n").append(date).toString();
    }

    @Override
    public int compareTo(Notification o) {
        return this.getDate().compareTo(o.getDate());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Notification that = (Notification) o;

        return id == that.id;
    }

    @Override
    public int hashCode() {
        return id;
    }
}
