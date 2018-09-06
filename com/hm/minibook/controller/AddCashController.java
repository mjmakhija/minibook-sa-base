package com.hm.minibook.controller;

import com.hm.minibook.Container;

import com.hm.minibook.FXMLLayouts;
import com.hm.minibook.StageHelper;
import com.hm.minibook.dao.CashSer;
import com.hm.minibook.dto.CashDTO;
import com.hm.utilities.Util;
import java.math.BigDecimal;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class AddCashController implements Initializable
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

	boolean modeAdd = true;

	CashDTO cash;
	CashSer cashSer;

	@Override
	public void initialize(URL url, ResourceBundle rb)
	{
		btnSave.setOnAction((event) -> handleClickBtnSave());
		btnCancel.setOnAction((event) -> handleClickBtnCancel());

		Platform.runLater(() -> txtName.requestFocus());

		myInit();
	}

	private void myInit()
	{
		modeAdd = true;

		cashSer = new CashSer();

		cash = new CashDTO();
	}

	public void setData(CashDTO cash)
	{
		this.cash = cash;
		this.modeAdd = false;

		txtName.setText(cash.getName());
		txtOpeningBalance.setText(String.valueOf(cash.getOpeningBalance()));
		txtNote.setText(cash.getNote());
	}

	private boolean checkIsValid()
	{
		return true;
	}

	private void clearBoxes()
	{
		txtName.clear();
		txtOpeningBalance.clear();
		txtNote.clear();

		cash = new CashDTO();

		txtName.requestFocus();
	}

	private void handleClickBtnSave()
	{
		StringBuilder errorMsg = new StringBuilder();
		boolean result;

		if (!checkIsValid(errorMsg))
		{
			StageHelper.showMessageBox(errorMsg.toString());
			return;
		}

		cash.setName(txtName.getText());
		if (!txtOpeningBalance.getText().equals(""))
		{
			cash.setOpeningBalance(BigDecimal.valueOf(Double.parseDouble(txtOpeningBalance.getText())));
		}
		else
		{
			cash.setOpeningBalance(BigDecimal.ZERO);
		}
		cash.setNote(txtNote.getText());

		if (modeAdd)
		{
			result = (cashSer.create(cash, errorMsg));
		}
		else
		{
			result = (cashSer.update(cash, errorMsg));
		}

		if (result)
		{
			StageHelper.showMessageBox("Cash saved successfully");
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
		Container.wrapperController.addPane(StageHelper.getFXMLLoader(FXMLLayouts.LIST_CASH));
	}

	private boolean checkIsValid(StringBuilder errorMsg)
	{
		if (!txtOpeningBalance.getText().equals("") && !Util.isDouble(txtOpeningBalance.getText()))
		{
			errorMsg.append("Invalid opening balance");
			return false;
		}
		return true;
	}

}
