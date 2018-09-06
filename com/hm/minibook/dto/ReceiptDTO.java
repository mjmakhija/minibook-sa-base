package com.hm.minibook.dto;

import com.hm.miniorm.Column;
import com.hm.miniorm.Table;
import java.math.BigDecimal;
import java.util.Date;

@Table(name = "receipt")
public class ReceiptDTO
{

	public enum FromType
	{
		CUSTOMER(1, "CUSTOMER"),
		SUPPLIER(2, "SUPPLIER");

		private final int id;
		private final String name;

		FromType(final int newValue, final String nameString)
		{
			id = newValue;
			name = nameString;
		}

		public int getId()
		{
			return id;
		}

		public String getName()
		{
			return name;
		}

	}

	public enum ToType
	{

		BANK(1, "BANK"),
		CASH(2, "CASH");

		private final int id;
		private final String name;

		ToType(final int newValue, final String nameString)
		{
			id = newValue;
			name = nameString;
		}

		public int getId()
		{
			return id;
		}

		public String getName()
		{
			return name;
		}

	}

	@Column(name = "id")
	private int id;

	@Column(name = "from_type_id")
	private int fromTypeId;

	@Column(name = "customer_id")
	private int customerId;

	@Column(name = "date")
	private Date date;

	@Column(name = "amount")
	private BigDecimal amount;

	@Column(name = "to_type_id")
	private int toTypeId;

	@Column(name = "bank_id")
	private Integer bankId;

	@Column(name = "cash_id")
	private Integer cashId;

	@Column(name = "note")
	private String note;

	public ReceiptDTO()
	{
	}

	public int getId()
	{
		return id;
	}

	public int getFromTypeId()
	{
		return fromTypeId;
	}

	public void setFromTypeId(int fromTypeId)
	{
		this.fromTypeId = fromTypeId;
	}

	public int getCustomerId()
	{
		return customerId;
	}

	public void setCustomerId(int customerId)
	{
		this.customerId = customerId;
	}

	public Date getDate()
	{
		return date;
	}

	public void setDate(Date date)
	{
		this.date = date;
	}

	public BigDecimal getAmount()
	{
		return amount;
	}

	public void setAmount(BigDecimal amount)
	{
		this.amount = amount;
	}

	public int getToTypeId()
	{
		return toTypeId;
	}

	public void setToTypeId(int toTypeId)
	{
		this.toTypeId = toTypeId;
	}

	public Integer getBankId()
	{
		return bankId;
	}

	public void setBankId(Integer bankId)
	{
		this.bankId = bankId;
	}

	public Integer getCashId()
	{
		return cashId;
	}

	public void setCashId(Integer cashId)
	{
		this.cashId = cashId;
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
