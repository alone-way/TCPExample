#### 题目

实现一个客户端和服务端(8001端口)。客户端发送字符串hello.html, 服务端读取hello-server.html的内容并返回。hello-server.html的内容是以下字符串。

```html
<head><body>hello world</body></html>
```

客户端接受到反馈字符串后，保存为文件hello.html。客户端发送字符串HelloWorld.action, 服务端动态调用执行HelloWorld.class。HelloWorld.class将输出以下字符串。

```html
<head><body>hello world 222</body></html>
```

客户端接受到反馈字符串后，保存为文件hello2.html。

服务端采用Executors线程池实现。服务器端的文件关联配置，如hello.html和hello-server.xml, HelloWorld.action和HelloWorld.class的对应关系，请从以下的server-mapping.xml读取。

````````xml
<file-mapping>
	<mapping>
		<client>hello.html</client>
		<server>hello-server.html</server>
	</mapping>
	<mapping>
		<client>HelloWorld.action</client>
		<server>HelloWorld.class</server>
	</mapping>
</file-mapping>	
````````

另外，服务端需要以Thread的方式，记录客户端的访问到数据库表t_client_access(id, ip_address, access_time, parameters)中。例如，数据(1, 192.168.1.100, '2019-03-18 12:00:00', 'hello.html') 。要求采用Mybatis访问。