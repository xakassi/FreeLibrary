package library.controller;

import library.services.interfaces.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.HttpSessionRequiredException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@ControllerAdvice
public class MainExceptionHandler {
    @Autowired
    private NotificationService notificationService;

    @ExceptionHandler(Exception.class)
    void getError(HttpServletResponse response) throws IOException {
        response.sendRedirect("/main");
    }

    @ExceptionHandler(HttpSessionRequiredException.class)
    void getSessionError(HttpServletResponse response) throws IOException {
        response.sendRedirect("/");
    }
}
