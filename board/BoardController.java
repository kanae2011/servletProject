package com.webjjang.board.controller;

import java.util.List;


import javax.servlet.http.HttpServletRequest;

import com.webjjang.board.vo.BoardReplyVO;
import com.webjjang.board.vo.BoardVO;
import com.webjjang.main.controller.Beans;
import com.webjjang.main.controller.Controller;
import com.webjjang.main.controller.ExeService;
import com.webjjang.util.PageObject;
import com.webjjang.util.filter.AuthorityFilter;

public class BoardController implements Controller {

	
	private final String MODULE = "board"; 
	private String jspInfo = null;
	
	
	public String execute(HttpServletRequest request)throws Exception {
		System.out.println("BoardController.execute()_실행");
		
		//페이지를 위한 처리
		PageObject pageObject = PageObject.getInstance(request);
		request.setAttribute("pageObject", pageObject); //페이지를 보여주기 위해 서버 객체에 담음 
		
		switch (AuthorityFilter.url) {
		//1.게시판 목록
		case "/" +  MODULE + "/list.do":
			//service - dao --> request에 저장 해줌 
			list(request,pageObject);
			//"list/list 넘김-> /WEB-INF/views/ + board/list + .jsp를 이용해서 HTML을 만듦"
			jspInfo = MODULE + "/list";
			break;

		//2.게시판 보기
		case "/" +  MODULE + "/view.do":
			//service - dao --> request에 저장 해줌 
			//글보기를 하는 데이터 가져오기.댓글 때문에 글 번호를 다시 돌려줌 
			//Long no = view(request);
			//글보기 할 때 댓글 목록 가져오기 
			replyList(view(request),pageObject,request); 
			//"view/list 넘김-> /WEB-INF/views/board/view.jsp를 이용해서 HTML을 만듦"
			jspInfo = MODULE + "/view";
			break;
		//3-1.게시판 글쓰기폼
		case "/" +  MODULE + "/writeForm.do":
			//"/writeForm/list" 넘김-> /WEB-INF/views/board/view.jsp를 이용해서 HTML을 만듦
			jspInfo = MODULE + "/writeForm";
			break;
		//3-2.게시판 글쓰기 처리
		case "/" +  MODULE + "/write.do":
			//service - dao --> request에 저장 해줌 
			write(request);
			//"write.do" 넘김-> /WEB-INF/views/board/view.jsp를 이용해서 HTML을 만듦
			jspInfo = "redirect:list.do?page=1&perPageNum=" + pageObject.getPerPageNum();
			break;
		
		//4-1.게시판 글수정폼
		case "/" +  MODULE + "/updateForm.do":
			updateForm(request);
			//"/updateForm/list" 넘김-> /WEB-INF/views/board/updateForm +.jsp를 이용해서 HTML을 만듦
			jspInfo = MODULE + "/updateForm";
			break;
		//4-2.게시판 글수정 처리
		case "/" +  MODULE + "/update.do":
			//service - dao --> request에 저장 해줌 
			//Long no = update(request);
			//"view.do" 넘김-> /view.do로 자동이동
			jspInfo = "redirect:view.do?no=" + update(request) + "&inc=0&page=" + pageObject.getPage() + "&perPageNum=" + pageObject.getPerPageNum();
			break;
		
		
		//5.게시판 글삭제 처리
		case "/" +  MODULE + "/delete.do":
			//service - dao --> request에 저장 해줌 
			delete(request);
			//"view.do" 넘김-> /view.do로 자동이동
			jspInfo = "redirect:list.do?page=1&perPageNum=" + pageObject.getPerPageNum();
			break;
		
			
		//6-1.게시판 댓글등록 처리
		case "/" +  MODULE + "/replyWrite.do":
			replyWrite(request);
			System.out.println("BoardCoontroller[query] : " + request.getQueryString());	
			//"view.do" 넘김-> /view.do로 자동이동
			jspInfo = "redirect:view.do?" + request.getQueryString() + "&inc=0";
			break;
		
		//6-2.게시판 댓글 수정 처리
		case "/" +  MODULE + "/replyUpdate.do":
			//"view.do" 넘김-> /view.do로 자동이동
			jspInfo = "redirect:view.do?no=" + request.getParameter("no") + "&inc=0";
			break;
		default:
			throw new Exception("페이지 요청 중 오류 발생- 존재하지 않는 페이지입니다.");
		}
	
		//jsp의 정보를 가지고 리턴
		return jspInfo;
	}
	
	//1.게시판 리스트 처리 
	private void list(HttpServletRequest request,PageObject pageObject)throws Exception{
		
		@SuppressWarnings("unchecked")
		List<BoardVO> list = (List<BoardVO>)ExeService.execute(Beans.get(AuthorityFilter.url), pageObject);

		//서버 객체 requset 에 담음
		request.setAttribute("list", list);
		
	}
	
	//2.게시판 보기 처리
	//return type을 Long 으로 한 이유 : 댓글을 가져오려면 글 번호가 필요하므로 타입을 Long
	private Long view(HttpServletRequest request) throws Exception{
		//servlet-controller(*)-DAO-view.do
		//넘어오는 데이터 받기
		String strNo = request.getParameter("no");
		long no = Long.parseLong(strNo);
		String strInc = request.getParameter("inc");
		long inc = Long.parseLong(strInc);

		//게시판 글보기 데이터 1개 가져오기
		BoardVO vo = (BoardVO)ExeService.execute(Beans.get(AuthorityFilter.url), new Long[]{no,inc});

		//서버 객체 requset 에 담음
		request.setAttribute("vo", vo);
		
		return no;
	}
	
	//3.게시판 글쓰기
	private void write(HttpServletRequest request) throws Exception{
		//1.데이터 수집
		String title = request.getParameter("title");
		String content = request.getParameter("content");
		String writer = request.getParameter("writer");

		BoardVO vo = new BoardVO();
		vo.setTitle(title);
		vo.setContent(content);
		vo.setWriter(writer);
		//2.DB처리 - write.jsp -> service-> dao
		
		Integer result = (Integer)ExeService.execute(Beans.get(AuthorityFilter.url), vo);
		
		System.out.println("BoardController.write().result:" + result);
	}
	
	//4-1.게시판 글수정폼
	private void updateForm(HttpServletRequest request) throws Exception{
		//1.넘어오는 데이터 받기 - 글 번호 
		String strNo = request.getParameter("no");
		long no = Long.parseLong(strNo);
		//2.글 번호에 맞는 데이터 가져오기 -> BoardViewService -> /board/view.jsp
		String url = "/board/view.do"; //현재 url(update)과 다르므로 강제로 셋팅  
		//조회수는 증가 하지 않도록 inc=0 강제 셋팅
		BoardVO vo = (BoardVO)ExeService.execute(Beans.get(url), new Long []{no,0L});
		//3.서버 객체에 넣기 
		request.setAttribute("vo", vo);
		//넘어오는 데이터 받기
	}
	
	//4-2.게시판 글수정
	private Long update(HttpServletRequest request) throws Exception{
		//1.데이터 수집
		String strNo = request.getParameter("no");
		long no = Long.parseLong(strNo);
		String title = request.getParameter("title");
		String content = request.getParameter("content");
		String writer = request.getParameter("writer");

		BoardVO vo = new BoardVO();
		vo.setNo(no);
		vo.setTitle(title);
		vo.setContent(content);
		vo.setWriter(writer);

		//2.DB처리 - update.jsp -> service-> dao
		String url = request.getServletPath();
		Integer result = (Integer)ExeService.execute(Beans.get(url), vo);
		
		if(result < 1) throw new Exception("게시판 글수정 처리 오류-수정 할 데이터가 존재하지 않습니다.");
		
		return no;
	}
	
	//5.게시판 글삭제
	private void delete(HttpServletRequest request)throws Exception{
		String strNo = request.getParameter("no");
		long no = Long.parseLong(strNo);

		//글번호 넘어감 
		String url = request.getServletPath();
		Integer result = (Integer)ExeService.execute(Beans.get(url),no);
		
		if(result==0)throw new Exception("게시판 글삭제 오류-삭제할 글이 존재하지 않습니다.");
	}
	
	//6-1.게시판 댓글 목록 가져오기
	private void replyList(Long no,PageObject pageObject,HttpServletRequest request) throws Exception {
		//DB에서 데이터 가져오기
		//연결 URL -> /board/view.do -> 게시판 글보기 
		//댓글 목록은 URL이 존재하지 않으나 데이터를 가져오기 위해 강제 셋팅 
		
		request.setAttribute("replyList",
				ExeService.execute(Beans.get("/board/replyList.do"),new Object[]{no,pageObject}));
	}
	
	//6-2.게시판 댓글 등록
	private void replyWrite(HttpServletRequest request)throws Exception {
		String strNo = request.getParameter("no");
		String content = request.getParameter("content");
		String writer = request.getParameter("writer");
		
		BoardReplyVO vo = new BoardReplyVO();
		vo.setNo(Long.parseLong(strNo));
		vo.setContent(content);
		vo.setWriter(writer);
		
		//정보를 출력하는 필터 처리
		ExeService.execute(Beans.get(AuthorityFilter.url), vo);
		
	}
	
}
