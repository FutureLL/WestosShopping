<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <title>Student</title>
</head>
<body>
    学生信息：<br>
    学号：${student.id}<br>
    姓名：${student.name}<br>
    年龄：${student.age}<br>
    家庭住址：${student.address}<br><br>

    <#-- <#list 集合名 as 元素> -->
    <#-- 学生列表 -->
    <table border="1">
        <tr>
            <th>序号</th>
            <th>学号</th>
            <th>姓名</th>
            <th>年龄</th>
            <th>家庭住址</th>
        </tr>
        <#list stuList as stu>
        <#if stu_index % 2 ==0>
            <tr bgcolor="#b0e0e6">
        <#else>
            <tr bgcolor="#adff2f">
        </#if>
                <td>${stu_index}</td>
                <td>${stu.id}</td>
                <td>${stu.name}</td>
                <td>${stu.age}</td>
                <td>${stu.address}</td>
            </tr>
        </#list>

    </table>
    <br>
    当前日期：${date?date}<br>
    当前时间：${date?time}<br>
    当前日期和时间：${date?datetime}<br>
    自定义日期和时间：${date?string("yyyy-MM-dd HH:mm:ss")}<br><br>

    <#--后边也可以什么也没有输出空串，例如：${val !}-->
    null的值处理：${val ! "val的值为null"}<br>
    null的值处理：
        <#if val ??>
            val不为空时的处理<br>
        <#else>
            val为空时的处理<br>
        </#if>

    引入模板测试；<br>
    <#include "hello.ftl">

</body>
</html>