package library.config;

import library.repository.UserRepository;
import library.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ServicesConfiguration {
    @Bean
    public DBService getDBService(@Autowired ConnectionSettings connectionSettings) {
        return new PostgreDBService(connectionSettings);
    }

    @Bean
    public BookService getBookService(@Autowired DBService dbService,
                                      @Autowired LibrarySettings librarySettings) {
        return new BookServiceImpl(dbService, librarySettings);
    }

    @Bean
    public UserService getUserService(@Autowired DBService dbService) {

        return new UserServiceImpl(dbService);
    }

    @Bean
    public SearchService getSearchService(@Autowired DBService dbService) {
        return new SQLSearchImpl(dbService);
    }

    @Bean
    public NotificationService getNotificationService(@Autowired DBService dbService) {
        return new NotificationServiceImpl(dbService);
    }

    @Bean
    public AuthenticationService getAuthenticationService(@Autowired UserService userService) {
        return new AuthenticationServiceImpl(userService);
    }
}
