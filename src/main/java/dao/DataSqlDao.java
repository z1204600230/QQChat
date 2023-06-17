package dao;

import entity.Message;
import entity.User;

import java.sql.Timestamp;

public interface DataSqlDao {
    String findUserSql(User clazz, String userNumber, String password);
    // 注册用户
    String addUserSql(User clazz, String userNumber, String password, String userName);
    //获得所有用户
    String findAllUser();
    //好友信息
    String findFriends(User clazz, int mid);
    //发送好友申请
    String sendApply(int mid, int fid);
    //获得好友申请
    String getApply(User user, int mid);
    //拒绝好友申请
    String refuseApply(int mid, int fid);
    //接受请求
    String setAccept(int mid, int fid);
    String addFriend(int mid, int fid);
    //存储聊天记录
    String setMessage(Message clazz, int fromId, int toId, String message, Timestamp timestamp);
    //获取聊天记录
    String getMessage(Message clazz, int fromId, int toId);
}
