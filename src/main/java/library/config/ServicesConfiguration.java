package library.config;

import library.repository.*;
import library.services.AuthenticationServiceImpl;
import library.services.JDBCImpl.JDBCPostgreDBService;
import library.services.SpringDataImpl.SpringDataBookServiceImpl;
import library.services.SpringDataImpl.SpringDataNotificationServiceImpl;
import library.services.SpringDataImpl.SpringDataSearchService;
import library.services.SpringDataImpl.SpringDataUserServiceImpl;
import library.services.interfaces.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ServicesConfiguration {
    @Bean
    public DBService getDBService(@Autowired ConnectionSettings connectionSettings) {
        return new JDBCPostgreDBService(connectionSettings);
    }

    @Bean
    public BookService getBookService(@Autowired DBService dbService,
                                      @Autowired LibrarySettings librarySettings,
                                      @Autowired BookRepository bookRepository,
                                      @Autowired GenreRepository genreRepository,
                                      @Autowired CategoryRepository categoryRepository,
                                      @Autowired UncheckedBookRepository uncheckedBookRepository,
                                      @Autowired AuthorRepository authorRepository) {
        return new SpringDataBookServiceImpl(bookRepository, genreRepository,
                categoryRepository, uncheckedBookRepository,
                authorRepository, librarySettings);
    }

    @Bean
    public UserService getUserService(@Autowired DBService dbService,
                                      @Autowired UserRepository userRepository,
                                      @Autowired UncheckedBookRepository uncheckedBookRepository,
                                      @Autowired NotificationRepository notificationRepository,
                                      @Autowired BookService bookService) {

        return new SpringDataUserServiceImpl(userRepository, notificationRepository,
                uncheckedBookRepository, bookService);
    }

    @Bean
    public SearchService getSearchService(@Autowired DBService dbService) {
        return new SpringDataSearchService();
    }

    @Bean
    public NotificationService getNotificationService(@Autowired DBService dbService,
                                                      @Autowired NotificationRepository notificationRepository) {
        return new SpringDataNotificationServiceImpl(notificationRepository);
    }

    @Bean
    public AuthenticationService getAuthenticationService(@Autowired UserService userService) {
        return new AuthenticationServiceImpl(userService);
    }
}
