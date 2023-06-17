package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbUtil {
    public static Connection getConnection() {
        // 驱动信息
        String driverName = "com.mysql.cj.jdbc.Driver";
        // 数据库连接参数
        String url = "jdbc:mysql://localhost:3306/qqchat?useUnicode=true&characterEncoding=UTF-8&useSSL=false&serverTimezone=GMT&allowPublicKeyRetrieval=true";
        // 用户名
        String userName = "root";
        // 密码
        String password = "root";

        Connection collection = null;
        System.out.println("开始加载驱动......");
        // 注册驱动
        try {
            Class.forName(driverName);
            System.out.println("驱动加载成功！\n开始连接！");
            // 建立连接
            collection = DriverManager.getConnection(url, userName, password);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return collection;
    }


    public static void main(String[] args) {

        new DbUtil().getConnection();
        System.out.println("连接成功");
    }
}


