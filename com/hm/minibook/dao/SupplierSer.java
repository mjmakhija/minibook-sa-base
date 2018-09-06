package com.hm.minibook.dao;

import com.hm.minibook.Container;
import com.hm.minibook.GV;
import com.hm.minibook.dto.SupplierDTO;
import com.hm.miniorm.MiniORM;

public class SupplierSer extends BaseSer<SupplierDTO>
{

	MiniORM myORM;
	SupplierDAO supplierDAO;

	public SupplierSer()
	{
		this.myORM = Container.myORMCompanyYear;
		supplierDAO = new SupplierDAO(myORM);

		super.setBaseDAO(supplierDAO);
		super.setMyORM(myORM);
	}

	@Override
	boolean checkIsValidCreate(SupplierDTO supplier, StringBuilder errorMsg)
	{
		if (!checkIsValid(supplier, errorMsg))
		{
			return false;
		}

		SupplierDTO supplierFound = supplierDAO.retrieveByName(supplier.getName());
		if (supplierFound != null)
		{
			errorMsg.append("Name already exists");
			return false;
		}

		if (GV.regStatus == GV.RegStatus.UNREGISTERED && supplierDAO.retrieve().size() >= 25)
		{
			errorMsg.append("You cannot make more than 25 entries in trial version");
			return false;
		}

		return true;
	}

	@Override
	boolean checkIsValidUpdate(SupplierDTO supplier, StringBuilder errorMsg)
	{
		if (!checkIsValid(supplier, errorMsg))
		{
			return false;
		}

		SupplierDTO supplierFound = supplierDAO.retrieveByName(supplier.getName());
		if (supplierFound != null && supplier.getId() != supplierFound.getId())
		{
			errorMsg.append("Name already exists");
			return false;
		}
		return true;
	}

	@Override
	boolean checkIsValid(SupplierDTO supplier, StringBuilder errorMsg)
	{

		if (supplier == null)
		{
			errorMsg.append("Supplier dto is null");
			return false;
		}

		if (supplier.getName() == null || supplier.getName().equals(""))
		{
			errorMsg.append("Name is required");
			return false;
		}

		return true;
	}
}
