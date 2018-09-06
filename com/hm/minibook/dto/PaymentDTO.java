package com.hm.minibook.dto;

import com.hm.miniorm.Column;
import com.hm.miniorm.Table;
import java.math.BigDecimal;
import java.util.Date;

@Table(name = "payment")
public class PaymentDTO
{

	public enum FromType
	{
		BANK(1, "BANK"),
		CASH(2, "CASH");

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
		SUPPLIER(1, "SUPPLIER"),
		EXPENSE(2, "EXPENSE"),
		CUSTOMER(3, "CUSTOMER");

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

	//private PaymentInternalDTO payment;
	// 
	@Column(name = "id")
	private int id;

	@Column(name = "from_type_id")
	private int fromTypeId;

	@Column(name = "bank_id")
	private Integer bankId;

	@Column(name = "cash_id")
	private Integer cashId;

	@Column(name = "to_type_id")
	private int toTypeId;

	@Column(name = "supplier_id")
	private int supplierId;

	@Column(name = "note")
	private String note;

	@Column(name = "date")
	private Date date;

	@Column(name = "amount")
	private BigDecimal amount;

	public int getId()
	{
		return id;
	}

	public FromType getFromType()
	{
		for (FromType fromType : FromType.values())
		{
			if (fromType.getId() == toTypeId)
			{
				return fromType;
			}
		}
		return null;
	}

	public void setFromType(FromType fromType)
	{
		fromTypeId = fromType.id;
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

	public ToType getToType()
	{
		for (ToType toType : ToType.values())
		{
			if (toType.getId() == toTypeId)
			{
				return toType;
			}
		}
		return null;
	}

	public void setToType(ToType toType)
	{
		toTypeId = toType.id;
	}

	public int getSupplierId()
	{
		return supplierId;
	}

	public void setSupplierId(int supplierId)
	{
		this.supplierId = supplierId;
	}

	public String getNote()
	{
		return note;
	}

	public void setNote(String note)
	{
		this.note = note;
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

}
