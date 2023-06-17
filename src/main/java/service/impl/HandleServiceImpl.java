package service.impl;

import entity.Message;
import entity.User;
import service.HandleService;
import dao.impl.DbReflectImpl;

import java.util.ArrayList;
import java.util.List;

public class HandleServiceImpl implements HandleService {
    @Override
    public List<User> findUser() {
        return new DbReflectImpl().findUser(new User());
    }

    @Override
    public List<User> findFriends(int mid) {
        return new DbReflectImpl().findFriends(new User(), mid);
    }

    @Override
    public List<User> findAllUsers() {
        return new DbReflectImpl().findAllUsers(new User());
    }

    @Override
    public boolean sendApply(int mid, int fid) {
        return new DbReflectImpl().sendApply(mid, fid);
    }

    @Override
    public List<User> getApply(int mid) {
        return new DbReflectImpl().getApply(new User(), mid);
    }

    @Override
    public boolean refuseApply(int mid, int fid) {
        return new DbReflectImpl().refuseApply(mid, fid);
    }

    @Override
    public boolean acceptApply(int mid, int fid) {
        return new DbReflectImpl().acceptApply(mid, fid);
    }

    @Override
    public boolean setMessage(int fromId, int toId, String message) {
        return new DbReflectImpl().setMessage(new Message(), fromId, toId, message);
    }

    @Override
    public List<Message> getMessages(int fromId, int toId) {
        return new DbReflectImpl().getMessage(new Message(), fromId, toId);
    }

    public List<User> getAll(){
        List<User> list1 = findUser();
        int id = list1.get(0).getId();
        List<User> list = new ArrayList<>(list1);
        List<User> list2 = findFriends(id);
        list.addAll(list2);
        return list;
    }
}
