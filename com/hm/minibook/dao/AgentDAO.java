package com.hm.minibook.dao;

import com.hm.minibook.dto.AgentDTO;
import com.hm.miniorm.MiniORM;

class AgentDAO extends BaseDAO<AgentDTO>
{

	String tableName = "agent";

	private MiniORM myORM;

	public AgentDAO(MiniORM myORM)
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
