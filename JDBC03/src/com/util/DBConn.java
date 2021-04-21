/*==========================
  DBConn.java
  - try ~ catch로 예외 처리
 =========================*/

package com.util;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBConn
{
	// 변수 선언
	private static Connection dbConn;
	
	//메소드 정의
	public static Connection getConnection()
	{
		if (dbConn == null)
		{
			try
			{
				String url = "jdbc:oracle:thin:@211.238.142.167:1521:xe";
				
				String user = "scott";
				String pwd = "tiger";
				
				// 클래스라는 이름을 가진 class를 forname호출
				Class.forName("oracle.jdbc.driver.OracleDriver");
				
				dbConn = DriverManager.getConnection(url, user, pwd);
				
				
			} catch (Exception e)
			{
				System.out.println(e.toString());
			}
		}
		
		return dbConn;
	}
	
	public static Connection getConnection(String url, String user, String pwd)
	{
		if (dbConn == null)
		{
			try
			{
				Class.forName("oracle.jdbc.driver.OracleDriver");
				
				dbConn = DriverManager.getConnection(url, user, pwd);
				
			} catch (Exception e)
			{
				System.out.println(e.toString());
			}
		}
		
		return dbConn;
	}
	
	public static void close()
	{
		if (dbConn != null)
		{
			try
			{
				if (!dbConn.isClosed())
				{
					dbConn.close();
				}
			} catch (Exception e)
			{
				System.out.println(e.toString());
			}
		}
		
	    dbConn = null;
	}
}


















