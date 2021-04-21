/*=====================
	Process.java
=====================*/

package com.test;

import java.util.Scanner;

public class Process
{
	// 주요 속성 구성 → 데이터베이스 액션 처리 전담 객체
	private ScoreDAO dao;
	
	// 생성자 정의
	public Process()
	{
		dao = new ScoreDAO();
	}
	
	// 성적 입력 기능
	public void sungjukInsert()
	{
		try
		{
			// 데이터베이스 연결
			dao.connection();
			
			// 레코드 수 확인
			int count = dao.count();
			
			Scanner sc = new Scanner(System.in);
			
			do
			{
				System.out.println();
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
				
				// dao의 add() 메소드 호출
				int result = dao.add(dto);
				
				if (result > 0)
				{
					System.out.println(">> 성적 입력이 완료되었습니다.");
				}
			} while (true);  //무한루프
			
			dao.close();
			
			
		} catch (Exception e)
		{
			System.out.println(e.toString());
		}
	}//end sungjukInsert()
	
	// 성적 전체 출력
	public void sungjukSelectAll()
	{
		try
		{
			dao.connection();
			
			System.out.printf("전체인원 %d명\n", dao.count());
			System.out.println("번호	이름	국어	영어	수학	총점	평균	석차");
			
			for (ScoreDTO dto : dao.lists())
			{
				System.out.printf("%s %3s %3d %3d %3d %3d %f %3d\n", dto.getSid(), dto.getName()
						     , dto.getKor(), dto.getEng(), dto.getMat(), dto.getTot(), dto.getAvg(), dto.getRank());
			}
			
			dao.close();
			
		} catch (Exception e)
		{
			System.out.println(e.toString());
		}
	}//end sungjukSelectAll()
	
	// 이름 검색 출력
	public void sungjukSearchName()
	{
		
	}
	
	// 성적 수정
	public void sungjukUpdate()
	{
		
	}
	
	// 성적 삭제
	public void sungjukDelete()
	{
		
	}
}





