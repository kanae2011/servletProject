package com.webjjang.main.controller;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;

import com.webjjang.board.controller.BoardController;
import com.webjjang.board.dao.BoardDAO;
import com.webjjang.board.service.BoardDeleteService;
import com.webjjang.board.service.BoardListService;
import com.webjjang.board.service.BoardUpdateService;
import com.webjjang.board.service.BoardViewService;
import com.webjjang.board.service.BoardWriteService;
import com.webjjang.image.dao.ImageDAO;
import com.webjjang.image.service.ImageDeleteService;
import com.webjjang.image.service.ImageListService;
import com.webjjang.image.service.ImageUpdateFileService;
import com.webjjang.image.service.ImageViewService;
import com.webjjang.image.service.ImageWriteService;
import com.webjjang.member.controller.MemberController;
import com.webjjang.member.dao.MemberDAO;
import com.webjjang.member.service.MemberGradeModifyService;
import com.webjjang.member.service.MemberJoinService;
import com.webjjang.member.service.MemberListService;
import com.webjjang.member.service.MemberLoginService;
import com.webjjang.member.service.MemberViewService;
import com.webjjang.message.controller.MessageController;
import com.webjjang.message.dao.MessageDAO;
import com.webjjang.message.service.MessageDeleteService;
import com.webjjang.message.service.MessageListService;
import com.webjjang.message.service.MessageViewService;
import com.webjjang.message.service.MessageWriteService;
import com.webjjang.notice.controller.NoticeController;
import com.webjjang.notice.dao.NoticeDAO;
import com.webjjang.notice.service.NoticeListService;
import com.webjjang.notice.service.NoticeViewService;
import com.webjjang.notice.service.NoticeWriteService;
import com.webjjang.qna.dao.QnaDAO;
import com.webjjang.qna.service.QnaAnswerService;
import com.webjjang.qna.service.QnaListService;
import com.webjjang.qna.service.QnaQuestionService;
import com.webjjang.qna.service.QnaViewService;

/**
 * Servlet implementation class Init
 */
@WebServlet(value = "/Init",loadOnStartup = 1)
public class Init extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Init() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see Servlet#init(ServletConfig)
	 */
	public void init(ServletConfig config) throws ServletException {
		// TODO Auto-generated method stub
		System.out.println("--jspProject2 실행--");
		System.out.println("--서버가 실행을 시작할 때 실행되는 처리--");
		
		//controller 생성 저장
		Beans.putController("/board", new BoardController());
		Beans.putController("/notice", new NoticeController());
		Beans.putController("/member", new MemberController());
		Beans.putController("/main", new MainController());
		Beans.putController("/message", new MessageController());
		
		//////////////게시판 객체 생성 후 저장 ///////////////
		
		//dao 생성 저장
		Beans.putDAO("boardDAO", new BoardDAO());
		
		//service 생성 저장
		Beans.put("/board/list.do", new BoardListService());
		Beans.put("/board/view.do", new BoardViewService());
		Beans.put("/board/write.do", new BoardWriteService());
		Beans.put("/board/update.do", new BoardUpdateService());
		Beans.put("/board/delete.do", new BoardDeleteService());
		
		//service에 dao 넣기 - 조립
		Beans.get("/board/list.do").setDAO(Beans.getDAO("boardDAO"));
		Beans.get("/board/view.do").setDAO(Beans.getDAO("boardDAO"));
		Beans.get("/board/write.do").setDAO(Beans.getDAO("boardDAO"));
		Beans.get("/board/update.do").setDAO(Beans.getDAO("boardDAO"));
		Beans.get("/board/delete.do").setDAO(Beans.getDAO("boardDAO"));
		
		//생성,저장이 잘되어 있는지 출력
		System.out.println(Beans.get("/board/list.do"));
		System.out.println(Beans.getDAO("boardDAO"));
		
		
		///////////////회원관리객체 생성 후 저장 /////////////////
		
		//dao 생성 저장
		Beans.putDAO("memberDAO", new MemberDAO());
				
		//service 생성 저장
		Beans.put("/member/join.do", new MemberJoinService());
		Beans.put("/member/login.do", new MemberLoginService());
		Beans.put("/member/list.do", new MemberListService());
		Beans.put("/member/gradeModify.do", new MemberGradeModifyService());
		Beans.put("/member/view.do", new MemberViewService());
		
		System.out.println("Init.init().Beans.get(\"/member/gradeModify.do) : " + Beans.get("/member/gradeModify.do"));
		System.out.println(Beans.get("/member/view.do"));
		
		//service에 dao 넣기 - 조립
		Beans.get("/member/join.do").setDAO(Beans.getDAO("memberDAO"));
		Beans.get("/member/login.do").setDAO(Beans.getDAO("memberDAO"));
		Beans.get("/member/list.do").setDAO(Beans.getDAO("memberDAO"));
		Beans.get("/member/gradeModify.do").setDAO(Beans.getDAO("memberDAO"));
		Beans.get("/member/view.do").setDAO(Beans.getDAO("memberDAO"));
		
		
		/////////////공지사항 객체 생성 후 저장//////////////////
		
		Beans.putDAO("noticeDAO", new NoticeDAO());
		
		Beans.put("/notice/list.do", new NoticeListService());
		Beans.put("/notice/view.do", new NoticeViewService());
		Beans.put("/notice/write.do", new NoticeWriteService());
		
		
		Beans.get("/notice/list.do").setDAO(Beans.getDAO("noticeDAO"));
		Beans.get("/notice/view.do").setDAO(Beans.getDAO("noticeDAO"));
		Beans.get("/notice/write.do").setDAO(Beans.getDAO("noticeDAO"));
		
		
		//////////////////질문답변 객체 생성 후 저장/////////////////
		Beans.putDAO("qnaDAO", new QnaDAO());
		
		Beans.put("/qna/list.do", new QnaListService());
		Beans.put("/qna/question.do", new QnaQuestionService());
		Beans.put("/qna/view.do", new QnaViewService());
		Beans.put("/qna/answer.do", new QnaAnswerService());
		//Beans.put("/qna/write.do", new QnaWriteService());
		
		
		Beans.get("/qna/list.do").setDAO(Beans.getDAO("qnaDAO"));
		Beans.get("/qna/question.do").setDAO(Beans.getDAO("qnaDAO"));
		Beans.get("/qna/view.do").setDAO(Beans.getDAO("qnaDAO"));
		Beans.get("/qna/answer.do").setDAO(Beans.getDAO("qnaDAO"));
		//Beans.get("/qna/write.do").setDAO(Beans.getDAO("qnaDAO"));
		
		
		////////이미지게시판 생성 후 저장//////
		Beans.putDAO("imageDAO", new ImageDAO());
		
		Beans.put("/image/list.do", new ImageListService());
		Beans.put("/image/view.do", new ImageViewService());
		Beans.put("/image/write.do", new ImageWriteService());
		Beans.put("/image/updateFile.do", new ImageUpdateFileService());
		Beans.put("/image/delete.do", new ImageDeleteService());
		
		Beans.get("/image/list.do").setDAO(Beans.getDAO("imageDAO"));
		Beans.get("/image/view.do").setDAO(Beans.getDAO("imageDAO"));
		Beans.get("/image/write.do").setDAO(Beans.getDAO("imageDAO"));
		Beans.get("/image/updateFile.do").setDAO(Beans.getDAO("imageDAO"));
		Beans.get("/image/delete.do").setDAO(Beans.getDAO("imageDAO"));
		
		///////메세지 객체 생성 후 저장//////////
		Beans.putDAO("messageDAO", new MessageDAO());
		
		Beans.put("/message/list.do", new MessageListService());
		Beans.put("/message/view.do", new MessageViewService());
		Beans.put("/message/write.do", new MessageWriteService());
		Beans.put("/message/delete.do", new MessageDeleteService());
		System.out.println("MessageWriteService : " +  Beans.get("/message/write.do"));
		
		//주의: key가 다르면 null이 나옴."/message/list.do" -> new MessageListService / "messageDAO" -> MessageDAO
		Beans.get("/message/list.do").setDAO(Beans.getDAO("messageDAO"));
		Beans.get("/message/view.do").setDAO(Beans.getDAO("messageDAO"));
		Beans.get("/message/write.do").setDAO(Beans.getDAO("messageDAO"));
		Beans.get("/message/delete.do").setDAO(Beans.getDAO("messageDAO"));
		System.out.println("messageDAO : " + Beans.getDAO("messageDAO"));
		
		
		
		//오라클 드라이버와 필요한 메소드 로딩 
		try {
			//class 안에 있는 static 부분이 로딩 되고  static{}초기화 블록이 실행
			Class.forName("com.webjjang.util.db.DBInfo");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new ServletException("드라이버 확인하는 처리 중 오류 발생");
		}
	}

}
