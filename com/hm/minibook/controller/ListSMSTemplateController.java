package com.hm.minibook.controller;

import com.hm.minibook.Container;

import com.hm.minibook.FXMLLayouts;
import com.hm.minibook.StageHelper;
import com.hm.minibook.dao.SMSTemplateSer;
import com.hm.minibook.dto.SMSTemplateDTO;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class ListSMSTemplateController implements Initializable
{

	@FXML
	TextField txtName;

	@FXML
	ListView<String> lst;

	@FXML
	TextArea taMessage;

	@FXML
	Button btnSearch;

	@FXML
	Button btnCancel;

	@FXML
	MenuItem miAdd;

	@FXML
	MenuItem miEdit;

	@FXML
	MenuItem miDelete;

	List<SMSTemplateDTO> smsTemplates;

	@Override
	public void initialize(URL url, ResourceBundle rb)
	{
		btnSearch.setOnAction((event) -> handleClickBtnSearch());
		btnCancel.setOnAction(this::handleClickBtnCancel);

		lst.getSelectionModel().getSelectedItems().addListener((ListChangeListener.Change<? extends String> c) -> handleTemplateChange());

		miAdd.setOnAction(this::handleClickBtnAdd);
		miEdit.setOnAction(this::handleClickBtnEdit);
		miDelete.setOnAction(this::handleClickBtnDelete);

		handleClickBtnSearch();

	}

	private void mLoadData(List<SMSTemplateDTO> smsTemplates)
	{
		this.smsTemplates = smsTemplates;

		ObservableList<String> seasonList = FXCollections.observableArrayList();
		for (SMSTemplateDTO smsTemplate : this.smsTemplates)
		{
			seasonList.add(smsTemplate.getName());
		}
		lst.setItems(seasonList);

	}

	private void handleClickBtnDelete(ActionEvent event)
	{

		if (lst.getSelectionModel().getSelectedItems().size() != 1)
		{
			StageHelper.showMessageBox("Select one row to delete");
		}
		else
		{
			if (StageHelper.showConfirmBox("Are you sure?"))
			{
				SMSTemplateSer objSMSTemplateSer = new SMSTemplateSer();

				int vSelectedIndex = lst.getSelectionModel().getSelectedIndex();
				objSMSTemplateSer.delete(smsTemplates.get(vSelectedIndex));
				handleClickBtnSearch();
				StageHelper.showMessageBox("Deleted successfully");
			}
		}

	}

	private void handleClickBtnSearch()
	{
		this.mLoadData(new SMSTemplateSer().retrieve());
	}

	private void handleClickBtnCancel(ActionEvent event)
	{
		txtName.clear();
		handleClickBtnSearch();
	}

	private void handleClickBtnAdd(ActionEvent event)
	{

		FXMLLoader fxmll = StageHelper.getFXMLLoader(FXMLLayouts.ADD_SMS_TEMPLATE);
		AddSMSTemplateController addSMSTemplateController = fxmll.getController();
		Container.wrapperController.addPane(fxmll);
	}

	private void handleClickBtnEdit(ActionEvent event)
	{
		if (lst.getSelectionModel().getSelectedItems().size() != 1)
		{
			StageHelper.showMessageBox("Select one row to edit");
			return;
		}

		int selectedIndex = lst.getSelectionModel().getSelectedIndex();

		FXMLLoader fxmll = StageHelper.getFXMLLoader(FXMLLayouts.ADD_SMS_TEMPLATE);
		AddSMSTemplateController addSMSTemplateController = fxmll.getController();
		addSMSTemplateController.setData(smsTemplates.get(selectedIndex));
		Container.wrapperController.addPane(fxmll);

	}

	private void handleTemplateChange()
	{
		taMessage.setText(smsTemplates.get(lst.getSelectionModel().getSelectedIndex()).getMessage());
	}

}
