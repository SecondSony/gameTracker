package ru.commonuser.gameTracker.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.commonuser.gameTracker.config.UserDetailsImp;
import ru.commonuser.gameTracker.dto.UserPasswordWrapper;
import ru.commonuser.gameTracker.dto.UserWrapper;
import ru.commonuser.gameTracker.dto.filters.UserFilterWrapper;
import ru.commonuser.gameTracker.entity.User;
import ru.commonuser.gameTracker.enums.UserStatus;
import ru.commonuser.gameTracker.exception.ServersException;
import ru.commonuser.gameTracker.exception.error.ErrorCodeConstants;
import ru.commonuser.gameTracker.exception.error.ErrorInformationBuilder;
import ru.commonuser.gameTracker.repository.PermissionRepository;
import ru.commonuser.gameTracker.repository.UserRepository;
import ru.commonuser.gameTracker.repository.specifications.UserSpecification;
import ru.commonuser.gameTracker.utils.CredentialsUtil;

import java.rmi.ServerException;
import java.util.Date;
import java.util.Objects;

@Service
@Transactional(rollbackFor = ServerException.class)
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;
    private final PermissionRepository permissionRepository;

    public UserService(UserRepository userRepository, PermissionRepository permissionRepository){
        this.userRepository = userRepository;
        this.permissionRepository = permissionRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findFirstByLogin(username)
                .map(UserWrapper::new)
                .map(user -> new UserDetailsImp(user, permissionRepository.findActionsByRole(user.getRole())))
                .orElseThrow(() -> new UsernameNotFoundException(ErrorCodeConstants.messages.get(ErrorCodeConstants.DATA_NOT_FOUND)));
    }

    public void updateLastTimeOnline(Long id) {
        User user = userRepository.findOne(id);
        user.setDateLastOnline(new Date());
        userRepository.save(user);
    }

    /**
     * Получает пользователя из текущей сессии
     *
     * @return сущность пользователя
     */
    @Transactional(noRollbackFor = ServerException.class)
    public User getCurrentUser() throws ServersException {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

            UserDetailsImp userDetails = (UserDetailsImp) authentication.getPrincipal();
            UserWrapper user = userDetails.getUser();
            return getUser(user.getId());
        } catch (Exception ex) {
            throw new ServersException(ErrorInformationBuilder.build(ErrorCodeConstants.USER_INFO_ERROR), ex);
        }
    }

    // TODO:
    private User getUser(Long id) throws ServersException {
        return userRepository.findById(id)
                .orElseThrow(() -> new ServersException(ErrorInformationBuilder.build(ErrorCodeConstants.DATA_NOT_FOUND)));
    }

    /**
     * Изменяет пароль пользователя в соответсвии с информацией из {@link UserWrapper}.
     * Пароль не меняется если задан пароль короче 8 символом или пароль не совпадает с паролем подтвеждения
     *
     * @param passwordWrapper информация о пароле пользователя
     * @return CustomHttpObject с кодом "OK" или с кодом "ERROR" и сообщением об ошибке
     */
    public void changePassword(User user, UserPasswordWrapper passwordWrapper) throws ServersException {
        if (validatePassword(passwordWrapper)) {
            user.setPassword(passwordWrapper.getPassword());
        }
    }

    /**
     * Изменяет пароль пользователя в соответсвии с информацией из {@link UserWrapper}
     *
     * @return CustomHttpObject с кодом "OK" или с кодом "ERROR" и сообщением об ошибке
     */
    public void changePassword(UserPasswordWrapper passwordWrapper)
            throws ServersException {
        try {
            User user = getUser(passwordWrapper.getId());

            changePassword(user, passwordWrapper);
        } catch (ServersException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new ServersException(ErrorInformationBuilder.build(ErrorCodeConstants.USER_PASSWORD_ERROR), ex);
        }
    }

    public boolean validatePassword(UserPasswordWrapper passwordWrapper) throws ServersException {
        if (passwordWrapper.getPassword().length() < CredentialsUtil.PASSWORD_MIN_LENGTH) {
            throw new ServersException(ErrorInformationBuilder.build(ErrorCodeConstants.USER_PASSWORD_LENGTH_ERROR));
        }
        if (!Objects.equals(passwordWrapper.getPassword(), passwordWrapper.getConfirmPassword())) {
            throw new ServersException(ErrorInformationBuilder.build(ErrorCodeConstants.USER_PASSWORD_COMPARE_ERROR));
        }
        return true;
    }

    /**
     * Получает из базы страницу объектов {@link UserWrapper} в зависимости от информации о пагинаторе и параметрах фильтра
     *
     * @param pageable          информация о пагинаторе
     * @param filterUserWrapper информация о фильтре
     * @return страница объектов
     */
    public Page getPageByFilter(Pageable pageable, UserFilterWrapper filterUserWrapper) throws ServersException {
        try {
            return userRepository.findAll(UserSpecification.build(filterUserWrapper), pageable)
                    .map(UserWrapper::new);
        } catch (Exception ex) {
            throw new ServersException(ErrorInformationBuilder.build(ErrorCodeConstants.USER_LIST_ERROR), ex);
        }
    }

    /**
     * Получает информацию о пользователе с заданным идентификатором из базы и пребразует ее в объект класса {@link UserWrapper}
     *
     * @param id идентификатор пользователя
     * @return объект, содержащий информацию о пользователе
     */
    public UserWrapper getById(Long id) throws ServersException {
        try {
            return new UserWrapper(getUser(id));
        } catch (Exception ex) {
            throw new ServersException(ErrorInformationBuilder.build(ErrorCodeConstants.USER_INFO_ERROR), ex);
        }
    }

    /**
     * Добавляет информацию о новом пользователе в базу из {@link UserWrapper}
     *
     * @param userWrapper инфомарция о новом пользователе
     * @return CustomHttpObject с кодом "OK" или с кодом "ERROR" и сообщением об ошибке
     */
    public void add(UserWrapper userWrapper) throws ServersException {
        try {
            User user = new User();
            userWrapper.fromWrapper(user);

            if (userRepository.findFirstByLogin(user.getLogin()).isPresent()) {
                throw new ServersException(ErrorInformationBuilder.build(ErrorCodeConstants.USER_LOGIN_ALREADY_EXIST));
            }

            changePassword(user, new UserPasswordWrapper(userWrapper));
            user.setDateCreate(new Date());
            user.setStatus(UserStatus.ACTIVE);

            userRepository.save(user);
        } catch (ServersException ex) {
            throw ex;
        } catch (Exception ex){
            throw new ServersException(ErrorInformationBuilder.build(ErrorCodeConstants.USER_ADD_ERROR), ex);
        }
    }

    /**
     * Обновляет информацию о пользователе в базе из {@link UserWrapper}
     *
     * @param userWrapper инфомарция о пользователе
     * @return CustomHttpObject с кодом "OK" или с кодом "ERROR" и сообщением об ошибке
     */
    public void edit(UserWrapper userWrapper) throws ServersException {
        try {
            User user = getUser(userWrapper.getId());
            userWrapper.fromWrapper(user);
            userRepository.save(user);
        } catch (ServersException ex) {
            throw ex;
        } catch (Exception ex){
            throw new ServersException(ErrorInformationBuilder.build(ErrorCodeConstants.USER_ADD_ERROR), ex);
        }
    }

    /**
     * Устанавливает пользователю с заданным идентификатором статус "BLOCK(Заблокирован)", если это не текущий пользователь системы
     *
     * @param id идентификатор пользователя
     * @return CustomHttpObject с кодом "OK" или с кодом "ERROR" и сообщением об ошибке
     */
    public void block(Long id) throws ServersException {
        try {
            User user = getUser(id);

            if (user == getCurrentUser()) {
                throw new ServersException(ErrorInformationBuilder.build(ErrorCodeConstants.USER_BLOCK_SELF_ERROR));
            }

            user.setDateBlock(new Date());
            user.setStatus(UserStatus.BLOCK);
            userRepository.save(user);
        } catch (ServersException ex) {
            throw ex;
        } catch (Exception ex){
            throw new ServersException(ErrorInformationBuilder.build(ErrorCodeConstants.USER_ADD_ERROR), ex);
        }
    }

    public void unblock(Long id) throws ServersException {
        try {
            User user = getUser(id);
            user.setDateBlock(null);
            user.setStatus(UserStatus.ACTIVE);
            userRepository.save(user);
        } catch (ServersException ex) {
            throw ex;
        } catch (Exception ex){
            throw new ServersException(ErrorInformationBuilder.build(ErrorCodeConstants.USER_ADD_ERROR), ex);
        }
    }
}
