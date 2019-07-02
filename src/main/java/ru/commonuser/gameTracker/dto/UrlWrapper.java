package ru.commonuser.gameTracker.dto;

import lombok.Data;
import ru.commonuser.gameTracker.entity.Url;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class UrlWrapper implements ObjectWrapper<Url> {
    private Long id;

    @NotNull
    @Size(max = 100)
    private String name;

    @NotNull
    @Size(max = 100)
    private String url;

    @NotNull
    private Boolean isAuth;

    @Size(max = 100)
    private String login;

    @Size(max = 100)
    private String password;

    public UrlWrapper() {

    }

    @Override
    public void toWrapper(Url item) {
        id = item.getId();
        name = item.getName();
        url = item.getUrl();
        isAuth = item.getIsAuth();
        login = item.getLogin();
        password = item.getPassword();
    }

    @Override
    public void fromWrapper(Url item) {
        item.setName(name);
        item.setUrl(url);
        item.setIsAuth(isAuth);
        item.setLogin(login);
        item.setPassword(password);
    }
}
