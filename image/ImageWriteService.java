package com.webjjang.image.service;


import com.webjjang.main.controller.Service;
import com.webjjang.image.dao.ImageDAO;
import com.webjjang.image.vo.ImageVO;


public class ImageWriteService implements Service {

	
		//dao 필요, 생성 후 넣어줌 -> 1.생성자 2.setter()
		private ImageDAO dao;
			
		
		//넘어오는 데이터 : ImageVO => obj
		@Override
		public Object service(Object obj) throws Exception {
			// TODO Auto-generated method stub
			
			return dao.write((ImageVO)obj);
		}
	
		@Override
		
		public void setDAO(Object dao) {
			// TODO Auto-generated method stub
			this.dao = (ImageDAO)dao;
		}
	}
