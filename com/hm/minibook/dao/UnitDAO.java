package com.hm.minibook.dao;

import com.hm.minibook.dto.UnitDTO;
import com.hm.miniorm.MiniORM;

class UnitDAO extends BaseDAO<UnitDTO>
{

	String tableName = "unit";

	private MiniORM myORM;

	public UnitDAO(MiniORM myORM)
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
