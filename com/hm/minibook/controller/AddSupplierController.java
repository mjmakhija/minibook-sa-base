package com.hm.minibook.controller;

import com.hm.minibook.CommonUIActions;
import com.hm.minibook.Container;
import com.hm.minibook.FXMLLayouts;
import com.hm.minibook.StageHelper;
import com.hm.minibook.dao.SupplierSer;
import com.hm.minibook.dao.StateSer;
import com.hm.minibook.dto.SupplierDTO;
import com.hm.minibook.dto.StateDTO;
import com.hm.minibook.Reload;
import com.hm.utilities.ComboBoxAutoComplete;
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
import javafx.scene.control.TextField;

public class AddSupplierController implements Initializable
{

	@FXML
	TextField txtName;

	@FXML
	TextField txtAddress;

	@FXML
	TextField txtCity;

	@FXML
	ComboBox<String> cmbState;

	@FXML
	TextField txtPIN;

	@FXML
	TextField txtContactNo;

	@FXML
	TextField txtEmail;

	@FXML
	TextField txtGSTNo;

	@FXML
	TextField txtOpeningBalance;

	@FXML
	TextField txtNote;

	@FXML
	Button btnSave;

	@FXML
	Button btnCancel;

	FXMLLoader returnFXMLLoader = null;
	ICallerController callerController = null;
	boolean modeAdd = true;

	SupplierDTO supplier;
	List<StateDTO> stateDTOs;
	SupplierSer supplierSer;

	@Override
	public void initialize(URL url, ResourceBundle rb)
	{
		new ComboBoxAutoComplete<String>(cmbState);

		btnSave.setOnAction((event) -> handleClickBtnSave());
		btnCancel.setOnAction((event) -> handleClickBtnCancel());

		Platform.runLater(() -> txtName.requestFocus());

		myInit();
	}

	private void myInit()
	{
		modeAdd = true;

		supplierSer = new SupplierSer();
		stateDTOs = new StateSer().retrieve();

		List<String> stateStrings = new ArrayList<>();
		for (StateDTO stateDTO : stateDTOs)
		{
			stateStrings.add(stateDTO.getName());
		}
		CommonUIActions.fillComboBox(cmbState, stateStrings);

		supplier = new SupplierDTO();
	}

	public void setData(SupplierDTO supplier)
	{
		this.modeAdd = false;
		this.supplier = supplier;

		txtName.setText(supplier.getName());
		txtAddress.setText(supplier.getAddress());
		txtCity.setText(supplier.getCity());
		StateDTO state = new StateSer().getById(supplier.getStateId());
		cmbState.getSelectionModel().select(state.getName());
		txtPIN.setText(supplier.getPin());
		txtContactNo.setText(supplier.getContactNo());
		txtEmail.setText(supplier.getEmail());
		txtGSTNo.setText(supplier.getGstNo());
		txtOpeningBalance.setText(String.valueOf(supplier.getOpeningBalance()));
		txtNote.setText(supplier.getNote());
	}

	public void setReturnFXMLLoader(FXMLLoader returnFXMLLoader)
	{
		this.returnFXMLLoader = returnFXMLLoader;
	}

	public void setCallerController(ICallerController callerController)
	{
		this.callerController = callerController;
	}

	private void clearBoxes()
	{
		txtName.clear();
		txtAddress.clear();
		txtCity.clear();
		cmbState.getSelectionModel().select("");
		txtPIN.clear();
		txtContactNo.clear();
		txtEmail.clear();
		txtGSTNo.clear();
		txtOpeningBalance.clear();
		txtNote.clear();

		supplier = new SupplierDTO();

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

		supplier.setName(txtName.getText());
		supplier.setAddress(txtAddress.getText());
		supplier.setCity(txtCity.getText());
		if (cmbState.getSelectionModel().getSelectedIndex() > 0)
		{
			supplier.setStateId(stateDTOs.get(cmbState.getSelectionModel().getSelectedIndex() - 1).getId());
		}
		supplier.setPin(txtPIN.getText());
		supplier.setContactNo(txtContactNo.getText());
		supplier.setEmail(txtEmail.getText());
		supplier.setGstNo(txtGSTNo.getText());
		if (!txtOpeningBalance.getText().equals(""))
		{
			supplier.setOpeningBalance(BigDecimal.valueOf(Double.parseDouble(txtOpeningBalance.getText())));
		}
		else
		{
			supplier.setOpeningBalance(BigDecimal.ZERO);
		}
		supplier.setNote(txtNote.getText());

		if (modeAdd)
		{
			result = (supplierSer.create(supplier, errorMsg));
		}
		else
		{
			result = (supplierSer.update(supplier, errorMsg));
		}

		if (result)
		{
			StageHelper.showMessageBox("Supplier saved successfully");
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
				if (callerController != null)
				{
					callerController.reload(Reload.SUPPLIERS);
				}
				Container.wrapperController.addPane(returnFXMLLoader);
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
		Container.wrapperController.addPane(StageHelper.getFXMLLoader(FXMLLayouts.LIST_SUPPLIER));
	}

	private boolean checkIsValid(StringBuilder errorMsg)
	{
		if (txtName.getText() == null || txtName.getText().isEmpty())
		{
			errorMsg.append("Name is required");
			return false;
		}

		if (!txtOpeningBalance.getText().equals("") && !Util.isDouble(txtOpeningBalance.getText()))
		{
			errorMsg.append("Invalid opening balance");
			return false;
		}

		if (cmbState.getSelectionModel().getSelectedIndex() == 0)
		{
			errorMsg.append("State is required");
			return false;
		}
		return true;
	}

}
