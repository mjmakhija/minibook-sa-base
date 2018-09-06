package com.hm.minibook.dao;

import com.hm.minibook.dto.StateDTO;
import com.hm.miniorm.MiniORM;

class StateDAO extends BaseDAO<StateDTO>
{

	String tableName = "state";

	private MiniORM myORM;

	public StateDAO(MiniORM myORM)
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
