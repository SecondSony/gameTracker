package ru.commonuser.gameTracker.service;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.commonuser.gameTracker.dto.LinkInfoWrapper;
import ru.commonuser.gameTracker.dto.UrlWrapper;
import ru.commonuser.gameTracker.entity.Url;
import ru.commonuser.gameTracker.exception.ServersException;
import ru.commonuser.gameTracker.exception.error.ErrorCodeConstants;
import ru.commonuser.gameTracker.exception.error.ErrorInformationBuilder;
import ru.commonuser.gameTracker.repository.UrlRepository;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional(rollbackFor = ServersException.class)
public class UrlService {
    private UrlRepository urlRepository;

    public UrlService(UrlRepository urlRepository) {
        this.urlRepository = urlRepository;
    }

    /**
     * Добавляет новую информацию о ссылке в базу из {@link UrlWrapper}
     *
     * @param urlWrapper новая ссылка
     * @return CustomHttpObject с кодом "OK" или с кодом "ERROR" и сообщением об ошибке
     */
    public void add(UrlWrapper urlWrapper) throws ServersException {
        try {
            Url url = new Url();
            urlWrapper.fromWrapper(url);

            if (urlRepository.findFirstByUrl(url.getUrl()).isPresent()) {
                // TODO:
                throw new ServersException(ErrorInformationBuilder.build(ErrorCodeConstants.USER_LOGIN_ALREADY_EXIST));
            }

            urlRepository.saveAndFlush(url);
        } catch (ServersException ex) {
            throw ex;
        } catch (Exception ex){
            throw new ServersException(ErrorInformationBuilder.build(ErrorCodeConstants.USER_ADD_ERROR), ex);
        }
    }

    /**
     * Обновляет информацию о ссылке url в базе из {@link UrlWrapper}
     *
     * @param urlWrapper инфомарция о ссылке
     * @return CustomHttpObject с кодом "OK" или с кодом "ERROR" и сообщением об ошибке
     */
    public void edit(UrlWrapper urlWrapper) throws ServersException {
        try {
            Url url = getUrl(urlWrapper.getId());
            urlWrapper.fromWrapper(url);
            urlRepository.saveAndFlush(url);
        } catch (ServersException ex) {
            throw ex;
        } catch (Exception ex){
            throw new ServersException(ErrorInformationBuilder.build(ErrorCodeConstants.USER_ADD_ERROR), ex);
        }
    }

    /**
     * Обновляет паттерн ссылки запроса
     *
     * @param urlName паттерн ссылки
     * @return CustomHttpObject с кодом "OK" или с кодом "ERROR" и сообщением об ошибке
     */
    public void editUrl(Long id, String urlName) throws ServersException {
        try {
            Url url = getUrl(id);
            UrlWrapper wrapper = new UrlWrapper();
            url.setUrl(urlName);
            wrapper.toWrapper(url);
            urlRepository.saveAndFlush(url);
        } catch (ServersException ex) {
            throw ex;
        } catch (Exception ex){
            throw new ServersException(ErrorInformationBuilder.build(ErrorCodeConstants.USER_ADD_ERROR), ex);
        }
    }

    /**
     * Обновляет паттерн ссылки запроса
     *
     * @param urlPattern паттерн ссылки
     * @return CustomHttpObject с кодом "OK" или с кодом "ERROR" и сообщением об ошибке
     */
    public void editUrlPattern(Long id, String urlPattern) throws ServersException {
        try {
            Url url = getUrl(id);
            UrlWrapper wrapper = new UrlWrapper();
            url.setUrlPattern(urlPattern);
            wrapper.toWrapper(url);
            urlRepository.saveAndFlush(url);
        } catch (ServersException ex) {
            throw ex;
        } catch (Exception ex){
            throw new ServersException(ErrorInformationBuilder.build(ErrorCodeConstants.USER_ADD_ERROR), ex);
        }
    }

    /**
     * Обновляет паттерн ссылки запроса
     *
     * @param cssPattern паттерн ссылки
     * @return CustomHttpObject с кодом "OK" или с кодом "ERROR" и сообщением об ошибке
     */
    public void editCssPattern(Long id, String cssPattern) throws ServersException {
        try {
            Url url = getUrl(id);
            UrlWrapper wrapper = new UrlWrapper();
            url.setCssPattern(cssPattern);
            wrapper.toWrapper(url);
            urlRepository.saveAndFlush(url);
        } catch (ServersException ex) {
            throw ex;
        } catch (Exception ex){
            throw new ServersException(ErrorInformationBuilder.build(ErrorCodeConstants.USER_ADD_ERROR), ex);
        }
    }

    /**
     * Получает ссылку url по id
     * @param id идентификатор
     * @return Сущность url
     * @throws ServersException Ошибка на сервере
     */
    public Url getUrl(Long id) throws ServersException {
        return urlRepository.findFirstById(id)
                .orElseThrow(() -> new ServersException(ErrorInformationBuilder.build(ErrorCodeConstants.DATA_NOT_FOUND)));
    }

    /**
     * Удаляет все информации о ссылке
     * @param id идентификатор
     * @throws ServersException Ошибка на сервере
     */
    public void delete(Long id) throws ServersException{
        try {
            Url url = getUrl(id);
            urlRepository.delete(url);
        } catch (ServersException ex) {
            throw ex;
        } catch (Exception ex){
            throw new ServersException(ErrorInformationBuilder.build(ErrorCodeConstants.USER_ADD_ERROR), ex);
        }
    }

    /**
     * Удаляет информацию о ссылке
     */
    public void deleteAll() {
        urlRepository.deleteAll();
    }

    /**
     * Получает все информации о ссылке
     * @return Список ссылок
     */
    public List<Url> getAll() {
        return urlRepository.findAll();
    }

    /**
     * Проверяет ссылку на необходимость авторизации
     * @return Подтверждение на авторизацию
     */
    public Boolean isAuth(Long id) throws ServersException {
        Url url = getUrl(id);
        return url.getIsAuth();
    }

    public List<UrlWrapper> getListUrl() {
        ArrayList<UrlWrapper> urlList = new ArrayList<>();
        List<Url> urls = getAll();

        for (Url url : urls) {
            UrlWrapper item = new UrlWrapper();
            item.toWrapper(url);
            urlList.add(item);
        }

        return urlList;
    }

    // TODO:

    /**
     * Получает ссылки на файл из сайтов в базе данных
     * @param searchName название искомого файла
     * @return список ссылок
     * @throws ServersException Ошибка со стороны сервера
     */
    public List<LinkInfoWrapper> getLinks(String searchName) throws ServersException {
//        searchName = "http://4pda.ru";
        List<LinkInfoWrapper> links = new ArrayList<>();
        List<Url> urls = getAll();

        if (urls != null && urls.size() > 0) {
            try {
                for (Url urlInfo : urls) {
                    // Парсинг сайта по ссылке
                    // - получить url-ссылку по паттерну (регулярка)
                    // - получить html-док
                    // - поиск элементов с href с помощью css query
                    // - закинуть href в linkInfo
                    // - закинуть в список

                    try {
                        String urlQuery = getUrlQuery(urlInfo, searchName);
                        Document doc = Jsoup.connect(urlQuery).get();
                        Elements items = doc.select(urlInfo.getCssPattern());

                            for (Element item : items) {

                                LinkInfoWrapper w = new LinkInfoWrapper();
                                w.setName("" + item.text());
                                w.setUrl(urlInfo.getUrl());
                                w.setUrlLink(item.attr("href"));
                                links.add(w);
                            }
                    } catch (IOException ex) {

                    }
                }
            } catch (Exception ex){
                throw new ServersException(ErrorInformationBuilder.build(ErrorCodeConstants.USER_ADD_ERROR), ex);
            }
        }
        
        return links;
    }

    public String getUrlQuery(Url url, String searchName) {
        String urlQuery = url.getUrlPattern();
        urlQuery = urlQuery.replaceFirst("<url>", url.getUrl());
        urlQuery = urlQuery.replaceAll("<search_name>", searchName);
        return urlQuery;
    }
}
