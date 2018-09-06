package com.hm.minibook.dao;

import com.hm.minibook.dto.TransporterDTO;
import com.hm.miniorm.MiniORM;

class TransporterDAO extends BaseDAO<TransporterDTO>
{

	String tableName = "transporter";

	private MiniORM myORM;

	public TransporterDAO(MiniORM myORM)
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
