package ru.commonuser.gameTracker.service;

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
import ru.commonuser.gameTracker.entity.User;
import ru.commonuser.gameTracker.exception.error.ErrorCodeConstants;
import ru.commonuser.gameTracker.exception.error.ErrorInformationBuilder;
import ru.commonuser.gameTracker.repository.PermissionRepository;
import ru.commonuser.gameTracker.repository.UserRepository;
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
    public User getCurrentUser() throws ServerException {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

            UserDetailsImp userDetails = (UserDetailsImp) authentication.getPrincipal();
            UserWrapper user = userDetails.getUser();
            return getUser(user.getId());
        } catch (Exception ex) {
            // TODO:
            return null;
//            throw new ServerException(ErrorInformationBuilder.build(ErrorCodeConstants.USER_INFO_ERROR), ex);
        }
    }

    // TODO:
    private User getUser(Long id) throws ServerException {
        return userRepository.findById(id)
                .orElse(null);
//                .orElseThrow(() -> new ServerException(ErrorInformationBuilder.build(ErrorCodeConstants.DATA_NOT_FOUND)));
    }

    /**
     * Изменяет пароль пользователя в соответсвии с информацией из {@link UserWrapper}.
     * Пароль не меняется если задан пароль короче 8 символом или пароль не совпадает с паролем подтвеждения
     *
     * @param passwordWrapper информация о пароле пользователя
     * @return CustomHttpObject с кодом "OK" или с кодом "ERROR" и сообщением об ошибке
     */
    public void changePassword(User user, UserPasswordWrapper passwordWrapper) throws ServerException {
        if (validatePassword(passwordWrapper)) {
            user.setPassword(passwordWrapper.getPassword());
        }
    }

    public boolean validatePassword(UserPasswordWrapper passwordWrapper) throws ServerException {
        if (passwordWrapper.getPassword().length() < CredentialsUtil.PASSWORD_MIN_LENGTH) {
            // TODO:
//            throw new ServerException(ErrorInformationBuilder.build(ErrorCodeConstants.USER_PASSWORD_LENGTH_ERROR));
            return false;
        }
        if (!Objects.equals(passwordWrapper.getPassword(), passwordWrapper.getConfirmPassword())) {
            // TODO:
//            throw new ServerException(ErrorInformationBuilder.build(ErrorCodeConstants.USER_PASSWORD_COMPARE_ERROR));
            return false;
        }
        return true;
    }
}
