package com.hm.minibook.dao;

import com.hm.minibook.Container;
import com.hm.minibook.GV;
import com.hm.minibook.dto.TaxDTO;
import com.hm.miniorm.MiniORM;

public class TaxSer extends BaseSer<TaxDTO>
{

	MiniORM myORM;
	TaxDAO taxDAO;

	public TaxSer()
	{
		this.myORM = Container.myORMCompanyYear;
		taxDAO = new TaxDAO(myORM);

		super.setBaseDAO(taxDAO);
		super.setMyORM(myORM);
	}

	@Override
	boolean checkIsValidCreate(TaxDTO tax, StringBuilder errorMsg)
	{
		if (!checkIsValid(tax, errorMsg))
		{
			return false;
		}

		TaxDTO taxFound = taxDAO.retrieveByName(tax.getName());
		if (taxFound != null)
		{
			errorMsg.append("Name already exists");
			return false;
		}

		if (GV.regStatus == GV.RegStatus.UNREGISTERED && taxDAO.retrieve().size() >= 25)
		{
			errorMsg.append("You cannot make more than 25 entries in trial version");
			return false;
		}

		return true;
	}

	@Override
	boolean checkIsValidUpdate(TaxDTO tax, StringBuilder errorMsg)
	{
		if (!checkIsValid(tax, errorMsg))
		{
			return false;
		}

		TaxDTO taxFound = taxDAO.retrieveByName(tax.getName());
		if (taxFound != null && tax.getId() != taxFound.getId())
		{
			errorMsg.append("Name already exists");
			return false;
		}
		return true;
	}

	@Override
	boolean checkIsValid(TaxDTO tax, StringBuilder errorMsg)
	{

		if (tax == null)
		{
			errorMsg.append("Tax dto is null");
			return false;
		}

		if (tax.getName() == null || tax.getName().equals(""))
		{
			errorMsg.append("Name is required");
			return false;
		}

		return true;
	}
}
