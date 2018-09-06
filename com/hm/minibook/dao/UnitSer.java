package com.hm.minibook.dao;

import com.hm.minibook.Container;
import com.hm.minibook.GV;
import com.hm.minibook.dto.UnitDTO;
import com.hm.miniorm.MiniORM;

public class UnitSer extends BaseSer<UnitDTO>
{

	MiniORM myORM;
	UnitDAO unitDAO;

	public UnitSer()
	{
		this.myORM = Container.myORMCompanyYear;
		unitDAO = new UnitDAO(myORM);

		super.setBaseDAO(unitDAO);
		super.setMyORM(myORM);
	}

	@Override
	boolean checkIsValidCreate(UnitDTO unit, StringBuilder errorMsg)
	{
		if (!checkIsValid(unit, errorMsg))
		{
			return false;
		}

		UnitDTO unitFound = unitDAO.retrieveByName(unit.getName());
		if (unitFound != null)
		{
			errorMsg.append("Name already exists");
			return false;
		}

		if (GV.regStatus == GV.RegStatus.UNREGISTERED && unitDAO.retrieve().size() >= 25)
		{
			errorMsg.append("You cannot make more than 25 entries in trial version");
			return false;
		}

		return true;
	}

	@Override
	boolean checkIsValidUpdate(UnitDTO unit, StringBuilder errorMsg)
	{
		if (!checkIsValid(unit, errorMsg))
		{
			return false;
		}

		UnitDTO unitFound = unitDAO.retrieveByName(unit.getName());
		if (unitFound != null && unit.getId() != unitFound.getId())
		{
			errorMsg.append("Name already exists");
			return false;
		}
		return true;
	}

	@Override
	boolean checkIsValid(UnitDTO unit, StringBuilder errorMsg)
	{

		if (unit == null)
		{
			errorMsg.append("Unit dto is null");
			return false;
		}

		if (unit.getName() == null || unit.getName().equals(""))
		{
			errorMsg.append("Name is required");
			return false;
		}

		return true;
	}
}
