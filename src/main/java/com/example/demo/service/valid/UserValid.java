package com.example.demo.service.valid;

import com.example.demo.config.ApplicationProperties;
import com.example.demo.dao.UserDao;
import com.example.demo.model.entity.User;
import com.example.demo.util.ValidUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserValid {

    @Autowired
    private ApplicationProperties properties;

    @Autowired
    private UserDao userDao;

    public void validateInsert(User user) {
        validateFields(user);
        validateRegex(user);
        validateEmailExist(user.getEmail());
    }

    private void validateFields(User user) {
        ValidUtil.isRequired(user.getName(), "Nombre");
        ValidUtil.isRequired(user.getEmail(), "Correo electrónico");
        ValidUtil.isRequired(user.getPassword(), "Contraseña");
        if (user.getPhones() != null && !user.getPhones().isEmpty()) {
            user.getPhones().forEach(phone -> {
                ValidUtil.isRequired(phone.getNumber(), "Número de teléfono");
                ValidUtil.isRequired(phone.getCityCode(), "Código de ciudad");
                ValidUtil.isRequired(phone.getCountryCode(), "Código de país");
                phone.setUser(user);
            });
        }
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

}
