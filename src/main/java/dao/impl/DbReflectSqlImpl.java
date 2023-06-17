package dao.impl;

import annotation.Column;
import annotation.Table;
import dao.DataSqlDao;
import entity.Message;
import entity.User;

import java.lang.reflect.Field;
import java.sql.Timestamp;

public class DbReflectSqlImpl implements DataSqlDao {
    @Override
    public String findUserSql(User clazz, String userNumber, String password) {
        StringBuilder sql = new StringBuilder();
        sql.append("select *");
        sql.append(" from ");
        Table table = clazz.getClass().getAnnotation(Table.class);

        sql.append(table.tableName());
        sql.append(" where user_number= '" + userNumber).append("' and ").append(" password= '" + password + "'");
        System.out.println("SQL:  " + sql);
        return sql.toString();
    }

    @Override
    public String addUserSql(User clazz, String userNumber, String password, String userName) {
        StringBuilder sql = new StringBuilder();
        sql.append("insert into ");

        // 获取类中注解的表名
        Table tableAnnotation = clazz.getClass().getAnnotation(Table.class);
        String tableName = tableAnnotation.tableName();
        sql.append(tableName).append("(user_number,password,user_name,state)").append(" values (");
        for (Field field : clazz.getClass().getDeclaredFields()){
            field.setAccessible(true);
            Column column = field.getAnnotation((Column.class));
            if (column.columnName().equals("user_number")){
                sql.append("'" + userNumber + "',");
            }
            else if (column.columnName().equals("password")){
                sql.append("'" + password + "',");
            }
            else if (column.columnName().equals("user_name")) {
                sql.append("'" + userName + "',");
            }
            else if (column.columnName().equals("state")) {
                sql.append(0);
            }
        }
        sql.append(")");

        System.out.println("addUserSql:" + sql);
        return sql.toString();
    }

    @Override
    public String findAllUser() {
        StringBuilder sql = new StringBuilder();
        sql.append("select * from user");
        return sql.toString();
    }

    @Override
    public String findFriends(User clazz, int mid) {
        StringBuilder sql = new StringBuilder();
        sql.append("select * from ");
        Table table = clazz.getClass().getAnnotation(Table.class);
        sql.append(table.tableName());
        sql.append(" where id in (select f_fid from friend where f_mid=" + mid + " and f_send=1 and f_accept=1)");
        return sql.toString();
    }

    @Override
    public String sendApply(int mid, int fid) {
        StringBuilder sql = new StringBuilder();
        sql.append("insert into friend values(");
        sql.append(mid + "," + fid + ",1,0)");
        return sql.toString();
    }

    @Override
    public String getApply(User clazz, int mid) {
        StringBuilder sql = new StringBuilder();
        sql.append("select * from ");
        Table table = clazz.getClass().getAnnotation(Table.class);
        sql.append(table.tableName());
        //mid表示当前登录用户的id
        sql.append(" where id in (select f_mid from friend where f_fid=" + mid + " and f_send=1 and f_accept=0)");
        return sql.toString();
    }

    @Override
    public String refuseApply(int mid, int fid) {
        //mid为当前登录用户id，fid为发送申请的id
        StringBuilder sql = new StringBuilder();
        sql.append("delete from friend where f_mid=" + fid + " and f_fid=" + mid + " and f_send=1 and f_accept=0");
        return sql.toString();
    }

    @Override
    public String setAccept(int mid, int fid) {
        //mid为当前登录用户id，fid为发送申请的id
        StringBuilder sql = new StringBuilder();
        sql.append("update friend set f_accept=1 where f_mid=" + fid + " and f_fid=" + mid);
        return sql.toString();
    }

    @Override
    public String addFriend(int mid, int fid) {
        //mid为当前登录用户id，fid为发送申请的id
        StringBuilder sql = new StringBuilder();
        sql.append("insert into friend values(" + mid + "," + fid + ",1,1)");
        return sql.toString();
    }

    @Override
    public String setMessage(Message clazz, int fromId, int toId, String message, Timestamp timestamp) {
        StringBuilder sql = new StringBuilder();
        sql.append("insert into ");
        // 获取类中注解的表名
        Table tableAnnotation = clazz.getClass().getAnnotation(Table.class);
        String tableName = tableAnnotation.tableName();
        sql.append(tableName).append("(m_from_id,m_to_id,m_message,m_time) values(");
        sql.append(fromId + "," + toId + ",'" + message + "','" + timestamp + "')");
        return sql.toString();
    }

    @Override
    public String getMessage(Message clazz, int fromId, int toId) {
        StringBuilder sql = new StringBuilder();
        sql.append("select * from ");
        // 获取类中注解的表名
        Table tableAnnotation = clazz.getClass().getAnnotation(Table.class);
        String tableName = tableAnnotation.tableName();
        sql.append(tableName).append(" where (m_from_id=" + fromId + " and m_to_id=" + toId + ") " +
                "or (m_from_id=" + toId + " and m_to_id=" + fromId + ") order by m_time");
        return sql.toString();
    }


}
