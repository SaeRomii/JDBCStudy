/*=====================================================
	Test001.java
	- CallableStatement를 활용한 SQL 구문 전송 실습1
=====================================================*/

package com.test;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.util.Scanner;

import com.util.DBConn;

public class Test001
{
	public static void main(String[] args)
	{
		try
		{
			Scanner sc = new Scanner(System.in);
			Connection conn = DBConn.getConnection();
			
			do
			{
				System.out.print("이름 입력(-1 종료) : ");
				String name = sc.next();
				
				if (name.equals("-1"))
				{
					break;
				}
				
				System.out.print("전화번호 입력 : ");
				String tel = sc.next();
				
				if (conn != null)
				{
					System.out.println("데이터베이스 연결 성공~!!!");
					
					try
					{
						// 쿼리문 준비 → 함수 / 프로시저 호출 구문
						String sql = "{call PRC_MEMBERINSERT(?, ?)}";
						
						// 작업 객체 생성 (CallableStatement 작업 객체)
						CallableStatement cstmt = conn.prepareCall(sql);
						
						// 매개변수 전달
						cstmt.setString(1, name);
						cstmt.setString(2, tel);
						
						int result = cstmt.executeUpdate();
						if (result > 0)
						{
							System.out.println("프로시저 호출 및 데이터 입력 완료~!!!");
						}
						
					} catch (Exception e)
					{
						System.out.println(e.toString());
					}
				}
				
			} while (true);
			
			DBConn.close();
			
			System.out.println("\n데이터베이스 연결 종료~!!!");
			System.out.println("프로그램 종료됨~!!!");
			
		} catch (Exception e)
		{
			System.out.println(e.toString());
		}
	}
}


/*
이름 입력(-1 종료) : 안정미
전화번호 입력 : 010-9797-6565
데이터베이스 연결 성공~!!!
프로시저 호출 및 데이터 입력 완료~!!!
이름 입력(-1 종료) : 이하림
전화번호 입력 : 010-4545-6767
데이터베이스 연결 성공~!!!
프로시저 호출 및 데이터 입력 완료~!!!
이름 입력(-1 종료) : -1

데이터베이스 연결 종료~!!!
프로그램 종료됨~!!!
*/
