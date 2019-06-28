package ru.commonuser.gameTracker.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.commonuser.gameTracker.dto.UrlWrapper;
import ru.commonuser.gameTracker.entity.Url;
import ru.commonuser.gameTracker.exception.ServersException;
import ru.commonuser.gameTracker.exception.error.ErrorCodeConstants;
import ru.commonuser.gameTracker.exception.error.ErrorInformationBuilder;
import ru.commonuser.gameTracker.repository.UrlRepository;

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
            // TODO:
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
            // TODO:
            throw new ServersException(ErrorInformationBuilder.build(ErrorCodeConstants.USER_ADD_ERROR), ex);
        }
    }

    /**
     * Получает ссылку url по id
     * @param id идентификатор
     * @return Сущность url
     * @throws ServersException
     */
    public Url getUrl(Long id) throws ServersException {
        return urlRepository.findFirstById(id)
                .orElseThrow(() -> new ServersException(ErrorInformationBuilder.build(ErrorCodeConstants.DATA_NOT_FOUND)));
    }

    /**
     * Удаляет все информации о ссылке
     * @param id идентификатор
     * @throws ServersException
     */
    public void delete(Long id) throws ServersException{
        try {
            Url url = getUrl(id);
            urlRepository.delete(url);
        } catch (ServersException ex) {
            throw ex;
        } catch (Exception ex){
            // TODO:
            throw new ServersException(ErrorInformationBuilder.build(ErrorCodeConstants.USER_ADD_ERROR), ex);
        }
    }

    /**
     * Удаляет информацию о ссылке
     * @throws ServersException
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
}
