package dao.impl;

import dao.LoginDao;
import entity.User;
import util.DbUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginImpl implements LoginDao {
    private final DbReflectImpl db = new DbReflectImpl();
    private Connection connection;
    private PreparedStatement statement;
    private ResultSet resultSet;
    static String number;
    static String passwd;

    @Override
    public boolean isSuccess(String userNumber, String password) {
        number = userNumber;
        passwd = password;
        connection = DbUtil.getConnection();
        try{
            DbReflectSqlImpl sqlUtil = new DbReflectSqlImpl();
            // 获取到SQL语句
            String sql= sqlUtil.findUserSql(new User(), userNumber, password);
            // 预编译SQL语句
            statement = connection.prepareStatement(sql);
            // 执行SQL语句
            System.out.println("user:"+statement);
            resultSet = statement.executeQuery(sql);
            while (resultSet.next()){
                return true;
            }
        }catch (RuntimeException | SQLException throwables){
            throwables.printStackTrace();
            db.release();
        }
        db.release();
        return false;
    }

    @Override
    public boolean registeredSuccess(String username, String password, String userNma) {
        connection = DbUtil.getConnection();
        try{
            DbReflectSqlImpl sqlUtil = new DbReflectSqlImpl();
            // 获取到SQL语句
            String sql= sqlUtil.addUserSql(new User(), username, password, userNma);
            // 预编译SQL语句
            statement = connection.prepareStatement(sql);
            // 执行SQL语句
            System.out.println("user:"+statement);
            if (statement.executeUpdate() > 0){
                return true;
            }
        }catch (RuntimeException | SQLException throwables){
            throwables.printStackTrace();
            db.release();
        }
        db.release();
        return false;
    }
}
