<%@page import="com.webjjang.main.controller.Beans"%>
<%@page import="com.webjjang.main.controller.ExeService"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%

String strNo = request.getParameter("no");
long no = Long.parseLong(strNo);

//글번호 넘어감 
String url = request.getServletPath();
Integer result = (Integer)ExeService.execute(Beans.get(url),no);
response.sendRedirect("list.jsp");
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title></title>
	


</head>
<body>

</body>
</html>