package com.hm.minibook.dao;

import com.hm.minibook.Container;
import com.hm.minibook.GV;
import com.hm.minibook.dto.StateDTO;
import com.hm.miniorm.MiniORM;

public class StateSer extends BaseSer<StateDTO>
{

	MiniORM myORM;
	StateDAO stateDAO;

	public StateSer()
	{
		this.myORM = Container.myORMCompanyYear;
		stateDAO = new StateDAO(myORM);

		super.setBaseDAO(stateDAO);
		super.setMyORM(myORM);
	}

	@Override
	boolean checkIsValidCreate(StateDTO state, StringBuilder errorMsg)
	{
		if (!checkIsValid(state, errorMsg))
		{
			return false;
		}

		StateDTO stateFound = stateDAO.retrieveByName(state.getName());
		if (stateFound != null)
		{
			errorMsg.append("Name already exists");
			return false;
		}

		if (GV.regStatus == GV.RegStatus.UNREGISTERED && stateDAO.retrieve().size() >= 25)
		{
			errorMsg.append("You cannot make more than 25 entries in trial version");
			return false;
		}

		return true;
	}

	@Override
	boolean checkIsValidUpdate(StateDTO state, StringBuilder errorMsg)
	{
		if (!checkIsValid(state, errorMsg))
		{
			return false;
		}

		StateDTO stateFound = stateDAO.retrieveByName(state.getName());
		if (stateFound != null && state.getId() != stateFound.getId())
		{
			errorMsg.append("Name already exists");
			return false;
		}
		return true;
	}

	@Override
	boolean checkIsValid(StateDTO state, StringBuilder errorMsg)
	{

		if (state == null)
		{
			errorMsg.append("State dto is null");
			return false;
		}

		if (state.getName() == null || state.getName().equals(""))
		{
			errorMsg.append("Name is required");
			return false;
		}

		return true;
	}
}
