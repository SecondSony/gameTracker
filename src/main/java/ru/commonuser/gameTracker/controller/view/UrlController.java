package ru.commonuser.gameTracker.controller.view;

import groovy.util.logging.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import ru.commonuser.gameTracker.service.UrlService;

@Log4j2
@Controller
@RequestMapping(value = "admin/urls")
public class UrlController {
    private String URL_VIEWS_PATH = "admin/url/";

    private final UrlService urlService;

    @Autowired
    public UrlController(UrlService urlService) {
        this.urlService = urlService;
    }

    @RequestMapping(value = "")
    public ModelAndView listPage() {
        ModelAndView page = new ModelAndView(URL_VIEWS_PATH + "list");
        page.addObject("urls", urlService.getAll());
        return page;
    }

    @RequestMapping(value = "/add")
    public ModelAndView addPage() {
        ModelAndView page = new ModelAndView(URL_VIEWS_PATH + "add");
        return page;
    }

    @RequestMapping(value = "/{id}")
    public ModelAndView editPage() {
        ModelAndView page = new ModelAndView(URL_VIEWS_PATH + "edit");
        return page;
    }
}
