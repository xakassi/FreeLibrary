package library.config;

import library.extra.SimpleUserCheckerFactory;
import library.extra.UserChecker;
import library.extra.UserCheckerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UserCheckerConfiguration {
    @Bean
    UserCheckerFactory getUserCheckerFactory() {
        return new SimpleUserCheckerFactory();
    }

    @Bean
    UserChecker getUserChecker() {
        return getUserCheckerFactory().createUserChecker();
    }
}
