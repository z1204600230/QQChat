package dao.impl;

import annotation.Column;
import entity.Message;
import entity.User;
import util.DbUtil;

import java.lang.reflect.Field;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DbReflectImpl {
    Connection connection = null;
    PreparedStatement statement = null;
    ResultSet resultSet = null;

    public static void main(String[] args) throws NullPointerException{
        DbReflectImpl dbReflect = new DbReflectImpl();
        dbReflect.setMessage(new Message(), 5, 1, "123");
        System.out.println(dbReflect.getMessage(new Message(), 5, 1));
    }

    public List<User> findUser(User clazz){
        List<User> result = new ArrayList<>();
        connection = DbUtil.getConnection();
        try{
            DbReflectSqlImpl sqlUtil = new DbReflectSqlImpl();
            // 获取到SQL语句
            String sql= sqlUtil.findUserSql(new User(), LoginImpl.number, LoginImpl.passwd);
//            String sql= sqlUtil.findUserSql(new User(), "123451", "123451");
            // 预编译SQL语句
            statement = connection.prepareStatement(sql);
            // 执行SQL语句
            System.out.println("user:"+statement);
            resultSet = statement.executeQuery(sql);
            while (resultSet.next()){
                // newInstance 通过反射创建新的类示例
                User obj = clazz.getClass().newInstance();
                // getMetaData 得到结果集的结构信息，比如字段数、字段名等。
                // getColumnCount 获取列数
                for (int i = 1; i <= resultSet.getMetaData().getColumnCount(); i++) {
                    String columnName = resultSet.getMetaData().getColumnName(i);
                    Field field = null;
                    Field[] declaredFields = obj.getClass().getDeclaredFields();
                    for (Field declaredField : declaredFields) {
                        String s = declaredField.getAnnotation(Column.class).columnName();
                        if (s.equals(columnName)) {
                            field = declaredField;
                            break;
                        }
                    }
                    field.setAccessible(true);
                    field.set(obj, resultSet.getObject(columnName));
                }
                result.add(obj);
            }
        } catch (SQLException | InstantiationException | IllegalAccessException e) {
            throw new RuntimeException(e);
        } finally {
            release();
        }
        return result;
    }

    //获得所有用户
    public List<User> findAllUsers(User clazz){
        List<User> result = new ArrayList<>();
        connection = DbUtil.getConnection();
        try {
            DbReflectSqlImpl sqlUtil = new DbReflectSqlImpl();
            String sql = sqlUtil.findAllUser();
            statement = connection.prepareStatement(sql);
            System.out.println("user:" + statement);
            resultSet = statement.executeQuery(sql);
            while (resultSet.next()){
                // newInstance 通过反射创建新的类示例
                User obj = clazz.getClass().newInstance();
                // getMetaData 得到结果集的结构信息，比如字段数、字段名等。
                // getColumnCount 获取列数
                for (int i = 1; i <= resultSet.getMetaData().getColumnCount(); i++) {
                    String columnName = resultSet.getMetaData().getColumnName(i);
                    Field field = null;
                    Field[] declaredFields = obj.getClass().getDeclaredFields();
                    for (Field declaredField : declaredFields) {
                        String s = declaredField.getAnnotation(Column.class).columnName();
                        if (s.equals(columnName)) {
                            field = declaredField;
                            break;
                        }
                    }
                    field.setAccessible(true);
                    field.set(obj, resultSet.getObject(columnName));
                }
                result.add(obj);
            }
        } catch (SQLException | InstantiationException | IllegalAccessException e) {
            throw new RuntimeException(e);
        } finally {
            release();
        }
        return result;
    }

    //好友信息
    public List<User> findFriends(User clazz, int id){
        List<User> result = new ArrayList<>();
        connection = DbUtil.getConnection();
        try {
            DbReflectSqlImpl sqlUtil = new DbReflectSqlImpl();
            String sql = sqlUtil.findFriends(clazz, id);
            statement = connection.prepareStatement(sql);
            System.out.println("friend:" + statement);
            resultSet = statement.executeQuery(sql);
            while (resultSet.next()){
                // newInstance 通过反射创建新的类示例
                User obj = clazz.getClass().newInstance();
                // getMetaData 得到结果集的结构信息，比如字段数、字段名等。
                // getColumnCount 获取列数
                for (int i = 1; i <= resultSet.getMetaData().getColumnCount(); i++) {
                    String columnName = resultSet.getMetaData().getColumnName(i);
                    Field field = null;
                    Field[] declaredFields = obj.getClass().getDeclaredFields();
                    for (Field declaredField : declaredFields) {
                        String s = declaredField.getAnnotation(Column.class).columnName();
                        if (s.equals(columnName)) {
                            field = declaredField;
                            break;
                        }
                    }
                    field.setAccessible(true);
                    field.set(obj, resultSet.getObject(columnName));
                }
                result.add(obj);
            }
        }catch (SQLException | InstantiationException | IllegalAccessException e){
            throw new RuntimeException(e);
        } finally {
            release();
        }
        return result;
    }

    //发送好友申请
    public boolean sendApply(int mid, int fid){
        connection = DbUtil.getConnection();
        try {
            DbReflectSqlImpl sqlUtil = new DbReflectSqlImpl();
            String sql = sqlUtil.sendApply(mid, fid);
            statement = connection.prepareStatement(sql);
            System.out.println("send:" + statement);
            if (statement.executeUpdate() > 0){
                return true;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            release();
        }
        return false;
    }

    //获得好友申请
    public List<User> getApply(User clazz, int mid){
        List<User> result = new ArrayList<>();
        connection = DbUtil.getConnection();
        try {
            DbReflectSqlImpl sqlUtil = new DbReflectSqlImpl();
            String sql = sqlUtil.getApply(clazz, mid);
            statement = connection.prepareStatement(sql);
            System.out.println("get:" + statement);
            resultSet = statement.executeQuery(sql);
            while (resultSet.next()){
                // newInstance 通过反射创建新的类示例
                User obj = clazz.getClass().newInstance();
                // getMetaData 得到结果集的结构信息，比如字段数、字段名等。
                // getColumnCount 获取列数
                for (int i = 1; i <= resultSet.getMetaData().getColumnCount(); i++) {
                    String columnName = resultSet.getMetaData().getColumnName(i);
                    Field field = null;
                    Field[] declaredFields = obj.getClass().getDeclaredFields();
                    for (Field declaredField : declaredFields) {
                        String s = declaredField.getAnnotation(Column.class).columnName();
                        if (s.equals(columnName)) {
                            field = declaredField;
                            break;
                        }
                    }
                    field.setAccessible(true);
                    field.set(obj, resultSet.getObject(columnName));
                }
                result.add(obj);
            }
        } catch (SQLException | InstantiationException | IllegalAccessException e) {
            throw new RuntimeException(e);
        } finally {
            release();
        }
        return result;
    }

    //拒绝好友申请
    //mid为当前登录用户id，fid为发送申请的id
    public boolean refuseApply(int mid, int fid){
        connection = DbUtil.getConnection();
        try{
            DbReflectSqlImpl sqlUtil = new DbReflectSqlImpl();
            String sql = sqlUtil.refuseApply(mid, fid);
            statement = connection.prepareStatement(sql);
            System.out.println("refuse:" + statement);
            if (statement.executeUpdate() > 0){
                return true;
            }
        }catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            release();
        }
        return false;
    }

    //事务写法
    //接受好友申请
//    public void acceptApply(int mid, int fid){
//        connection = DbUtil.getConnection();
//        try {
//            connection.setAutoCommit(false);
//            DbReflectSqlImpl sqlUtil = new DbReflectSqlImpl();
//            String sql1 = sqlUtil.setAccept(mid, fid);
//            statement = connection.prepareStatement(sql1);
//            System.out.println("accept:" + statement);
//            statement.executeUpdate();
//            String sql2 = sqlUtil.addFriend(mid, fid);
//            statement = connection.prepareStatement(sql2);
//            System.out.println("accept:" + statement);
//            connection.commit();
//            connection.setAutoCommit(true);
//            System.out.println("成功");
//        } catch (SQLException e) {
//            try {
//                connection.rollback();
//            } catch (SQLException ex) {
//                throw new RuntimeException(ex);
//            }
//            throw new RuntimeException(e);
//        } finally {
//            release();
//        }
//    }

    //接受好友申请
    public boolean acceptApply(int mid, int fid){
        connection = DbUtil.getConnection();
        try {
            DbReflectSqlImpl sqlUtil = new DbReflectSqlImpl();
            String sql1 = sqlUtil.setAccept(mid, fid);
            statement = connection.prepareStatement(sql1);
            System.out.println("set:" + statement);
            if (statement.executeUpdate() > 0){
                String sql2 = sqlUtil.addFriend(mid, fid);
                statement = connection.prepareStatement(sql2);
                System.out.println("add:" + statement);
                if (statement.executeUpdate() > 0){
                    return true;
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            release();
        }
        return false;
    }

    //存储信息
    public boolean setMessage(Message clazz, int fromId, int toId, String message){
        connection = DbUtil.getConnection();
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        try {
            DbReflectSqlImpl sqlUtil = new DbReflectSqlImpl();
            String sql = sqlUtil.setMessage(clazz, fromId, toId, message, timestamp);
            statement = connection.prepareStatement(sql);
            System.out.println("message:" + statement);
            if (statement.executeUpdate() > 0){
                return true;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            release();
        }
        return false;
    }

    //获得聊天记录
    public List<Message> getMessage(Message clazz, int fromId, int toId){
        List<Message> result = new ArrayList<>();
        connection = DbUtil.getConnection();
        try {
            DbReflectSqlImpl sqlUtil = new DbReflectSqlImpl();
            String sql = sqlUtil.getMessage(clazz, fromId, toId);
            statement = connection.prepareStatement(sql);
            System.out.println("get:" + statement);
            resultSet = statement.executeQuery(sql);
            while (resultSet.next()){
                // newInstance 通过反射创建新的类示例
                Message obj = clazz.getClass().newInstance();
                // getMetaData 得到结果集的结构信息，比如字段数、字段名等。
                // getColumnCount 获取列数
                for (int i = 1; i <= resultSet.getMetaData().getColumnCount(); i++) {
                    String columnName = resultSet.getMetaData().getColumnName(i);
                    Field field = null;
                    Field[] declaredFields = obj.getClass().getDeclaredFields();
                    for (Field declaredField : declaredFields) {
                        String s = declaredField.getAnnotation(Column.class).columnName();
                        if (s.equals(columnName)) {
                            field = declaredField;
                            break;
                        }
                    }
                    field.setAccessible(true);
                    field.set(obj, resultSet.getObject(columnName));
                }
                result.add(obj);
            }
        } catch (SQLException | InstantiationException | IllegalAccessException e) {
            throw new RuntimeException(e);
        } finally {
            release();
        }
        return result;
    }

    //关闭连接
    public void release() {

        try {
            if (connection != null) {
                connection.close();
            }


            if (statement != null) {
                statement.close();
            }

            if (resultSet != null) {
                resultSet.close();
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
