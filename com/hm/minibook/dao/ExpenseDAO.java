package com.hm.minibook.dao;

import com.hm.minibook.dto.ExpenseDTO;
import com.hm.miniorm.MiniORM;

class ExpenseDAO extends BaseDAO<ExpenseDTO>
{

	String tableName = "expense";

	private MiniORM myORM;

	public ExpenseDAO(MiniORM myORM)
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
