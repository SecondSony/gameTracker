package ru.commonuser.gameTracker.controller.view;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import ru.commonuser.gameTracker.dto.UserWrapper;
import ru.commonuser.gameTracker.dto.filters.UserFilterWrapper;
import ru.commonuser.gameTracker.enums.UserRole;
import ru.commonuser.gameTracker.enums.UserStatus;
import ru.commonuser.gameTracker.exception.ServersException;
import ru.commonuser.gameTracker.service.UserService;

@Log4j2
@Controller
@RequestMapping(value = "admin/users")
public class UserController {
    private String USER_VIEWS_PATH = "admin/user/";

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping(value = "")
    public ModelAndView list(Pageable pageable, UserFilterWrapper filterUserWrapper) throws ServersException, java.rmi.ServerException {
        ModelAndView usersPage = new ModelAndView(USER_VIEWS_PATH + "list");
        usersPage.addObject("filter", filterUserWrapper);
        usersPage.addObject("page", userService.getPageByFilter(pageable, filterUserWrapper));
        prepareUserModelAndView(usersPage);
        return usersPage;
    }

    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public ModelAndView addPage() {
        ModelAndView addPage = new ModelAndView(USER_VIEWS_PATH + "add");
        addPage.addObject("user", new UserWrapper());
        addPage.addObject("roles", UserRole.values());
        return addPage;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ModelAndView editPage(@PathVariable("id") Long id) throws ServersException {
        ModelAndView editPage = new ModelAndView(USER_VIEWS_PATH + "edit");
        editPage.addObject("user", userService.getById(id));
        prepareUserModelAndView(editPage);
        return editPage;
    }

    private void prepareUserModelAndView(ModelAndView modelAndView) {
        modelAndView.addObject("statuses", UserStatus.values());
        modelAndView.addObject("roles", UserRole.values());
    }
}
