package com.hm.minibook.dao;

import com.hm.minibook.Container;
import com.hm.minibook.GV;
import com.hm.minibook.dto.ExpenseDTO;
import com.hm.miniorm.MiniORM;

public class ExpenseSer extends BaseSer<ExpenseDTO>
{

	MiniORM myORM;
	ExpenseDAO expenseDAO;

	public ExpenseSer()
	{
		this.myORM = Container.myORMCompanyYear;
		expenseDAO = new ExpenseDAO(myORM);

		super.setBaseDAO(expenseDAO);
		super.setMyORM(myORM);
	}

	@Override
	boolean checkIsValidCreate(ExpenseDTO expense, StringBuilder errorMsg)
	{
		if (!checkIsValid(expense, errorMsg))
		{
			return false;
		}

		ExpenseDTO expenseFound = expenseDAO.retrieveByName(expense.getName());
		if (expenseFound != null)
		{
			errorMsg.append("Name already exists");
			return false;
		}

		if (GV.regStatus == GV.RegStatus.UNREGISTERED && expenseDAO.retrieve().size() >= 25)
		{
			errorMsg.append("You cannot make more than 25 entries in trial version");
			return false;
		}

		return true;
	}

	@Override
	boolean checkIsValidUpdate(ExpenseDTO expense, StringBuilder errorMsg)
	{
		if (!checkIsValid(expense, errorMsg))
		{
			return false;
		}

		ExpenseDTO expenseFound = expenseDAO.retrieveByName(expense.getName());
		if (expenseFound != null && expense.getId() != expenseFound.getId())
		{
			errorMsg.append("Name already exists");
			return false;
		}
		return true;
	}

	@Override
	boolean checkIsValid(ExpenseDTO expense, StringBuilder errorMsg)
	{

		if (expense == null)
		{
			errorMsg.append("Expense dto is null");
			return false;
		}

		if (expense.getName() == null || expense.getName().equals(""))
		{
			errorMsg.append("Name is required");
			return false;
		}

		return true;
	}
}
