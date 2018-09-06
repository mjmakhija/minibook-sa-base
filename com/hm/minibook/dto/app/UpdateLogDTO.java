package com.hm.minibook.dto.app;

import com.hm.miniorm.Column;
import com.hm.miniorm.Table;
import java.util.Date;

@Table(name = "update_log")
public class UpdateLogDTO
{

	@Column(name = "id")
	private int id;

	@Column(name = "version")
	private String version;

	@Column(name = "status_id")
	private int statusId;

	@Column(name = "path")
	private String path;

	@Column(name = "created_at")
	private Date createdAt;

	@Column(name = "updated_at")
	private Date updatedAt;

	public int getId()
	{
		return id;
	}

	public String getVersion()
	{
		return version;
	}

	public void setVersion(String version)
	{
		this.version = version;
	}

	public int getStatusId()
	{
		return statusId;
	}

	public void setStatusId(int statusId)
	{
		this.statusId = statusId;
	}

	public String getPath()
	{
		return path;
	}

	public void setPath(String path)
	{
		this.path = path;
	}

	public Date getCreatedAt()
	{
		return createdAt;
	}

	public void setCreatedAt(Date createdAt)
	{
		this.createdAt = createdAt;
	}

	public Date getUpdatedAt()
	{
		return updatedAt;
	}

	public void setUpdatedAt(Date updatedAt)
	{
		this.updatedAt = updatedAt;
	}

}
