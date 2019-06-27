package ru.commonuser.gameTracker.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class UserPasswordWrapper {

    @Getter
    @Setter
    private Long id;

    @JsonIgnore
    @NotNull
    @Size(min = 8, max = 20)
    @Getter
    @Setter
    private String password;

    @JsonIgnore
    @NotNull
    @Size(min = 8, max = 20)
    @Getter
    @Setter
    private String confirmPassword;

    public UserPasswordWrapper() {

    }

    public UserPasswordWrapper(UserWrapper userWrapper) {
        id = userWrapper.getId();
        password = userWrapper.getPassword();
        confirmPassword = userWrapper.getConfirmPassword();
    }
}
