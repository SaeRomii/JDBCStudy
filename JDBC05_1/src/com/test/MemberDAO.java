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
	
	//데이터베이스 연결 담당 메소드
	public Connection connection()
	{
		conn = DBConn.getConnection();
		return conn;
	}
	
	//데이터입력 담당 메소드
	public int add(MemberDTO dto) throws SQLException
	{
		int result = 0;
		
		Statement stmt = conn.createStatement();
		
		String sql = String.format("INSERT INTO TBL_EMP(EMP_ID, EMP_NAME, SSN, IBSADATE, CITY_ID, TEL, BUSEO_ID, JIKWI_ID, BASICPAY, SUDANG)"
				+ " VALUES (EMPSEQ.NEXTVAL, '%s', '%s', TO_DATE('%s', 'YYYY-MM-DD'), '%s', %d, %d, %d, %d)"
				, dto.getEMP_NAME(), dto.getSSN(), dto.getIBSADATE(), dto.getCITY_ID(), dto.getTEL()
				, dto.getBUSEO_ID(), dto.getJIKWI_ID(), dto.getBASICPAY(), dto.getSUDANG());
		
		result = stmt.executeUpdate(sql);
		stmt.close();
		
		return result;
	}
	
	//직원 전체 리스트 출력 담당 메소드
	public ArrayList<MemberDTO> lists(int num) throws SQLException
	{
		ArrayList<MemberDTO> result = new ArrayList<MemberDTO>();
		
		try
		{
			
			Statement stmt = conn.createStatement();
			String sql = "SELECT * FROM TBL_EMP E JOIN TBL_CITY C  ON E.CITY_ID = C.CITY_ID LEFT JOIN TBL_BUSEO B" 
					+ "    ON E.BUSEO_ID = B.BUSEO_ID LEFT JOIN TBL_JIKWI J ON E.JIKWI_ID = J.JIKWI_ID";
			switch (num)
			{
			case 1:
				sql = sql + "EMP_ID ASC";
				break;
			case 2:
				sql = sql + "EMP_NAME ASC";
				break;
			case 3:
				sql = sql + "BUSEO_ID ASC";
				break;
			case 4:
				sql = sql + "JIKWI_ID ASC";
			case 5:
				sql = sql + "BASICPAY DESC";
				break;
			default :
				System.out.println("입력 내용이 잘못되었습니다.");
				break;
			}
			
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next())
			{
				MemberDTO dto = new MemberDTO();
				dto.setEMP_ID(rs.getInt("EMP_ID"));
				dto.setEMP_NAME(rs.getString("EMP_NAME"));
				dto.setSSN(rs.getString("SSN"));
				dto.setIBSADATE(rs.getString("IBSADATE"));
				dto.setCITY_ID(rs.getInt("CITY_ID"));
				dto.setTEL(rs.getString("TEL"));
				dto.setBUSEO_ID(rs.getInt("BUSEO_ID"));
				dto.setJIKWI_ID(rs.getInt("JIKWI_ID"));
				dto.setBASICPAY(rs.getInt("BASICPAY"));
				dto.setSUDANG(rs.getInt("SUDANG"));
				
				result.add(dto);
			}
			

			rs.close();
			stmt.close();
			
		
			
		} catch (Exception e)
		{
			System.out.println(e.toString());
		}
		return result;
		
	}
	
	// 인원 수
	public int count() throws SQLException
	{
		int result = 0;
		
		Statement stmt = conn.createStatement();
		
		String sql = "SELECT COUNT(*) AS COUNT FROM TBL_EMP";
		
		ResultSet rs = stmt.executeQuery(sql);
		
		while (rs.next())
		{
			result = rs.getInt("COUNT");
			
		}
		
		rs.close();
		stmt.close();
		
		return result;
			
	}
	
	//사원번호 검색
	public ArrayList<MemberDTO> lists (int num, String vrb) throws SQLException
	{
	
		ArrayList<MemberDTO> result = new ArrayList<MemberDTO>();
		Statement stmt = conn.createStatement();
		String sql =  "SELECT E.EMP_ID as ID, E.EMP_NAME AS NAME, E.SSN AS SSN, E.IBSADATE AS IBSADATE, "
				+ " FROM TBL_EMP E JOIN TBL_CITY C" + 
				" ON E.CITY_ID = C.CITY_ID" + 
				"    LEFT JOIN TBL_BUSEO B" + 
				"    ON E.BUSEO_ID = B.BUSEO_ID" + 
				"        LEFT JOIN TBL_JIKWI J" + 
				"        ON E.JIKWI_ID = J.JIKWI_ID ";
		
		switch (num)
		{
		case 1:
			sql = sql + String.format("WHERE E.EMP_ID = %d", vrb);
			break;
		case 2:
			sql = sql + String.format("WHERE E.EMP_NAME = '%s'", vrb);
			break;
		case 3:
			sql = sql + String.format("WHERE B.BUSEO_NAME = '%s'", vrb);
			break;
		case 4:
			sql = sql + String.format("WHERE J.JIKWI_NAME = '%s'", vrb);
			break;
		}
		
		ResultSet rs = stmt.executeQuery(sql);
		while (rs.next())
		{
			MemberDTO dto = new MemberDTO();
			dto.setEMP_ID(rs.getInt("EMP_ID"));
			dto.setEMP_NAME(rs.getString("EMP_NAME"));
			dto.setSSN(rs.getString("SSN"));
			dto.setIBSADATE(rs.getString("IBSADATE"));
			dto.setCITY_LOC(rs.getString("CITY_LOC"));
			dto.setTEL(rs.getString("TEL"));
			dto.setBUSEO_NAME(rs.getString("BUSEO_NAME"));
			dto.setJIKWI_NAME(rs.getString("JIKWI_NAME"));
			dto.setBASICPAY(rs.getInt("BASICPAY"));
			dto.setSUDANG(rs.getInt("SUDANG"));
			
			result.add(dto);
		}
		rs.close();
		stmt.close();
				
		return result;
	}
	
	//직원 정보 수정
	public int modify(MemberDTO dto) throws SQLException
	{
		int result=0;
		
		Statement stmt = conn.createStatement();
		
		String sql = String.format("UPDATE TBL_EMP" + 
				" SET NAME = '%s', SSN = '%s', IBSADATE = '%s', CITY_ID = %s" +
				", TEL = '%s', BUSEO_ID = %s, JIKWI_ID = %s" + 
				", BASICPAY = %d, SUDANG = %d" + 
				" WHERE EMP_ID = %d"
				, dto.getEMP_ID(), dto.getEMP_NAME(), dto.getSSN(), dto.getIBSADATE()
				, dto.getCITY_ID(), dto.getTEL(), dto.getBUSEO_ID(), dto.getJIKWI_ID()
				, dto.getBASICPAY(), dto.getSUDANG());
		
		result = stmt.executeUpdate(sql);
		stmt.close();
	
		return result;
	}
	
	//직원 정보 삭제
	public int remove(int EMP_ID) throws SQLException 
	{
		int result = 0;
		
		Statement stmt = conn.createStatement();
		
		String sql = String.format("DELETE FROM TBL_EMP E JOIN TBL_CITY C "
				+ " ON E.CITY_ID = C.CITY_ID LEFT JOIN TBL_BUSEO B "
				+ " ON E.BUSEO_ID = B.BUSEO_ID LEFT JOIN TBL_JIKWI J ON M.JIKWI_ID = J.JIKWI_ID"
				, EMP_ID);
		result = stmt.executeUpdate(sql);
		stmt.close();
		
		return result;
	}
	
}


















