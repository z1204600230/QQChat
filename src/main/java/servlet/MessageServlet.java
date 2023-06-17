package servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import entity.Message;
import service.HandleService;
import service.impl.HandleServiceImpl;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/html/message")
public class MessageServlet extends HttpServlet {
    private final HandleService HANDLE_SERVICE = new HandleServiceImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        List<Message> data;
        resp.setCharacterEncoding("utf-8");
        resp.setContentType("text/html;charset=UTF-8");
        int fromId = Integer.parseInt(req.getParameter("fromId"));
        int toId = Integer.parseInt(req.getParameter("toId"));
        data = HANDLE_SERVICE.getMessages(fromId, toId);
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

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        int fromId = Integer.parseInt(req.getParameter("fromId"));
        int toId = Integer.parseInt(req.getParameter("toId"));
        String message = req.getParameter("message");
        int state = Integer.parseInt(req.getParameter("state"));
        boolean b = HANDLE_SERVICE.setMessage(fromId, toId, message);
        resp.getWriter().write(String.valueOf(b));
    }

}
