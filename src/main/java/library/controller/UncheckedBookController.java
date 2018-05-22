package library.controller;

import library.config.LibrarySettings;
import library.model.Author;
import library.model.Book;
import library.model.UncheckedBook;
import library.model.User;
import library.services.interfaces.BookService;
import library.services.interfaces.NotificationService;
import library.services.interfaces.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.mail.internet.MimeUtility;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.NoSuchElementException;
import java.util.Set;

@Controller
@SessionAttributes("user")
public class UncheckedBookController {
    @Autowired
    private BookService bookService;
    @Autowired
    private NotificationService notificationService;
    @Autowired
    private LibrarySettings librarySettings;

    private static final Logger LOG = LoggerFactory.getLogger(UncheckedBookController.class.getName());

    @RequestMapping(value = "/unchecked-books")
    public ModelAndView getUncheckedBooks(HttpServletRequest request,
                                          @ModelAttribute("user") User user) {
        if (user.getLogin().equals("") || !user.getRole().equals(UserService.ADMIN_ROLE)) {
            return new ModelAndView("redirect:" + "/main");
        }

        ModelAndView modelAndView = new ModelAndView();
        Set<UncheckedBook> uncheckedBooks = bookService.getAllUncheckedBooks();
        int unchNum = uncheckedBooks != null ? uncheckedBooks.size() : 0;
        request.setAttribute("unchNum", unchNum);
        modelAndView.addObject("unchBooks", uncheckedBooks);

        modelAndView.setViewName("uncheckedBooks");
        return modelAndView;
    }

    @RequestMapping(value = "/unch-book-info")
    public ModelAndView processUncheckedBook(HttpServletRequest request,
                                             @RequestParam("getId") int getId,
                                             @ModelAttribute("user") User user) {
        if (user.getLogin().equals("") || !user.getRole().equals(UserService.ADMIN_ROLE)) {
            return new ModelAndView("redirect:" + "/main");
        }

        ModelAndView modelAndView = new ModelAndView();
        UncheckedBook uncheckedBook = bookService.getUncheckedBookById(getId);

        request.setAttribute("unchBook", uncheckedBook);
        request.setAttribute("book", new Book(new Author()));
        modelAndView.addObject("genres", bookService.getAllGenres());
        modelAndView.addObject("categories", bookService.getAllCategories());
        modelAndView.setViewName("unchBookInfo");
        return modelAndView;
    }

    @RequestMapping(value = "/check-file/{unchBookID}")
    public void downloadBook(@PathVariable("unchBookID") int unchBookID,
                             @ModelAttribute("user") User user,
                             HttpServletRequest request,
                             HttpServletResponse response) throws IOException {
        if (user.getLogin().equals("") || !user.getRole().equals(UserService.ADMIN_ROLE)) {
            response.sendRedirect("/main");
        }

        UncheckedBook uncheckedBook = bookService.getUncheckedBookById(unchBookID);
        if (uncheckedBook == null) {
            throw new NoSuchElementException();
        }
        String fullPath = librarySettings.getUncheckedPath() + uncheckedBook.getBookName();
        System.out.println(fullPath);

        File downloadFile = new File(fullPath);
        FileInputStream inputStream = null;
        try {
            inputStream = new FileInputStream(downloadFile);
        } catch (FileNotFoundException e) {
            LOG.error("Exception in downloadBook()", e);
        }

        ServletContext context = request.getServletContext();
        String mimeType = context.getMimeType(fullPath);
        if (mimeType == null) {
            mimeType = "application/octet-stream";
        }

        response.setContentType(mimeType);
        response.setContentLength((int) downloadFile.length());
        response.setHeader("Content-Disposition", "inline; filename=\"" +
                MimeUtility.encodeWord(downloadFile.getName(), "utf-8", "Q") + "\"");


        final int BUFFER_SIZE = 4096;
        try {
            OutputStream outStream = response.getOutputStream();

            byte[] buffer = new byte[BUFFER_SIZE];
            int bytesRead = -1;

            while ((bytesRead = inputStream.read(buffer)) != -1) {
                outStream.write(buffer, 0, bytesRead);
            }

            inputStream.close();
            outStream.flush();
            outStream.close();
        } catch (IOException e) {
            LOG.error("Exception in downloadBook()", e);
        }
    }

    @RequestMapping(value = "/accept-new-book")
    public ModelAndView acceptNewBook(@RequestParam("unchBookID") int unchBookID,
                                      @ModelAttribute("book") Book book,
                                      @ModelAttribute("user") User user,
                                      RedirectAttributes redirect) {
        if (user.getLogin().equals("") || !user.getRole().equals(UserService.ADMIN_ROLE)) {
            return new ModelAndView("redirect:" + "/main");
        }

        UncheckedBook uncheckedBook = bookService.getUncheckedBookById(unchBookID);

        if (book.getName().equals("") || book.getAuthor().getLastName().equals("")
                || book.getGenre().equals("Choose a genre") ||
                book.getCategory().equals("Choose a category")) {
            String message = "At least book name, author surname, genre and category fields " +
                    "should be filled!";
            redirect.addFlashAttribute("message", message);
            redirect.addFlashAttribute("success", "no");
            return new ModelAndView("redirect:" + "/unch-book-info?getId=" + unchBookID);
        } else {
            bookService.acceptUncheckedBook(uncheckedBook, book);
            notificationService.createNotification(uncheckedBook.getUser(), "Your book \"" +
                    uncheckedBook.getBookName() + "\" is successfully added to the library as " +
                    book + ".");

            String message = "The book is successfully added to the library.";
            redirect.addFlashAttribute("message", message);
            redirect.addFlashAttribute("success", "yes");

            return new ModelAndView("redirect:" + "/unchecked-books");
        }
    }

    @RequestMapping(value = "/refuse-new-book")
    public ModelAndView refuseNewBook(@RequestParam("unchBookID") int unchBookID,
                                      @ModelAttribute("user") User user,
                                      RedirectAttributes redirect) {
        if (user.getLogin().equals("") || !user.getRole().equals(UserService.ADMIN_ROLE)) {
            return new ModelAndView("redirect:" + "/main");
        }
        UncheckedBook uncheckedBook = bookService.getUncheckedBookById(unchBookID);

        bookService.refuseUncheckedBook(uncheckedBook);
        notificationService.createNotification(uncheckedBook.getUser(), "Your book \"" +
                uncheckedBook.getBookName() + "\" will not be added to the library.");

        redirect.addFlashAttribute("message", "The book is refused.");
        redirect.addFlashAttribute("success", "yes");
        return new ModelAndView("redirect:" + "/unchecked-books");
    }

}
