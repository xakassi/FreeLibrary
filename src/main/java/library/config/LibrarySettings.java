package library.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties("library.books")
public class LibrarySettings {
    private String booksPath;
    private String uncheckedPath;

    public String getUncheckedPath() {
        return uncheckedPath;
    }

    public void setUncheckedPath(String uncheckedPath) {
        this.uncheckedPath = uncheckedPath;
    }

    public String getBooksPath() {
        return booksPath;
    }

    public void setBooksPath(String booksPath) {
        this.booksPath = booksPath;
    }
}
