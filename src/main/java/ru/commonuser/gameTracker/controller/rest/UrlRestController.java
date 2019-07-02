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
@RequestMapping(value = "admin/urls")
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

    @PatchMapping("/{id}")
    public void edit(@RequestBody UrlWrapper urlWrapper) throws ServersException {
        urlService.edit(urlWrapper);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") Long id) throws ServersException {
        urlService.delete(id);
    }

    @DeleteMapping("")
    public void deleteAll() {
        urlService.deleteAll();
    }

    @GetMapping("/{id}")
    public boolean isAuth(@PathVariable("id") Long id) throws ServersException {
        return urlService.isAuth(id);
    }
}
