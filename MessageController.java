package com.webjjang.message.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.webjjang.main.controller.Beans;
import com.webjjang.main.controller.Controller;
import com.webjjang.main.controller.ExeService;
import com.webjjang.member.vo.LoginVO;
import com.webjjang.message.vo.MessageVO;
import com.webjjang.util.PageObject;
import com.webjjang.util.filter.AuthorityFilter;

public class MessageController implements Controller{

	private final String MODULE = "message"; 
	private String jspInfo = null;
	
	public String execute(HttpServletRequest request)throws Exception{
		
		PageObject pageObject = PageObject.getInstance(request);
		request.setAttribute("pageObject", pageObject); //페이지를 보여주기 위해 서버 객체에 담음 
		
		switch (AuthorityFilter.url) {
		case "/" + MODULE + "/list.do":
			list(request,pageObject);
			jspInfo = MODULE + "/list";
			break;
		case "/" + MODULE + "/view.do":
			view(request);
			jspInfo = MODULE + "/view";
			break;
		case "/" + MODULE + "/writeForm.do":
			jspInfo = MODULE + "/writeForm";
			break;
		case "/" + MODULE + "/write.do":
			write(request);
			jspInfo = "redirect:list.do?page=1&perPageNum=" + pageObject.getPerPageNum();
			break;
		case "/" + MODULE + "/delete.do":
			delete(request);
			jspInfo = "redirect:list.do?page=1&perPageNum=" + pageObject.getPerPageNum();
			break;

		default:
			break;
		}
		return jspInfo;
	}
	
	private void list(HttpServletRequest request,PageObject pageObject) throws Exception {
		//내 아이디를 가져와서 pageObject에 저장해두기
		pageObject.setAccepter(((LoginVO)request.getSession().getAttribute("login")).getId());

		//데이터 가져오기
		@SuppressWarnings("unchecked")
		List<MessageVO>list = (List<MessageVO>)ExeService.execute(Beans.get(AuthorityFilter.url), pageObject);

		 request.setAttribute("list",list);
		 
	}
	
	private void view(HttpServletRequest request) throws Exception{
		//넘어오는 데이터 받기 - 메세지 번호 
		String strNo = request.getParameter("no");

		//내 아이디 정보를 꺼내야 함 
		String id = ((LoginVO)request.getSession().getAttribute("login")).getId(); 
		//vo객체 생성-데이터 셋팅
		MessageVO vo = new MessageVO();
		vo.setNo(Long.parseLong(strNo)); //코드 줄였음 
		vo.setAccepter(id); //받는 사람 = 나인 데이터를 읽기표시 하기 위해서 id를 accepter에 넣음 
		//db처리 데이터 가져오기
		//1.받은 사람이 로그인한 사람과 같아야 하고(받은 메세지) 번호가 같고 받은 날짜가 null인 읽지 않은 메세지를 읽음표시로 바꿈 (acceptDate를 현재 날짜로 -update)
		//2.메세지 번호에 맞는 전체 메세지 정보 가져오기
		MessageVO messageVO = (MessageVO)ExeService.execute(Beans.get(AuthorityFilter.url), vo);
		//서버객체에 저장
		request.setAttribute("vo", messageVO);
	}
	
	private void write(HttpServletRequest request)throws Exception {
		//넘어오는 데이터 수집
		String accepter = request.getParameter("accepter");
		String content = request.getParameter("content");

		//session 에서 내 아이디 가져오기
		//session의 내용은 /member/login.jsp에서 key를 저장해놨음 (27번째줄)
		LoginVO vo = (LoginVO)request.getSession().getAttribute("login"); 
		String sender = vo.getId();
		//vo객체에 데이터넣기
		MessageVO messageVO = new MessageVO();
		messageVO.setSender(sender);
		messageVO.setContent(content);
		messageVO.setAccepter(accepter);

		//db처리 : jsp-service-dao-db
		//(실행할 서비스,전달되는 데이터 )
		ExeService.execute(Beans.get(AuthorityFilter.url), messageVO);
		
	}
	
	private void delete(HttpServletRequest request)throws Exception{
		String strNo = request.getParameter("no");
		Long no = Long.parseLong(strNo);

		ExeService.execute(Beans.get(AuthorityFilter.url), no);
		
	}
	
}
