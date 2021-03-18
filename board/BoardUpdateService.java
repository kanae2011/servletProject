package com.webjjang.board.service;

import com.webjjang.board.dao.BoardDAO;
import com.webjjang.board.vo.BoardVO;
import com.webjjang.main.controller.Service;

public class BoardUpdateService implements Service {

	//dao 필요, 생성 후 넣어줌 -> 1.생성자 2.setter()
	private BoardDAO dao;
	
	@Override
	public void setDAO(Object dao) {
		this.dao = (BoardDAO)dao;
	} 
	
	@Override
	public Object service(Object obj) throws Exception {
		// TODO Auto-generated method stub
		return dao.update((BoardVO)obj);
	}

}
