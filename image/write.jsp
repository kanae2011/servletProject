<%@page import="com.webjjang.main.controller.Beans"%>
<%@page import="com.webjjang.util.filter.AuthorityFilter"%>
<%@page import="com.webjjang.main.controller.ExeService"%>
<%@page import="com.webjjang.image.vo.ImageVO"%>
<%@page import="com.webjjang.member.vo.LoginVO"%>
<%@page import="com.oreilly.servlet.multipart.DefaultFileRenamePolicy"%>
<%@page import="com.oreilly.servlet.MultipartRequest"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
//자바
//저장위치
String path ="/upload/image/";
String realPath = request.getServletContext().getRealPath(path);
System.out.println("/image/write.jsp[realPath]"+ realPath);

//fileSize : 용량 제한 -10Mbyte
int fileSize = 10 * 1024 * 1024; // 1000-> 1K, 1000K-> 1M, 1000M-> 1G, 1000G-> 1T

//파일 정보 처리해주는 객체 생성-> 파일 업로드가 자동으로 이루어짐
//new MultipartRequest(request,실제적인 저장위치(컴퓨터),파일용량제한,엔코딩방식,중복된 파일 이름 처리 객체)
MultipartRequest multi = new MultipartRequest(request,realPath,fileSize,"utf-8",new DefaultFileRenamePolicy());
//MultipartRequest생성 후 requset에서는 아무것도 나오지 않음 
//System.out.println("/image/write.jsp[request.title]-"+ request.getParameter("title"));
//System.out.println("/image/write.jsp[multi.title]-"+ multi.getParameter("title"));
String title = multi.getParameter("title");
String content = multi.getParameter("content");
String id = ((LoginVO)session.getAttribute("login")).getId();
//서버에 저장된 파일명
String fileName = multi.getFilesystemName("imageFile");
System.out.println("/image/write.jsp[fileName]-"+ fileName);
//VO객체를 생성하고 저장
ImageVO vo = new ImageVO();
vo.setTitle(title);
vo.setContent(content);
vo.setId(id);
vo.setFileName(path + fileName);
//DB처리
ExeService.execute(Beans.get(AuthorityFilter.url),vo);
//자동이동
response.sendRedirect("list.jsp?page=1&perPageNum=" + multi.getParameter("perPageNum"));

%>
