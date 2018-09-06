package com.hm.minibook.controller;

import com.hm.minibook.Container;

import com.hm.minibook.FXMLLayouts;
import com.hm.minibook.StageHelper;
import com.hm.minibook.dao.ExpenseSer;
import com.hm.minibook.dto.ExpenseDTO;
import com.hm.utilities.Util;
import java.math.BigDecimal;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class AddExpenseController implements Initializable
{

	@FXML
	TextField txtName;

	@FXML
	TextField txtOpeningBalance;

	@FXML
	TextField txtNote;

	@FXML
	Button btnSave;

	@FXML
	Button btnCancel;

	ExpenseDTO expense = new ExpenseDTO();
	boolean modeAdd = true;

	@Override
	public void initialize(URL url, ResourceBundle rb)
	{
		btnSave.setOnAction((event) -> handleClickBtnSave());
		btnCancel.setOnAction((event) -> handleClickBtnCancel());

		Platform.runLater(() -> txtName.requestFocus());
	}

	public void setData(ExpenseDTO expense)
	{
		this.expense = expense;
		this.modeAdd = false;
		txtName.setText(expense.getName());
		if (expense.getOpeningBalance() != null)
		{
			txtOpeningBalance.setText(expense.getOpeningBalance().toString());
		}
		txtNote.setText(expense.getNote());
	}

	private boolean checkIsValid(StringBuilder errorMsg)
	{
		if (!txtOpeningBalance.getText().isEmpty() && !Util.isDouble(txtOpeningBalance.getText()))
		{
			errorMsg.append("Invalid opening balance");
			return false;
		}

		return true;
	}

	private void clearBoxes()
	{
		txtName.clear();
		txtOpeningBalance.clear();
		txtNote.clear();

		expense = new ExpenseDTO();

		txtName.requestFocus();
	}

	private void handleClickBtnSave()
	{
		StringBuilder errorMsg = new StringBuilder();

		if (!checkIsValid(errorMsg))
		{
			StageHelper.showMessageBox(errorMsg.toString());
			return;
		}

		expense.setName(txtName.getText());

		if (txtOpeningBalance.getText().isEmpty())
		{
			expense.setOpeningBalance(null);
		}
		else
		{
			expense.setOpeningBalance(new BigDecimal(txtOpeningBalance.getText()));
		}

		expense.setNote(txtNote.getText());

		ExpenseSer expenseSer = new ExpenseSer();
		boolean result;

		if (modeAdd)
		{
			result = (expenseSer.create(expense, errorMsg));
		}
		else
		{
			result = (expenseSer.update(expense, errorMsg));
		}

		if (result)
		{
			StageHelper.showMessageBox("Expense saved successfully");
		}
		else
		{
			StageHelper.showMessageBox(errorMsg.toString());
			return;
		}

		if (modeAdd)
		{
			clearBoxes();
		}
		else
		{
			this.showListForm();
		}

	}

	private void handleClickBtnCancel()
	{
		this.showListForm();
	}

	private void showListForm()
	{
		Container.wrapperController.addPane(StageHelper.getFXMLLoader(FXMLLayouts.LIST_EXPENSE));
	}

}
