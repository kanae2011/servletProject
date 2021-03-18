package com.webjjang.image.service;


import com.webjjang.main.controller.Service;
import com.webjjang.util.PageObject;
import com.webjjang.image.dao.ImageDAO;



public class ImageListService implements Service {

	
		//dao 필요, 생성 후 넣어줌 -> 1.생성자 2.setter()
		private ImageDAO dao;
		
		public ImageListService() {
			System.out.println("ImageListService()_생성완료");
		}
			
		
		//넘어오는 데이터 : ImageVO => obj
		@Override
		public Object service(Object obj) throws Exception {
			// TODO Auto-generated method stub
			//PageObject 전체 데이터 갯수 셋팅,화면에 보여지는 페이지 정보가 계산됨
			((PageObject)obj).setTotalRow(dao.getTotalRow());
			return dao.list((PageObject)obj);
		}
	
		@Override
		
		public void setDAO(Object dao) {
			// TODO Auto-generated method stub
			System.out.println("ImageListService().setDAO"+ dao);
			this.dao = (ImageDAO)dao;
		}
	}
