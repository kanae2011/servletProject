package com.webjjang.member.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.webjjang.main.controller.Beans;
import com.webjjang.main.controller.Controller;
import com.webjjang.main.controller.ExeService;
import com.webjjang.member.vo.LoginVO;
import com.webjjang.member.vo.MemberVO;
import com.webjjang.util.PageObject;
import com.webjjang.util.filter.AuthorityFilter;

public class MemberController implements Controller{


	private final String MODULE = "member";
	private String jspInfo = null;
	
	public String execute(HttpServletRequest request)throws Exception {
		
		System.out.println("MemberController.execute()");
		
		// 페이지를 위한 처리
		PageObject pageObject = PageObject.getInstance(request);
		request.setAttribute("pageObject", pageObject); // 페이지를 보여주기 위해 서버객체에 담기
		
		switch (AuthorityFilter.url) {
		//1.회원 목록 
		case "/" + MODULE + "/list.do":
			list(request,pageObject);
			jspInfo = MODULE + "/list";
			break;
			
		//2.회원 정보보기
		case "/" + MODULE + "/view.do":
	//		view(request);
			jspInfo = MODULE + "/view";
		
	//	if(loginVO == null){ 
	//		respons.sendRedirect("loginForm.do");
			
			break;
		
		//3.회원 등급수정
		case "/" + MODULE + "/gradeModify.do":
//			gradeModify(request);
			jspInfo = "redirect:/member/list.do";
			break;
			
		//4.회원 탈퇴처리
		case "/" + MODULE + "/delete.do":
//			delete(request);
			jspInfo = "redirect:/member/list.do";
			break;
			
		//5-1.회원 로그인폼
		case "/" + MODULE + "/loginForm.do":
			System.out.println("membercontroller-loginform");
			jspInfo = MODULE + "/loginForm";
			break;
			
		//5-2.회원 로그인
		case "/" + MODULE + "/login.do":
			login(request);
			jspInfo = "redirect:/main/main.do";
			break;
			
		//5-3.회원 로그아웃 
		case "/" + MODULE + "/logout.do":
			logout(request);
			jspInfo = "redirect:/board/list.do";
			break;
			
		//6-1.회원 가입폼 
		case "/" + MODULE + "/joinForm.do":
			jspInfo = MODULE + "/joinForm";
			break;
		
		//6-2.회원 가입 
		case "/" + MODULE + "/join.do":
			join(request);
			jspInfo = "redirect:/main/main.do";
			break;
		
		//7.아이디 중복체크
		case "/ajax/checkId.do":
			//DB에서 입력한 아이디를 찾음 
			//찾은 아이디를 request에 넣음 
			checkId(request);
			//div안에 들어갈 코드만 있는 jsp로 이동시킴
			jspInfo = "member/checkId";
			break;
		default:
			throw new Exception("페이지 오류- 존재하지 않는 페이지 입니다.");
		}
		return jspInfo;
	}
	
	//1.회원 목록
	private void list(HttpServletRequest request,PageObject pageObject)throws Exception {
		@SuppressWarnings("unchecked")
		List<MemberVO>list = (List<MemberVO>)ExeService.execute(Beans.get(AuthorityFilter.url), pageObject);
		
		request.setAttribute("list", list);
	}
	
	//2.회원정보보기
	private void view(HttpServletRequest request,PageObject pageObject)throws Exception {
		//session에 login 정보가 들어있기때문에 가져와야함!!
		LoginVO loginVO = (LoginVO)request.getSession().getAttribute("login");
		System.out.println("view.jsp[session-login]:" + loginVO); //null이면 로그인이 안 된 상태 
		String id = loginVO.getId();
		
		MemberVO vo= (MemberVO)ExeService.execute(Beans.get(AuthorityFilter.url), id);
		//html 안에 쉽게 사용하는 EL을 쓰려면 서버 저장객체에 넣어야 함. 주로 사용하는 것이 requset
		request.setAttribute("vo", vo);
	}
	
	//3.회원 등급 수정
	private void gradeModify(HttpServletRequest request,PageObject pageObject)throws Exception {
		//전달 되는 데이터 수집
		//아이디 받아내기 - 데이터가 안나오면 list.jsp에서 부터 실행을 해야 함 , list.jsp_form 안의 input의 name="id"라고 셋팅 되어 있어야함 
		String id = request.getParameter("id");
		System.out.println("gradeModify.jsp[id]-" + id);

		String strGradeNo = request.getParameter("gradeNo");
		System.out.println("gradeModify.jsp[strGradeNo]-" + strGradeNo);
		int gradeNo = Integer.parseInt(strGradeNo);
		System.out.println("gradeModify.jsp[gradeNo]-" + gradeNo);

		//수집한 데이터를 DB처리 - jsp -> service -> dao :매개변수로 전달. 값이 두개 이므로 클래스(VO) 사용 
		//저장할 VO 객체생성
		MemberVO vo = new MemberVO();
		//생성된 VO 객체에 데이터 넣기 -setter()
		vo.setId(id);
		vo.setGradeNo(gradeNo);
		//데이터 셋팅이 잘되어 있는지 확인
		System.out.println("gradeModify.jsp[vo]-" + vo);

		//MemeberGradeModifyService,MemberDAO.gradeModify(vo),DBSQL.MEMBER_GRADE_MOFIFY
		//init생성,조립
		//수집한 데이터를 service 로 넘겨서 실행 : jsp-service-dao-memberDB
		//service 선택해서 가져오기 
		String url = request.getServletPath();
		System.out.println("gradeModify.jsp[url]" + url);

		ExeService.execute(Beans.get(url), vo);
		
	}
	
	//4.회원탈퇴
	private void delete(HttpServletRequest request,PageObject pageObject)throws Exception{
		String strNo = request.getParameter("no");
		Long no = Long.parseLong(strNo);
		
		Integer result = (Integer)ExeService.execute(Beans.get(AuthorityFilter.url), no);
				
		if(result ==0 ) throw new Exception("회원 탈퇴 오류 - 존재하지 않는 회원");
		
	}
	
	private void login(HttpServletRequest request) throws Exception {
		//로그인 정보 처리를 하는데 성공하는 로그인 처리를 함
		//데이터 받기
		String id = request.getParameter("id");
		String pw = request.getParameter("pw");

		//받은 데이터를 VO객체에 저장(한개를 넘기기 위해)
		LoginVO vo = new LoginVO();
		vo.setId(id);
		vo.setPw(pw);

		//DB 처리-아이디,이름,등급번호,등급이름 가져옴 
		//jsp -> service -> dao  
		String url = request.getServletPath();
		LoginVO loginVO = (LoginVO)ExeService.execute(Beans.get(url), vo);


		//id,pw 틀린 경우 처리 
		if(loginVO==null) throw new Exception("로그인 정보를 확인해주세요");

		request.getSession().setAttribute("login", loginVO);
		
	}
	
	private void logout(HttpServletRequest request) throws Exception {
		// 로그아웃 처리
		request.getSession().invalidate();
		System.out.println("로그아웃 처리가 되었습니다.");
	}
	
	private void join(HttpServletRequest request) throws Exception{
		
		String id = request.getParameter("id");
		String pw = request.getParameter("pw");
		String name = request.getParameter("name");
		String gender = request.getParameter("gender");
		String birth = request.getParameter("birth");
		String tel = request.getParameter("tel");
		String email = request.getParameter("email");
		
		MemberVO vo = new MemberVO();
		vo.setId(id);
		vo.setPw(pw);
		vo.setName(name);
		vo.setGender(gender);
		vo.setBirth(birth);
		vo.setTel(tel);
		vo.setEmail(email);
		
		Integer result = (Integer)ExeService.execute(Beans.get(AuthorityFilter.url), vo);
				
	}
	//아이디 중복체크 
	private void checkId(HttpServletRequest request) throws Exception {
		//넘어오는 아이디 받기
		String id= request.getParameter("id");
		//DB처리->아이디 가져오기
		String result = (String)ExeService.execute(Beans.get(AuthorityFilter.url), id);
		//서버 객체에 저장
		request.setAttribute("id",result);
		
				
		
		
	}
}
