package library.controller;

import library.config.LibrarySettings;
import library.model.*;
import library.services.interfaces.BookService;
import library.services.interfaces.NotificationService;
import library.services.interfaces.SearchService;
import library.services.interfaces.UserService;
import org.apache.tika.Tika;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.mail.internet.MimeUtility;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.List;
import java.util.NoSuchElementException;

@Controller
@SessionAttributes("user")
public class BookController {
    @Autowired
    private BookService bookService;
    @Autowired
    private SearchService searchService;
    @Autowired
    private LibrarySettings librarySettings;
    @Autowired
    private NotificationService notificationService;

    private static final Logger LOG = LoggerFactory.getLogger(BookController.class.getName());

    @RequestMapping(value = "/main")
    public ModelAndView showMainPage(@ModelAttribute("user") User user) {
        ModelAndView modelAndView = new ModelAndView();
        if (user.getLogin().equals("")) {
            return new ModelAndView("redirect:" + "/");
        }
        modelAndView.addObject("user", user);
        modelAndView.setViewName("main");
        modelAndView.addObject("genres", bookService.getAllGenres());
        modelAndView.addObject("categories", bookService.getAllCategories());
        modelAndView.addObject("bookRequest", new BookSearchRequest());

        return modelAndView;
    }

    @RequestMapping(value = "/search-book")
    public ModelAndView searchBook(@ModelAttribute("bookRequest") BookSearchRequest bookSearchRequest,
                                   HttpServletRequest request,
                                   @ModelAttribute("user") User user) {
        if (user.getLogin().equals("")) {
            return new ModelAndView("redirect:" + "/main");
        }
        ModelAndView modelAndView = new ModelAndView();

        List<Book> searchingBooks = searchService.searchBooks(bookSearchRequest);
        if (!searchingBooks.isEmpty()) {
            modelAndView.addObject("books", searchingBooks);
        } else {
            request.setAttribute("message", "There are no such books in the library. Sorry.");
            request.setAttribute("success", "no");
        }

        modelAndView.addObject("genres", bookService.getAllGenres());
        modelAndView.addObject("categories", bookService.getAllCategories());
        modelAndView.setViewName("main");
        return modelAndView;
    }


    @RequestMapping(value = "/book-info")
    public ModelAndView getBookInfo(@RequestParam("getId") int getId,
                                    @ModelAttribute("user") User user) {
        if (user.getLogin().equals("")) {
            return new ModelAndView("redirect:" + "/main");
        }

        ModelAndView modelAndView = new ModelAndView();
        Book book = bookService.getBookByID(getId);
        if (book != null) {
            modelAndView.addObject("book", book);
        } else {
            throw new NoSuchElementException();
        }
        modelAndView.setViewName("book");
        return modelAndView;
    }

    @RequestMapping(value = "/download-book")
    public ModelAndView downloadBook(@RequestParam("bookID") int bookID,
                                     @ModelAttribute("user") User user,
                                     HttpServletRequest request, HttpServletResponse response) {
        if (user.getLogin().equals("")) {
            return new ModelAndView("redirect:" + "/main");
        }

        ModelAndView modelAndView = new ModelAndView();
        Book book = bookService.getBookByID(bookID);
        modelAndView.setViewName("book");
        modelAndView.addObject("book", book);
        String fullPath = librarySettings.getBooksPath() + book.getAuthor() +
                " " + book.getName();

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
        try {
            response.setHeader("Content-Disposition", "attachment; filename=\"" +
                    MimeUtility.encodeWord(downloadFile.getName(), "utf-8", "Q") + "\"");
        } catch (UnsupportedEncodingException e) {
            LOG.error("Exception in downloadBook()", e);
        }

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
        return modelAndView;
    }

    @RequestMapping(value = "/delete-book")
    public void deleteBook(@RequestParam("bookID") int bookID,
                           @ModelAttribute("user") User user,
                           HttpServletResponse response) throws IOException {

        if (user.getRole().equals(UserService.ADMIN_ROLE)) {
            Book book = bookService.getBookByID(bookID);
            if (book != null) {
                bookService.deleteBook(book);
            }
        }
        response.sendRedirect("/main");
    }

    @RequestMapping(value = "/upload")
    public ModelAndView upload(@ModelAttribute("user") User user) {
        if (user.getLogin().equals("")) {
            return new ModelAndView("redirect:" + "/main");
        }

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("uncheckedBook", new UncheckedBook());
        List<Notification> userNotifications =
                notificationService.getNotificationsForUser(user.getId());
        modelAndView.addObject("notifs", userNotifications);
        modelAndView.setViewName("upload");
        return modelAndView;
    }

    @RequestMapping(value = "/delete-notif")
    public void deleteNotification(@RequestParam("notifID") int notifID,
                                   @ModelAttribute("user") User user,
                                   HttpServletResponse response) throws IOException {

        Notification notification = notificationService.getNotificationByID(notifID);
        if (notification != null && notification.getUserID() == user.getId()) {
            notificationService.deleteNotification(notification);
        }
        response.sendRedirect("/upload");
    }

    @RequestMapping(value = "/upload-file")
    public ModelAndView uploadFile(@RequestParam("file") MultipartFile file,
                                   @ModelAttribute("uncheckedBook") UncheckedBook uncheckedBook,
                                   @ModelAttribute("user") User user,
                                   RedirectAttributes redirect) {
        if (user.getLogin().equals("")) {
            return new ModelAndView("redirect:" + "/main");
        }

        String name;

        String success = "no";
        if (!file.isEmpty()) {
            try {
                name = file.getOriginalFilename();

                double bytess = file.getSize();
                double kilobytes = (bytess / 1024);
                double megabytes = (kilobytes / 1024);
                if (megabytes > 20) {
                    redirect.addFlashAttribute("message", "File is too big! " +
                            "Maximum allowed file size is 20 MB.");
                    redirect.addFlashAttribute("success", "no");
                    return new ModelAndView("redirect:" + "/upload");
                }

                if (!bookService.isExtensionValid(name)
                        || "application/x-msdownload".equals(file.getContentType())) {
                    redirect.addFlashAttribute("message", "Invalid file format!" +
                            "You can only download text files.");
                    redirect.addFlashAttribute("success", "no");
                    return new ModelAndView("redirect:" + "/upload");
                }

                byte[] bytes = file.getBytes();
                String uploadPath = librarySettings.getUncheckedPath();

                File uploadedFile = new File(uploadPath + name);
                if (uploadedFile.exists()) {
                    redirect.addFlashAttribute("message", "A file with this " +
                            "name is already exist!");
                    redirect.addFlashAttribute("success", "no");
                    return new ModelAndView("redirect:" + "/upload");
                }

                BufferedOutputStream stream = new BufferedOutputStream(
                        new FileOutputStream(uploadedFile));
                stream.write(bytes);
                stream.flush();
                stream.close();

                Tika tika = new Tika();
                if ("application/x-msdownload".equals(tika.detect(uploadedFile))) {
                    redirect.addFlashAttribute("message", "Invalid file format!" +
                            "You can only download text files.");
                    uploadedFile.delete();
                    redirect.addFlashAttribute("success", "no");
                    return new ModelAndView("redirect:" + "/upload");
                }

                uncheckedBook.setUserID(user.getId());
                bookService.addNewUncheckedBook(uncheckedBook.getUserID(),
                        name, uncheckedBook.getAuthor(),
                        uncheckedBook.getDescription());

                redirect.addFlashAttribute("message", "The file is successfully uploaded!");
                success = "yes";
            } catch (Exception e) {
                LOG.error("Loading error. Method uploadFile()", e);
                redirect.addFlashAttribute("message", "Loading error!");
            }
        } else {
            redirect.addFlashAttribute("message", "Loading error!");
        }
        redirect.addFlashAttribute("success", success);
        return new ModelAndView("redirect:" + "/upload");
    }
}
