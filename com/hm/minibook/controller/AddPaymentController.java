package com.hm.minibook.controller;

import com.hm.minibook.CommonUIActions;
import com.hm.minibook.Container;

import com.hm.minibook.FXMLLayouts;
import com.hm.minibook.StageHelper;
import com.hm.minibook.dao.PaymentSer;
import com.hm.minibook.dto.PaymentDTO;
import com.hm.minibook.dao.BankSer;
import com.hm.minibook.dao.CashSer;
import com.hm.minibook.dao.SupplierSer;
import com.hm.minibook.dto.BankDTO;
import com.hm.minibook.dto.CashDTO;
import com.hm.minibook.dto.SupplierDTO;
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

public class AddPaymentController implements Initializable
{

	@FXML
	ComboBox<String> cmbSupplier;

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

	PaymentDTO payment;
	PaymentSer paymentSer;

	List<SupplierDTO> supplierDTOs;
	List<BankDTO> bankDTOs;
	List<CashDTO> cashDTOs;

	@Override
	public void initialize(URL url, ResourceBundle rb)
	{
		new ComboBoxAutoComplete<String>(cmbSupplier);
		new ComboBoxAutoComplete<String>(cmbAccount);

		new DatePickerHelper(txtDate);

		btnSave.setOnAction((event) -> handleClickBtnSave());
		btnCancel.setOnAction((event) -> handleClickBtnCancel());

		Platform.runLater(() -> cmbSupplier.requestFocus());

		myInit();
	}

	private void myInit()
	{
		modeAdd = true;

		paymentSer = new PaymentSer();

		supplierDTOs = new SupplierSer().retrieve();
		List<String> l1 = new ArrayList<>();
		for (SupplierDTO supplierDTO : supplierDTOs)
		{
			l1.add(supplierDTO.getName());
		}
		CommonUIActions.fillComboBox(cmbSupplier, l1);

		rdoBank.selectedProperty().addListener((observable, oldValue, newValue) -> handleRadioButtonChanged());
		rdoBank.setSelected(true);

		payment = new PaymentDTO();
	}

	public void setData(PaymentDTO payment)
	{
		this.modeAdd = false;
		this.payment = payment;

		SupplierDTO supplier = new SupplierSer().getById(payment.getSupplierId());
		cmbSupplier.getSelectionModel().select(supplier.getName());

		txtDate.setValue(Util.getLocalDate(payment.getDate()));
		txtAmt.setText(payment.getAmount().toString());
		if (payment.getFromType() == PaymentDTO.FromType.BANK)
		{
			rdoBank.setSelected(true);

			BankDTO bank = new BankSer().getById(payment.getBankId());
			cmbAccount.getSelectionModel().select(bank.getName());

		}
		else
		{
			rdoCash.setSelected(true);

			CashDTO cash = new CashSer().getById(payment.getCashId());
			cmbAccount.getSelectionModel().select(cash.getName());
		}

		txtNote.setText(payment.getNote());
	}

	public void setReturnFXMLLoader(FXMLLoader returnFXMLLoader)
	{
		this.returnFXMLLoader = returnFXMLLoader;
	}

	private void clearBoxes()
	{
		cmbSupplier.getSelectionModel().select(0);
		txtDate.setValue(null);
		txtAmt.clear();
		rdoBank.setSelected(true);
		cmbAccount.getSelectionModel().select(0);
		txtNote.clear();

		payment = new PaymentDTO();

		cmbSupplier.requestFocus();
	}

	private void handleClickBtnSave()
	{

		StringBuilder errorMsg = new StringBuilder();
		if (!checkIsValid(errorMsg))
		{
			StageHelper.showMessageBox(errorMsg.toString());
			return;
		}

		if (rdoBank.isSelected())
		{
			payment.setFromType(PaymentDTO.FromType.BANK);
			payment.setBankId(bankDTOs.get(cmbAccount.getSelectionModel().getSelectedIndex() - 1).getId());
			payment.setCashId(null);
		}
		else
		{
			payment.setFromType(PaymentDTO.FromType.CASH);
			payment.setBankId(null);
			payment.setCashId(cashDTOs.get(cmbAccount.getSelectionModel().getSelectedIndex() - 1).getId());
		}
		payment.setToType(PaymentDTO.ToType.SUPPLIER);
		payment.setSupplierId(supplierDTOs.get(cmbSupplier.getSelectionModel().getSelectedIndex() - 1).getId());
		payment.setDate(Util.getDate(txtDate.getValue()));
		payment.setAmount(BigDecimal.valueOf(Double.valueOf(txtAmt.getText())));

		payment.setNote(txtNote.getText());

		boolean result;

		if (modeAdd)
		{
			result = (paymentSer.create(payment, errorMsg));
		}
		else
		{
			result = (paymentSer.update(payment, errorMsg));
		}

		if (result)
		{
			StageHelper.showMessageBox("Payment saved successfully");
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
				asc.reload(Reload.PAYMENTS);
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
		Container.wrapperController.addPane(StageHelper.getFXMLLoader(FXMLLayouts.LIST_PAYMENT));
	}

	private boolean checkIsValid(StringBuilder errorMsg)
	{

		if (cmbSupplier.getSelectionModel().getSelectedIndex() == 0)
		{
			errorMsg.append("Supplier is required");
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
