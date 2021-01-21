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
        for (Map.Entry<String, String> entry : accounts.entrySet()) {
            if (entry.getKey().equals(loginForm.getUsername())) {
                return entry.getValue().equals(loginForm.getPassword());
            }
        }
        return false;
    }
}
