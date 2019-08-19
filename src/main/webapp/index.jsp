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
    <title>后台开发者界面</title>
</head>
<style>
    input[type=text], select {
        width: 100%;
        padding: 12px 20px;
        margin: 8px 0;
        display: inline-block;
        border: 1px solid #ccc;
        border-radius: 4px;
        box-sizing: border-box;
    }

    input[type=submit] {
        width: 100%;
        background-color: #4CAF50;
        color: white;
        padding: 14px 20px;
        margin: 8px 0;
        border: none;
        border-radius: 4px;
        cursor: pointer;
    }

    input[type=submit]:hover {
        background-color: #45a049;
    }

    div {
        border-radius: 5px;
        background-color: #f2f2f2;
        padding: 20px;
        position: relative;
        top: 22%;
    }
</style>
<body>

<h3 style="position: relative;left: 39%;top: 17%;font-size: 45px;">欢迎使用后台开发者界面</h3>

<div>
    <form action="/test/changeUrl" method="post">
        <label for="fname">域</label>
        <input type="text" id="fname" name="domain" placeholder="Your domain..">

        <label for="lname">端口</label>
        <input type="text" id="lname" name="port" placeholder="Your port..">

        <input type="submit" value="修改">
    </form>
</div>

</body>
</html>
