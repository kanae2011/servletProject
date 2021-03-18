package com.webjjang.message.service;

import com.webjjang.main.controller.Service;
import com.webjjang.message.dao.MessageDAO;
import com.webjjang.util.PageObject;


			public class MessageListService implements Service {

				private MessageDAO dao;
				
				public MessageListService(){}
				
				public void setDAO(Object dao) {
					this.dao = (MessageDAO)dao;
				}
				
			
				@Override
				public Object service(Object obj) throws Exception {
				// TODO Auto-generated method stub
				//넘어오는 데이터 확인 
				System.out.println("MessageListService.obj : " + obj);
				//전체 데이터 가져오기
				 long totalRow = dao.getTotalRow();
				 PageObject pageObject = (PageObject)obj;
				 pageObject.setTotalRow(totalRow);
				 System.out.println("MessageListService.pageObject : " + pageObject);
				 
				 return dao.list(pageObject);
	}
}
