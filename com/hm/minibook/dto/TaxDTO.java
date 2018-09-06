package com.hm.minibook.dto;

import com.hm.miniorm.Column;
import com.hm.miniorm.Table;
import java.math.BigDecimal;
import java.util.Objects;

@Table(name = "tax")
public class TaxDTO
{

	@Column(name = "id")
	private int id;

	@Column(name = "name")
	private String name;

	@Column(name = "cgst")
	private BigDecimal cgst;

	@Column(name = "sgst")
	private BigDecimal sgst;

	@Column(name = "igst")
	private BigDecimal igst;

	@Column(name = "note")
	private String note;

	public TaxDTO()
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

	public BigDecimal getCgst()
	{
		return cgst;
	}

	public void setCgst(BigDecimal cgst)
	{
		this.cgst = cgst;
	}

	public BigDecimal getSgst()
	{
		return sgst;
	}

	public void setSgst(BigDecimal sgst)
	{
		this.sgst = sgst;
	}

	public BigDecimal getIgst()
	{
		return igst;
	}

	public void setIgst(BigDecimal igst)
	{
		this.igst = igst;
	}

	public String getNote()
	{
		return note;
	}

	public void setNote(String note)
	{
		this.note = note;
	}

	@Override
	public int hashCode()
	{
		int hash = 7;
		hash = 53 * hash + this.id;
		hash = 53 * hash + Objects.hashCode(this.name);
		hash = 53 * hash + Objects.hashCode(this.cgst);
		hash = 53 * hash + Objects.hashCode(this.sgst);
		hash = 53 * hash + Objects.hashCode(this.igst);
		hash = 53 * hash + Objects.hashCode(this.note);
		return hash;
	}

	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
		{
			return true;
		}
		if (obj == null)
		{
			return false;
		}
		if (getClass() != obj.getClass())
		{
			return false;
		}
		final TaxDTO other = (TaxDTO) obj;
		if (this.id != other.id)
		{
			return false;
		}
		if (!Objects.equals(this.name, other.name))
		{
			return false;
		}
		if (!Objects.equals(this.note, other.note))
		{
			return false;
		}
		if (!Objects.equals(this.cgst, other.cgst))
		{
			return false;
		}
		if (!Objects.equals(this.sgst, other.sgst))
		{
			return false;
		}
		if (!Objects.equals(this.igst, other.igst))
		{
			return false;
		}
		return true;
	}

}
