package dao;

public interface LoginDao {

    boolean isSuccess(String userNumber,String password);

    boolean registeredSuccess(String userNumber, String password, String userName);
}
