<%--
  Created by IntelliJ IDEA.
  User: Misterchaos
  Date: 2019-08-10
  Time: 下午 07:21
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
    <h2>欢迎访问后台页面</h2>
    <form action="/test/changeUrl" method="post">
        <p>域名: <input type="text" name="domain" /></p>
        <p>端口: <input type="text" name="port" /></p>
        <input type="submit" value="更改" />
    </form>
</body>
</html>
