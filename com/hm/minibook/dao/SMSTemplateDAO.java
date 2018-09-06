package com.hm.minibook.dao;

import com.hm.minibook.dto.SMSTemplateDTO;
import com.hm.miniorm.MiniORM;

class SMSTemplateDAO extends BaseDAO<SMSTemplateDTO>
{

	String tableName = "sms_template";

	private MiniORM myORM;

	public SMSTemplateDAO(MiniORM myORM)
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
