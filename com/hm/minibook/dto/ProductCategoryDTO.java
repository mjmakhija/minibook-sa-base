package com.hm.minibook.dto;

import com.hm.miniorm.Column;
import com.hm.miniorm.Table;
import java.util.Objects;

@Table(name = "product_category")
public class ProductCategoryDTO
{

	@Column(name = "id")
	private int id;
	@Column(name = "name")
	private String name;
	@Column(name = "note")
	private String note;

	public ProductCategoryDTO()
	{
	}

	public ProductCategoryDTO(String name, String note)
	{
		this.name = name;
		this.note = note;
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
		int hash = 3;
		hash = 97 * hash + this.id;
		hash = 97 * hash + Objects.hashCode(this.name);
		hash = 97 * hash + Objects.hashCode(this.note);
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
		final ProductCategoryDTO other = (ProductCategoryDTO) obj;
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
		return true;
	}

}
