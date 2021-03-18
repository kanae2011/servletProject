package com.webjjang.main.controller;

import javax.servlet.http.HttpServletRequest;

import com.webjjang.util.PageObject;
import com.webjjang.util.filter.AuthorityFilter;

public class MainController implements Controller {

	public String execute(HttpServletRequest request)throws Exception{
		
		String jspInfo = null;
		
		switch (AuthorityFilter.url) {
		case "/main.do": //실제로 localhost 입력한 결과
			jspInfo = "redirect:/main/main.do";
			break;
			
		case "/main/main.do": //실제로 localhost로 입력한 결과
			list(request);
			jspInfo = "main/main";
			break;

		default:
			throw new Exception("존재하지 않는 페이지 입니다.");
			
		}
		return jspInfo;
	}
	
	//메인에 띄울 목록
	private void list(HttpServletRequest request)throws Exception {
		PageObject pageObject = PageObject.getInstance(request); //page:1 perPageNum:10
		//perPageNum = 7 로 변경
		pageObject.setPerPageNum(7);
		request.setAttribute("noticeList", ExeService.execute(Beans.get("/notice/list.do"), pageObject));
		request.setAttribute("boardList", ExeService.execute(Beans.get("/board/list.do"), pageObject));
	}
}
