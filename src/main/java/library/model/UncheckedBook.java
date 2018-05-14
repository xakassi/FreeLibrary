package library.model;

public class UncheckedBook {
    private int id;
    private int userID;
    private String bookName;
    private String author;
    private String description;

    public UncheckedBook(int id, int userID, String bookName, String author, String description) {
        this.id = id;
        this.userID = userID;
        this.bookName = bookName;
        this.author = author;
        this.description = description;
    }

    public UncheckedBook() {
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
}
