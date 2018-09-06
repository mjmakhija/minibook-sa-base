package com.hm.minibook.dto.app;

import com.hm.miniorm.Column;
import com.hm.miniorm.Table;

@Table(name = "company")
public class CompanyDTO
{

	@Column(name = "id")
	private int id;

	@Column(name = "name")
	private String name;

	public CompanyDTO()
	{
	}

	public CompanyDTO(String name)
	{
		this.name = name;
	}

	public Integer getId()
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

}
