package com.hm.minibook.controller;

import com.hm.minibook.Container;
import com.hm.minibook.FXMLLayouts;
import com.hm.minibook.StageHelper;
import com.hm.minibook.dao.UnitSer;
import com.hm.minibook.dto.UnitDTO;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class AddUnitController implements Initializable
{

	@FXML
	TextField txtName;

	@FXML
	TextField txtNote;

	@FXML
	Button btnSave;

	@FXML
	Button btnCancel;

	UnitDTO unit = new UnitDTO();
	boolean modeAdd = true;

	@Override
	public void initialize(URL url, ResourceBundle rb)
	{
		btnSave.setOnAction((event) -> handleClickBtnSave());
		btnCancel.setOnAction((event) -> handleClickBtnCancel());

		Platform.runLater(() -> txtName.requestFocus());
	}

	public void setData(UnitDTO unit)
	{
		this.unit = unit;
		this.modeAdd = false;
		txtName.setText(unit.getName());
		txtNote.setText(unit.getNote());
	}

	private boolean checkIsValid()
	{
		return true;
	}

	private void clearBoxes()
	{
		txtName.clear();
		txtNote.clear();

		unit = new UnitDTO();

		txtName.requestFocus();
	}

	private void handleClickBtnSave()
	{
		if (!checkIsValid())
		{
			return;
		}

		unit.setName(txtName.getText());
		unit.setNote(txtNote.getText());

		UnitSer unitSer = new UnitSer();
		StringBuilder errorMsg = new StringBuilder();
		boolean result;

		if (modeAdd)
		{
			result = (unitSer.create(unit, errorMsg));
		}
		else
		{
			result = (unitSer.update(unit, errorMsg));
		}

		if (result)
		{
			StageHelper.showMessageBox("Unit saved successfully");
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
		Container.wrapperController.addPane(StageHelper.getFXMLLoader(FXMLLayouts.LIST_UNIT));
	}

}
