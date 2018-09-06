package com.hm.minibook.dto.app;

import com.hm.miniorm.Column;
import com.hm.miniorm.Table;
import java.util.Date;

@Table(name = "year")
public class YearDTO
{

	@Column(name = "id")
	private int id;

	@Column(name = "company_id")
	private int companyId;

	@Column(name = "date_from")
	private Date dateFrom;

	@Column(name = "date_to")
	private Date dateTo;

	public int getId()
	{
		return id;
	}

	public int getCompanyId()
	{
		return companyId;
	}

	public void setCompanyId(int companyId)
	{
		this.companyId = companyId;
	}

	public Date getDateFrom()
	{
		return dateFrom;
	}

	public void setDateFrom(Date dateFrom)
	{
		this.dateFrom = dateFrom;
	}

	public Date getDateTo()
	{
		return dateTo;
	}

	public void setDateTo(Date dateTo)
	{
		this.dateTo = dateTo;
	}

}
