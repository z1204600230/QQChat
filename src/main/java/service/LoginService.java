package service;

public interface LoginService {

    boolean loginSuccess(String userNumber,String password);
    boolean registeredSuccess(String userNumber, String password, String userName);
}
