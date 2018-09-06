package com.hm.minibook.dao;

import com.hm.minibook.Container;
import com.hm.minibook.GV;
import com.hm.minibook.dto.BankDTO;
import com.hm.miniorm.MiniORM;

public class BankSer extends BaseSer<BankDTO>
{

	MiniORM myORM;
	BankDAO bankDAO;

	public BankSer()
	{
		this.myORM = Container.myORMCompanyYear;
		bankDAO = new BankDAO(myORM);

		super.setBaseDAO(bankDAO);
		super.setMyORM(myORM);
	}

	@Override
	boolean checkIsValidCreate(BankDTO bank, StringBuilder errorMsg)
	{
		if (!checkIsValid(bank, errorMsg))
		{
			return false;
		}

		BankDTO bankFound = bankDAO.retrieveByName(bank.getName());
		if (bankFound != null)
		{
			errorMsg.append("Name already exists");
			return false;
		}

		if (GV.regStatus == GV.RegStatus.UNREGISTERED && bankDAO.retrieve().size() >= 25)
		{
			errorMsg.append("You cannot make more than 25 entries in trial version");
			return false;
		}

		return true;
	}

	@Override
	boolean checkIsValidUpdate(BankDTO bank, StringBuilder errorMsg)
	{
		if (!checkIsValid(bank, errorMsg))
		{
			return false;
		}

		BankDTO bankFound = bankDAO.retrieveByName(bank.getName());
		if (bankFound != null && bank.getId() != bankFound.getId())
		{
			errorMsg.append("Name already exists");
			return false;
		}
		return true;
	}

	@Override
	boolean checkIsValid(BankDTO bank, StringBuilder errorMsg)
	{

		if (bank == null)
		{
			errorMsg.append("Bank dto is null");
			return false;
		}

		if (bank.getName() == null || bank.getName().equals(""))
		{
			errorMsg.append("Name is required");
			return false;
		}

		return true;
	}
}
