/*========================
	MemberMain.java
========================*/

/*
○ 문제
   TBL_MEMBER 테이블을 활용하여
   이름과 전화번호를 여러 건 입력받고, 전체 출력하는 프로그램을 구현한다.
   단, 데이터베이스 연동이 이루어져야 하고
   MemberDAO, MemberDTO 클래스를 활용해야 한다.
   
실행 예)

이름 전화번호 입력(1) : 박민지 010-1111-1111
>> 회원 정보 입력 완료~!!!
이름 전화번호 입력(2) : 이희주 010-2222-2222
>> 회원 정보 입력 완료~!!!
이름 전화번호 입력(3) : 심혜진 010-3333-3333
>> 회원 정보 입력 완료~!!!
이름 전화번호 입력(4) : .

 ----------------------------------
 전체 회원 수 : 3명
 ----------------------------------
 번호	  이름	   전화번호
 1       박민지    010-1111-1111
 2       이희주	   010-2222-2222
 3       심혜진	   010-3333-3333
 ----------------------------------
*/

package com.test;

import java.util.Scanner;

import com.util.DBConn;

public class MemberMain_backup
{
	public static void main(String[] args)
	{
		Scanner sc = new Scanner(System.in);
		
		try
		{
			MemberDAO dao = new MemberDAO();
			System.out.println("데이터베이스 연결됨~!!!");
			
			int count = dao.count();
			do
			{
				System.out.printf("이름 전화번호 입력(%d) : ", (++count));
				
				String name = sc.next();
				if (name.equals("."))
				{
					break;
				}
				
				String tel = sc.next();
				
				MemberDTO dto = new MemberDTO();
				dto.setName(name);
				dto.setTel(tel);
				
				int result = dao.add(dto);
				
				if (result >0)
				{	
					System.out.println("회원 정보 입력 완료~!!!");
				}
				
			} while (true);
			
			System.out.println("---------------------");
			System.out.printf("전체 회원 수 : %d명          \n", dao.count());
			System.out.println("---------------------");
			System.out.println("번호     이름     전화번호     ");
			
			for (MemberDTO dto : dao.lists())
			{
				System.out.printf("%3s %10s %20s%n", dto.getSid(), dto.getName(), dto.getTel());
			}
			System.out.println("-------------------");
			
		} catch (Exception e)
		{
			System.out.println(e.toString());
		} finally
		{
			try
			{
				DBConn.close();
				System.out.println("데이터베이스 연결 닫힘~!!!");
				System.out.println("프로그램 종료~!!!");
			} catch (Exception e2)
			{
				System.out.println(e2.toString());
		    }
		}
	}
}













