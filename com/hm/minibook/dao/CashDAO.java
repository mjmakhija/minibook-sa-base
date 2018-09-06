package com.hm.minibook.dao;

import com.hm.minibook.dto.CashDTO;
import com.hm.miniorm.MiniORM;

class CashDAO extends BaseDAO<CashDTO>
{

	String tableName = "cash";

	private MiniORM myORM;

	public CashDAO(MiniORM myORM)
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
