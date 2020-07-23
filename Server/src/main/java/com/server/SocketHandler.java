package com.server;

import com.cmd.RuntimeUtil;
import com.model.ClientAccess;
import com.model.Result;
import com.service.ClientAccessService;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.sql.Timestamp;
import java.time.Instant;

/**
 * @author IceCube
 * @date 2020/7/22 21:25
 */
@SuppressWarnings("AlibabaSwitchStatement")
public class SocketHandler implements Runnable {
    private final Socket socket;
    private final String xmlPath;

    public SocketHandler(Socket socket, String xmlPath) {
        this.socket = socket;
        this.xmlPath = xmlPath;
    }


    /**
     * 根据收到的客户端消息msg, 返回对应的结果消息html给客户端, 期间将每一次访问记录都保存到数据库中
     */
    @Override
    public void run() {
        boolean finished = false;
        String html = "";
        while (!finished) {
            String msg = receive();
            System.out.println("msg = " + msg);

            //将客户端访问记录保存到数据库中
            new Thread(() -> {
                ClientAccess access = new ClientAccess();
                access.setIpAddress(socket.getInetAddress().getHostAddress());
                access.setAccessTime(Timestamp.from(Instant.now()));
                access.setParameters(msg);
                new ClientAccessService().saveAccess(access);
            }).start();

            if ("end".equals(msg)) {
                finished = true;
                close();
                continue;
            }

            String target = getServerByClient(msg);
            System.out.println("target = " + target);

            switch (target) {
                case "hello-server.html":
                    html = read(target);
                    send(html);
                    break;
                case "HelloWorld.class":
                    Result result = new RuntimeUtil().exec("cmd /c java com.HelloWorld");
                    if (result.getExitValue() == 0) {
                        html = result.getMessage();
                    } else {
                        html = result.getError();
                    }
                    send(html);
                    break;
                default:
            }
            System.out.println("html = " + html);
            System.out.println("=====================================");
        }
    }

    /**
     * 接收客户端消息
     */
    public String receive() {
        String reply = "";
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream(),
                    StandardCharsets.UTF_8));
            String line;
            StringBuilder lines = new StringBuilder();
            String exit = "exit";
            while (!exit.equals(line = reader.readLine())) {
                lines.append(line);
            }
            reply = lines.toString();

        } catch (IOException e) {
            e.printStackTrace();
        }

        return reply;
    }

    /**
     * 发送消息给客户端
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
     * 从文件中读取数据
     */
    public String read(String path) {
        String content = "";
        try (BufferedReader reader = new BufferedReader(new FileReader(path))) {
            String line;
            StringBuilder lines = new StringBuilder();
            while ((line = reader.readLine()) != null) {
                lines.append(line);
            }
            content = lines.toString();

        } catch (IOException e) {
            e.printStackTrace();
        }

        return content;
    }


    /**
     * 从xml文件中寻找client标签对应的server标签的文本
     */
    public String getServerByClient(String client) {
        String serverValue = "";
        boolean clientFlag = false;
        try {
            DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            Document document = builder.parse(xmlPath);
            NodeList mappingList = document.getElementsByTagName("mapping");

            for (int i = 0; i < mappingList.getLength(); i++) {
                Node mapping = mappingList.item(i);
                NodeList childList = mapping.getChildNodes();
                for (int j = 0; j < childList.getLength(); j++) {
                    Node child = childList.item(j);
                    //忽略空白节点
                    if (child.getNodeType() == Node.TEXT_NODE) {
                        continue;
                    }
                    if ("client".equals(child.getNodeName()) &&
                            child.getTextContent().equals(client)) {
                        clientFlag = true;
                    } else if (clientFlag &&
                            "server".equals(child.getNodeName())) {
                        serverValue = child.getTextContent();
                        break;
                    }
                }
                if (clientFlag) {
                    break;
                }
            }

        } catch (ParserConfigurationException | SAXException | IOException e) {
            e.printStackTrace();
        }

        return serverValue;
    }

    /**
     * 关闭Socket
     */
    public void close() {
        System.out.println("关闭socket");
        try {
            if (socket != null) {
                socket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
