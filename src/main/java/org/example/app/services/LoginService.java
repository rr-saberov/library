package org.example.app.services;

import org.apache.log4j.Logger;
import org.example.web.dto.LoginForm;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class LoginService {
    private Logger logger = Logger.getLogger(LoginService.class);
    private static Map<String, String> accounts = new HashMap<>();

    static {
        accounts.put("root", "123");
    }

    public void addUser(String name, String password) {
        accounts.put(name, password);
    }

    public boolean authenticate(LoginForm loginForm) {
        logger.info("try auth with user-form: " + loginForm);

        String userPass = accounts.get(loginForm.getUsername());
        return userPass != null && userPass.equals(loginForm.getPassword());
    }
}
