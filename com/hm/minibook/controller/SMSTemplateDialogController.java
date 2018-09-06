package com.hm.minibook.controller;


import com.hm.minibook.dao.SMSTemplateSer;
import com.hm.minibook.dto.SMSTemplateDTO;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

public class SMSTemplateDialogController implements Initializable
{

	@FXML
	Button btnCancel;

	@FXML
	Button btnSend;

	@FXML
	ListView<String> lstTemplates;

	@FXML
	TextArea taMessage;

	boolean res = false;

	Stage stage;

	List<SMSTemplateDTO> smsTemplateDTOs;
	SMSTemplateSer smsTemplateSer;

	@Override
	public void initialize(URL url, ResourceBundle rb)
	{
		lstTemplates.getSelectionModel().getSelectedItems().addListener(new ListChangeListener()
		{
			@Override
			public void onChanged(ListChangeListener.Change c)
			{
				handleTemplateChange();
			}
		});

		btnSend.setOnAction((event) -> handleClickBtnSave());
		btnCancel.setOnAction((event) -> handleClickBtnCancel());
		Platform.runLater(() -> lstTemplates.requestFocus());

		myInit();

	}

	private void myInit()
	{
		smsTemplateSer = new SMSTemplateSer();
		smsTemplateDTOs = smsTemplateSer.retrieve();
		ObservableList<String> seasonList = FXCollections.observableArrayList();
		for (SMSTemplateDTO sMSTemplateDTO : smsTemplateDTOs)
		{
			seasonList.add(sMSTemplateDTO.getName());
		}
		lstTemplates.setItems(seasonList);
	}

	public void setStage(Stage stage)
	{
		this.stage = stage;
	}

	private void handleTemplateChange()
	{
		taMessage.setText(smsTemplateDTOs.get(lstTemplates.getSelectionModel().getSelectedIndex()).getMessage());
	}

	private void handleClickBtnSave()
	{
		res = true;
		stage.close();
	}

	private void handleClickBtnCancel()
	{
		res = false;
		stage.close();
	}

	public String getMessage()
	{
		return taMessage.getText();
	}

	public boolean showAndWait()
	{
		stage.showAndWait();
		return res;
	}

}
