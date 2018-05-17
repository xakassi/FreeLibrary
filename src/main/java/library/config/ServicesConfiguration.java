package library.config;

import library.repository.UserRepository;
import library.services.JDBCImpl.*;
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
                                      @Autowired LibrarySettings librarySettings) {
        return new JDBCBookServiceImpl(dbService, librarySettings);
    }

    @Bean
    public UserService getUserService(@Autowired DBService dbService,
                                      @Autowired UserRepository userRepository) {

        return new SpringDataUserServiceImpl(userRepository);
    }

    @Bean
    public SearchService getSearchService(@Autowired DBService dbService) {
        return new JDBCSQLSearchImpl(dbService);
    }

    @Bean
    public NotificationService getNotificationService(@Autowired DBService dbService) {
        return new JDBCNotificationServiceImpl(dbService);
    }

    @Bean
    public AuthenticationService getAuthenticationService(@Autowired UserService userService) {
        return new JDBCAuthenticationServiceImpl(userService);
    }
}
