/*===========================================
  Test001.java
  - main() 메소드를 포함하는 테스트 클래스
===========================================*/

package com.test;

import java.sql.Connection;

import com.util.DBConn;

public class Test001
{
	public static void main(String[] args)
	{
		Connection conn = DBConn.getConnection();
		// ※ DB 연결 과정이가장 부하가 크기 때문에
		//    한 번 연결된 객체를 계속 사용할 수 있도록 Singleton 패턴 적용
		
		if (conn != null)
		{
			System.out.println("데이터베이스 연결 성공~!!!");
		}
		
		DBConn.close();
		//-- close() 메소드 호출을 통해 연결 종료
	}
}
