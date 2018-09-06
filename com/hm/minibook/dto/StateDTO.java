package com.hm.minibook.dto;

import com.hm.miniorm.Column;
import com.hm.miniorm.Table;

@Table(name = "state")
public class StateDTO
{

	@Column(name = "id")
	private int id;

	@Column(name = "name")
	private String name;

	@Column(name = "code_name")
	private String codeName;

	@Column(name = "code_no")
	private String codeNo;

	public int getId()
	{
		return id;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public String getCodeName()
	{
		return codeName;
	}

	public void setCodeName(String codeName)
	{
		this.codeName = codeName;
	}

	public String getCodeNo()
	{
		return codeNo;
	}

	public void setCodeNo(String codeNo)
	{
		this.codeNo = codeNo;
	}

}
