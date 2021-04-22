/*=====================
	Process.java
=====================*/

package com.test;

import java.util.ArrayList;
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
			System.out.println("번호   이름    국어  영어  수학   총점  평균  석차");
			
			for (ScoreDTO dto : dao.lists())
			{
				System.out.printf("%3s %5s %5d %5d %5d %6d %6.1f %4d\n", dto.getSid(), dto.getName()
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
		try
		{
			// 검색할 이름 입력받기
			Scanner sc = new Scanner(System.in);
			
			System.out.print("검색할 이름 입력 : ");
			
			String name = sc.next();
			
			//-- 필요할 경우 이 과정에서 프로그래밍적으로 검증(검사) 수행
			
			// 데이터베이스 연결
			dao.connection();
			
			// dao의 lists() 메소드 호출 → 매개변수로 검색할 이름 넘겨주기
			ArrayList<ScoreDTO> arrayList = dao.lists(name);
			
			if (arrayList.size() > 0)	
			{
				// 수신된 내용 출력
				System.out.println("번호   이름    국어  영어  수학   총점  평균  석차");
				
				//arrayList에 수신되어야 해서 ScoreDTO dto : arrayList임
				for (ScoreDTO dto : arrayList)
				{
					System.out.printf("%3s %5s %5d %5d %5d %6d %6.1f %4d\n", dto.getSid(), dto.getName()
							     , dto.getKor(), dto.getEng(), dto.getMat(), dto.getTot(), dto.getAvg(), dto.getRank());
				}
			} 
			else
			{
				System.out.println("검색 결과가 존재하지 않습니다.");
			}
			
			// 데이터베이스 연결 종료
			dao.close();
			
		} catch (Exception e)
		{
			System.out.println(e.toString());
		}
	}//end sungjukSearchName()
	
	// 성적 수정
	public void sungjukUpdate()
	{
		// 번호
		try
		{
			// 수정할 번호 입력받기
			Scanner sc = new Scanner(System.in);
			
			System.out.print("수정할 학생의 번호를 입력하세요 : ");
			
			int sid = sc.nextInt();
			
			//-- 입력받은 번호로 체크해야 할 로직 적용 삽입 가능
			
			// 데이터베이스 연결
			dao.connection();
			
			// 데이터 수정을 위해 대상 검색 → 수정할 대상 수신
			ArrayList<ScoreDTO> arrayList = dao.lists(sid);
			
			if (arrayList.size() > 0)
			{
				//수신된 내용 출력
				System.out.println("번호   이름    국어  영어  수학   총점  평균  석차");
				
				for (ScoreDTO dto : arrayList)
				{
					System.out.printf("%3s %5s %5d %5d %5d %6d %6.1f %4d\n", dto.getSid(), dto.getName()
							     , dto.getKor(), dto.getEng(), dto.getMat(), dto.getTot(), dto.getAvg(), dto.getRank());
				}
				
				System.out.println();
				System.out.print("수정할 데이터 입력(이름 국어 영어 수학) : ");
				
				String name = sc.next();
				int kor = sc.nextInt();
				int eng = sc.nextInt();
				int mat = sc.nextInt();
				
				// dto 구성
				ScoreDTO dto = new ScoreDTO();
				dto.setName(name);
				dto.setKor(kor);
				dto.setEng(eng);
				dto.setMat(mat);
				dto.setSid(String.valueOf(sid));
				
			    int result = dao.modify(dto);
				
				if (result > 0)
				{
					System.out.println(">> 수정이 완료되었습니다.");
				}
				
			}
			else
			{
				System.out.println(">> 수정 대상이 존재하지 않습니다.");
			}
			
			// 데이터베이스 연결 종료
			dao.close();
			
		} catch (Exception e)
		{
			System.out.println(e.toString());
		}
	
	}//end sungjukUpdate()
	
	// 성적 삭제
	public void sungjukDelete()
	{
		try
		{
			// 삭제할 번호 입력받기
			Scanner sc = new Scanner(System.in);
			
			System.out.print("삭제할 학생의 번호를 입력하세요 : ");
			
			int sid = sc.nextInt();
			
			//데이터베이스 연결
			dao.connection();
			
			//데이터 삭제를 위해 대상 검색
			ArrayList<ScoreDTO> arrayList = dao.lists(sid);
			
			if (arrayList.size() > 0)
			{
				//수신된 내용 출력
				System.out.println("번호   이름    국어  영어  수학   총점  평균  석차");
				
				for (ScoreDTO dto : arrayList)
				{
					System.out.printf("%3s %5s %5d %5d %5d %6d %6.1f %4d\n", dto.getSid(), dto.getName()
							     , dto.getKor(), dto.getEng(), dto.getMat(), dto.getTot(), dto.getAvg(), dto.getRank());
				}
				
				System.out.println();
				System.out.print(">> 정말 삭제하시겠습니까?(Y/N) : ");
				String response = sc.next();
				
				if (response.equals("Y") || response.equals("y"))
				{
					int result = dao.remove(sid);
					
					if (result > 0)
					{
						System.out.println(">> 삭제가 완료되었습니다.");
					}
					
				} else
				{
					System.out.println(">> 취소되었습니다.");
				}
			}
			else
			{
				System.out.println(">> 삭제 대상이 존재하지 않습니다.");
			}
			
			//데이터베이스 연결 종료
			dao.close();
			
		} catch (Exception e)
		{
			System.out.println(e.toString());
		}
	}
}





