package com.webjjang.board.service;

import com.webjjang.board.dao.BoardDAO;
import com.webjjang.main.controller.Service;

public class BoardViewService implements Service {

	//dao 필요, 생성 후 넣어줌 -> 1.생성자 2.setter()
	private BoardDAO dao;
	
	@Override
	public void setDAO(Object dao) {
		this.dao = (BoardDAO)dao;
	} 
	
	@Override
	public Object service(Object obj) throws Exception {
		// TODO Auto-generated method stub
		//글 보기와 글 수정 진행, 글보기(list->view)는 조회수 1 증가, 수정(update->view)은 증가 안함/데이터 두개가 넘어옴 [글번호,증가여부] 
		//obj[0] - no / obj[1] - inc
		Object[] objs = (Object[])obj;
		long no = (Long)objs[0];
		long inc = (Long)objs[1];
		if(inc == 1)dao.increase(no);
		return dao.view((Long)objs[0]);
		
	}

}
