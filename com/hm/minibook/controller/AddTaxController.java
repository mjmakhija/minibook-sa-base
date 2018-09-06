package com.hm.minibook.controller;

import com.hm.minibook.Container;

import com.hm.minibook.FXMLLayouts;
import com.hm.minibook.StageHelper;
import com.hm.minibook.dao.TaxSer;
import com.hm.minibook.dto.TaxDTO;
import com.hm.utilities.Util;
import java.math.BigDecimal;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class AddTaxController implements Initializable
{

	@FXML
	TextField txtName;

	@FXML
	TextField txtCGST;

	@FXML
	TextField txtSGST;

	@FXML
	TextField txtIGST;

	@FXML
	TextField txtNote;

	@FXML
	Button btnSave;

	@FXML
	Button btnCancel;

	boolean modeAdd = true;

	TaxDTO tax;
	TaxSer taxSer;

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

		taxSer = new TaxSer();

		tax = new TaxDTO();
	}

	public void setData(TaxDTO tax)
	{
		this.tax = tax;
		this.modeAdd = false;

		txtName.setText(tax.getName());
		txtCGST.setText(tax.getCgst().toString());
		txtSGST.setText(tax.getSgst().toString());
		txtIGST.setText(tax.getIgst().toString());
		txtNote.setText(tax.getNote());
	}

	private boolean checkIsValid(StringBuilder errorMsg)
	{
		if (!txtCGST.getText().equals("") && !Util.isDouble(txtCGST.getText()))
		{
			errorMsg.append("Invalid CGST");
			return false;
		}
		else if (!txtSGST.getText().equals("") && !Util.isDouble(txtSGST.getText()))
		{
			errorMsg.append("Invalid SGST");
			return false;
		}
		else if (!txtIGST.getText().equals("") && !Util.isDouble(txtIGST.getText()))
		{
			errorMsg.append("Invalid IGST");
			return false;
		}
		return true;
	}

	private void clearBoxes()
	{
		txtName.clear();
		txtCGST.clear();
		txtSGST.clear();
		txtIGST.clear();
		txtNote.clear();

		tax = new TaxDTO();

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

		tax.setName(txtName.getText());
		if (txtCGST.getText() != null && !txtCGST.getText().equals(""))
		{
			tax.setCgst(BigDecimal.valueOf(Double.valueOf(txtCGST.getText())));
		}
		else
		{
			tax.setCgst(BigDecimal.ZERO);
		}
		if (txtSGST.getText() != null && !txtSGST.getText().equals(""))
		{
			tax.setSgst(BigDecimal.valueOf(Double.valueOf(txtSGST.getText())));
		}
		else
		{
			tax.setSgst(BigDecimal.ZERO);
		}
		if (txtIGST.getText() != null && !txtIGST.getText().equals(""))
		{
			tax.setIgst(BigDecimal.valueOf(Double.valueOf(txtIGST.getText())));
		}
		else
		{
			tax.setIgst(BigDecimal.ZERO);
		}
		tax.setNote(txtNote.getText());

		if (modeAdd)
		{
			result = (taxSer.create(tax, errorMsg));
		}
		else
		{
			result = (taxSer.update(tax, errorMsg));
		}

		if (result)
		{
			StageHelper.showMessageBox("Tax saved successfully");
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
		Container.wrapperController.addPane(StageHelper.getFXMLLoader(FXMLLayouts.LIST_TAX));
	}

}
