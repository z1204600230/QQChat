package service;

import entity.Message;
import entity.User;

import java.util.List;
import java.util.Map;

public interface HandleService {
    //用户信息
    List<User> findUser();
    //好友信息
    List<User> findFriends(int mid);
    //获得所有用户
    List<User> findAllUsers();
    //发送申请
    boolean sendApply(int mid, int fid);
    //获得好友申请
    List<User> getApply(int mid);
    //拒绝好友申请
    boolean refuseApply(int mid, int fid);
    //接受好友申请
    boolean acceptApply(int mid, int fid);
    //存储聊条记录
    boolean setMessage(int fromId, int toId, String message);
    //获得聊天记录
    List<Message> getMessages(int fromId, int toId);
}
