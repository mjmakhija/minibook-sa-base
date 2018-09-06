package com.hm.minibook.controller;

import com.hm.minibook.Container;

import com.hm.minibook.FXMLLayouts;
import com.hm.minibook.StageHelper;
import com.hm.minibook.dao.SMSTemplateSer;
import com.hm.minibook.dto.SMSTemplateDTO;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class AddSMSTemplateController implements Initializable
{

	@FXML
	TextField txtName;

	@FXML
	TextArea taMessage;

	@FXML
	Button btnSave;

	@FXML
	Button btnCancel;

	boolean modeAdd = true;

	SMSTemplateDTO smsTemplate;
	SMSTemplateSer smsTemplateSer;

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

		smsTemplateSer = new SMSTemplateSer();

		smsTemplate = new SMSTemplateDTO();
	}

	public void setData(SMSTemplateDTO smsTemplate)
	{
		this.modeAdd = false;
		this.smsTemplate = smsTemplate;

		txtName.setText(smsTemplate.getName());
		taMessage.setText(smsTemplate.getMessage());
	}

	private void clearBoxes()
	{
		txtName.clear();
		taMessage.clear();

		smsTemplate = new SMSTemplateDTO();

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

		smsTemplate.setName(txtName.getText());
		smsTemplate.setMessage(taMessage.getText());

		boolean result;

		if (modeAdd)
		{
			result = (smsTemplateSer.create(smsTemplate, errorMsg));
		}
		else
		{
			result = (smsTemplateSer.update(smsTemplate, errorMsg));
		}

		if (result)
		{
			StageHelper.showMessageBox("SMSTemplate saved successfully");
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
		Container.wrapperController.addPane(StageHelper.getFXMLLoader(FXMLLayouts.LIST_SMS_TEMPLATE));
	}

	private boolean checkIsValid(StringBuilder errorMsg)
	{
		if (txtName.getText() == null || txtName.getText().equals(""))
		{
			errorMsg.append("Name is required");
			return false;
		}

		if (taMessage.getText() == null || taMessage.getText().equals(""))
		{
			errorMsg.append("Message is required");
			return false;
		}

		return true;
	}

}
