package com.webjjang.util.db;

public class DBSQL {

	///////////////게시판 쿼리//////////////////
	
	//list
	public static final String BOARD_LIST 
	= " SELECT RNUM, NO,TITLE,WRITER,TO_CHAR(WRITEDATE,'YYYY.MM.DD')WRITEDATE,HIT FROM("
		+ " SELECT ROWNUM RNUM,NO,TITLE,WRITER,WRITEDATE,HIT FROM("
		+ " SELECT NO,TITLE,WRITER,WRITEDATE,HIT FROM BOARD"
		+ " ORDER BY NO DESC)"
		+ " )WHERE RNUM BETWEEN ? AND ?";
	
	//view
	public static final String BOARD_VIEW 
	= " SELECT NO,TITLE,CONTENT,WRITER,TO_CHAR(WRITEDATE,'YYYY.MM.DD')WRITEDATE,HIT"
		+ " FROM BOARD WHERE NO = ?";
	
	
	//write
	public static final String BOARD_WRITE 
	= " INSERT INTO BOARD(NO,TITLE,CONTENT,WRITER)"
		+ "VALUES (BOARD_SEQ.NEXTVAL,?,?,?) ";
	
	
	//update
	public static final String BOARD_UPDATE
	= " UPDATE BOARD SET TITLE =? , CONTENT =? , WRITER =? WHERE NO =? ";
	
	//delete
	public static final String BOARD_DELETE 
	= " DELETE FROM BOARD WHERE NO =?";
	
	
	//increase
	public static final String BOARD_INCREASE 
	= " UPDATE BOARD SET HIT = HIT +1 WHERE NO =? ";
	
	//total
	public static final String BOARD_GET_TOTALROW 
	= " SELECT COUNT(*) FROM BOARD ";
	
	//댓글 목록 
	public static final String BOARD_REPLY_LIST 
	= " SELECT RNUM, RNO,NO,CONTENT,WRITER,TO_CHAR(WRITEDATE,'YYYY.MM.DD')WRITEDATE FROM("
		+ " SELECT ROWNUM RNUM,RNO,NO,CONTENT,WRITER,WRITEDATE FROM("
		+ " SELECT RNO,NO,CONTENT,WRITER,WRITEDATE FROM BOARD_REPLY"
		+ " WHERE NO =? "
		+ " ORDER BY RNO DESC)"
		+ " )WHERE RNUM BETWEEN ? AND ?";
	
	//댓글 갯수 
	public static final String BOARD_GET_REPLY_TOTALROW 
	= " SELECT COUNT(*) FROM BOARD_REPLY WHERE NO =? ";
	
	//댓글 등록
	public static final String BOARD_REPLY_WRITE 
	= " INSERT INTO BOARD_REPLY (RNO,NO,CONTENT,WRITER) VALUES(BOARD_REPLY_SEQ.NEXTVAL,?,?,?) ";
	
	
	
	/////////////회원관리 쿼리///////////////
	
	//회원가입처리
	public static final String MEMBER_JOIN
	=" INSERT INTO MEMBER(ID,PW,NAME,GENDER,BIRTH,TEL,EMAIL) VALUES (?,?,?,?,?,?,?) ";
	
	//아이디 중복 체크 
	public static final String MEMBER_CHECK_ID
	=" SELECT ID FROM MEMBER WHERE ID =? ";
	
	//로그인 처리
	public static final String MEMBER_LOGIN
	=" SELECT M.ID, M.NAME, M.GRADENO, G.GRADENAME FROM MEMBER M, GRADE G WHERE (M.ID = ? AND M.PW = ?) "
			+ "AND ( M.GRADENO = G.GRADENO)";
	
	//회원 목록 
	public static final String MEMBER_LIST
	=" SELECT RNUM,ID,NAME,GENDER,"
			+ " TO_CHAR(BIRTH,'YYYY.MM.DD')BIRTH,TEL,EMAIL,"
			+ " STATUS,GRADENO,GRADENAME FROM ("
			+ " SELECT ROWNUM RNUM, ID, NAME, GENDER, BIRTH, TEL, EMAIL,"
			+ " STATUS, GRADENO, GRADENAME FROM(SELECT M.ID, M.NAME, M.GENDER, M.BIRTH, M.TEL, M.EMAIL, "
			+ " M.STATUS, M.GRADENO, G.GRADENAME FROM MEMBER M, GRADE G WHERE M.GRADENO = G.GRADENO ORDER BY ID)"
			+ ")WHERE RNUM BETWEEN ? AND ?";
	
	//회원 등급 수정
	public static final String MEMBER_GRADE_MODIFY
	=" UPDATE MEMBER SET GRADENO =? WHERE ID =? ";
	
	//회원 정보 보기
	public static final String MEMBER_VIEW
	=" SELECT M.ID,M.NAME,M.GENDER,TO_CHAR(M.BIRTH,'YYYY.MM.DD')BIRTH,M.TEL,M.EMAIL,TO_CHAR(M.REGDATE,'YYYY.MM.DD')REGDATE,"
			+ "M.STATUS,M.GRADENO,G.GRADENAME FROM MEMBER M, GRADE G WHERE M.ID=? AND (M.GRADENO = G.GRADENO) ";
	
	
	////////공지사항 쿼리////////
	
	public static final String NOTICE_LIST
	=" SELECT RNUM,NO,TITLE,"
			+ " TO_CHAR(STARTDATE,'YYYY.MM.DD') STARTDATE,"
			+ " TO_CHAR(ENDDATE,'YYYY.MM.DD') ENDDATE,"
			+ " TO_CHAR(UPDATEDATE,'YYYY.MM.DD')UPDATEDATE FROM("
			+ " SELECT ROWNUM RNUM,NO,TITLE,STARTDATE,ENDDATE,UPDATEDATE FROM("
			+ " SELECT NO,TITLE,STARTDATE,ENDDATE,UPDATEDATE FROM NOTICE"
			+ " ORDER BY NO DESC )) "
			+ " WHERE RNUM BETWEEN ? AND ? ";
	
	public static final String NOTICE_GET_TOTALROW 
	= " SELECT COUNT(*) FROM NOTICE ";
	
	
	public static final String NOTICE_VIEW 
	= " SELECT NO,TITLE,CONTENT,TO_CHAR(STARTDATE,'YYYY.MM.DD')STARTDATE,TO_CHAR(ENDDATE,'YYYY.MM.DD')ENDDATE,"
		+ " TO_CHAR(UPDATEDATE,'YYYY.MM.DD')UPDATEDATE FROM NOTICE WHERE NO = ?";
	
	
	public static final String NOTICE_WRITE
	=" INSERT INTO NOTICE (NO,TITLE,CONTENT,STARTDATE,ENDDATE) VALUES(NOTICE_SEQ.NEXTVAL,?,?,?,?) ";
	
	//////////이미지 게시판 쿼리//////////
	
	//1.이미지 목록
	public static final String IMAGE_LIST
	=" SELECT RNUM,NO,TITLE,NAME,ID,TO_CHAR(WRITEDATE,'YYYY.MM.DD')WRITEDATE, FILENAME "
			+ " FROM(SELECT ROWNUM RNUM, NO,TITLE,NAME,ID,WRITEDATE,FILENAME FROM( "
			+ " SELECT I.NO,I.TITLE,M.NAME,I.ID,I.WRITEDATE,I.FILENAME FROM "
			+ " IMAGE I,MEMBER M WHERE I.ID = M.ID"
			+ " ORDER BY NO DESC)) "
			+ " WHERE RNUM BETWEEN ? AND ? ";
	
	//1-1.이미지게시판 전제 데이터 갯수 
	public static final String IMAGE_GET_TOTALROW 
	= " SELECT COUNT(*) FROM IMAGE ";
	
	//2.이미지 보기
	public static final String IMAGE_VIEW 
	= " SELECT I.NO,I.TITLE,I.CONTENT,M.NAME,I.ID,TO_CHAR(I.WRITEDATE,'YYYY.MM.DD')WRITEDATE,I.FILENAME FROM IMAGE I,MEMBER M WHERE (no = ?) and (I.ID = M.ID) ";
	
	//3.이미지 등록
	public static final String IMAGE_WRITE
	=" INSERT INTO IMAGE (NO,TITLE,CONTENT,ID,FILENAME) VALUES(IMAGE_SEQ.NEXTVAL,?,?,?,?) ";
	
	//4-1.이미지 파일정보 수정
	public static final String IMAGE_UPDATE_FILE
	=" UPDATE IMAGE SET FILENAME =? WHERE NO=? ";
	
	//5.이미지 삭제
	public static final String IMAGE_DELETE
	=" DELETE FROM IMAGE WHERE NO=? ";
	
	
	
	
	
	////질묻답변 쿼리////
	//1.번호,제목,작성자이름(아이디),작성일,조회수,들여ㅑ쓰기 
	public static final String QNA_LIST
	=" SELECT RNUM,NO,TITLE,NAME,ID,TO_CHAR(WRITEDATE,'YYYY.MM.DD')WRITEDATE,HIT,LEVNO "
			+ " FROM(SELECT ROWNUM RNUM, NO,TITLE,NAME,ID,WRITEDATE,HIT,LEVNO FROM( "
			+ " SELECT Q.NO,Q.TITLE,M.NAME,Q.ID,Q.WRITEDATE,Q.HIT,Q.LEVNO FROM "
			+ " QNA Q,MEMBER M WHERE Q.ID = M.ID"
			+ " ORDER BY Q.REFNO DESC,Q.ORDNO )) "
			+ " WHERE RNUM BETWEEN ? AND ? ";
	
	//1-1.데이터 갯수 가져오기
	public static final String QNA_GET_TOTALROW
	=" SELECT COUNT(*)FROM QNA ";
	
	
	//2.질문답변보기 - 조인 조건 확인 / 답변쓰기 에도 동일한 쿼리 사용. 추가 정보 - 관련 글번호,순서번호,들여쓰기 
	public static final String QNA_VIEW
	=" SELECT Q.NO,Q.TITLE,Q.CONTENT,M.NAME,Q.ID,Q.WRITEDATE,Q.HIT, "
			+ " Q.REFNO,Q.ORDNO,Q.LEVNO "
			+ " FROM QNA Q,MEMBER M WHERE (Q.NO = ?) AND (Q.ID = M.ID) ";
	
	//2-1.조회수 증가
	public static final String QNA_INCREASE
	=" UPDATE QNA SET HIT = HIT + 1 WHERE NO =? ";
	
	
	//3-1.질문하기
	public static final String QNA_QUSETION
	=" INSERT INTO QNA (NO,TITLE,CONTENT,ID,REFNO,ORDNO,LEVNO,PARENTNO) VALUES(QNA_SEQ.NEXTVAL,?,?,?,QNA_SEQ.NEXTVAL,1,0,QNA_SEQ.NEXTVAL) ";
	
	//3-2.답변하기-> 3-3을 먼저 실행 
	public static final String QNA_ANSWER
	=" INSERT INTO QNA (NO,TITLE,CONTENT,ID,REFNO,ORDNO,LEVNO,PARENTNO) VALUES(QNA_SEQ.NEXTVAL,?,?,?,?,?,?,?) ";
	
	//3-3.답변하기의 경우 등록하기 전에 관련글 번호가 같고 순서번호가 같거나 클 때 순서 번호를 1 증가 시킴 
	public static final String QNA_ANSWER_INCREASE_ORDNO
	=" UPDATE QNA SET ORDNO = ORDNO + 1 WHERE REFNO = ? AND ORDNO >= ? ";
	
	/////메세지 쿼리/////
	//1.목록-번호,보낸사람 아이디, 보낸 날짜, 받은 사람 아이디, 받은 날짜 
	public static final String MESSAGE_LIST
	=" SELECT RNUM, NO, SENDER, TO_CHAR(SENDDATE,'YYYY.MM.DD')SENDDATE, ACCEPTER, TO_CHAR(ACCEPTDATE,'YYYY.MM.DD')ACCEPTDATE FROM (SELECT ROWNUM RNUM, NO, SENDER, SENDDATE, ACCEPTER , ACCEPTDATE "
			+ " FROM (SELECT NO, SENDER, SENDDATE, ACCEPTER, ACCEPTDATE  FROM MESSAGE WHERE SENDER = ? OR ACCEPTER = ? ORDER BY NO DESC)) WHERE RNUM BETWEEN ? AND ? ";
	//1-1.전체 데이터 갯수 가져오기
	public static final String MESSAGE_GET_TOTALROW
	=" SELECT COUNT(*)FROM MESSAGE ";
	
	//2.메세지 읽기
	public static final String MESSAGE_VIEW
	=" SELECT NO, SENDER ,TO_CHAR(SENDDATE,'YYYY.MM.DD')SENDDATE, CONTENT, ACCEPTER,TO_CHAR(ACCEPTDATE,'YYYY.MM.DD')ACCEPTDATE FROM MESSAGE WHERE NO =? ";
	
	//2-2읽기표시 처리 - 보려고 하는 글과 같고 받은 사람이 나, 받은 날짜가 없어야 함 
	public static final String MESSAGE_VIEW_READ
	=" UPDATE MESSAGE SET ACCEPTDATE = SYSDATE WHERE NO =? AND ACCEPTER =? AND ACCEPTDATE IS NULL ";
	
	//3.메세지 작성
	public static final String MESSAGE_WRITE
	=" INSERT INTO MESSAGE (NO,SENDER,CONTENT,ACCEPTER)VALUES(MESSAGE_SEQ.NEXTVAL,?,?,?) ";
	
	//4.메세지 삭제
	public static final String MESSAGE_DELETE
	=" DELETE FROM MESSAGE WHERE NO =?";
	
	//5.새로운 메세지 갯수 가져오기
	public static final String MESSAGE_GET_MESSAGE_CNT
	=" SELECT COUNT(*) FROM MESSAGE WHERE ACCEPTER =? AND ACCEPTDATE IS NULL ";


}
