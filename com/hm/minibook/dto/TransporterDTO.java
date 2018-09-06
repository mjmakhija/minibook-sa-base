package com.hm.minibook.dto;

import com.hm.miniorm.Column;
import com.hm.miniorm.Table;
import java.util.Objects;

@Table(name = "transporter")
public class TransporterDTO
{

	@Column(name = "id")
	private int id;

	@Column(name = "name")
	private String name;

	@Column(name = "transporter_id")
	private String transporterId;

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

	public String getTransporterId()
	{
		return transporterId;
	}

	public void setTransporterId(String transporterId)
	{
		this.transporterId = transporterId;
	}

	@Override
	public int hashCode()
	{
		int hash = 5;
		hash = 43 * hash + this.id;
		hash = 43 * hash + Objects.hashCode(this.name);
		hash = 43 * hash + Objects.hashCode(this.transporterId);
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
		final TransporterDTO other = (TransporterDTO) obj;
		if (this.id != other.id)
		{
			return false;
		}
		if (!Objects.equals(this.name, other.name))
		{
			return false;
		}
		if (!Objects.equals(this.transporterId, other.transporterId))
		{
			return false;
		}
		return true;
	}

}
