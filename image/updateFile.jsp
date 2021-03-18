<%@page import="com.webjjang.util.file.FileUtil"%>
<%@page import="com.webjjang.main.controller.Beans"%>
<%@page import="com.webjjang.util.filter.AuthorityFilter"%>
<%@page import="com.webjjang.main.controller.ExeService"%>
<%@page import="com.webjjang.image.vo.ImageVO"%>
<%@page import="com.oreilly.servlet.multipart.DefaultFileRenamePolicy"%>
<%@page import="com.oreilly.servlet.MultipartRequest"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" errorPage="/error/error_page.jsp"%>
<%
//올라갈 파일 서버의 상대 위치
String path = "/upload/image/";
String realPath = request.getServletContext().getRealPath(path);
int fileSize = 10 * 1024 * 1024;
//파일 올리는 라이브러리 객체 생성 -> 자동으로 파일이 올라감 
MultipartRequest multi = new MultipartRequest(request,realPath,fileSize,"utf-8",new DefaultFileRenamePolicy());

String strNo = multi.getParameter("no");
System.out.println(strNo);
String deleteFile = multi.getParameter("deleteFile");
String fileName = multi.getFilesystemName("imageFile");

//page에 대한 정보를 문자열로 받아서 되돌아가는 url뒤에 정보로 붙여서 이동시킴
String strPage = multi.getParameter("page");
String strPerPageNum = multi.getParameter("perPageNum");

//넘겨 받은 정보 확인
System.out.println("updateFile.jsp[no] : " + strNo);
System.out.println("updateFile.jsp[deleteFile] : " + deleteFile);
System.out.println("updateFile.jsp[fileName] : " + fileName);

//파일 정보를 수정 - 번호, 파일명 
ImageVO vo= new ImageVO();
vo.setNo(Long.parseLong(strNo));
vo.setFileName(path + fileName);

//DB 파일정보 수정
int result = (Integer)ExeService.execute(Beans.get(AuthorityFilter.url), vo);

//이전 파일 삭제
String deletePath = request.getServletContext().getRealPath("/");
if(FileUtil.exist(deletePath + deleteFile))
	System.out.println(deleteFile + "파일이 존재합니다.");
//실제로 지우는 처리
FileUtil.remove(deletePath + deleteFile);

//보기로 이동
response.sendRedirect("view.jsp?no=" + strNo + "&page=" + strPage + "&perPageNum" + strPerPageNum);

%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>

</body>
</html>