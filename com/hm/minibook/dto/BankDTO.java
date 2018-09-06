package com.hm.minibook.dto;

import com.hm.miniorm.Column;
import com.hm.miniorm.Table;
import java.math.BigDecimal;

@Table(name = "bank")
public class BankDTO
{

	@Column(name = "id")
	private int id;

	@Column(name = "name")
	private String name;

	@Column(name = "address")
	private String address;

	@Column(name = "contact_no")
	private String contactNo;

	@Column(name = "ifsc")
	private String ifsc;

	@Column(name = "branch")
	private String branch;

	@Column(name = "opening_balance")
	private BigDecimal openingBalance;

	@Column(name = "note")
	private String note;

	public BankDTO()
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

	public String getAddress()
	{
		return address;
	}

	public void setAddress(String address)
	{
		this.address = address;
	}

	public String getContactNo()
	{
		return contactNo;
	}

	public void setContactNo(String contactNo)
	{
		this.contactNo = contactNo;
	}

	public String getIfsc()
	{
		return ifsc;
	}

	public void setIfsc(String ifsc)
	{
		this.ifsc = ifsc;
	}

	public String getBranch()
	{
		return branch;
	}

	public void setBranch(String branch)
	{
		this.branch = branch;
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
