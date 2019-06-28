package ru.commonuser.gameTracker.controller.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.commonuser.gameTracker.dto.UserPasswordWrapper;
import ru.commonuser.gameTracker.dto.UserWrapper;
import ru.commonuser.gameTracker.entity.User;
import ru.commonuser.gameTracker.exception.InvalidDataException;
import ru.commonuser.gameTracker.exception.NotFoundException;
import ru.commonuser.gameTracker.exception.ServersException;
import ru.commonuser.gameTracker.exception.error.ErrorCodeConstants;
import ru.commonuser.gameTracker.exception.error.ErrorInformationBuilder;
import ru.commonuser.gameTracker.service.UserService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = "admin/users")
public class UserRestController {
    private final UserService userService;

    @Autowired
    public UserRestController(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public void add(@Valid UserWrapper userWrapper, BindingResult bindingResult) throws InvalidDataException, ServersException {
        // TODO:
        if (bindingResult.hasErrors()) {
            throw new InvalidDataException(ErrorInformationBuilder.build(ErrorCodeConstants.DATA_VALIDATE_ERROR));
        }
        userService.add(userWrapper);
    }

    @RequestMapping(value = "/{id}/edit", method = RequestMethod.PATCH)
    public void editUser(@Valid UserWrapper userWrapper, BindingResult bindingResult)
            throws InvalidDataException, NotFoundException, ServersException {
        // TODO:
        if (bindingResult.hasErrors()) {
            throw new InvalidDataException(ErrorInformationBuilder.build(ErrorCodeConstants.DATA_VALIDATE_ERROR));
        }
        userService.edit(userWrapper);
    }

    @RequestMapping(value = "/{id}/password", method = RequestMethod.PATCH)
    public void changePassword(@Valid UserPasswordWrapper passwordWrapper, BindingResult bindingResult)
            throws InvalidDataException, NotFoundException, ServersException {
        // TODO:
        if (bindingResult.hasErrors()) {
            throw new InvalidDataException(ErrorInformationBuilder.build(ErrorCodeConstants.DATA_VALIDATE_ERROR));
        }
        userService.changePassword(passwordWrapper);
    }

    @RequestMapping(value = "/{id}/block", method = RequestMethod.POST)
    public void block(@PathVariable("id") Long id)
            throws InvalidDataException, ServersException, NotFoundException {
        // TODO:
        userService.block(id);
    }

    @RequestMapping(value = "/{id}/unblock", method = RequestMethod.POST)
    public void unblock(@PathVariable("id") Long id) throws NotFoundException, ServersException {
        // TODO:
        userService.unblock(id);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") Long id) throws ServersException {
        userService.delete(id);
    }

    @DeleteMapping("")
    public void deleteAll() {
        userService.deleteAll();
    }

    @GetMapping("")
    public List<User> getAll() {
        return userService.getAll();
    }
}
