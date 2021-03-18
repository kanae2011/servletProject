package com.webjjang.image.service;


import com.webjjang.main.controller.Service;
import com.webjjang.image.dao.ImageDAO;



public class ImageViewService implements Service {

	
		//dao 필요, 생성 후 넣어줌 -> 1.생성자 2.setter()
		private ImageDAO dao;
			
		public ImageViewService() {
			System.out.println("ImageViewService()_생성완료");
		}
		
		//넘어오는 데이터 : no => obj
		@Override
		public Object service(Object obj) throws Exception {
			// TODO Auto-generated method stub
			
			return dao.view((Long)obj);
		}
	
		@Override
		
		public void setDAO(Object dao) {
			// TODO Auto-generated method stub
			this.dao = (ImageDAO)dao;
		}
	}
