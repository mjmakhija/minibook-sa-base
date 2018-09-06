package com.hm.minibook.dao;

import com.hm.minibook.dto.TaxDTO;
import com.hm.miniorm.MiniORM;

class TaxDAO extends BaseDAO<TaxDTO>
{

	String tableName = "tax";

	private MiniORM myORM;

	public TaxDAO(MiniORM myORM)
	{
		super(myORM);
		this.myORM = myORM;
	}

	@Override
	String getTableName()
	{
		return tableName;
	}

}
