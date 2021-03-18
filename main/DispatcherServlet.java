package com.webjjang.main.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;

import javax.servlet.ServletException;
//import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import com.webjjang.util.filter.AuthorityFilter;

/**
 * Servlet implementation class DispacherServlet
 */
//@WebServlet("/DispacherServlet")
public class DispatcherServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public DispatcherServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#service(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */

	protected void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		// 이 곳에서 처리해야 할 모든 URL(*.do)을 받도록 설정 -> web.xml
		System.out.println("DispatcherServelt.service()-*.do");
		
		//순수한 데이터를 전달하기 위한 객체
		PrintWriter out = response.getWriter();

		//String module = AuthorityFilter.url.substring(0, AuthorityFilter.url.indexOf("/", 1));
		//System.out.println("url:" + AuthorityFilter.url);

		int endIndex = AuthorityFilter.url.indexOf("/",1);
		String module = "/main";
		//module이 존재하면 바꿈. "/main.do" : module이 존재하지 않음. module 변수게 있는 값은 바뀌지 않음
		if(endIndex >= 0) module = AuthorityFilter.url.substring(0,endIndex);
		System.out.println("DispatcherServelt.service().module:" + module);
		
		//모듈에 포함이 안되어 있는 URL의 처리 -> siteMesh에 적용이 안되도록 만들어야 하므로 
		if(AuthorityFilter.url.equals("/ajax/checkId.do")) {
			module = "/member"; //MemberController가 선택 
		}
		else if(AuthorityFilter.url.equals("/ajax/getMessageCnt.do")) {
			module = "/message"; //MessageController가 선택 
			//DB에서 데이터 가져오기 - 새로운 메세지의 갯수를 가져오는 프로그램 
			//controller-service-dao
			try {
				String data = Beans.getController(module).execute(request);
				System.out.println("DispatcherServelt.service().module:" + module + "::ajax:getMessageCnt");
				out.write(data);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return;
		}

		try {

			Controller controller = Beans.getController(module);
			if (controller == null)
				throw new Exception("Controller is null : 404오류-요청하신 url이 존재하지 않습니다.");

			String jspInfo = controller.execute(request);
			System.out.println(request);

			// sendRedirect 하려면 리턴되는 문자열 앞에 "redirect:" 붙여줌
			if (jspInfo.indexOf("redirect:") == 0) {
				// "redirect:list.do" -> jspInfo.substring("redirect:".length());
				jspInfo = jspInfo.substring("redirect:".length());
				response.sendRedirect(jspInfo);
			}
			// "redirect:" 없으면 jsp로 forward 됨
			else {
				request.getRequestDispatcher("/WEB-INF/views/" + jspInfo + ".jsp").forward(request, response);
			}
		} catch (Exception e) {
			// TODO: handle exception
			request.setAttribute("exception", e);
			request.getRequestDispatcher("/WEB-INF/views/error/error_page.jsp").forward(request, response);

		}

	}
}
