package com.hm.minibook.dao;

import com.hm.minibook.dto.SupplierDTO;
import com.hm.miniorm.MiniORM;

class SupplierDAO extends BaseDAO<SupplierDTO>
{

	String tableName = "supplier";

	private MiniORM myORM;

	public SupplierDAO(MiniORM myORM)
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
