package com.webjjang.board.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.webjjang.board.vo.BoardVO;
import com.webjjang.util.db.DBInfo;
import com.webjjang.util.db.DBSQL;

import com.webjjang.util.PageObject;

/*
 * 필요한 메소드
 * list() view () increase() write() update() delete() getTotalRow()
 */
public class BoardDAO {

	//필요한 객체 선언 - con,pstmt,rs
	Connection con = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	
	//1.게시판 목록
	public List<BoardVO> list(PageObject pageObject) throws Exception{
		
		//넘어오는 데이터 확인
		 System.out.println("BoardDAO.list().pageObject : " + pageObject);
		
		List<BoardVO> list = null;
		try {
			//1.드라이버 확인은 DBInfo + 2.연결
			con = DBInfo.getConnection();
			System.out.println("BoardDAO.list().con : " + con);
			//3.sql -> DBSQL + 4.실행객체 + 데이터 셋팅
			System.out.println(DBSQL.BOARD_LIST);
			pstmt = con.prepareStatement(DBSQL.BOARD_LIST);
			pstmt.setLong(1, pageObject.getStartRow()); // 시작 번호
			pstmt.setLong(2, pageObject.getEndRow()); // 끝 번호 
			//5.실행
			rs = pstmt.executeQuery();
			//6.표시 -> 데이터 담기 
			if(rs != null) {
				while(rs.next()) {
					if (list == null) list = new ArrayList<BoardVO>();
					BoardVO vo = new BoardVO();
					vo.setNo(rs.getLong("no"));
					vo.setTitle(rs.getString("title"));
					vo.setWriter(rs.getString("writer"));
					vo.setWriteDate(rs.getString("writeDate"));
					vo.setHit(rs.getLong("hit"));
					
					list.add(vo);
				}
			}
		}catch (Exception e) {
			//개발자를 위한 오류 콘솔
		e.printStackTrace();
		//사용자를 위한 오류처리
		throw new Exception("게시판 목록 실행 중 DB처리 오류 ");
			
	    }finally {
			DBInfo.close(con, pstmt, rs);
		}
		return list;
	}
	
	//1-1.전체 갯수 구하기
	public long getTotalRow() throws Exception{
		long result = 0;
		try {
			con=DBInfo.getConnection();
			System.out.println("BoardDAO.getTotlaRow().con : " + con);
			pstmt= con.prepareStatement(DBSQL.BOARD_GET_TOTALROW);
			System.out.println("BoardDAO.getTotlaRow().pstmt : " + pstmt);
			rs=pstmt.executeQuery();
			System.out.println("BoardDAO.getTotlaRow().rs : " + rs);
			
			//rs출력은 가능하나 rs.next()는 데이터가 넘어가므로 안됨 
			if(rs!=null && rs.next()) {
				result = rs.getLong(1);
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
;		throw new Exception("게시판 전제 갯수 가져오는  DB처리중 오류");
		}finally {
			DBInfo.close(con, pstmt,rs);
		}
		System.out.println("BoardDAO.getTotlaRow().result : " + result);
		return result;
	}
	
	//2.게시판 글보기
	public BoardVO view(long no) throws Exception{
		BoardVO vo = null;
		try {
			//1.드라이버 확인은 DBInfo + 2.연결
			con = DBInfo.getConnection();
			//3.sql -> DBSQL + 4.실행객체 + 데이터 셋팅
			pstmt = con.prepareStatement(DBSQL.BOARD_VIEW);
			pstmt.setLong(1, no); 
			//5.실행 -데이터 한개 발생, 반복문 필요X
			rs = pstmt.executeQuery();
			//6.표시 -> 데이터 담기 
			if(rs != null && rs.next()) {
				vo = new BoardVO();
				vo.setNo(rs.getLong("no"));
				vo.setTitle(rs.getString("title"));
				vo.setContent(rs.getString("content"));
				vo.setWriter(rs.getString("writer"));
				vo.setWriteDate(rs.getString("writeDate"));
				vo.setHit(rs.getLong("hit"));
				}
			
		}catch (Exception e) {
			//개발자를 위한 오류 콘솔
		e.printStackTrace();
		//사용자를 위한 오류처리
		throw new Exception("게시판 글보기 실행 중 DB처리 오류 ");
			
	    }finally {
			DBInfo.close(con, pstmt, rs);
		}
		return vo;
	}
	
	//2-1.조회수 1 증가 (목록-> 글보기 할 때만)
	public int increase(long no) throws Exception{
		int result = 0;
		try {
			//1.드라이버 확인은 DBInfo + 2.연결
			con = DBInfo.getConnection();
			//3.sql -> DBSQL + 4.실행객체 + 데이터 셋팅
			pstmt = con.prepareStatement(DBSQL.BOARD_INCREASE);
			pstmt.setLong(1, no); 
			//5.실행 -데이터 한개 발생, 반복문 필요X
			result = pstmt.executeUpdate();
			//6.표시 
			System.out.println("조회수 1증가 성공");
			
		}catch (Exception e) {
			//개발자를 위한 오류 콘솔
		e.printStackTrace();
		//사용자를 위한 오류처리
		throw new Exception("게시판 조회수 실행 중 DB처리 오류 ");
			
	    }finally {
			DBInfo.close(con, pstmt);
		}
		return result;
	}
	
	//3.게시판 글쓰기
	public int write(BoardVO vo) throws Exception{
		int result = 0;
		try {
			//1.드라이버 확인은 DBInfo + 2.연결
			con = DBInfo.getConnection();
			//3.sql -> DBSQL + 4.실행객체 + 데이터 셋팅
			pstmt = con.prepareStatement(DBSQL.BOARD_WRITE);
			pstmt.setString(1, vo.getTitle()); 
			pstmt.setString(2, vo.getContent()); 
			pstmt.setString(3, vo.getWriter()); 
			//5.실행 -데이터 한개 발생, 반복문 필요X
			result = pstmt.executeUpdate();
			//6.표시 -> 데이터 담기 
		}catch (Exception e) {
			//개발자를 위한 오류 콘솔
		e.printStackTrace();
		//사용자를 위한 오류처리
		throw new Exception("게시판 글쓰기 실행 중 DB처리 오류 ");
			
	    }finally {
			DBInfo.close(con, pstmt);
		}
		return result;
	}
	
	//4.게시판 글수정
	public int update(BoardVO vo)throws Exception{
		int result = 0;
		try {
			//1.드라이버 확인은 DBInfo + 2.연결
			con = DBInfo.getConnection();
			//3.sql -> DBSQL + 4.실행객체 + 데이터 셋팅
			pstmt = con.prepareStatement(DBSQL.BOARD_UPDATE);
			pstmt.setString(1, vo.getTitle()); 
			pstmt.setString(2, vo.getContent()); 
			pstmt.setString(3, vo.getWriter()); 
			pstmt.setLong(4, vo.getNo()); 
			//5.실행 -데이터 한개 발생, 반복문 필요X
			result = pstmt.executeUpdate();
			//6.표시 -> 데이터 담기 
		}catch (Exception e) {
			//개발자를 위한 오류 콘솔
		e.printStackTrace();
		//사용자를 위한 오류처리
		throw new Exception("게시판 글수정 실행 중 DB처리 오류 ");
			
	    }finally {
			DBInfo.close(con, pstmt);
		}
		return result;
	}
	
	//5.게시판 글삭제
	public int delete(long no)throws Exception{
		int result = 0;
		try {
			//1.드라이버 확인은 DBInfo + 2.연결
			con = DBInfo.getConnection();
			//3.sql -> DBSQL + 4.실행객체 + 데이터 셋팅
			pstmt = con.prepareStatement(DBSQL.BOARD_DELETE);
			pstmt.setLong(1, no); 
			
			//5.실행 -데이터 한개 발생, 반복문 필요X
			result = pstmt.executeUpdate();
			//6.출력
			if(result==1)
				System.out.println("게시판 글삭제 성공");
			else
				System.out.println("삭제하려는 글 정보 확인");
		}catch (Exception e) {
			//개발자를 위한 오류 콘솔
		e.printStackTrace();
		//사용자를 위한 오류처리
		throw new Exception("게시판 글삭제 실행 중 DB처리 오류 ");
			
	    }finally {
			DBInfo.close(con, pstmt);
		}
		return result;
	}
}
