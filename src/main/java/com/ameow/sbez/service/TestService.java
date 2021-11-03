package com.ameow.sbez.service;

/**
 * @author Leslie Leung
 * @description
 * @date 2021/11/3
 */
public interface TestService {
    int checkPassword(String username, String password);

    String grantToken(int id, int role);

    int getRole(int id);
}
