SELECT USER
FROM DUAL;
--==>> SCOTT

DROP TABLE TBL_MEMBER;
--==>> Table TBL_MEMBER이(가) 삭제되었습니다.


---○ 실습 테이블 생성
CREATE TABLE TBL_MEMBER
( SID   NUMBER
, NAME  VARCHAR2(30)
, TEL   VARCHAR2(60)
, CONSTRAINT MEMBER_SID_PK PRIMARY KEY(SID)
);
--==>> Table TBL_MEMBER이(가) 생성되었습니다.


--○ 샘플 데이터 입력
INSERT INTO TBL_MEMBER(SID, NAME, TEL) VALUES(1, '김가영', '010-1111-1111');
--==>> 1 행 이(가) 삽입되었습니다.

--○ 확인
SELECT *
FROM TBL_MEMBER;
--==>> 1	김가영	010-1111-1111

--○ 커밋
COMMIT;
--==>> 커밋완

--○ 자바에서 Test002 클래스 실행 후 다시 확인
SELECT *
FROM TBL_MEMBER;
--==>>
/*
2	김서현	010-2222-2222
1	김가영	010-1111-1111
*/




