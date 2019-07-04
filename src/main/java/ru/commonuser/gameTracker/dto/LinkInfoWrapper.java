package ru.commonuser.gameTracker.dto;

import lombok.Data;
import ru.commonuser.gameTracker.entity.LinkInfo;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
public class LinkInfoWrapper implements ObjectWrapper<LinkInfo>, Serializable {
    @NotNull
    private String name;

    @NotNull
    private String url;

    @NotNull
    private String urlLink;

    @Override
    public void toWrapper(LinkInfo item) {
        name = item.getName();
        url = item.getUrl();
        urlLink = item.getUrlLink();
    }

    @Override
    public void fromWrapper(LinkInfo item) {
        item.setName(name);
        item.setUrl(url);
        item.setUrlLink(urlLink);
    }
}
