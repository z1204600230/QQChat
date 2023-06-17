package servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import entity.User;
import service.HandleService;
import service.impl.HandleServiceImpl;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/html/handle")
public class HandleServlet extends HttpServlet {
    private final HandleService HANDLE_SERVICE = new HandleServiceImpl();
    HandleServiceImpl handleServiceImpl = new HandleServiceImpl();

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        List<User> data;
        resp.setCharacterEncoding("utf-8");
        resp.setContentType("text/html;charset=UTF-8");
        data = handleServiceImpl.getAll();
        System.out.println("data:" + data);
        // ObjectMapper提供一些功能将转换成Java对象匹配JSON结构
        ObjectMapper objectMapper = new ObjectMapper();
        // writeValueAsString()会把一个对象生成JSON，并将生成的JSON作为String
        String s = objectMapper.writeValueAsString(data);
        System.out.println(s);
        // getWriter 返回一个PrintWriter对象
        resp.getWriter().write(s);
        System.out.println("完成");
    }

    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        int mid = Integer.parseInt(req.getParameter("mid"));
        int fid = Integer.parseInt(req.getParameter("fid"));
        System.out.println("mid:" + mid);
        System.out.println("fid:" + fid);
        boolean b = HANDLE_SERVICE.sendApply(mid, fid);
        resp.getWriter().write(String.valueOf(b));
    }
}
