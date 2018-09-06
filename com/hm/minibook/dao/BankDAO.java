package com.hm.minibook.dao;

import com.hm.minibook.dto.BankDTO;
import com.hm.miniorm.MiniORM;

class BankDAO extends BaseDAO<BankDTO>
{

	String tableName = "bank";

	private MiniORM myORM;

	public BankDAO(MiniORM myORM)
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
