package com.hm.minibook.controller;

import com.hm.minibook.Container;

import com.hm.minibook.FXMLLayouts;
import com.hm.minibook.StageHelper;
import com.hm.minibook.dao.TransporterSer;
import com.hm.minibook.dto.TransporterDTO;
import com.hm.minibook.Reload;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class AddTransporterController implements Initializable
{

	@FXML
	TextField txtName;

	@FXML
	TextField txtTransporterId;

	@FXML
	Button btnSave;

	@FXML
	Button btnCancel;

	FXMLLoader returnFXMLLoader = null;
	ICallerController callerController = null;
	boolean modeAdd = true;

	TransporterDTO transporter = new TransporterDTO();

	@Override
	public void initialize(URL url, ResourceBundle rb)
	{
		btnSave.setOnAction((event) -> handleClickBtnSave());
		btnCancel.setOnAction((event) -> handleClickBtnCancel());

		Platform.runLater(() -> txtName.requestFocus());
	}

	public void setData(TransporterDTO transporter)
	{
		this.transporter = transporter;
		this.modeAdd = false;
		txtName.setText(transporter.getName());
		txtTransporterId.setText(transporter.getTransporterId());
	}

	public void setReturnFXMLLoader(FXMLLoader returnFXMLLoader)
	{
		this.returnFXMLLoader = returnFXMLLoader;
	}

	public void setCallerController(ICallerController callerController)
	{
		this.callerController = callerController;
	}

	private boolean checkIsValid()
	{
		return true;
	}

	private void clearBoxes()
	{
		txtName.clear();
		txtTransporterId.clear();

		transporter = new TransporterDTO();

		txtName.requestFocus();
	}

	private void handleClickBtnSave()
	{
		if (!checkIsValid())
		{
			return;
		}

		transporter.setName(txtName.getText());
		transporter.setTransporterId(txtTransporterId.getText());

		TransporterSer transporterSer = new TransporterSer();
		StringBuilder errorMsg = new StringBuilder();
		boolean result;

		if (modeAdd)
		{
			result = (transporterSer.create(transporter, errorMsg));
		}
		else
		{
			result = (transporterSer.update(transporter, errorMsg));
		}

		if (result)
		{
			StageHelper.showMessageBox("Transporter saved successfully");
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
					callerController.reload(Reload.TRANSPORTERS);
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
		Container.wrapperController.addPane(StageHelper.getFXMLLoader(FXMLLayouts.LIST_TRANSPORTER));
	}

}
