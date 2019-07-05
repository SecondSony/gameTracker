package ru.commonuser.gameTracker.controller.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.commonuser.gameTracker.dto.UrlWrapper;
import ru.commonuser.gameTracker.entity.Url;
import ru.commonuser.gameTracker.exception.ServersException;
import ru.commonuser.gameTracker.service.UrlService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = "admin/rest_urls")
public class UrlRestController {
    private final UrlService urlService;

    @Autowired
    public UrlRestController(UrlService urlService) {
        this.urlService = urlService;
    }

    @GetMapping("")
    public List<Url> getAll() {
        return urlService.getAll();
    }

    @GetMapping("/{id}/get")
    public Url get(@PathVariable("id") Long id) throws ServersException {
        return urlService.getUrl(id);
    }

    @PostMapping("/add")
    public void add(@Valid UrlWrapper urlWrapper) throws ServersException {
        urlService.add(urlWrapper);
    }

    @PatchMapping("/{id}/edit")
    public void edit(@Valid UrlWrapper urlWrapper) throws ServersException {
        urlService.edit(urlWrapper);
    }

    @PatchMapping("/{id}/edit_url")
    public void editUrl(Long id, String url) throws ServersException {
        urlService.editUrl(id, url);
    }

    @PatchMapping("/{id}/edit_url_pattern")
    public void editUrlPattern(Long id, String urlPattern) throws ServersException {
        urlService.editUrlPattern(id, urlPattern);
    }

    @PatchMapping("/{id}/edit_css_pattern")
    public void editCssPattern(Long id, String urlPattern) throws ServersException {
        urlService.editCssPattern(id, urlPattern);
    }

    @DeleteMapping("/{id}/delete")
    public void delete(@PathVariable("id") Long id) throws ServersException {
        urlService.delete(id);
    }

    @DeleteMapping("/deleteAll")
    public void deleteAll() {
        urlService.deleteAll();
    }

    @GetMapping("/{id}/getIsAuth")
    public boolean isAuth(@PathVariable("id") Long id) throws ServersException {
        return urlService.isAuth(id);
    }
}
