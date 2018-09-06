package com.hm.minibook.dao;

import com.hm.minibook.Container;
import com.hm.minibook.GV;
import com.hm.minibook.dto.AgentDTO;
import com.hm.miniorm.MiniORM;

public class AgentSer extends BaseSer<AgentDTO>
{

	MiniORM myORM;
	AgentDAO agentDAO;

	public AgentSer()
	{
		this.myORM = Container.myORMCompanyYear;
		agentDAO = new AgentDAO(myORM);

		super.setBaseDAO(agentDAO);
		super.setMyORM(myORM);
	}

	@Override
	boolean checkIsValidCreate(AgentDTO agent, StringBuilder errorMsg)
	{
		if (!checkIsValid(agent, errorMsg))
		{
			return false;
		}

		AgentDTO agentFound = agentDAO.retrieveByName(agent.getName());
		if (agentFound != null)
		{
			errorMsg.append("Name already exists");
			return false;
		}

		if (GV.regStatus == GV.RegStatus.UNREGISTERED && agentDAO.retrieve().size() >= 25)
		{
			errorMsg.append("You cannot make more than 25 entries in trial version");
			return false;
		}

		return true;
	}

	@Override
	boolean checkIsValidUpdate(AgentDTO agent, StringBuilder errorMsg)
	{
		if (!checkIsValid(agent, errorMsg))
		{
			return false;
		}

		AgentDTO agentFound = agentDAO.retrieveByName(agent.getName());
		if (agentFound != null && agent.getId() != agentFound.getId())
		{
			errorMsg.append("Name already exists");
			return false;
		}
		return true;
	}

	@Override
	boolean checkIsValid(AgentDTO agent, StringBuilder errorMsg)
	{

		if (agent == null)
		{
			errorMsg.append("Agent dto is null");
			return false;
		}

		if (agent.getName() == null || agent.getName().equals(""))
		{
			errorMsg.append("Name is required");
			return false;
		}

		return true;
	}

}
