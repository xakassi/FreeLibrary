package library.controller;

import library.extra.UserChecker;
import library.model.User;
import library.services.AuthenticationService;
import library.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;

@Controller
@SessionAttributes("user")
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private AuthenticationService authenticationService;
    @Autowired
    UserChecker userChecker;

    private static final Logger LOG = LoggerFactory.getLogger(UserController.class.getName());

    @RequestMapping(value = "/")
    public ModelAndView main() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("user", new User());
        modelAndView.setViewName("index");
        return modelAndView;
    }

    @RequestMapping(value = "/check-user")
    public ModelAndView checkUser(@ModelAttribute("user") User user, HttpServletRequest request,
                                  RedirectAttributes redirect) {
        ModelAndView modelAndView = new ModelAndView();

        if (authenticationService.authenticate(user.getLogin(), user.getPasswordHash())) {
            user = userService.getUserByLogin(user.getLogin());
            redirect.addFlashAttribute("user", user);
            return new ModelAndView("redirect:" + "/main");
        } else {
            request.setAttribute("message", "Can not log in.\n" +
                    "Please check the spelling of your login and password.");
            modelAndView.setViewName("index");
        }
        return modelAndView;
    }

    @RequestMapping(value = "/reg-user")
    public ModelAndView regUser(@ModelAttribute("user") User user) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("registration");
        modelAndView.addObject("user", user);
        return modelAndView;
    }

    @RequestMapping(value = "/check-login")
    public ModelAndView checkLogin(@ModelAttribute("user") User user,
                                   @RequestParam("password") String passwordConfirm,
                                   HttpServletRequest request) {
        ModelAndView modelAndView = new ModelAndView();

        String message = "This login is already in use!";
        String success = "no";
        String userCheckerResult = userChecker.check(user, passwordConfirm);
        if (userCheckerResult.equals("Success!")) {
            if (userService.register(user.getLogin(), user.getFirstName(),
                    user.getLastName(), user.getPasswordHash(), UserService.USER_ROLE)) {
                message = "You have successfully registered!";
                success = "yes";
            }
        } else {
            message = userCheckerResult;
        }
        request.setAttribute("success", success);
        request.setAttribute("message", message);
        modelAndView.setViewName("registration");
        return modelAndView;
    }

    @RequestMapping(value = "/reg-admin")
    public ModelAndView regAdmin(@ModelAttribute("user") User user) {
        if (user.getLogin().equals("") || !user.getRole().equals(UserService.ADMIN_ROLE)) {
            return new ModelAndView("redirect:" + "/main");
        }
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("adminRegistration");
        modelAndView.addObject("newUser", new User());
        return modelAndView;
    }

    @RequestMapping(value = "/check-admin-login")
    public ModelAndView checkAdminLogin(@ModelAttribute("newUser") User newUser,
                                        @RequestParam("password") String passwordConfirm,
                                        HttpServletRequest request) {
        User user = (User) request.getSession().getAttribute("user");
        if (user == null || user.getLogin().equals("") ||
                !user.getRole().equals(UserService.ADMIN_ROLE)) {
            return new ModelAndView("redirect:" + "/main");
        }

        ModelAndView modelAndView = new ModelAndView();

        String message = "This login is already in use!";
        String success = "no";
        String userCheckerResult = userChecker.check(newUser, passwordConfirm);
        if (userCheckerResult.equals("Success!")) {
            if (userService.register(newUser.getLogin(), newUser.getFirstName(),
                    newUser.getLastName(), newUser.getPasswordHash(), UserService.ADMIN_ROLE)) {
                message = "You have successfully registered a new administrator!";
                success = "yes";
            }
        } else {
            message = userCheckerResult;
        }
        request.setAttribute("success", success);
        request.setAttribute("message", message);
        modelAndView.setViewName("adminRegistration");
        return modelAndView;
    }

    @RequestMapping(value = "/settings")
    public ModelAndView makeSettings(@ModelAttribute("user") User user) {
        if (user.getLogin().equals("")) {
            return new ModelAndView("redirect:" + "/main");
        }
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("settings");
        modelAndView.addObject("changeUser", new User(user));
        return modelAndView;
    }

    @RequestMapping(value = "/change-user-parameters")
    public ModelAndView changeUserParameters(@ModelAttribute("changeUser") User changeUser,
                                             @RequestParam("password") String passwordConfirm,
                                             @RequestParam("passwordOld") String passwordOld,
                                             HttpServletRequest request) {
        User user = (User) request.getSession().getAttribute("user");
        if (user.getLogin().equals("")) {
            return new ModelAndView("redirect:" + "/main");
        }

        changeUser.setLogin(user.getLogin());
        String message = "", success = "no";
        if (changeUser.getPasswordHash().equals("")) {
            changeUser.setPasswordHash(user.getPasswordHash());
            passwordConfirm = user.getPasswordHash();
        }
        String userCheckerResult = userChecker.check(changeUser, passwordConfirm);
        if (userCheckerResult.equals("Success!")) {
            if (!changeUser.getFirstName().equals(user.getFirstName())) {
                userService.updateFirstName(user.getId(), changeUser.getFirstName());
                message += "Your first name was successfully changed!\n";
                success = "yes";
            }
            if (!changeUser.getLastName().equals(user.getLastName())) {
                userService.updateLastName(user.getId(), changeUser.getLastName());
                message += "Your last name was successfully changed!\n";
                success = "yes";
            }
            if (!changeUser.getPasswordHash().equals(user.getPasswordHash())) {
                if (authenticationService.authenticate(user.getLogin(), passwordOld)) {
                    userService.updatePassword(user.getId(), changeUser.getPasswordHash());
                    message += "Your password was successfully changed!\n";
                    success = "yes";
                } else {
                    message += "Incorrect old password!\n";
                }
            }
        } else {
            message = userCheckerResult;
        }

        request.setAttribute("success", success);
        request.setAttribute("message", message);

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("settings");
        return modelAndView;
    }

    @RequestMapping(value = "/delete-user")
    public ModelAndView deleteUser(@ModelAttribute("user") User user) {
        if (user.getLogin().equals("") || user.getLogin().equals("mainAdmin")) {
            return new ModelAndView("redirect:" + "/main");
        }

        ModelAndView modelAndView = new ModelAndView();
        userService.delete(user);
        modelAndView.setViewName("index");
        return modelAndView;
    }
}
