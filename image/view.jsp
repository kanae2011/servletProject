<%@page import="com.webjjang.main.controller.Beans"%>
<%@page import="com.webjjang.util.filter.AuthorityFilter"%>
<%@page import="com.webjjang.main.controller.ExeService"%>
<%@page import="com.webjjang.image.vo.ImageVO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
//넘어오는 데이터 : 번호[,페이지,한 페이지당 이미지 갯수]
String strNo = request.getParameter("no"); 
Long no = Long.parseLong(strNo);
//DB에서 데이터 가져오기
ImageVO vo = (ImageVO)ExeService.execute(Beans.get(AuthorityFilter.url), no);

//프로젝트 패스 구하기
String path = request.getContextPath();
//서버객체 저장
request.setAttribute("vo", vo);
request.setAttribute("path", path);
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>이미지 보기</title>

<script type="text/javascript">
	var imageExt =["JPG","JPEG","GIF","PNG"];
$(function () {
	//허용되는 이미지 파일 형식들
	//모달창 이미지 변경 버튼 
	$("#changeBtn").click(function () {
		var fileName = $("#imageFile").val();
		
		//변경 파일란이 비어있는 경우의 처리
		if(!fileName){
			alert("변경 할 이미지를 선택하세요.");
			$("#imageFile").focus();
			return false;
		}
		//비어있지 않은 경우의 처리-지원하는 이미지 확인 처리
		//substring(시작[,끝])-부분 문자열 잘라내기 
		//lastIndexOf(찾는문자열)-뒤에서부터 찾는 문자열의 위치. 찾는 문자열이 없으면 -1이 됨 
		//toUpperCase()-> 모든 영문자를 대문자로 만들어줌 <-> toLowerCase()
		var ext = fileName.substring(fileName.lastIndexOf(".")+1).toUpperCase();
		//alert(ext);
		
		//이미지 확장자인지 확인하는 반복문(imageExt가 위에 배열로 선언이 되어져 있어야만 함)
		var checkExt = false; //지원하지 않는 확장자를 기본으로 셋팅 
		for(i = 0; i < imageExt.length; i++){
			if(ext == imageExt[i]){
				checkExt = true; //지원하는 확장자로 바꿈
				break;
			}
		}
		//지원하지 않는 이미지 파일이 선택됐을 경우의 처리 
		if(!checkExt){
			alert("지원하지 않는 이미지 파일입니다.");
			$("#imageFile").focus();
			return false;
		}
		//강제적으로  폼데이터 전송하는 설정
		$("#updateFileForm").submit();
		
		});
	
		//삭제 할 때 확인 
		$(".deleteBtn").click(function(){
			if(!confirm("삭제하시겠습니까?"))
				return false;
	});
});
</script>
</head>
<body>
<div class="container">
	<h1>이미지 보기</h1>
	<table class="table">
		<tr>
			<td colspan="2">
			<c:if test="${vo.id == login.id || login.gradeNo == 9}">
			<!-- 작성자가 로그인한 회원인 경우,관리자에게만 나타나는 메뉴 -->
				<a href="updateForm.jsp?no=${vo.no }" class="btn btn-default">내용 수정</a>
				<button type="button" class="btn btn-default" data-toggle="modal" data-target="#myModal">이미지 수정</button>
				<a href="delete.jsp?no=${vo.no }&perPageNum=${param.perPageNum}&deleteFile=${vo.fileName}" class="btn btn-default deleteBtn">삭제</a>
			</c:if>
			<a href="list.jsp?page=${param.page }&" class="btn btn-default">목록</a>
			</td>
		</tr>
		<tr>
			<th>번호</th>
			<td>${vo.no }</td>
		</tr>
		<tr>
			<th>제목</th>
			<td>${vo.title }</td>
		</tr>
		<tr>
			<th> </th>
			<td><img src="${path }${vo.fileName }" style="width:95%"></td>
		</tr>
		<tr>
			<th>내용</th>
			<td>${vo.content }</td>
		</tr>
		<tr>
			<th>작성자</th>
			<td>${vo.name }(${vo.id })</td>
		</tr>
		<tr>
			<th>작성일</th>
			<td>${vo.writeDate }</td>
		</tr>
		
			
			<tr>
			<td colspan="2">
			<a href="updateForm.jsp?no=${vo.no }" class="btn btn-default">내용 수정</a>
			<button type="button" class="btn btn-default" data-toggle="modal" data-target="#myModal">이미지 수정</button>
			<a href="delete.jsp?no=${vo.no }" class="btn btn-default" id="deleteBtn">삭제</a>
			<!-- EL객체 -param.page-> requset.getparameter("page") -->
			<a href="list.jsp?page=${param.page }&perPageNum=${param.perPageNum}" class="btn btn-default">목록</a>
			</td>
			</tr>
		
	</table>
	<!-- Modal -->
	<div class="modal fade" id="myModal" role="dialog">
    <div class="modal-dialog">
       <!-- Modal content-->
	<div class="modal-content">
        <div class="이미지 변경">
          <button type="button" class="close" data-dismiss="modal">&times;</button>
          <h4 class="modal-title">이미지 변경</h4>
        </div>
        <div class="modal-body">
          <p>변경 할 이미지 선택</p>
          <form action="updateFile.jsp" method="post" enctype="multipart/form-data" id="updateFileForm">
	          <!-- 페이지 정보를 숨겨서 넘김 -->
	          <input name="page" value="${param.page }" type="hidden">
	          <input name="perPageNum" value="${param.page }" type="hidden">
	          <div class="form-group">
		          <label for="no">번호</label>
		          <input name="no" id="no" class="form-control" value="${vo.no }" readonly="readonly">
	          </div>
	          <div class="form-group">
		          <label for="deleteFile">원본이미지</label>
		          <input name="deleteFile" id="deleteFile" class="form-control" value="${vo.fileName }" readonly="readonly">
	          </div>
	          <div class="form-group">
		          <label for="imageFile">변경 이미지</label>
		          <input name="imageFile" id="imageFile" class="form-control" type="file">
	          </div>
          </form>
        </div>
        <div class="modal-footer">
          <button type="button" class="btn btn-default" id="changeBtn">변경</button>
          <button type="button" class="btn btn-default" data-dismiss="modal">닫기</button>
        </div>
      </div>
     </div>
   </div>
      
</div>
</body>
</html>