package com.webjjang.board.service;

import com.webjjang.board.dao.BoardDAO;
import com.webjjang.main.controller.Service;

import com.webjjang.util.PageObject;

public class BoardListService implements Service {

	//dao 필요, 생성 후 넣어줌 -> 1.생성자 2.setter()
	BoardDAO dao;
	
	@Override
	public void setDAO(Object dao) {
		this.dao = (BoardDAO)dao;
	} 
	
	//url 요청에 따른 처리
	//넘어오는 데이터가 pageObject -->obj
	@Override
	public Object service(Object obj) throws Exception {
		// TODO Auto-generated method stub
		//넘어오는 데이터 확인 
		System.out.println("BoardListService.obj : " + obj);
		//전체 데이터 가져오기
		 int totalRow = (int) dao.getTotalRow();
		 PageObject pageObject = (PageObject)obj;
		 pageObject.setTotalRow(totalRow);
		 System.out.println("BoardListService.pageObject : " + pageObject);
		return dao.list(pageObject);
	}

}
