
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>게시판 글수정</title>
	<!-- BootStrap CDN 방식 -->
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
  <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>

</head>
<body>
<div class= "container">
<h1>게시판 글수정</h1>
<form action="update.do" id="writeForm" method="post">
<input name="page" type="hidden" value="${pageObject.page }">
<input name="perPageNum" type="hidden" value="${pageObject.perPageNum }">
	<div class="form-group">
		<label>번호</label>
		<input name="no" class ="form-control" id="no" readonly="readonly" value="${vo.no }">
	</div>
	<div class="form-group">
		<label>제목</label>
		<input name="title" class ="form-control" id="title" required="required" value="${vo.title }">
	</div>
	<div class="form-group">
		<label>내용</label>
		<textarea name="content" class ="form-control" id="content" >${vo.content }</textarea>
	</div>
	<div class="form-group">
		<label>작성자</label>
		<input name="writer" id="writer" class ="form-control"  value="${vo.writer }">
	</div>


<button class="btn btn-default">수정</button>
<button class="btn btn-default" type="reset">새로 입력</button>
<button class="btn btn-default" type="button" id="cancelBtn">취소</button>
</form>
</div>
</body>
</html>