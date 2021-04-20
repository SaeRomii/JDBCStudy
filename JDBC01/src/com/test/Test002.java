/*===========================================
   Test002.java
   - main() 메소드를 포함하는 테스트 클래스
   - 데이터베이스 연결
   - 데이터 입력
============================================*/

package com.test;

import java.sql.Connection;
import java.sql.Statement;

import com.util.DBConn;

public class Test002
{

	public static void main(String[] args)
	{
		Connection conn = DBConn.getConnection();
		// 연결객체 넘겨받음
		
		if (conn == null)	// 연결객체 넘겨받는 과정에서 문제가 생기면~
		{
			System.out.println("데이터베이스 연결 실패~!!!");
			System.exit(0);
		}
		
		//System.out.println("데이터베이스 연결 성공~!!!");
		
		try
		{
			// 작업 객체(Statement) 준비
			Statement stmt = conn.createStatement();
			// createStatement는 Statement 반환한다.
			// 데이터 입력 쿼리 실행 과정
			// 한 번 실행하면 다시 실행하지 못하는 상황이다.
			// TBL_MEMBER 테이블에는 기본키 제약조건이 설정되어 있으므로
			// 동일한 키 값이 중복될 수 없기 때문이다.
			// 4월19일에 INSERT문 추가했을 때는 데이터베이스 연결 성공했지만 4월 20일에 또 실행하게 되면
			// 이미 그 데이터가 있기 때문에 에러 발생
			
			
			// 쿼리문 준비
			String sql = "INSERT INTO TBL_MEMBER(SID, NAME, TEL) VALUES(2, '김서현', '010-2222-2222')";
			//-- 주의. 쿼리문 끝에 『;』붙이지 않는다.
			//-- 주의. 자바에서 실행한 DML 구문(INSERT)은 내부적으로 자동 commit 된다.
			//-- 주의. 오라클에서 트랜잭션 처리가 끝나지 않으면
			//		   데이터 액션 처리가 이루어지지 않는다.
			
			int result = stmt.executeUpdate(sql);
			// executeUpdate는 int를 반환한다.
			// 작업객체 실행(쿼리문)
			// INSERT로 받으면 SQL문에서 업데이트가 발생(변경사항 발생) → executeUpdate가 되는것.
			
			if (result >0)
			{
				System.out.println("데이터 입력 성공~!!!");
			}
			else 
			{
				System.out.println("데이터 입력 실패 ㅠ_ㅠ");
			}
			
		} catch (Exception e)
		{
			System.out.println(e.toString());
		}
		
		DBConn.close();
		// DBConn에서 dbConn != null이면 dbConn.isClosed()   → 닫혀있지 않으면 닫아라
		
	}

}
