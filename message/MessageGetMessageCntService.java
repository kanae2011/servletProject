package com.webjjang.message.service;

import com.webjjang.main.controller.Service;
import com.webjjang.message.dao.MessageDAO;
import com.webjjang.message.vo.MessageVO;



			public class MessageGetMessageCntService implements Service {

				private MessageDAO dao;
				
				public MessageGetMessageCntService(){
					System.out.println(" MessageGetMessageCntService() - 생성완료");
				}
				
				public void setDAO(Object dao) {
					System.out.println(" MessageGetMessageCntService.setDAO() : " + dao);
					this.dao = (MessageDAO)dao;
				}
				
				//넘어오는 데이터 : String id -> obj : accepter : 로그인한 정보(session에서)
				@Override
				public Object service(Object obj) throws Exception {
				// TODO Auto-generated method stub
				//넘어오는 데이터 확인 
				System.out.println(" MessageGetMessageCntService.obj : " + obj);
				
				 //no가 넘어오니까 넣음 
				return dao.getMessageCnt((String)obj);
	}
}
