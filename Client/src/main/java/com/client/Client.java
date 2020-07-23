package com.client;

import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

/**
 * @author IceCube
 * @date 2020/7/22 20:34
 */
public class Client {
    private Socket socket;
    private String lastReply; //最近一次接收的消息

    public Client(String host, int port) {
        try {
            socket = new Socket(host, port);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 发送消息
     */
    public void send(String msg) {
        try {
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream(),
                    StandardCharsets.UTF_8));
            writer.write(msg);
            writer.newLine();
            writer.write("exit");
            writer.newLine();
            writer.flush();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 接收消息
     */
    public String receive() {
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream(),
                    StandardCharsets.UTF_8));
            String line;
            StringBuilder lines = new StringBuilder();
            String exit = "exit";
            while (!exit.equals(line = reader.readLine())) {
                lines.append(line);
            }
            lastReply = lines.toString();

        } catch (IOException e) {
            e.printStackTrace();
        }

        return lastReply;
    }

    /**
     * 保存最近一次接收的消息
     */
    public void save(String path) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(path))) {
            writer.write(lastReply);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 关闭Socket
     */
    public void close() {
        try {
            if (socket != null) {
                socket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
