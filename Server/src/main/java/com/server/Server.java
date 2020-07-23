package com.server;

import com.model.ClientAccess;
import com.service.ClientAccessService;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @author IceCube
 * @date 2020/7/22 21:25
 */
public class Server {
    private ServerSocket serverSocket;
    private String xmlPath;
    private ThreadPoolExecutor executor;

    public Server(int port, String xmlPath) {
        try {
            this.xmlPath = xmlPath;
            serverSocket = new ServerSocket(port);
            executor = (ThreadPoolExecutor) Executors.newCachedThreadPool();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void startup() {

        try {
            while (true) {
                System.out.println("服务器开始监听...");
                Socket socket = serverSocket.accept();
                System.out.println("建立一个新连接! 来自 " + socket.getInetAddress().getHostAddress() + ":" + socket.getPort());
                SocketHandler handler = new SocketHandler(socket, xmlPath);
                executor.execute(handler);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
