package com.webjjang.image.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import com.webjjang.image.vo.ImageVO;
import com.webjjang.util.PageObject;
import com.webjjang.util.db.DBInfo;
import com.webjjang.util.db.DBSQL;

public class ImageDAO {

	
	Connection con = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	
	//1.이미지 목록
		public List<ImageVO> list(PageObject pageObject) throws Exception{
		
			//넘어오는 데이터 확인
			 System.out.println("ImageDAO.list().pageObject : " + pageObject);
			
			List<ImageVO> list = null;
			try {
				//1.드라이버 확인은 DBInfo + 2.연결
				con = DBInfo.getConnection();
				System.out.println("ImageDAO.list().con : " + con);
				//3.sql -> DBSQL + 4.실행객체 + 데이터 셋팅
				System.out.println(DBSQL.IMAGE_LIST);
				pstmt = con.prepareStatement(DBSQL.IMAGE_LIST);
				pstmt.setLong(1, pageObject.getStartRow()); // 시작 번호
				pstmt.setLong(2, pageObject.getEndRow()); // 끝 번호 
				//5.실행
				rs = pstmt.executeQuery();
				//6.표시 -> 데이터 담기 
				if(rs != null) {
					while(rs.next()) {
						if (list == null) list = new ArrayList<ImageVO>();
						ImageVO vo = new ImageVO();
						vo.setNo(rs.getLong("no"));
						vo.setTitle(rs.getString("title"));
						vo.setName(rs.getString("name"));
						vo.setId(rs.getString("id"));
						vo.setWriteDate(rs.getString("writeDate"));
						vo.setFileName(rs.getString("filename"));
						
						list.add(vo);
					}
				}
			}catch (Exception e) {
				//개발자를 위한 오류 콘솔
			e.printStackTrace();
			//사용자를 위한 오류처리
			throw new Exception("이미지 목록 실행 중 DB처리 오류 ");
				
		    }finally {
				DBInfo.close(con, pstmt, rs);
			}
			return list;
		}
	
	//1-1.이미지 전체 데이터 갯수
		public long getTotalRow() throws Exception{
			long result = 0;
			try {
				con=DBInfo.getConnection();
				System.out.println("ImageDAO.getTotlaRow().con : " + con);
				pstmt= con.prepareStatement(DBSQL.IMAGE_GET_TOTALROW);
				System.out.println("ImageDAO.getTotlaRow().pstmt : " + pstmt);
				rs=pstmt.executeQuery();
				System.out.println("ImageDAO.getTotlaRow().rs : " + rs);
				
				//rs출력은 가능하나 rs.next()는 데이터가 넘어가므로 안됨 
				if(rs!=null && rs.next()) {
					result = rs.getLong(1);
				}
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
		;		throw new Exception("이미지 전제 갯수 가져오는  DB처리중 오류");
			}finally {
				DBInfo.close(con, pstmt,rs);
			}
			System.out.println("ImageDAO.getTotlaRow().result : " + result);
			return result;
		}
		
		
		//2.이미지 보기 
		public ImageVO view(Long no) throws Exception{
			ImageVO vo= null;
			try {
				con=DBInfo.getConnection();
				pstmt= con.prepareStatement(DBSQL.IMAGE_VIEW);
				pstmt.setLong(1, no);
				rs=pstmt.executeQuery();
				
				//rs출력은 가능하나 rs.next()는 데이터가 넘어가므로 안됨 
				if(rs!=null && rs.next()) {
					vo = new ImageVO();
					vo.setNo(rs.getLong("no"));
					vo.setTitle(rs.getString("title"));
					vo.setContent(rs.getString("content"));
					vo.setName(rs.getString("name"));
					vo.setId(rs.getString("id"));
					vo.setWriteDate(rs.getString("writeDate"));
					vo.setFileName(rs.getString("filename"));
					
					System.out.println("ImageVO view().vo" + vo);
				}
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
				;		throw new Exception("이미지 보기 가져오는  DB처리중 오류");
			}finally {
				DBInfo.close(con, pstmt,rs);
			}
			
			return vo;
		}
	
	//3.이미지 등록
	public int write(ImageVO vo) throws Exception {
		int result = 0;
		try {
			
			con = DBInfo.getConnection();
			pstmt = con.prepareStatement(DBSQL.IMAGE_WRITE);
			
			pstmt.setString(1,vo.getTitle());
			pstmt.setString(2,vo.getContent());
			pstmt.setString(3,vo.getId());
			pstmt.setString(4,vo.getFileName());
			
			result = pstmt.executeUpdate();
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			throw new Exception("이미지 등록중 DB 처리 중 오류 발생");
		}finally{
			DBInfo.close(con, pstmt);
		}
		return result;
	}
	
	
	//4.이미지 파일 수정 (변경)-수정,파일명
	public int updateFile(ImageVO vo) throws Exception {
		int result = 0;
		try {
			
			con = DBInfo.getConnection();
			pstmt = con.prepareStatement(DBSQL.IMAGE_UPDATE_FILE);
			pstmt.setString(1,vo.getFileName());
			pstmt.setLong(2,vo.getNo());
			
			result = pstmt.executeUpdate();
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			throw new Exception("이미지 파일 수정 DB 처리 중 오류 발생");
		}finally{
			DBInfo.close(con, pstmt);
		}
		return result;
	}
	//4.-1이미지 파일 수정 (변경)-수정,파일명
	public int update(ImageVO vo) throws Exception {
		int result = 0;
		try {
			
			con = DBInfo.getConnection();
			pstmt = con.prepareStatement(DBSQL.IMAGE_UPDATE_FILE);
			pstmt.setString(1,vo.getFileName());
			pstmt.setLong(2,vo.getNo());
			
			result = pstmt.executeUpdate();
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			throw new Exception("이미지 파일 수정 DB 처리 중 오류 발생");
		}finally{
			DBInfo.close(con, pstmt);
		}
		return result;
	}
	//5.이미지 파일 삭제
	public int delete(Long no) throws Exception {
		int result = 0;
		try {
			
			con = DBInfo.getConnection();
			pstmt = con.prepareStatement(DBSQL.IMAGE_DELETE);
			
			pstmt.setLong(1,no);
			
			result = pstmt.executeUpdate();
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			throw new Exception("이미지 삭제 DB 처리 중 오류 발생");
		}finally{
			DBInfo.close(con, pstmt);
		}
		return result;
	}
}
