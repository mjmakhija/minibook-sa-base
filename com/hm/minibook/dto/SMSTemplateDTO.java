package com.hm.minibook.dto;

import com.hm.miniorm.Column;
import com.hm.miniorm.Table;
import java.util.Date;

@Table(name = "sms_template")
public class SMSTemplateDTO
{

	@Column(name = "id")
	private int id;

	@Column(name = "name")
	private String name;

	@Column(name = "message")
	private String message;

	@Column(name = "create_at")
	private Date createdAt;

	@Column(name = "updated_at")
	private Date updatedAt;

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

	public String getMessage()
	{
		return message;
	}

	public void setMessage(String message)
	{
		this.message = message;
	}

	public Date getCreatedAt()
	{
		return createdAt;
	}

	public Date getUpdatedAt()
	{
		return updatedAt;
	}

}
