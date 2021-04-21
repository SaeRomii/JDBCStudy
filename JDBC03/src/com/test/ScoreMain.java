/*===========================
	ScoreMain.java
===========================*/

/*
○ 성적 처리 프로그램 구현 → 데이터베이스 연동 → ScoreDAO, ScoreDTO 클래스 활용
   여러 명의 이름, 국어점수, 영어점수, 수학점수를 입력받아
   총점, 평균을 연산하여 출력하는 프로그램을 구현한다.
 
실행 예)

1번 학생 성적 입력(이름 국어 영어 수학) : 선혜연 90 80 70
2번 학생 성적 입력(이름 국어 영어 수학) : 이상화 100 90 80
3번 학생 성적 입력(이름 국어 영어 수학) : 이유림 80 85 80
4번 학생 성적 입력(이름 국어 영어 수학) : .

-----------------------------------------------------------
번호	 이름	 국어	 영어	수학	총점	평균
-----------------------------------------------------------
 1		선혜연	 90		 80		 70		xxx		xx.x
 2		이상화	 100     90		 80		xxx		xx.x
 3		이유림	 80		 85		 80		xxx		xx.x
-----------------------------------------------------------
*/


package com.test;

import java.util.Scanner;

import com.util.DBConn;

public class ScoreMain
{
	public static void main(String[] args)
	{
		Scanner sc = new Scanner(System.in);
		
		try
		{
			ScoreDAO dao = new ScoreDAO();
			System.out.println("데이터베이스 연결됨~!!!");
			
			// 현재 데이터 몇 개 들어있는지 확인
			int count = dao.count();
			
			do
			{
				System.out.printf("%d번 학생 성적 입력(이름 국어 영어 수학) : ", (++count));
				
				String name = sc.next();
				
				// 반복의 조건을 무너뜨리는 코드 구성
				if (name.equals("."))
					break;
				
				int kor = sc.nextInt();
				int eng = sc.nextInt();
				int mat = sc.nextInt();
				
				// ScoreDTO 객체 구성
				ScoreDTO dto = new ScoreDTO();
				dto.setName(name);
				dto.setKor(kor);
				dto.setEng(eng);
				dto.setMat(mat);
				
				
				// 데이터베이스에 데이터를 입력하는 메소드 호출
				int result = dao.add(dto);
				
				if (result >0)
				{
					System.out.println("성적 입력 완료~!!!");
				}
				

			} while (true);	//무한반복
			
			
			System.out.println("-----------------------------------------------------------");
			System.out.println("번호	 이름	 국어	 영어	수학	총점	평균");
			System.out.println("-----------------------------------------------------------");
			
			for (ScoreDTO dto : dao.lists())
			{
				System.out.printf("%3s %7s %5d %5d %d %d %d\n", dto.getSid(), dto.getName()
						          , dto.getKor(), dto.getEng(), dto.getMat(), dto.getKor()+dto.getEng()+dto.getMat()
						          , (dto.getKor()+dto.getEng()+dto.getMat())/3);
			}
			System.out.println("-----------------------------------------------------------");
			

		} catch (Exception e)
		{
			System.out.println(e.toString());
		}
		finally 
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
		sc.close();
		
	}
	
}














