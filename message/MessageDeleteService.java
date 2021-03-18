package com.webjjang.message.service;

import com.webjjang.main.controller.Service;
import com.webjjang.message.dao.MessageDAO;



			public class MessageDeleteService implements Service {

				private MessageDAO dao;
				
				//기본 생성자 만들기-서비스 만들었는지 확인하기 위해 
				public MessageDeleteService(){
					System.out.println("MessageDeleteService()-생성완료");
				}
				
				//Init에서 조립할 때 dao 확인
				public void setDAO(Object dao) {
					//받아온 dao를 저장 
					this.dao = (MessageDAO)dao;
				}
				
			
				@Override
				//넘어오는 데이터no => obj
				public Object service(Object obj) throws Exception {
				// TODO Auto-generated method stub
				//넘어오는 데이터 확인 
				System.out.println("MessageDeleteService.obj : " + obj);
				//전체 데이터 가져오기
				//dao의delete() 실행해서 결과를 리턴
				 return dao.delete((Long)obj);
	}
}
