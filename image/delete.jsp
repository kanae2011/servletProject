<%@page import="com.webjjang.util.file.FileUtil"%>
<%@page import="com.webjjang.main.controller.Beans"%>
<%@page import="com.webjjang.util.filter.AuthorityFilter"%>
<%@page import="com.webjjang.main.controller.ExeService"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%
//넘어오는 데이터 받기
String strNo = request.getParameter("no");
String strPerPageNum = request.getParameter("perPageNum");
String deleteFile = request.getParameter("deleteFile");
Long no = Long.parseLong(strNo);

System.out.println("/image/delete.jsp [no] " + strNo);
System.out.println("/image/delete.jsp [perPageNum] " + strPerPageNum);
System.out.println("/image/delete.jsp [deleteFile] " + deleteFile);
//DB에서 데이터 삭제
ExeService.execute(Beans.get(AuthorityFilter.url),no );
//이미지 파일 삭제
FileUtil.remove(request.getServletContext().getRealPath("/") + deleteFile);
//목록 - 페이지 : 1 한 페이지당 표시갯수 : : 넘겨 받은 데이터 
response.sendRedirect("list.jsp?page=1&perPageNum=" + strPerPageNum);

%>
