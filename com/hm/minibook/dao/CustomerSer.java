package com.hm.minibook.dao;

import com.hm.minibook.Container;
import com.hm.minibook.GV;
import com.hm.minibook.dto.CustomerDTO;
import com.hm.miniorm.MiniORM;

public class CustomerSer extends BaseSer<CustomerDTO>
{

	MiniORM myORM;
	CustomerDAO customerDAO;

	public CustomerSer()
	{
		this.myORM = Container.myORMCompanyYear;
		customerDAO = new CustomerDAO(myORM);

		super.setBaseDAO(customerDAO);
		super.setMyORM(myORM);
	}

	@Override
	boolean checkIsValidCreate(CustomerDTO customer, StringBuilder errorMsg)
	{
		if (!checkIsValid(customer, errorMsg))
		{
			return false;
		}

		CustomerDTO customerFound = customerDAO.retrieveByName(customer.getName());
		if (customerFound != null)
		{
			errorMsg.append("Name already exists");
			return false;
		}

		if (GV.regStatus == GV.RegStatus.UNREGISTERED && customerDAO.retrieve().size() >= 25)
		{
			errorMsg.append("You cannot make more than 25 entries in trial version");
			return false;
		}

		return true;
	}

	@Override
	boolean checkIsValidUpdate(CustomerDTO customer, StringBuilder errorMsg)
	{
		if (!checkIsValid(customer, errorMsg))
		{
			return false;
		}

		CustomerDTO customerFound = customerDAO.retrieveByName(customer.getName());
		if (customerFound != null && customer.getId() != customerFound.getId())
		{
			errorMsg.append("Name already exists");
			return false;
		}
		return true;
	}

	@Override
	boolean checkIsValid(CustomerDTO customer, StringBuilder errorMsg)
	{

		if (customer == null)
		{
			errorMsg.append("Customer dto is null");
			return false;
		}

		if (customer.getName() == null || customer.getName().equals(""))
		{
			errorMsg.append("Name is required");
			return false;
		}

		return true;
	}
}
