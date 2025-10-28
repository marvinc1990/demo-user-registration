package com.example.demo.service.valid;

import com.example.demo.config.ApplicationProperties;
import com.example.demo.dao.UserDao;
import com.example.demo.model.entity.User;
import com.example.demo.util.ValidUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Component
public class UserValid {

    @Autowired
    private ApplicationProperties properties;

    @Autowired
    private UserDao userDao;

    public void validateInsert(User user) {
        validateFields(user, false);
        validateRegex(user);
        validateEmailExist(user.getEmail());
    }

    public void validateUpdate(User user) {
        validateFields(user, true);
        validateRegex(user);
        validateUser(user);
    }

    public void validateDelete(UUID userId) {
        Optional<User> optionalFound = userDao.findById(userId);
        ValidUtil.comply(optionalFound.isPresent(), "Usuario no existe");
    }

    private void validateFields(User user, boolean isUpdate) {
        if (isUpdate) {
            ValidUtil.isRequired(user.getUserId(), "Identificador de usuario");
            ValidUtil.isRequired(user.isActive(), "Estado del usuario");
        }
        ValidUtil.isRequired(user.getName(), "Nombre");
        ValidUtil.isRequired(user.getEmail(), "Correo electrónico");
        ValidUtil.isRequired(user.getPassword(), "Contraseña");
    }

    private void validateEmailExist(String email) {
        ValidUtil.comply(!userDao.existsByEmail(email),
                "El correo ya se encuentra registrado");
    }

    private void validateRegex(User user) {
        ValidUtil.complyRegex(user.getEmail(), properties.getEmailRegex(),
                "El correo no cuenta con el formato adecuado");
        ValidUtil.complyRegex(user.getPassword(), properties.getPasswordRegex(),
                "La contraseña no cuenta con el formato adecuado");
    }

    private void validateUser(User user) {
        Optional<User> optionalFound = userDao.findById(user.getUserId());
        ValidUtil.comply(optionalFound.isPresent(), "Usuario no existe");
        if (!user.getEmail().equals(optionalFound.get().getEmail())) {
            validateEmailExist(user.getEmail());
        }
        user.setCreated(optionalFound.get().getCreated());
        user.setLastLogin(optionalFound.get().getLastLogin());
        user.setToken(optionalFound.get().getToken());
    }

}
