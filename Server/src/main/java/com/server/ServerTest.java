package com.server;

/**
 * @author IceCube
 * @date 2020/7/22 21:25
 */
public class ServerTest {
    public static void main(String[] args) {
        Server server = new Server(8001,"server-mapping.xml");
        server.startup();
    }
}
