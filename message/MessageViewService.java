package com.webjjang.message.service;

import com.webjjang.main.controller.Service;
import com.webjjang.message.dao.MessageDAO;
import com.webjjang.message.vo.MessageVO;



			public class MessageViewService implements Service {

				private MessageDAO dao;
				
				public MessageViewService(){
					System.out.println("MessageViewService() - 생성완료");
				}
				
				public void setDAO(Object dao) {
					System.out.println("MessageViewService.setDAO() : " + dao);
					this.dao = (MessageDAO)dao;
				}
				
				//넘어오는 데이터 : MessageVO -> obj : no,accepter
				@Override
				public Object service(Object obj) throws Exception {
				// TODO Auto-generated method stub
				//넘어오는 데이터 확인 
				System.out.println("MessageViewService.obj : " + obj);
				//메세지 읽음처리
				MessageVO vo = (MessageVO) obj;
				dao.viewReaded(vo);
				 //no가 넘어오니까 넣음 
				return dao.view(vo.getNo());
	}
}
