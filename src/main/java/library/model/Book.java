package library.model;

public class Book {
    private int id;
    private String name;
    private Author author;
    private String genre;
    private String category;
    private int popularity;
    private String description;

    public Book(int id, String name, Author author, String genre,
                String category, int popularity, String description) {
        this.id = id;
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

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
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

    public void setCategory(String category) {
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

    public String getCategory() {
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
