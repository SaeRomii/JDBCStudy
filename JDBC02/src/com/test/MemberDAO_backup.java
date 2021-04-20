/*======================
	MemberDAO.java
======================*/

// Database 에 Access 하는 기능
// → DBConn 활용

// 데이터를 입력하는 기능 → insert

// 인원 수를 확인하는 기능 
// → 대상 테이블(TBL_MEMBER)의 레코드 카운팅 기능 → select

// 전체 리스트 조회하는 기능
// → 대상 테이블(TBL_MEMBER)의 데이터를 조회하는 기능 → select


package com.test;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import com.util.DBConn;

public class MemberDAO_backup
{
	// 주요 변수 선언 → DB 연결 객체
	private Connection conn;
	
	// 생성자 정의
	public MemberDAO_backup() throws ClassNotFoundException, SQLException
	{
		conn = DBConn.getConnection();
	}
	
	// 메소드 정의 → 데이터를 입력하는 기능 → insert
	// 회원정보(데이터)추가
	public int add(MemberDTO dto) throws SQLException
	{
		//결과값 변수 선언
		int result = 0;
		
		// 작업 객체 생성
		Statement stmt = conn.createStatement();
		
		// 쿼리문 준비
		String sql = String.format("INSERT INTO TBL_MEMBER(SID, NAME, TEL) VALUES(MEMBERSEQ.NEXTVAL, '%s', '%s')", dto.getName(), dto.getTel());
		
		// 쿼리문 실행
		result = stmt.executeUpdate(sql);
		
		return result;
	}
	
	// 메소드 정의 → 전체 인원수 확인 가능 → select (count)
	// 인원 확인
	public int count() throws SQLException
	{
		// 결과값 변수 선언
		int result = 0;
		
		// 작업 객체 생성
		Statement stmt = conn.createStatement();
		
		// 쿼리문 준비
		String sql = "SELECT COUNT(*) AS COUNT FROM TBL_MEMBER";
		
		// 쿼리문 실행
		ResultSet rs = stmt.executeQuery(sql);
		
		while (rs.next())
		{
			result = rs.getInt("COUNT");
			
		}
		
		//리소스 반납
		rs.close();
		
		//최종 결과 반환
		return result;
	}
	
	// 메소드 정의 → 전체 리스트 조회 가능 → select
	// 목록 조회
	public ArrayList<MemberDTO> lists() throws SQLException
	{
		// 결과값 변수 선언
		ArrayList<MemberDTO> result = new ArrayList<MemberDTO>();
		
		// 작업 객체 생성
		Statement stmt = conn.createStatement();
		
		// 쿼리문 준비
		String sql = String.format("SELECT SID, NAME, TEL FROM TBL_MEMBER ORDER BY SID");
		
		// 쿼리문 실행
		ResultSet rs = stmt.executeQuery(sql);
		
		
		while (rs.next())
		{
			 MemberDTO dto = new MemberDTO();
			 dto.setSid(rs.getString("SID"));
			 dto.setName(rs.getString("NAME"));
			 dto.setTel(rs.getString("TEL"));
			 
			 result.add(dto);
			
		}
		
		//리소스 반납
		rs.close();
		stmt.close();
		
		//최종 결과값 반환
		return result;
	}
	
	//데이터베이스 연결 해제
	public void close() throws Exception
	{
		DBConn.close();
	}
	
}




