package servlet;

import entity.Message;
import service.HandleService;
import service.impl.HandleServiceImpl;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@ServerEndpoint("/websocket/{username}")
public class WebSocket {

    private static int onlineCount = 0;
    private static Map<String, WebSocket> clients = new ConcurrentHashMap<String, WebSocket>();
    private Session session;
    private String username;
    private int fromId;
    private int toId;

    @OnOpen
    public void onOpen(@PathParam("username") String username, Session session) throws IOException {

        this.username = username;
        this.session = session;

        addOnlineCount();
        clients.put(username, this);
        System.out.println("已连接");
    }

    @OnClose
    public void onClose() throws IOException {
        clients.remove(username);
        subOnlineCount();
    }

    @OnMessage
    public void onMessage(String message) throws IOException {
        System.out.println(message);
        if (message.startsWith("文件")){
            String[] str = message.split(" ");
            fromId = Integer.parseInt(str[1]);
            toId = Integer.parseInt(str[2]);
            dealImageToOnline(str[3]);
        }
        else {
            String[] str = message.split(" ");
            System.out.println(message);
            sendMessageTo(str[0], str[1]);
        }
    }

    boolean open_file = false;
    FileOutputStream outputStream = null;
    ByteArrayOutputStream byteArrayOutputStream = null;
    String fileName = null;

    private void open(String name) {
        try {
            fileName = name + ".jpg";
            outputStream = new FileOutputStream("C:\\Users\\张杰\\IdeaProjects\\高级Java\\期末作业\\DesignDemo\\web\\img\\"+fileName);
            byteArrayOutputStream = new ByteArrayOutputStream();
            open_file = true;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
    byte[] bc = null;
    @OnMessage
    public void onMessage(byte[] bytes, boolean flag) throws Exception {
        if (!flag) {
            if (!open_file){
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
                String time = simpleDateFormat.format(System.currentTimeMillis());
                open(time);
            }
            outputStream.write(bytes, 0, bytes.length);
            byteArrayOutputStream.write(bytes,0,bytes.length);
        } else {
            outputStream.write(bytes, 0, bytes.length);
            byteArrayOutputStream.write(bytes,0,bytes.length);
            bc = byteArrayOutputStream.toByteArray();
            byteArrayOutputStream.close();
            outputStream.close();
            open_file = false;
        }
    }

    private void dealImageToOnline(String to) throws IOException {
        WebSocket webSocket = null;
        for (WebSocket item : clients.values()) {
            if (item.username.equals(to) )
                webSocket = item;
        }
        if (webSocket != null){
            HandleService handleService = new HandleServiceImpl();
            handleService.setMessage(fromId, toId, fileName);
            fileName = null;
            webSocket.session.getBasicRemote().sendBinary(ByteBuffer.wrap(bc));
            webSocket.session.getBasicRemote().sendText("$ServiceImageFrom:" + to);
        }
    }

    @OnError
    public void onError(Session session, Throwable error) {
        error.printStackTrace();
    }

    public void sendMessageTo(String to, String message) throws IOException {
        for (WebSocket item : clients.values()) {
            if (item.username.equals(to) )
                item.session.getAsyncRemote().sendText(to + " " + message);
        }
    }

    public void sendMessageAll(String message) throws IOException {
        for (WebSocket item : clients.values()) {
            item.session.getAsyncRemote().sendText(message);
        }
    }



    public static synchronized int getOnlineCount() {
        return onlineCount;
    }

    public static synchronized void addOnlineCount() {
        WebSocket.onlineCount++;
    }

    public static synchronized void subOnlineCount() {
        WebSocket.onlineCount--;
    }

    public static synchronized Map<String, WebSocket> getClients() {
        return clients;
    }
}

