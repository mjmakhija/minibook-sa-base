package com.hm.minibook.dto;

import com.hm.miniorm.Column;
import com.hm.miniorm.Table;

@Table(name = "info")
public class CompanyYearInfoDTO
{

	@Column(name = "id")
	private int id;

	@Column(name = "info_key")
	private String infoKey;

	@Column(name = "info_value")
	private String infoValue;

	public int getId()
	{
		return id;
	}

	public String getInfoKey()
	{
		return infoKey;
	}

	public String getInfoValue()
	{
		return infoValue;
	}

	public void setInfoValue(String infoValue)
	{
		this.infoValue = infoValue;
	}

}
