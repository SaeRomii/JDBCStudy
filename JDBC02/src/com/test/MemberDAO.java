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

public class MemberDAO
{
	// 주요 변수 선언 → DB 연결 객체
	private Connection conn;
	
	// 생성자 정의
	public MemberDAO() throws ClassNotFoundException, SQLException
	{
		conn = DBConn.getConnection();
	}
	
	// 메소드 정의 → 데이터를 입력하는 기능 → insert
	// 회원정보(데이터)추가
	public int add(MemberDTO dto) throws SQLException
	{
		//결과값 변수 선언
		//int형에 반환하겠다.
		//반환할 결과값을 담아낼 변수(적용된 행의 갯수)
		int result = 0;
		
		// 작업 객체 생성
		Statement stmt = conn.createStatement();
		
		/*
		※ Statement 의 종류
		   - Statement(할아버지)
		     : 하나의 쿼리를 사용하고 나면 더 이상 사용할 수 없다.
		   - PreparedStatement(아버지) → Statement의 자식
		     : 하나의 PreparedStatement 로 쿼리를 여러 번 처리할 수 있다.
		   - CallableStatement(자식)   → PreparedStatement의 자식
		     : 데이터베이스 내의 스토어드 프로시저나 함수 등을 호출할 수 있다.
		     
		※ Statement 의 의미
		   자바에서 사용되는 3가지 종류의 작업 객체들은
		   데이터베이스로 쿼리를 담아서 보내는 그릇 정도로 생각하자.
		   즉, 작업 객체에 쿼리를 실어 데이터베이스로 보내버리면
		   그 내용이 데이터베이스에서 처리되는 것이다.
		   이 때, 한 번 사용하고 버리는 그릇은 Statement 이며,
		   재사용이 가능한 그릇은 PreparedStatement 이다.
		*/
		
		// 쿼리문 준비
		String sql = String.format("INSERT INTO TBL_MEMBER(SID, NAME, TEL) VALUES(MEMBERSEQ.NEXTVAL, '%s', '%s')", dto.getName(), dto.getTel());
		
		// 쿼리문 실행
		// 작업 객체를 활용하여 쿼리문 전달(실행) → 적용된 행의 갯수 반환
		result = stmt.executeUpdate(sql);
		
		// 리소스 반납
		stmt.close();
		
		// 최종 결과값 반환
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
		
		// 쿼리문 준비 → select
		String sql = "SELECT COUNT(*) AS COUNT FROM TBL_MEMBER";
		
		// 쿼리문 실행
		ResultSet rs = stmt.executeQuery(sql);
		
		// ResultSet 처리 → 반복문 구성 → 결과값 수신
		while (rs.next())	// if(rs.next())
		{
			result = rs.getInt("COUNT");	// rs.getInt(1)	  	// ※ 컬럼 인덱스는 1부터
		}
		
		//리소스 반납
		rs.close();
		stmt.close();
		
		//최종 결과 반환
		return result;
	}
	
	// 메소드 정의 → 전체 리스트 조회 가능 → select
	// 목록 조회
	public ArrayList<MemberDTO> lists() throws SQLException
	{
		// 결과값 변수 선언
		// 결과값으로 반환할 변수 선언 및 초기화
		ArrayList<MemberDTO> result = new ArrayList<MemberDTO>();
		
		// 작업 객체 생성
		Statement stmt = conn.createStatement();
		
		// 쿼리문 준비 → select
		String sql = String.format("SELECT SID, NAME, TEL FROM TBL_MEMBER ORDER BY SID");
		
		// 쿼리문 실행 → select → executeQuery() → ResultSet 반환
		ResultSet rs = stmt.executeQuery(sql);
		
		// ResultSet 처리 → 반복문 활용 → MemberDTO 인스턴스 생성 → 속성 구성 → ArrayList에 적재
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




