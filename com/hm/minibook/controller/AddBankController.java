package com.hm.minibook.controller;

import com.hm.minibook.Container;

import com.hm.minibook.FXMLLayouts;
import com.hm.minibook.StageHelper;
import com.hm.minibook.dao.BankSer;
import com.hm.minibook.dto.BankDTO;
import com.hm.utilities.Util;
import java.math.BigDecimal;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class AddBankController implements Initializable
{

	@FXML
	TextField txtName;

	@FXML
	TextField txtAddress;

	@FXML
	TextField txtContactNo;

	@FXML
	TextField txtIFSC;

	@FXML
	TextField txtBranch;

	@FXML
	TextField txtOpeningBalance;

	@FXML
	TextField txtNote;

	@FXML
	Button btnSave;

	@FXML
	Button btnCancel;

	boolean modeAdd = true;

	BankDTO bank;
	BankSer bankSer;

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

		bankSer = new BankSer();

		bank = new BankDTO();
	}

	public void setData(BankDTO bank)
	{
		this.modeAdd = false;
		this.bank = bank;

		txtName.setText(bank.getName());
		txtAddress.setText(bank.getAddress());
		txtContactNo.setText(bank.getContactNo());
		txtIFSC.setText(bank.getIfsc());
		txtBranch.setText(bank.getBranch());
		txtOpeningBalance.setText(String.valueOf(bank.getOpeningBalance()));
		txtNote.setText(bank.getNote());
	}

	private void clearBoxes()
	{
		txtName.clear();
		txtAddress.clear();
		txtContactNo.clear();
		txtIFSC.clear();
		txtBranch.clear();
		txtOpeningBalance.clear();
		txtNote.clear();

		bank = new BankDTO();

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

		bank.setName(txtName.getText());
		bank.setAddress(txtAddress.getText());
		bank.setContactNo(txtContactNo.getText());
		bank.setIfsc(txtIFSC.getText());
		bank.setBranch(txtBranch.getText());
		if (!txtOpeningBalance.getText().equals(""))
		{
			bank.setOpeningBalance(BigDecimal.valueOf(Double.parseDouble(txtOpeningBalance.getText())));
		}
		else
		{
			bank.setOpeningBalance(BigDecimal.ZERO);
		}
		bank.setNote(txtNote.getText());

		if (modeAdd)
		{
			result = (bankSer.create(bank, errorMsg));
		}
		else
		{
			result = (bankSer.update(bank, errorMsg));
		}

		if (result)
		{
			StageHelper.showMessageBox("Bank saved successfully");
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
		Container.wrapperController.addPane(StageHelper.getFXMLLoader(FXMLLayouts.LIST_BANK));
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
