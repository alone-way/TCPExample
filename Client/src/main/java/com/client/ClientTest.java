package com.client;

/**
 * @author IceCube
 * @date 2020/7/22 20:31
 */
public class ClientTest {
    public static final String HOST = "127.0.0.1";
    public static final int PORT = 8001;
    public static final String FIRST_MSG = "hello.html";
    public static final String SECOND_MSG = "HelloWorld.action";

    public static void main(String[] args) {
        Client client = new Client(HOST, PORT);

        client.send(FIRST_MSG);
        String reply1 = client.receive();
        client.save("hello.html");
        System.out.println("reply1 = " + reply1);

        client.send(SECOND_MSG);
        String reply2 = client.receive();
        client.save("hello2.html");
        System.out.println("reply2 = " + reply2);

        client.send("end");
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        client.close();
        System.out.println("关闭socket");

    }
}
