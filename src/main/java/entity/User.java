package entity;

import annotation.Column;
import annotation.Id;
import annotation.Table;

@Table(tableName = "user")
public class User {
    @Id(idName = "id")
    @Column(columnName = "id")
    private int id;
    @Column(columnName = "user_number")
    private String userNumber;
    @Column(columnName = "password")
    private String password;
    @Column(columnName = "user_name")
    private String userName;
    @Column(columnName = "state")
    private int state;

    public User() {
    }

    public User(int id, String userNumber, String password, String userName, int state) {
        this.id = id;
        this.userNumber = userNumber;
        this.password = password;
        this.userName = userName;
        this.state = state;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserNumber() {
        return userNumber;
    }

    public void setUserNumber(String userNumber) {
        this.userNumber = userNumber;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", userNumber='" + userNumber + '\'' +
                ", password='" + password + '\'' +
                ", userName='" + userName + '\'' +
                ", state='" + state + '\'' +
                '}';
    }
}
