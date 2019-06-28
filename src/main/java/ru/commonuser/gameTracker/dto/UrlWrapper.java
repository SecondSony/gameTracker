package ru.commonuser.gameTracker.dto;

import lombok.Data;
import ru.commonuser.gameTracker.entity.Url;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class UrlWrapper implements ObjectWrapper<Url> {
    @Size(min = 1)
    private Long id;

    @NotNull
    @Size(max = 100)
    private String name;

    @NotNull
    @Size(max = 100)
    private String url;

    public UrlWrapper() {

    }

    @Override
    public void toWrapper(Url item) {
        id = item.getId();
        name = item.getName();
        url = item.getUrl();
    }

    @Override
    public void fromWrapper(Url item) {
        item.setId(id);
        item.setName(name);
        item.setUrl(url);
    }
}
