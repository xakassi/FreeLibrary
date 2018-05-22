package library.model;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "uncheckedbook")
public class UncheckedBook {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne(optional = false, cascade = CascadeType.REFRESH)
    @JoinColumn(name = "userid")
    private User user;

    @Column(name = "bookname", nullable = false)
    private String bookName;

    private String author;
    private String description;

    public UncheckedBook(int id, User user, String bookName, String author, String description) {
        this.id = id;
        this.user = user;
        this.bookName = bookName;
        this.author = author;
        this.description = description;
    }

    public UncheckedBook(User user, String bookName,
                         String author, String description) {
        this.user = user;
        this.bookName = bookName;
        this.author = author;
        this.description = description;
    }

    public UncheckedBook(UncheckedBook uncheckedBook) {
        this.id = uncheckedBook.id;
        this.user = uncheckedBook.user;
        this.bookName = uncheckedBook.bookName;
        this.author = uncheckedBook.author;
        this.description = uncheckedBook.description;
    }

    public UncheckedBook() {
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

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return bookName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UncheckedBook that = (UncheckedBook) o;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
