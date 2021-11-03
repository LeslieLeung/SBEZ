package com.ameow.sbez.service.impl;

import com.ameow.sbez.constants.Roles;
import com.ameow.sbez.service.TestService;
import com.ameow.sbez.utils.TokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Leslie Leung
 * @description
 * @date 2021/11/3
 */
@Service
public class TestServiceImpl implements TestService {

    @Autowired
    private TokenUtils tokenUtils;

    @Override
    public int checkPassword(String username, String password) {
        if (username.equals("admin") && password.equals("123456")) {
            return 111; // admin, id=111
        } else if (username.equals("user") && password.equals("654321")) {
            return 222; // user, id=222
        }
        return 0; // 密码错误
    }

    @Override
    public String grantToken(int id, int role) {
        return tokenUtils.grantToken(id, role);
    }

    @Override
    public int getRole(int id) {
        if (id == 111) {
            return Roles.ROLE_ADMIN;
        } else if (id == 222) {
            return Roles.ROLE_USER;
        }
        return 0; // error
    }
}
