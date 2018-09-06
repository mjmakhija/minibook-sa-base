package com.hm.minibook.dto;

import com.hm.miniorm.Column;
import com.hm.miniorm.Table;
import java.math.BigDecimal;

@Table(name = "expense")
public class ExpenseDTO
{

	@Column(name = "id")
	private int id;

	@Column(name = "name")
	private String name;

	@Column(name = "opening_balance")
	private BigDecimal openingBalance;

	@Column(name = "note")
	private String note;

	public ExpenseDTO()
	{
	}

	public int getId()
	{
		return id;
	}

	public void setId(int id)
	{
		this.id = id;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public BigDecimal getOpeningBalance()
	{
		return openingBalance;
	}

	public void setOpeningBalance(BigDecimal openingBalance)
	{
		this.openingBalance = openingBalance;
	}

	public String getNote()
	{
		return note;
	}

	public void setNote(String note)
	{
		this.note = note;
	}

}
