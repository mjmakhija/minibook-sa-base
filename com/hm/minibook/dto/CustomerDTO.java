package com.hm.minibook.dto;

import com.hm.miniorm.Column;
import com.hm.miniorm.Table;
import java.math.BigDecimal;

@Table(name = "customer")
public class CustomerDTO
{

	@Column(name = "id")
	private int id;

	@Column(name = "name")
	private String name;

	@Column(name = "address")
	private String address;

	@Column(name = "city")
	private String city;

	@Column(name = "state_id")
	private int stateId;

	@Column(name = "pin")
	private String pin;

	@Column(name = "contact_no")
	private String contactNo;

	@Column(name = "email")
	private String email;

	@Column(name = "gst_no")
	private String gstNo;

	@Column(name = "opening_balance")
	private BigDecimal openingBalance;

	@Column(name = "note")
	private String note;

	public CustomerDTO()
	{
	}

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

	public String getAddress()
	{
		return address;
	}

	public void setAddress(String address)
	{
		this.address = address;
	}

	public String getCity()
	{
		return city;
	}

	public void setCity(String city)
	{
		this.city = city;
	}

	public int getStateId()
	{
		return stateId;
	}

	public void setStateId(int stateId)
	{
		this.stateId = stateId;
	}

	public String getPin()
	{
		return pin;
	}

	public void setPin(String pin)
	{
		this.pin = pin;
	}

	public String getContactNo()
	{
		return contactNo;
	}

	public void setContactNo(String contactNo)
	{
		this.contactNo = contactNo;
	}

	public String getEmail()
	{
		return email;
	}

	public void setEmail(String email)
	{
		this.email = email;
	}

	public String getGstNo()
	{
		return gstNo;
	}

	public void setGstNo(String gstNo)
	{
		this.gstNo = gstNo;
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
