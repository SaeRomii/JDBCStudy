/*=======================================
	MemberDAO.java
	- 데이터베이스 액션 처리 전용 클래스
========================================*/


package com.test;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import com.util.DBConn;

public class MemberDAO
{
	private Connection conn;
	
	
	// 데이터베이스 연결
	public Connection connection()
	{
		conn = DBConn.getConnection();
		return conn;
	}
	
	
	// 데이터베이스 연결 종료
	public void close()
	{
		DBConn.close();
	}
	
	
	// 직원 데이터 입력
	public int add(MemberDTO dto) throws SQLException
	{
		int result = 0;
		
		Statement stmt = conn.createStatement();
		
		String sql = String.format("INSERT INTO TBL_EMP"
				+ "(EMP_ID, EMP_NAME, SSN, IBSADATE, CITY_ID"
				+ ", TEL, BUSEO_ID, JIKWI_ID, BASICPAY, SUDANG)"
				+ " VALUES (EMPSEQ.NEXTVAL, '%s', '%s', TO_DATE('%s', 'YYYY-MM-DD')"
				+ ", (SELECT CITY_ID FROM TBL_CITY WHERE CITY_LOC='%s'), '%s'"
				+ ", (SELECT BUSEO_ID FROM TBL_BUSEO WHERE BUSEO_NAME='%s')"
				+ ", (SELECT JIKWI_ID FROM TBL_JIKWI WHERE JIKWI_NAME='%s')"
				+ ", %d, %d)"
				, dto.getEmpName(), dto.getSsn(), dto.getIbsaDate(), dto.getCityLoc(), dto.getTel()
				, dto.getBuseoName(), dto.getJikwiName()
				, dto.getBasicPay(), dto.getSudang());
		
		result = stmt.executeUpdate(sql);
		
		stmt.close();
		
		return result;
		
	}//end add()
	
	
	// 전체 직원 수 조회
	public int memberCount() throws SQLException
	{
		// 반환할 결과 변수 선언
		int result = 0;
		
		// 작업 객체 생성
		Statement stmt = conn.createStatement();
		
		// 쿼리문 준비
		String sql = "SELECT COUNT(*) AS COUNT FROM TBL_EMP";
		
		// 쿼리문 실행 → executeQuery() → ResultSet 반환
		ResultSet rs = stmt.executeQuery(sql);
		
		// ResultSet 처리 → 반복문 구성(단일 레코드 단일값일 경우 조건문도 가능) → 결과값 수신
		while (rs.next())
		{
			result = rs.getInt("COUNT");
			
		}
		
		// 리소스 반납
		rs.close();
		stmt.close();
		
		// 최종 결과값 반환
		return result;
		
	}//end memberCount()
	
	
	// 검색 결과 직원 수 조회
	// EMP_ID=1001			→ key : EMP_ID     / value : 1001
	// EMP_NAME='이상화'	→ key : EMP_NAME   / value : '이상화'
	// BUSEO_NAME='개발부'	→ key : BUSEO_NAME / value : '개발부'
	// JIKWI_NAME='대리'	→ key : JIKWI_NAME / value : '대리'
	public int memberCount(String key, String value) throws SQLException
	{
		int result = 0;
		
		Statement stmt = conn.createStatement();
		
		String sql = "";
		//EMP_ID는 숫자형태라서 
		if (key.equals("EMP_ID"))
		{
			sql = String.format("SELECT COUNT(*) AS COUNT FROM EMPVIEW WHERE %s='%s'", key, value);
		}
		else 
		{
			sql = String.format("SELECT COUNT(*) AS COUNT FROM EMPVIEW WHERE %s='%s'", key, value);
		}
		
		
		ResultSet rs = stmt.executeQuery(sql);
		
		while (rs.next())
		{
			result = rs.getInt("COUNT");
			
		}
		
		rs.close();
		stmt.close();
		
		return result;
	}
	
	
	// 직원 데이터 전체 조회 (사번/이름/부서/직위/급여내림차순 정렬)
	// 사원,이름,부서,직위,급여를 key값으로 받아와서 조회
	public ArrayList<MemberDTO> lists(String key) throws SQLException
	{
		// 반환할 결과값 선언 및 초기화
		ArrayList<MemberDTO> result = new ArrayList<MemberDTO>();
		
		// 작업 객체 생성
		Statement stmt = conn.createStatement();
		
		// 쿼리문 준비
		String sql = String.format("SELECT EMP_ID, EMP_NAME, SSN"
				+ ", TO_CHAR(IBSADATE, 'YYYY-MM-DD') AS IBSADATE, CITY_LOC, TEL"
				+ ", BUSEO_NAME, JIKWI_NAME, BASICPAY, SUDANG, PAY FROM EMPVIEW"
				+ " ORDER BY %s", key);
		
		
		
		// 쿼리문 실행
		ResultSet rs = stmt.executeQuery(sql);
		
		// ResultSet 처리 → 반복문 구성
		// MemberDTO 수신하게 처리
		while (rs.next())
		{
			// MemberDTO 인스턴스 생성
			MemberDTO dto = new MemberDTO();
			
			dto.setEmpId(rs.getInt("EMP_ID"));
			dto.setEmpName(rs.getString("EMP_NAME"));
			dto.setSsn(rs.getString("SSN"));
			dto.setIbsaDate(rs.getString("IBSADATE"));
			dto.setCityLoc(rs.getString("CITY_LOC"));
			dto.setTel(rs.getString("TEL"));
			dto.setBuseoName(rs.getString("BUSEO_NAME"));
			dto.setJikwiName(rs.getString("JIKWI_NAME"));
			dto.setBasicPay(rs.getInt("BASICPAY"));
			dto.setSudang(rs.getInt("SUDANG"));
			dto.setPay(rs.getInt("PAY"));
			
			result.add(dto);
			
		}
		
		// 리소스 반납
		rs.close();
		stmt.close();
		
		// 최종 결과값 반환
		return result;
		
	}//end lists()
	
	
	// 직원 검색
	// EMP_ID=1001			→ key : EMP_ID     / value : 1001
	// EMP_NAME='이상화'	→ key : EMP_NAME   / value : '이상화'
	// BUSEO_NAME='개발부'	→ key : BUSEO_NAME / value : '개발부'
	// JIKWI_NAME='대리'	→ key : JIKWI_NAME / value : '대리'
	public ArrayList<MemberDTO> searchLists(String key, String value) throws SQLException
	{
		// 반환할 결과값 선언 및 초기화
		ArrayList<MemberDTO> result = new ArrayList<MemberDTO>();
		
		// 작업 객체 생성
		Statement stmt = conn.createStatement();
		
		// 쿼리문 준비
		String sql = "";
		if (key.equals("EMP_ID"))
		{
			sql = String.format("SELECT EMP_ID, EMP_NAME, SSN"
					+ ", TO_CHAR(IBSADATE, 'YYYY-MM-DD') AS IBSADATE, CITY_LOC"
					+ ", TEL, BUSEO_NAME, JIKWI_NAME, BASICPAY, SUDANG, PAY "
					+ " FROM EMPVIEW WHERE %s='%s'", key, value);
		}
		else
		{
			sql = String.format("SELECT EMP_ID, EMP_NAME, SSN"
					+ ", TO_CHAR(IBSADATE, 'YYYY-MM-DD') AS IBSADATE, CITY_LOC"
					+ ", TEL, BUSEO_NAME, JIKWI_NAME, BASICPAY, SUDANG, PAY "
					+ " FROM EMPVIEW WHERE %s='%s'", key, value);
		}
		
		
		// 쿼리문 실행
		ResultSet rs = stmt.executeQuery(sql);
		
		//ResultSet 반복문
		while (rs.next())
		{
			// MemberDTO 객체 생성
			MemberDTO dto = new MemberDTO();
			
			dto.setEmpId(rs.getInt("EMP_ID"));
			dto.setEmpName(rs.getString("EMP_NAME"));
			dto.setSsn(rs.getString("SSN"));
			dto.setIbsaDate(rs.getString("IBSADATE"));
			dto.setCityLoc(rs.getString("CITY_LOC"));
			dto.setTel(rs.getString("TEL"));
			dto.setBuseoName(rs.getString("BUSEO_NAME"));
			dto.setJikwiName(rs.getString("JIKWI_NAME"));
			dto.setBasicPay(rs.getInt("BASICPAY"));
			dto.setSudang(rs.getInt("SUDANG"));
			dto.setPay(rs.getInt("PAY"));
			
			result.add(dto);
			
		}
		
		// 리소스 반납
		rs.close();
		stmt.close();
		
		// 최종 결과값 반환
		return result;
	}// end searchLists()
	
	
	// 지역 리스트 조회
	public ArrayList<String> searchCity() throws SQLException
	{
		ArrayList<String> result = new ArrayList<String>();
		
		Statement stmt = conn.createStatement();
		
		String sql = "SELECT CITY_LOC FROM TBL_CITY";
		ResultSet rs = stmt.executeQuery(sql);
		
		while (rs.next())
		{
			result.add(rs.getString("CITY_LOC"));
		}
		
		rs.close();
		stmt.close();
		
		return result;
	}

	
	// 부서 리스트 조회
	public ArrayList<String> searchBuseo() throws SQLException
	{
		ArrayList<String> result = new ArrayList<String>();
		
		Statement stmt = conn.createStatement();
		
		String sql = "SELECT BUSEO_NAME FROM TBL_BUSEO";
		ResultSet rs = stmt.executeQuery(sql);
		
		while (rs.next())
		{
			result.add(rs.getString("BUSEO_NAME"));
			
		}
		rs.close();
		stmt.close();
		
		return result;
	}
	
	
	// 직위 리스트 조회
	public ArrayList<String> searchJikwi() throws SQLException
	{
		ArrayList<String> result = new ArrayList<String>();
		
		Statement stmt = conn.createStatement();
		
		String sql = "SELECT JIKWI_NAME FROM TBL_JIKWI";
		
		ResultSet rs = stmt.executeQuery(sql);
		
		while (rs.next())
		{
			result.add(rs.getString("JIKWI_NAME"));
			
		}
		rs.close();
		stmt.close();
		
		return result;
	}
	
	
	// 직위에 따른 최소 기본급 조회
	// 직위 넘겨주고 최소기본급 받아야해서
	public int searchBasicPay(String jikwiName) throws SQLException
	{
		int result = 0;
		
		Statement stmt = conn.createStatement();
		
		String sql = String.format("SELECT MIN_BASICPAY FROM TBL_JIKWI WHERE JIKWI_NAME = '%s'", jikwiName);
		
		ResultSet rs = stmt.executeQuery(sql);
		
		while (rs.next())
		{
			// 단일 값 넘겨받게 됨
			// 값 받아와서 result에 담기
			result = rs.getInt("MIN_BASICPAY");
			
		}
		
		// 리소스 반납
		rs.close();
		stmt.close();
		
		return result;
	}
	
	
	// 직원 수정
	public int modify(MemberDTO dto) throws SQLException
	{
		int result = 0;
		
		Statement stmt = conn.createStatement();
		
		String sql = String.format("UPDATE TBL_EMP SET EMP_NAME = '%s'"
				+ ", SSN = '%s', IBSADATE = TO_DATE('%s', 'YYYY-MM-DD')"
				+ ", CITY_ID = (SELECT CITY_ID FROM TBL_CITY WHERE CITY_LOC='%s')"
				+ ", TEL = '%s'"
				+ ", BUSEO_ID = (SELECT BUSEO_ID FROM TBL_BUSEO WHERE BUSEO_NAME = '%s')"
				+ ", JIKWI_ID = (SELECT JIKWI_ID FROM TBL_JIKWI WHERE JIKWI_NAME = '%s')"
				+ ", BASICPAY = %d, SUDANG = %d WHERE EMP_ID = %d"
				, dto.getEmpName(), dto.getSsn(), dto.getIbsaDate(), dto.getCityLoc()
				, dto.getTel(), dto.getBuseoName(), dto.getJikwiName()
				, dto.getBasicPay(), dto.getSudang(), dto.getEmpId());
		
		result = stmt.executeUpdate(sql);
		stmt.close();
		
		return result;
	}
	
	
	// 직원 삭제
	// empId 받기
	public int remove(int empId) throws SQLException
	{
		int result = 0;
		
		Statement stmt = conn.createStatement();
		
		String sql = String.format("DELETE FROM TBL_EMP WHERE EMP_ID=%d", empId);
		
		result = stmt.executeUpdate(sql);
		
		stmt.close();
		
		return result;
	}
}
































