package com.webjjang.util.filter;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

//import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


import com.webjjang.member.vo.LoginVO;

/**
 * Servlet Filter implementation class AuthorityFilter
 */
//@WebFilter("/AuthorityFilter")
public class AuthorityFilter implements Filter {

	//url에 대한 권한 정보를 저장하는 MAP
	//Map<url,gradeNo>
	private static Map<String, Integer> authMap = new HashMap<>();
	
	//페이지에 대한 등급정보를 저장하는 메소드 
	//데이터를 넣는 방법 : static 초기화 블록 
	static {
		//공지사항 작성, 수정, 삭제 - 관리자:9
		authMap.put("/notice/writeForm.do", 9);
		authMap.put("/notice/write.do", 9);
		authMap.put("/notice/updateForm.do", 9);
		authMap.put("/notice/update.do", 9);
		authMap.put("/notice/delete.do", 9);
		
		//이미지 게시판
		authMap.put("/image/writeForm.do", 1);
		authMap.put("/image/write.do", 1);
		authMap.put("/image/updateForm.do", 1);
		authMap.put("/image/update.do", 1);
		authMap.put("/image/updateFile.do", 1);
		authMap.put("/image/delete.do", 1);
		
		//질문답변 - 보기,질문하기,답변하기,삭제 : 1 -> 일반회원도 답변이 가능한 시스템
//		authMap.put("/qna/list.do", 1);
		authMap.put("/qna/view.do", 1);
		authMap.put("/qna/question.do", 1);
		authMap.put("/qna/questionForm.do", 1);
		authMap.put("/qna/answerForm.do", 1);
		authMap.put("/qna/answer.do", 1);
		authMap.put("/qna/updateForm.do", 1);
		authMap.put("/qna/update.do", 1);
		authMap.put("/qna/delete.do", 1);
		
		//메세지 - 목록,보기,보내기,삭제 : 1
		authMap.put("/message/list.do", 1);
		authMap.put("/message/view.do", 1);
		authMap.put("/message/write.do", 1);
		authMap.put("/message/writeForm.do", 1);
		authMap.put("/message/delete.do", 1);
		authMap.put("/ajax/getMessageCnt.do", 1);
		
	}
	
	//요청한 url
	public static String url;
	
    /**
     * Default constructor. 
     */
    public AuthorityFilter() {
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
		// TODO Auto-generated method stub
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		
		HttpServletRequest req = (HttpServletRequest )request;
		
		// TODO Auto-generated method stub
		// place your code here
		//전처리 - 권한체크 프로그램
		System.out.println("Authority.dofilter-권한처리");
		//jsp의 request와 여기의 request는 같은 것. HttpServletRequest가 SelvletRequest 상속 하고 있음 
		url = ((HttpServletRequest)request).getServletPath();
		System.out.println("Authority.dofilter.url:" + url);
		
		
		//로그인 객체 꺼내기
		//로그인 정보는  session에 있는데 안보일 경우 -> request 에서 꺼낼 수 있음
		HttpSession session = req.getSession();
		LoginVO vo = (LoginVO)session.getAttribute("login");
		
		//새로운 메세지 갯수 처리를 하는데 로그인이 안되어 있으면 바로 로그인폼으로 이동
		if(AuthorityFilter.url.equals("/ajax/getMessageCnt") && vo == null) {
			throw new ServletException("메세지 갯수 가져오기 오류 ");
		}
		
		//권한이 없는 경우의 처리 
		if(!checkAuthority(vo)) {
			//오류 페이지로 이동시킴 
			((HttpServletResponse)response).sendRedirect(req.getContextPath() + "/error/auth_error.do");
			//호출한 쪽으로 되돌아감 
			 return;
		}
		

		// pass the request along the filter chain
		chain.doFilter(request, response);
	}
	
	private boolean checkAuthority(LoginVO vo) {
		//url 정보가 authMap에 있는지 확인
		//데이터가 없으면(null이면) 권한 체크가 없는 필요없는 페이지 요청 
		Integer pageGradeNo = authMap.get(url);
		if(pageGradeNo == null) {
			System.out.println("AuthorityFilter.checkAuthority() - 권한이 필요없는 페이지 입니다. " );
			return true;
		}
		
		//여기서부터는 로그인이 필요한 처리.vo가 null이면 안됨 
		if(vo==null) {
			System.out.println("AuthorityFilter.checkAuthority() - 로그인이 필요합니다.");
			
			
			return false;
		}
		System.out.println("AuthorityFilter.checkAuthority().pageGradeNo : " + pageGradeNo);
		System.out.println("AuthorityFilter.checkAuthority().userGradeNo : " + vo.getGradeName());
		//권한이 없는 페이지 요청에 대한 처리 
		if(pageGradeNo > vo.getGradeNo()) {
			System.out.println("AuthorityFilter.checkAuthority() - 이용가능한 권한이 없습니다. " );
			return false;
		}
		System.out.println("AuthorityFilter.checkAuthority() - 권한이 있습니다. " );
		return true;
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
	}

}
