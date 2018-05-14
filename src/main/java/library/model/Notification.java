package library.model;

import java.sql.Date;

public class Notification implements Comparable<Notification> {
    private int id;
    private int userID;
    private String notice;
    private Date date;

    public Notification(int id, int userID, String notice, Date date) {
        this.id = id;
        this.userID = userID;
        this.notice = notice;
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
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
        return new StringBuilder("Notification for user \'").append(userID).append("\':\n")
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
