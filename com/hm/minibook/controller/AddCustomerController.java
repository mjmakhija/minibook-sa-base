package com.hm.minibook.controller;

import com.hm.minibook.CommonUIActions;
import com.hm.minibook.Container;
import com.hm.minibook.FXMLLayouts;
import com.hm.minibook.StageHelper;
import com.hm.minibook.dao.CustomerSer;
import com.hm.minibook.dao.StateSer;
import com.hm.minibook.dto.CustomerDTO;
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

public class AddCustomerController implements Initializable
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

	CustomerDTO customer;
	List<StateDTO> stateDTOs;
	CustomerSer customerSer;

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

		customerSer = new CustomerSer();
		stateDTOs = new StateSer().retrieve();

		List<String> stateStrings = new ArrayList<>();
		for (StateDTO stateDTO : stateDTOs)
		{
			stateStrings.add(stateDTO.getName());
		}
		CommonUIActions.fillComboBox(cmbState, stateStrings);

		customer = new CustomerDTO();
	}

	public void setData(CustomerDTO customer)
	{
		this.modeAdd = false;
		this.customer = customer;

		txtName.setText(customer.getName());
		txtAddress.setText(customer.getAddress());
		txtCity.setText(customer.getCity());
		StateDTO state = new StateSer().getById(customer.getStateId());
		cmbState.getSelectionModel().select(state.getName());
		txtPIN.setText(customer.getPin());
		txtContactNo.setText(customer.getContactNo());
		txtEmail.setText(customer.getEmail());
		txtGSTNo.setText(customer.getGstNo());
		txtOpeningBalance.setText(String.valueOf(customer.getOpeningBalance()));
		txtNote.setText(customer.getNote());
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

		customer = new CustomerDTO();

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

		customer.setName(txtName.getText());
		customer.setAddress(txtAddress.getText());
		customer.setCity(txtCity.getText());
		if (cmbState.getSelectionModel().getSelectedIndex() > 0)
		{
			customer.setStateId(stateDTOs.get(cmbState.getSelectionModel().getSelectedIndex() - 1).getId());
		}
		customer.setPin(txtPIN.getText());
		customer.setContactNo(txtContactNo.getText());
		customer.setEmail(txtEmail.getText());
		customer.setGstNo(txtGSTNo.getText());
		if (!txtOpeningBalance.getText().equals(""))
		{
			customer.setOpeningBalance(BigDecimal.valueOf(Double.parseDouble(txtOpeningBalance.getText())));
		}
		else
		{
			customer.setOpeningBalance(BigDecimal.ZERO);
		}
		customer.setNote(txtNote.getText());

		if (modeAdd)
		{
			result = (customerSer.create(customer, errorMsg));
		}
		else
		{
			result = (customerSer.update(customer, errorMsg));
		}

		if (result)
		{
			StageHelper.showMessageBox("Customer saved successfully");
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
					callerController.reload(Reload.CUSTOMERS);
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
		Container.wrapperController.addPane(StageHelper.getFXMLLoader(FXMLLayouts.LIST_CUSTOMER));
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
