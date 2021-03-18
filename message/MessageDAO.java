package com.webjjang.message.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.webjjang.message.vo.MessageVO;
import com.webjjang.util.PageObject;
import com.webjjang.util.db.DBInfo;
import com.webjjang.util.db.DBSQL;

public class MessageDAO {

	   Connection con = null;
	   PreparedStatement pstmt = null;
	   ResultSet rs = null;
	
	   //1.메세지 목록
	   public List<MessageVO>list(PageObject pageObject) throws Exception{
	      List<MessageVO> list=null;
	      
	      try {
	         con = DBInfo.getConnection();
	         System.out.println(DBSQL.MESSAGE_LIST);
	         pstmt = con.prepareStatement(DBSQL.MESSAGE_LIST);
	         pstmt.setString(1, pageObject.getAccepter());
	         pstmt.setString(2, pageObject.getAccepter());
	         pstmt.setLong(3, pageObject.getStartRow());
	         pstmt.setLong(4, pageObject.getEndRow());
	         
	         rs=pstmt.executeQuery();
	         
	         if(rs!=null) {
	        	 while(rs.next()) {
	        		 if(list==null) list = new ArrayList<>();
	        		 MessageVO vo = new MessageVO();
	        		 vo.setNo(rs.getLong("no"));
	        		 vo.setSender(rs.getString("sender"));
	        		 vo.setSendDate(rs.getString("sendDate"));
	        		 vo.setAccepter(rs.getString("accepter"));
	        		 vo.setAcceptDate(rs.getString("acceptDate"));
	        		 
	        		 list.add(vo);
	        	 }
	         }
	         
	      } catch (Exception e) {
	         // TODO: handle exception
	         e.printStackTrace();
	         throw new Exception("메세지 목록 DB처리중 오류");
	      }finally {
	         DBInfo.close(con, pstmt, rs);
	      }
	      
	      
	      return list;
	}//end of list
	   
	   //1-1.데이터 가져오기
	   public long getTotalRow() throws Exception{
		   long result = 0;
		   
		   try {
			   	con = DBInfo.getConnection();
		        pstmt = con.prepareStatement(DBSQL.MESSAGE_GET_TOTALROW);
		        rs = pstmt.executeQuery();
		        if(rs !=null && rs.next()) {
		        	result = rs.getLong(1);
		        }
			   
		} catch (Exception e) {
			// TODO: handle exception
			 e.printStackTrace();
	         throw new Exception("메세지 데이터 DB처리중 오류");
		}finally {
			DBInfo.close(con, pstmt, rs);
		}
		   
		   return result;
		   
	   }//end of getTotalRow
	   
	   //2-1.메세지 읽기
	   public MessageVO view(long no) throws Exception {
		   MessageVO vo = null;
		   
		   try {
			   con=DBInfo.getConnection();
			   pstmt=con.prepareStatement(DBSQL.MESSAGE_VIEW);
			   pstmt.setLong(1, no);
			   rs=pstmt.executeQuery();
			   
			   if(rs != null && rs.next()) {
				   vo = new MessageVO();
				   vo.setNo(rs.getLong("no"));
				   vo.setSender(rs.getString("sender"));
				   vo.setSendDate(rs.getString("sendDate"));
				   vo.setContent(rs.getString("content"));
				   vo.setAccepter(rs.getString("accepter"));
				   vo.setAcceptDate(rs.getString("acceptDate"));
			   }
		} catch (Exception e) {
			// TODO: handle exception
			 e.printStackTrace();
	         throw new Exception("메세지 보기 DB처리중 오류");
		}finally {
			DBInfo.close(con, pstmt, rs);
		}
		return vo;
		   
	   }
	   
	   //2-2.읽음처리 
	   public int viewReaded(MessageVO vo) throws Exception {
		   int result = 0;
		   
		   try {
			   con=DBInfo.getConnection();
			   System.out.println(DBSQL.MESSAGE_VIEW_READ);
			   pstmt=con.prepareStatement(DBSQL.MESSAGE_VIEW_READ);
			   pstmt.setLong(1, vo.getNo());
			   pstmt.setString(2, vo.getAccepter());
			   rs=pstmt.executeQuery();
			   
		   } catch (Exception e) {
			   // TODO: handle exception
			   e.printStackTrace();
			   throw new Exception("메세지 보기 DB처리중 오류");
		   }finally {
			   DBInfo.close(con, pstmt, rs);
		   }
		   return result;
		   
	   }
	   
	   //3.메세지 쓰기
	   public int write(MessageVO vo) throws Exception {
		   int result=0;
		   
		   try {
			   con=DBInfo.getConnection();
			   System.out.println(DBSQL.MESSAGE_WRITE);
			   pstmt=con.prepareStatement(DBSQL.MESSAGE_WRITE);
			   pstmt.setString(1,vo.getSender());
			   pstmt.setString(2,vo.getContent());
			   pstmt.setString(3,vo.getAccepter());
			   result = pstmt.executeUpdate();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
	         throw new Exception("메세지 데이터 DB처리중 오류");
		}finally {
			DBInfo.close(con, pstmt);
		}
		   
		   return result;
		   
	   }
	   
	   //4.메세지 지우기
	   public int delete(long no) throws Exception {
		   int result=0;
		   
		   try {
			   con=DBInfo.getConnection();
			   System.out.println(DBSQL.MESSAGE_DELETE);
			   pstmt=con.prepareStatement(DBSQL.MESSAGE_DELETE);
			   pstmt.setLong(1,no);
			   result = pstmt.executeUpdate();
		   } catch (Exception e) {
			   // TODO: handle exception
			   e.printStackTrace();
			   throw new Exception("메세지 삭제 DB처리중 오류");
		   }finally {
			   DBInfo.close(con, pstmt);
		   }
		   
		   return result;
		   
	   }
	   
	   //5.새로운 메세지 갯수 가져오기
	   public Long getMessageCnt(String id) throws Exception {
		   Long cnt = 0L;
		   
		   try {
			   con=DBInfo.getConnection();
			   pstmt=con.prepareStatement(DBSQL.MESSAGE_GET_MESSAGE_CNT);
			   pstmt.setString(1,id);
			   
			   rs = pstmt.executeQuery();
			   if(rs != null && rs.next()) {
				   cnt = rs.getLong(1);
			   }
		   } catch (Exception e) {
			   // TODO: handle exception
			   e.printStackTrace();
			   throw new Exception("새로운 메세지 갯수 DB처리중 오류");
		   }finally {
			   DBInfo.close(con, pstmt,rs);
		   }
		   System.out.println("MessageDAO.getMessageCnt().cnt :" + cnt);
		   return cnt;
		   
	   }
}
