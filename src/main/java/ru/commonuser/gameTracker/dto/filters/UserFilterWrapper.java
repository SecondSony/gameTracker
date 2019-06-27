package ru.commonuser.gameTracker.dto.filters;


import lombok.Getter;
import lombok.Setter;
import ru.commonuser.gameTracker.enums.UserRole;
import ru.commonuser.gameTracker.enums.UserStatus;

public class UserFilterWrapper extends ObjectFilter
{
    @Getter
    @Setter
    private String mail;
    @Getter
    @Setter
    private UserRole role;
    @Getter
    @Setter
    private UserStatus status;
}
