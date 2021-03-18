<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>


<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>게시판 글쓰기</title>
	<!-- BootStrap CDN 방식 -->
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
  <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>
  <script type="text/javascript" src="../js/formUtil.js"></script>
  
  <script type="text/javascript">
  $(function () {
	$("#cancelBtn").click(function () {
		history.back();
	});
	$("#writeForm").submit(function () {
		//alert("데이터 전달 이벤트 발생");
		
		//필수입력
		//alert(!require($("#title"), "제목"));
		if(!require($("#title"), "제목")) return false;
		if(!require($("#content"), "내용")) return false;
		if(!require($("#writer"), "작성자")) return false;
		
		//길이
		if(!checkLength($("#title"), "제목", 3)) return false;
		if(!checkLength($("#content"), "내용", 3)) return false;
		if(!checkLength($("#writer"), "작성자", 2)) return false;
		
		//submit 이벤트 취소
		//return false;
	});
	
});
  </script>
</head>
<body>
<div class= "container">
<h1>게시판 글쓰기</h1>
<form action="write.do" id="writeForm" method="post">
<!-- 페이지에 대한 정보 넘기기 -->
<input name="perPageNum" type="hidden" value="${pageObject.perPageNum }">
	<div class="form-group">
	<label for ="title">제목</label>
	<input name="title" class ="form-control" id="title"  placeholder="3글자 이상 50자 이내 입력" required="required">
	</div>
	<div class ="form-group">
	<label for = "content">내용</label><textarea rows="7" cols="5" name="content" class="form-control" id="content"  placeholder="3글자 이상 입력" required="required"></textarea>
	</div>
	<div class="form-group">
	<label for="writer">작성자</label>
	<input name ="writer" class="form-control" id="writer"  placeholder="2글자 이상 입력" required="required">
	</div>
	<button class="btn btn-default">등록</button>
	<button class="btn btn-default" type="reset">새로 입력</button>
	<button type= "button" class="btn btn-default" id="cancelBtn" >취소</button>

</form>
</div>
</body>
</html>
