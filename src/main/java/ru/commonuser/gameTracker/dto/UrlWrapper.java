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
    @Size(max = 100)
    private String urlPattern;

    @NotNull
    @Size(max = 100)
    private String cssPattern;

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
        urlPattern = item.getUrlPattern();
        cssPattern = item.getCssPattern();
        isAuth = item.getIsAuth();
        login = item.getLogin();
        password = item.getPassword();
    }

    @Override
    public void fromWrapper(Url item) {
        item.setName(name);
        item.setUrl(url);
        item.setUrlPattern(urlPattern);
        item.setCssPattern(cssPattern);
        item.setIsAuth(isAuth);
        item.setLogin(login);
        item.setPassword(password);
    }
}
