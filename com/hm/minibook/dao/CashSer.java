package com.hm.minibook.dao;

import com.hm.minibook.Container;
import com.hm.minibook.GV;
import com.hm.minibook.dto.CashDTO;
import com.hm.miniorm.MiniORM;

public class CashSer extends BaseSer<CashDTO>
{

	MiniORM myORM;
	CashDAO cashDAO;

	public CashSer()
	{
		this.myORM = Container.myORMCompanyYear;
		cashDAO = new CashDAO(myORM);

		super.setBaseDAO(cashDAO);
		super.setMyORM(myORM);
	}

	@Override
	boolean checkIsValidCreate(CashDTO cash, StringBuilder errorMsg)
	{
		if (!checkIsValid(cash, errorMsg))
		{
			return false;
		}

		CashDTO cashFound = cashDAO.retrieveByName(cash.getName());
		if (cashFound != null)
		{
			errorMsg.append("Name already exists");
			return false;
		}

		if (GV.regStatus == GV.RegStatus.UNREGISTERED && cashDAO.retrieve().size() >= 25)
		{
			errorMsg.append("You cannot make more than 25 entries in trial version");
			return false;
		}

		return true;
	}

	@Override
	boolean checkIsValidUpdate(CashDTO cash, StringBuilder errorMsg)
	{
		if (!checkIsValid(cash, errorMsg))
		{
			return false;
		}

		CashDTO cashFound = cashDAO.retrieveByName(cash.getName());
		if (cashFound != null && cash.getId() != cashFound.getId())
		{
			errorMsg.append("Name already exists");
			return false;
		}
		return true;
	}

	@Override
	boolean checkIsValid(CashDTO cash, StringBuilder errorMsg)
	{

		if (cash == null)
		{
			errorMsg.append("Cash dto is null");
			return false;
		}

		if (cash.getName() == null || cash.getName().equals(""))
		{
			errorMsg.append("Name is required");
			return false;
		}

		return true;
	}
}
