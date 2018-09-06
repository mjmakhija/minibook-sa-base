package com.hm.minibook.dao;

import com.hm.minibook.dto.CustomerDTO;
import com.hm.miniorm.MiniORM;

class CustomerDAO extends BaseDAO<CustomerDTO>
{

	String tableName = "customer";

	private MiniORM myORM;

	public CustomerDAO(MiniORM myORM)
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
