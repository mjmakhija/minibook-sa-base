package com.hm.minibook.dao;

import com.hm.minibook.Container;
import com.hm.minibook.GV;
import com.hm.minibook.dto.TransporterDTO;
import com.hm.miniorm.MiniORM;

public class TransporterSer extends BaseSer<TransporterDTO>
{

	MiniORM myORM;
	TransporterDAO transporterDAO;

	public TransporterSer()
	{
		this.myORM = Container.myORMCompanyYear;
		transporterDAO = new TransporterDAO(myORM);

		super.setBaseDAO(transporterDAO);
		super.setMyORM(myORM);
	}

	@Override
	boolean checkIsValidCreate(TransporterDTO transporter, StringBuilder errorMsg)
	{
		if (!checkIsValid(transporter, errorMsg))
		{
			return false;
		}

		TransporterDTO transporterFound = transporterDAO.retrieveByName(transporter.getName());
		if (transporterFound != null)
		{
			errorMsg.append("Name already exists");
			return false;
		}

		if (GV.regStatus == GV.RegStatus.UNREGISTERED && transporterDAO.retrieve().size() >= 25)
		{
			errorMsg.append("You cannot make more than 25 entries in trial version");
			return false;
		}

		return true;
	}

	@Override
	boolean checkIsValidUpdate(TransporterDTO transporter, StringBuilder errorMsg)
	{
		if (!checkIsValid(transporter, errorMsg))
		{
			return false;
		}

		TransporterDTO transporterFound = transporterDAO.retrieveByName(transporter.getName());
		if (transporterFound != null && transporter.getId() != transporterFound.getId())
		{
			errorMsg.append("Name already exists");
			return false;
		}
		return true;
	}

	@Override
	boolean checkIsValid(TransporterDTO transporter, StringBuilder errorMsg)
	{

		if (transporter == null)
		{
			errorMsg.append("Transporter dto is null");
			return false;
		}

		if (transporter.getName() == null || transporter.getName().equals(""))
		{
			errorMsg.append("Name is required");
			return false;
		}

		return true;
	}
}
