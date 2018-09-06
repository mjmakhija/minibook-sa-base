package com.hm.minibook.dao;

import com.hm.minibook.Container;
import com.hm.minibook.GV;
import com.hm.minibook.dto.SMSTemplateDTO;
import com.hm.miniorm.MiniORM;

public class SMSTemplateSer extends BaseSer<SMSTemplateDTO>
{

	MiniORM myORM;
	SMSTemplateDAO smstemplateDAO;

	public SMSTemplateSer()
	{
		this.myORM = Container.myORMCompanyYear;
		smstemplateDAO = new SMSTemplateDAO(myORM);

		super.setBaseDAO(smstemplateDAO);
		super.setMyORM(myORM);
	}

	@Override
	boolean checkIsValidCreate(SMSTemplateDTO smstemplate, StringBuilder errorMsg)
	{
		if (!checkIsValid(smstemplate, errorMsg))
		{
			return false;
		}

		SMSTemplateDTO smstemplateFound = smstemplateDAO.retrieveByName(smstemplate.getName());
		if (smstemplateFound != null)
		{
			errorMsg.append("Name already exists");
			return false;
		}

		if (GV.regStatus == GV.RegStatus.UNREGISTERED && smstemplateDAO.retrieve().size() >= 25)
		{
			errorMsg.append("You cannot make more than 25 entries in trial version");
			return false;
		}

		return true;
	}

	@Override
	boolean checkIsValidUpdate(SMSTemplateDTO smstemplate, StringBuilder errorMsg)
	{
		if (!checkIsValid(smstemplate, errorMsg))
		{
			return false;
		}

		SMSTemplateDTO smstemplateFound = smstemplateDAO.retrieveByName(smstemplate.getName());
		if (smstemplateFound != null && smstemplate.getId() != smstemplateFound.getId())
		{
			errorMsg.append("Name already exists");
			return false;
		}
		return true;
	}

	@Override
	boolean checkIsValid(SMSTemplateDTO smstemplate, StringBuilder errorMsg)
	{

		if (smstemplate == null)
		{
			errorMsg.append("SMSTemplate dto is null");
			return false;
		}

		if (smstemplate.getName() == null || smstemplate.getName().equals(""))
		{
			errorMsg.append("Name is required");
			return false;
		}

		return true;
	}
}
