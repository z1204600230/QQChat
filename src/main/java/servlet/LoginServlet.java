package servlet;

import service.LoginService;
import service.impl.LoginServiceImpl;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    private final LoginService LOGIN_SER = new LoginServiceImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String userNumber = req.getParameter("username");
        String password = req.getParameter("password");
        System.out.println("账号:" + userNumber);
        System.out.println("密码:" + password);
        boolean b = LOGIN_SER.loginSuccess(userNumber, password);
        resp.getWriter().write(String.valueOf(b));
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String userNumber = req.getParameter("userNumber");
        String password = req.getParameter("password");
        String userName = req.getParameter("username");
        System.out.println("账号" + userNumber);
        System.out.println("密码" + password);
        System.out.println("昵称" + userName);
        boolean b = LOGIN_SER.registeredSuccess(userNumber, password, userName);
        resp.getWriter().write(String.valueOf(b));
    }
}
