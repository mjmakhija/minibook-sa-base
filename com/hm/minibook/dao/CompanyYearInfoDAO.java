package com.hm.minibook.dao;

import com.hm.minibook.dto.CompanyYearInfoDTO;
import com.hm.miniorm.MiniORM;

class CompanyYearInfoDAO extends BaseDAO<CompanyYearInfoDTO>
{

	String tableName = "info";

	private MiniORM myORM;

	public CompanyYearInfoDAO(MiniORM myORM)
	{
		super(myORM);
		this.myORM = myORM;
	}

	@Override
	String getTableName()
	{
		return tableName;
	}

	@Override
	public boolean delete(CompanyYearInfoDTO objBank)
	{
		throw new UnsupportedOperationException("Not supported yet.");
	}

	@Override
	public boolean create(CompanyYearInfoDTO vObj)
	{
		throw new UnsupportedOperationException("Not supported yet.");
	}

}
