# SBEZ - Spring Boot EASY
> 一个基于拦截器实现的带接口访问权限管理的脚手架。

> NEWS
> - 配套说明教程即将更新，敬请关注

## 实现功能
- 优雅的接口权限管理（通过在控制器方法上打注解的方法即可实现接口权限管理）
- 封装JSON返回方法
- 提供几个前后端分离的接口，可使用postman等接口测试工具体验

## 接口文档
### 登录接口
#### 接口路径
`/login`
#### 说明
用于登录获取token，进行后续的操作。
#### 请求参数
```json
// 管理员登录
{
    "username": "admin",
    "password": "123456"
}
// 用户登录
{
    "username": "user",
    "password": "654321"
}
```
#### 返回结果
```json
{
    "code": 0,
    "data": {
        "level": 1, // admin为1，user为2
        "id": 111, // admin为111，user为222
        "token": "340a1690ac474b2888525a58f99a4f50"
    },
    "msg": "操作成功"
}
// 错误结果
{
    "code": 400,
    "msg": "用户名或密码错误"
}
```

### 获取当前用户信息接口
#### 接口路径
`/getCurrentUser`
#### 说明
用于获取当前用户存在Redis中的信息，包括id和权限。
#### 请求参数
```
不需要任何参数，在Headers中添加Token，值为之前登录接口获得的token即可，下同。
(Headers)
Token: 9f29df5d505b4b7c89e3ed240e81097a
```
#### 返回结果
```json
{
    "code": 0,
    "data": {
        "role": 2,
        "id": 222
    },
    "msg": "操作成功"
}
```

### 用户权限测试接口
#### 接口路径
`/userTest`
#### 说明
仅用户权限可访问该接口。
#### 请求参数
```
(Headers)
Token: 9f29df5d505b4b7c89e3ed240e81097a
```
#### 返回结果
```json
{
    "code": 0,
    "data": "成功使用用户接口",
    "msg": "操作成功"
}
{
    "code": 400,
    "msg": "权限不足"
}
```

### 管理员权限测试接口
#### 接口路径
`/adminTest`
#### 说明
仅用户权限可访问该接口。
#### 请求参数
```
(Headers)
Token: 9f29df5d505b4b7c89e3ed240e81097a
```
#### 返回结果
```json
{
    "code": 0,
    "data": "成功使用管理员接口",
    "msg": "操作成功"
}
{
    "code": 400,
    "msg": "权限不足"
}
```

## See Also
姊妹项目 [tpez - 基于ThinkPHP6.0再封装的框架](https://github.com/LeslieLeung/TPEZ) 

## 致谢
感谢[Quanta（量子）信息技术服务中心](https://quantacenter.com)和塔里师兄师姐在这几年成长中给我提供的机会和帮助！感谢塔里一起开发的小伙伴！感谢这几年遇到的不胜枚举但又给我莫大帮助的各位！