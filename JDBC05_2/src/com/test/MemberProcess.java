/*=============================================
	MemberProcess.java
	-콘솔 기반 서브 메뉴 입출력 전용 클래스
=============================================*/


package com.test;

import java.util.ArrayList;
import java.util.Scanner;

public class MemberProcess
{
	private MemberDAO dao;
	
	// 생성자 정의
	public MemberProcess()
	{
		// dao 인스턴스 생성
		dao = new MemberDAO();
	}
	
	// 직원 정보 입력 메소드 정의
	public void memberInsert()
	{
		Scanner sc = new Scanner(System.in);
		
		try
		{
			// 데이터베이스 연결
			dao.connection();
			
			// 지역 리스트 구성
			ArrayList<String> citys = dao.searchCity();
			
			StringBuilder cityStr = new StringBuilder();
			
			for (String city : citys)
			{
				cityStr.append(city + "/");
			}
			
			// 부서 리스트 구성
			ArrayList<String> buseos = dao.searchBuseo();
			
			StringBuilder buseoStr = new StringBuilder();
			
			for (String buseo : buseos)
			{
				buseoStr.append(buseo + "/");
			}
			
			// 직위 리스트 구성
			ArrayList<String> jikwis = dao.searchJikwi();
			
			StringBuilder jikwiStr = new StringBuilder();
			
			for (String jikwi : jikwis)
			{
				jikwiStr.append(jikwi + "/");
			}
			
			
			//스트링빌더 스트링버퍼..차이..
			
			// 사용자에게 보여지는 화면 처리
			/*
			직원 정보 입력--------------------------------------------------------------
			이름 : 선혜연
			주민등록번호(yymmdd-nnnnnnn) : 951102-2234567
			입사일(yyyy-mm-dd) : 2020-02-12
			지역(강원/경기/경남/경북/부산/서울/인천/전남/전북/제주/충남/충북/) : 경기
			전화번호 : 010-1111-1111
			부서(개발부/기획부/영업부/인사부/자재부/총무부/홍보부/) : 개발부
			직위(사장/전무/상무/이사/부장/차장/과장/대리/사원/) : 사원
			기본급(최소 840000원 이상) : 850000
			수당 : 2000000
			
			직원 정보 입력 완료~!!!
			----------------------------------------------------------------------------
			*/
			
			System.out.println();
			System.out.println("직원 정보 입력--------------------------------------------------------------");
			System.out.print("이름 : ");
			String empName = sc.next();
			System.out.print("주민등록번호(yymmdd-nnnnnnn) : ");
			String ssn = sc.next();
			System.out.print("입사일(yyyy-mm-dd) : ");
			String ibsaDate = sc.next();
			System.out.printf("지역(%s) : ", cityStr.toString());
			String cityLoc = sc.next();
			System.out.print("전화번호 : ");
			String tel = sc.next();
			System.out.printf("부서(%s) : ", buseoStr.toString());
			String buseoName = sc.next();
			System.out.printf("직위(%s) : ", jikwiStr.toString());
			String jikwiName = sc.next();
			System.out.printf("기본급(최소 %d원 이상) : ", dao.searchBasicPay(jikwiName));  //직위 넘겨주면서 기본급 얻어내는거임
			int basicPay = sc.nextInt();
			System.out.print("수당 : ");
			int sudang = sc.nextInt();
			System.out.println();
			
			MemberDTO dto = new MemberDTO();
			dto.setEmpName(empName);
			dto.setSsn(ssn);
			dto.setIbsaDate(ibsaDate);
			dto.setCityLoc(cityLoc);
			dto.setTel(tel);
			dto.setBuseoName(buseoName);
			dto.setJikwiName(jikwiName);
			dto.setBasicPay(basicPay);
			dto.setSudang(sudang);
			
			
			int result = dao.add(dto);
			if (result > 0)
			{
				System.out.println("직원 정보 입력 완료~!!!");
			}
			
			System.out.println("----------------------------------------------------------------------------");
			
			
		} catch (Exception e)
		{
			System.out.println(e.toString());
		}
		finally 
		{
			try
			{
				dao.close();
			} catch (Exception e2)
			{
				System.out.println(e2.toString());
			}
		}
	}//end memberInsert()
	
	// 직원 전체 출력 메소드 정의
	/*
	1. 사번 정렬
	2. 이름 정렬
	3. 부서 정렬
	4. 직위 정렬
	5. 급여 내림차순 정렬
	>> 선택(1~5, -1종료) : 1
	*/
	public void memberList()
	{
		Scanner sc = new Scanner(System.in);
		
		// 서브 메뉴 출력
		System.out.println();
		System.out.println("1. 사번 정렬");				//EMP_ID
		System.out.println("2. 이름 정렬");				//EMP_NAME
		System.out.println("3. 부서 정렬");				//BUSEO_NAME
		System.out.println("4. 직위 정렬");				//JIKWI_NAME
		System.out.println("5. 급여 내림차순 정렬");	//PAY DESC
		System.out.print(">> 항목 선택(1~5, -1종료) : ");
		
		String menuStr = sc.next();
		
		try
		{
			int menu = Integer.parseInt(menuStr);
			
			if (menu==-1)
			{
				return;
			}
			
			String key = "";
			
			switch (menu)
			{
				case 1:
					key = "EMP_ID ASC";
					break;
				case 2:
					key = "EMP_NAME ASC";
					break;
				case 3:
					key = "BUSEO_NAME ASC";
					break;
				case 4:
					key = "JIKWI_NAME ASC";
					break;
				case 5:
					key = "PAY DESC";
					break;
			}
			
		// 데이터베이스 연결
		dao.connection();
		
		// 직원 리스트 출력
		System.out.println();
		System.out.printf("전체 인원 : %d명\n", dao.memberCount());
		System.out.println("사번  이름  주민번호  입사일  지역  전화번호    부서  직위  기본급   수당  급여");
		// 매개변수 넘겨받을 key값
		ArrayList<MemberDTO> memList = dao.lists(key);
		for (MemberDTO memberDTO : memList)
		{
			System.out.printf("%d %s %s %s %s %s %s %s %d %d %d\n"
						, memberDTO.getEmpId(), memberDTO.getEmpName(), memberDTO.getSsn()
						, memberDTO.getIbsaDate(), memberDTO.getCityLoc(), memberDTO.getTel()
						, memberDTO.getBuseoName(), memberDTO.getJikwiName()
						, memberDTO.getBasicPay(), memberDTO.getSudang(), memberDTO.getPay());
		}
			
			
			
		} catch (Exception e)
		{
			System.out.println(e.toString());
		}
		finally 
		{
			try
			{
				dao.close();
				
			} catch (Exception e2)
			{
				System.out.println(e2.toString());
			}
		}
	}//end memberList()
	
	
	// 직원 검색 출력 메소드 정의
	public void memberSearch()
	{
		Scanner sc = new Scanner(System.in);
		
		// 서브 메뉴 구성
		System.out.println();
		System.out.println("1. 사번 검색");					//EMP_ID
		System.out.println("2. 이름 검색");					//EMP_NAME
		System.out.println("3. 부서 검색");					//BUSEO_NAME
		System.out.println("4. 직위 검색");					//JIKWI_NAME
		System.out.print(">> 항목 선택(1~4, -1종료) : ");
		String menuStr = sc.next();
		
		try
		{
			int menu = Integer.parseInt(menuStr);
			
			if (menu==-1)
			{
				return;
			}
			
			String key = "";
			String value = "";
			
			switch (menu)
			{
				case 1:
					key = "EMP_ID";
					System.out.print("검색할 사원 번호 입력 : ");
					value = sc.next();		//263번에서 받아서 value에 담기
					break;
				case 2:
					key = "EMP_NAME";
					System.out.print("검색할 사원 이름 입력 : ");
					value = sc.next();
					break;
				case 3:
					key = "BUSEO_NAME";
					System.out.print("검색할 부서명 입력 : ");
					value = sc.next();
					break;
				case 4:
					key = "JIKWI_NAME";
					System.out.print("검색할 직위명 입력 : ");
					value = sc.next();
					break;

			}
			
			// 데이터베이스 연결
			dao.connection();
			
			//int count = dao.memberCount(key, value);
			
			
			//if (count > 0)
			//{
				// 검색 결과 출력
				System.out.println();
				System.out.printf("검색 인원 : %d명\n", dao.memberCount(key, value));
				System.out.println("사번  이름  주민번호  입사일  지역  전화번호    부서  직위  기본급   수당  급여");
				ArrayList<MemberDTO> memList = dao.searchLists(key, value);
				for (MemberDTO memberDTO : memList)
				{
					System.out.printf("%d %s %s %s %s %s %s %s %d %d %d\n"
							, memberDTO.getEmpId(), memberDTO.getEmpName(), memberDTO.getSsn()
							, memberDTO.getIbsaDate(), memberDTO.getCityLoc(), memberDTO.getTel()
							, memberDTO.getBuseoName(), memberDTO.getJikwiName()
							, memberDTO.getBasicPay(), memberDTO.getSudang(), memberDTO.getPay());
				}
			//}
			//else 
			//{
			//	System.out.println("검색 대상이 존재하지 않습니다.");
			//}
			
			
			
			
		} catch (Exception e)
		{
			System.out.println(e.toString());
		}
		finally 
		{
			try
			{
				dao.close();
			} catch (Exception e2)
			{
				System.out.println(e2.toString());
			}
		}
		
		
	}//end memberSearch()
	
	
	// 직원 정보 수정 메소드 정의
	public void memberUpdate()
	{
		Scanner sc = new Scanner(System.in);
		
		try
		{
			// 수정 대상 입력받기
			System.out.print("수정할 직원의 사원번호 입력 : ");
			String value = sc.next();
			
			// 데이터베이스 연결
			dao.connection();
			
			ArrayList<MemberDTO> members = dao.searchLists("EMP_ID", value);
			
			if (members.size() > 0)
			{
				// 지역 리스트 구성
				ArrayList<String> citys = dao.searchCity();
				StringBuilder cityStr = new StringBuilder();
				for (String city : citys)
				{
					cityStr.append(city + "/");
				}
				
				// 부서 리스트 구성
				ArrayList<String> buseos = dao.searchBuseo();
				StringBuilder buseoStr = new StringBuilder();
				for (String buseo : buseos)
				{
					buseoStr.append(buseo + "/");
				}
				
				// 직위 리스트 구성
				ArrayList<String> jikwis = dao.searchJikwi();
				StringBuilder jikwiStr = new StringBuilder();
				for (String jikwi : jikwis)
				{
					jikwiStr.append(jikwi + "/");
				}
				
				//members 리스트에서 0번째 인덱스값 가져오기
				MemberDTO mMember = members.get(0);
				
				int mEmpId = mMember.getEmpId();
				String mEmpName = mMember.getEmpName();
				String mSsn = mMember.getSsn();
				String mIbsaDate = mMember.getIbsaDate();
				String mCityLoc = mMember.getCityLoc();
				String mTel = mMember.getTel();
				String mBuseoName = mMember.getBuseoName();
				String mJikwiName = mMember.getJikwiName();
				int mBasicPay = mMember.getBasicPay();
				int mSudang = mMember.getSudang();
				
				
				System.out.println();
				System.out.println("직원 정보 수정--------------------------------------------------------------");
				System.out.printf("기존 이름 : %s\n", mEmpName);
				System.out.print("수정 이름 : ");
				String empName = sc.next();
				if (empName.equals("-"))
				{
					empName = mEmpName;
				}
				
				System.out.printf("기존 주민등록번호(yymmdd-nnnnnnn) : %s\n", mSsn);
				System.out.print("수정 주민등록번호(yymmdd-nnnnnnn) : ");
				String ssn = sc.next();
				if (ssn.equals("-"))
				{
					ssn = mSsn;
				}
				
				System.out.printf("기존 입사일(yyyy-mm-dd) : %s\n", mIbsaDate);
				System.out.print("수정 입사일(yyyy-mm-dd) : ");
				String ibsaDate = sc.next();
				if (ibsaDate.equals("-"))
				{
					ibsaDate = mIbsaDate;
				}
				
				System.out.printf("기존 지역 : %s\n", mCityLoc);
				System.out.printf("수정 지역(%s) : ", cityStr.toString());
				String cityLoc = sc.next();
				if (cityLoc.equals("-"))
				{
					cityLoc = mCityLoc;
				}
				
				
				System.out.printf("기존 전화번호 : %s\n", mTel);
				System.out.print("수정 전화번호 : ");
				String tel = sc.next();
				if (tel.equals("-"))
				{
					tel = mTel;
				}
				
				
				System.out.printf("기존 부서 : %s\n", mBuseoName);
				System.out.printf("수정 부서(%s) : ", buseoStr.toString());
				String buseoName = sc.next();
				if (buseoName.equals("-"))
				{
					buseoName = mBuseoName;
				}
				
				
				System.out.printf("기존 직위 : %s\n", mJikwiName);
				System.out.printf("수정 직위(%s) : ", jikwiStr.toString());
				String jikwiName = sc.next();
				if (jikwiName.equals("-"))
				{
					jikwiName = mJikwiName;
				}
				
				
				//기본급은 int형인데 입력하다가 문자가 들어갈 수도 있어서 sc.next로 받아오는 구문 작성
				System.out.printf("기존 기본급 : %d\n", mBasicPay);
				System.out.printf("수정 기본급(최소 %d원 이상) : ", dao.searchBasicPay(jikwiName));  //직위 넘겨주면서 기본급 얻어내는거임
				
				String basicPayStr = sc.next();
				int basicPay = 0;
				if (basicPayStr.equals("-"))
				{
					basicPay = mBasicPay;
				}
				else 
				{
					basicPay = Integer.parseInt(basicPayStr);
				}
				
				
				System.out.printf("기존 수당 : %d\n", mSudang);
				System.out.print("수정 수당 : ");
				
				String sudangStr = sc.next();
				int sudang = 0;
				if (sudangStr.equals("-"))
				{
					sudang = mSudang;
				}
				else 
				{
					sudang = Integer.parseInt(sudangStr);
				}			
				
				System.out.println();
				
				MemberDTO dto = new MemberDTO();
				dto.setEmpId(mEmpId);				//insert문과 다르게 여기는 empid해줘야함
				dto.setEmpName(empName);
				dto.setSsn(ssn);
				dto.setIbsaDate(ibsaDate);
				dto.setCityLoc(cityLoc);
				dto.setTel(tel);
				dto.setBuseoName(buseoName);
				dto.setJikwiName(jikwiName);
				dto.setBasicPay(basicPay);
				dto.setSudang(sudang);
				
				
				int result = dao.modify(dto);
				if (result > 0)
				{
					System.out.println("직원 정보 수정 완료~!!!");
				}
				
				System.out.println("----------------------------------------------------------------------------");
				
			}
			else
			{
				System.out.println("수정 대상을 찾지 못했습니다.");
			}
		} catch (Exception e)
		{
			System.out.println(e.toString());
		}
		finally 
		{
			try
			{
				dao.close();
				
			} catch (Exception e2)
			{
				System.out.println(e2.toString());
			}
		}
	}//end memberUpdate()
	
	
	// 직원 정보 삭제 메소드 정의
	public void memberDelete()
	{
		Scanner sc = new Scanner(System.in);
		
		try
		{
			// 삭제 대상 입력받기
			System.out.print("삭제할 직원의 사원번호 입력 : ");
			String value = sc.next();
			
			//직원 정보 확인 후 삭제 결정
			// 데이터베이스 연결
			dao.connection();
			
			ArrayList<MemberDTO> members = dao.searchLists("EMP_ID", value);
			
			if (members.size() > 0)
			{
				System.out.println();
				System.out.println("사번  이름  주민번호  입사일  지역  전화번호    부서  직위  기본급   수당  급여");
				//MemberDTO member = members.get(0);
				for (MemberDTO dto : members)
	            {
	               System.out.printf("%5d %5s %16s %12s %5s %5s %5s %10d %10d %10d\n"
	                              ,dto.getEmpId(),dto.getEmpName(),dto.getSsn(),dto.getIbsaDate(),dto.getCityLoc(),dto.getBuseoName(),dto.getJikwiName()
	                              ,dto.getBasicPay(),dto.getSudang(),dto.getPay());
	            }

				
				System.out.print("정말 삭제하시겠습니까(Y/N)? : ");
				String response = sc.next();
				if (response.equals("Y") || response.equals("y"))
				{
					int result = dao.remove(Integer.parseInt(value));
					if (result > 0)
					{
						System.out.println();
					}
				}
				
				
			}
			else 
			{
				System.out.println("삭제할 대상이 존재하지 않습니다.");
			}
			
			
		} catch (Exception e)
		{
			System.out.println(e.toString());
		}
		finally 
		{
			try
			{
				dao.close();
			} catch (Exception e2)
			{
				System.out.println(e2.toString());
			}
		}
	}//end memberDelete()
}










