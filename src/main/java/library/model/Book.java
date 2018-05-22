package library.model;

import javax.persistence.*;

@Entity
@Table(name = "book")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false)
    private String name;

    @ManyToOne(optional = false, cascade = CascadeType.REFRESH)
    @JoinColumn(name = "authorid")
    private Author author;

    @ManyToOne(optional = false, cascade = CascadeType.REFRESH)
    @JoinColumn(name = "genreid")
    private Genre genre;

    @ManyToOne(optional = false, cascade = CascadeType.REFRESH)
    @JoinColumn(name = "categoryid")
    private Category category;

    @Column
    private int popularity;

    @Column
    private String description;

    public Book(int id, String name, Author author, Genre genre,
                Category category, int popularity, String description) {
        this.id = id;
        this.name = name;
        this.author = author;
        this.genre = genre;
        this.category = category;
        this.popularity = popularity;
        this.description = description;
    }

    public Book(String name, Author author, Genre genre,
                Category category, int popularity, String description) {
        this.name = name;
        this.author = author;
        this.genre = genre;
        this.category = category;
        this.popularity = popularity;
        this.description = description;
    }

    public Book() {
    }

    public Book(Author author) {
        this.author = author;
    }

    public Genre getGenre() {
        return genre;
    }

    public void setGenre(Genre genre) {
        this.genre = genre;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public void setPopularity(int popularity) {
        this.popularity = popularity;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Author getAuthor() {
        return author;
    }

    public Category getCategory() {
        return category;
    }

    public int getPopularity() {
        return popularity;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Book book = (Book) o;

        return id == book.id;
    }

    @Override
    public int hashCode() {
        return id;
    }

    @Override
    public String toString() {
        return new StringBuilder("\"").append(name).append("\" ").append(author).toString();
    }

    public static int sortByPopularity(Book b1, Book b2) {
        return (b1.getPopularity() == b2.getPopularity()) ? 0 :
                (b1.getPopularity() < b2.getPopularity() ? 1 : -1);
    }
}
