package com.ameow.sbez.entity;

import lombok.Data;

/**
 * @author Leslie Leung
 * @description 用户
 * @date 2021/11/3
 */
@Data
public class User {
    private Integer id;
    private String username;
    private String password;
    private Integer role;
}
