<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Person Test</title>
    </head>
    <body>
        <h1>Person Form</h1>
        <form:form modelAttribute="person" method="get" action="/person/form">
            <form:input path="name"/>
            <form:errors path="name"/>
            <br/>
            <form:input path="age"/>
            <form:errors path="age"/>
            <br/>
            <input type="submit" value="submit">
        </form:form>
    </body>
</html>