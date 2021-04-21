package com.test;

public class ScoreDTO
{
	private String sid, name;	//학생 번호, 이름
	private int kor, eng, mat;	//국영수 점수
	
	// getter / setter 구성
	public String getSid()
	{
		return sid;
	}
	public void setSid(String sid)
	{
		this.sid = sid;
	}
	public String getName()
	{
		return name;
	}
	public void setName(String name)
	{
		this.name = name;
	}
	public int getKor()
	{
		return kor;
	}
	public void setKor(int kor)
	{
		this.kor = kor;
	}
	public int getEng()
	{
		return eng;
	}
	public void setEng(int eng)
	{
		this.eng = eng;
	}
	public int getMat()
	{
		return mat;
	}
	public void setMat(int mat)
	{
		this.mat = mat;
	}

	
}
