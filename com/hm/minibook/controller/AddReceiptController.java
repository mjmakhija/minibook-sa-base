package com.hm.minibook.controller;

import com.hm.minibook.CommonUIActions;
import com.hm.minibook.Container;

import com.hm.minibook.FXMLLayouts;
import com.hm.minibook.StageHelper;
import com.hm.minibook.dao.ReceiptSer;
import com.hm.minibook.dto.ReceiptDTO;
import com.hm.minibook.dao.BankSer;
import com.hm.minibook.dao.CashSer;
import com.hm.minibook.dao.CustomerSer;
import com.hm.minibook.dto.BankDTO;
import com.hm.minibook.dto.CashDTO;
import com.hm.minibook.dto.CustomerDTO;
import com.hm.utilities.ComboBoxAutoComplete;
import com.hm.utilities.DatePickerHelper;
import com.hm.utilities.Util;
import java.math.BigDecimal;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;

public class AddReceiptController implements Initializable
{

	@FXML
	ComboBox<String> cmbCustomer;

	@FXML
	DatePicker txtDate;

	@FXML
	TextField txtAmt;

	@FXML
	RadioButton rdoBank;

	@FXML
	RadioButton rdoCash;

	@FXML
	ComboBox<String> cmbAccount;

	@FXML
	TextField txtNote;

	@FXML
	Button btnSave;

	@FXML
	Button btnCancel;

	FXMLLoader returnFXMLLoader = null;
	boolean modeAdd = true;

	ReceiptDTO receipt;
	ReceiptSer receiptSer;

	List<CustomerDTO> customerDTOs;
	List<BankDTO> bankDTOs;
	List<CashDTO> cashDTOs;

	@Override
	public void initialize(URL url, ResourceBundle rb)
	{
		new ComboBoxAutoComplete<String>(cmbCustomer);
		new ComboBoxAutoComplete<String>(cmbAccount);

		new DatePickerHelper(txtDate);

		btnSave.setOnAction((event) -> handleClickBtnSave());
		btnCancel.setOnAction((event) -> handleClickBtnCancel());

		Platform.runLater(() -> cmbCustomer.requestFocus());

		myInit();
	}

	private void myInit()
	{
		modeAdd = true;

		receiptSer = new ReceiptSer();

		customerDTOs = new CustomerSer().retrieve();
		List<String> l1 = new ArrayList<>();
		for (CustomerDTO customerDTO : customerDTOs)
		{
			l1.add(customerDTO.getName());
		}
		CommonUIActions.fillComboBox(cmbCustomer, l1);

		rdoBank.selectedProperty().addListener((observable, oldValue, newValue) -> handleRadioButtonChanged());
		rdoBank.setSelected(true);

		receipt = new ReceiptDTO();
	}

	public void setData(ReceiptDTO receipt)
	{
		this.modeAdd = false;
		this.receipt = receipt;

		CustomerDTO customer = new CustomerSer().getById(receipt.getCustomerId());
		cmbCustomer.getSelectionModel().select(customer.getName());
		txtDate.setValue(Util.getLocalDate(receipt.getDate()));
		txtAmt.setText(receipt.getAmount().toString());
		if (receipt.getToTypeId() == 1)
		{
			rdoBank.setSelected(true);
			BankDTO bank = new BankSer().getById(receipt.getBankId());
			cmbAccount.getSelectionModel().select(bank.getName());
		}
		else
		{
			rdoCash.setSelected(true);
			CashDTO cash = new CashSer().getById(receipt.getCashId());
			cmbAccount.getSelectionModel().select(cash.getName());
		}

		txtNote.setText(receipt.getNote());
	}

	public void setReturnFXMLLoader(FXMLLoader returnFXMLLoader)
	{
		this.returnFXMLLoader = returnFXMLLoader;
	}

	private void clearBoxes()
	{
		cmbCustomer.getSelectionModel().select(0);
		txtDate.setValue(null);
		txtAmt.clear();
		rdoBank.setSelected(true);
		cmbAccount.getSelectionModel().select(0);
		txtNote.clear();

		receipt = new ReceiptDTO();

		cmbCustomer.requestFocus();
	}

	private void handleClickBtnSave()
	{

		StringBuilder errorMsg = new StringBuilder();
		if (!checkIsValid(errorMsg))
		{
			StageHelper.showMessageBox(errorMsg.toString());
			return;
		}

		receipt.setFromTypeId(1);
		receipt.setCustomerId(customerDTOs.get(cmbCustomer.getSelectionModel().getSelectedIndex() - 1).getId());
		receipt.setDate(Util.getDate(txtDate.getValue()));
		receipt.setAmount(BigDecimal.valueOf(Double.valueOf(txtAmt.getText())));
		if (rdoBank.isSelected())
		{
			receipt.setToTypeId(1);
			receipt.setBankId(bankDTOs.get(cmbAccount.getSelectionModel().getSelectedIndex() - 1).getId());
			receipt.setCashId(null);
		}
		else
		{
			receipt.setToTypeId(2);
			receipt.setBankId(null);
			receipt.setCashId(cashDTOs.get(cmbAccount.getSelectionModel().getSelectedIndex() - 1).getId());
		}

		receipt.setNote(txtNote.getText());

		boolean result;

		if (modeAdd)
		{
			result = (receiptSer.create(receipt, errorMsg));
		}
		else
		{
			result = (receiptSer.update(receipt, errorMsg));
		}

		if (result)
		{
			StageHelper.showMessageBox("Receipt saved successfully");
		}
		else
		{
			StageHelper.showMessageBox(errorMsg.toString());
			return;
		}

		if (modeAdd)
		{
			if (returnFXMLLoader == null)
			{
				clearBoxes();
			}
			else
			{
				/*
				AddSalesController asc = returnFXMLLoader.getController();
				asc.reload(Reload.RECEIPTS);
				Container.wrapperController.addPane(returnFXMLLoader);
				 */
			}
		}
		else
		{
			this.showListForm();
		}

	}

	private void handleClickBtnCancel()
	{
		if (modeAdd && returnFXMLLoader != null)
		{
			Container.wrapperController.addPane(returnFXMLLoader);
		}
		else
		{
			this.showListForm();
		}
	}

	private void showListForm()
	{
		Container.wrapperController.addPane(StageHelper.getFXMLLoader(FXMLLayouts.LIST_RECEIPT));
	}

	private boolean checkIsValid(StringBuilder errorMsg)
	{

		if (cmbCustomer.getSelectionModel().getSelectedIndex() == 0)
		{
			errorMsg.append("Customer is required");
			return false;
		}

		if ((txtDate.getValue() == null) || (txtDate.getValue().equals("")))
		{
			errorMsg.append("Date is required");
			return false;
		}

		if (txtAmt.getText() != null
				&& !txtAmt.getText().equals("")
				&& !Util.isNumeric(txtAmt.getText()))
		{
			errorMsg.append("Amount is invalid");
			return false;
		}

		if (!rdoBank.isSelected() && !rdoCash.isSelected())
		{
			errorMsg.append("Select bank or cash");
			return false;
		}

		if (cmbAccount.getSelectionModel().getSelectedIndex() == 0)
		{
			errorMsg.append("Select bank/cash account name");
			return false;
		}

		return true;
	}

	private void handleRadioButtonChanged()
	{
		if (rdoBank.isSelected())
		{
			if (bankDTOs == null)
			{
				bankDTOs = new BankSer().retrieve();
			}

			List<String> l1 = new ArrayList<>();
			for (BankDTO bankDTO : bankDTOs)
			{
				l1.add(bankDTO.getName());
			}
			CommonUIActions.fillComboBox(cmbAccount, l1);
		}
		else
		{
			if (cashDTOs == null)
			{
				cashDTOs = new CashSer().retrieve();
			}

			List<String> l1 = new ArrayList<>();
			for (CashDTO cashDTO : cashDTOs)
			{
				l1.add(cashDTO.getName());
			}
			CommonUIActions.fillComboBox(cmbAccount, l1);
		}
	}

}
