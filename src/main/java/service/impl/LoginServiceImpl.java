package service.impl;

import dao.impl.LoginImpl;
import service.LoginService;

public class LoginServiceImpl implements LoginService {
    //登录
    @Override
    public boolean loginSuccess(String userNumber, String password) {
        return new LoginImpl().isSuccess(userNumber, password);
    }

    //注册
    @Override
    public boolean registeredSuccess(String userNumber, String password, String userName) {
        return new LoginImpl().registeredSuccess(userNumber, password, userName);
    }
}
