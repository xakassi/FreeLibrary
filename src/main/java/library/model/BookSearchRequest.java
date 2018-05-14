package library.model;

public class BookSearchRequest {
    private String name;
    private String author;
    private String genre;
    private String category;

    public BookSearchRequest() {
    }

    public BookSearchRequest(String name, String author, String genre, String category) {
        this.name = name;
        this.author = author;
        this.genre = genre;
        this.category = category;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BookSearchRequest that = (BookSearchRequest) o;

        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (author != null ? !author.equals(that.author) : that.author != null) return false;
        if (genre != null ? !genre.equals(that.genre) : that.genre != null) return false;
        return category != null ? category.equals(that.category) : that.category == null;
    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (author != null ? author.hashCode() : 0);
        result = 31 * result + (genre != null ? genre.hashCode() : 0);
        result = 31 * result + (category != null ? category.hashCode() : 0);
        return Math.abs(result);
    }
}
