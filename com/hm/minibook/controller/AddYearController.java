package com.hm.minibook.controller;

import com.hm.minibook.Container;
import com.hm.minibook.DatabaseHelper;
import com.hm.minibook.FXMLLayouts;
import com.hm.minibook.StageHelper;
import com.hm.minibook.dao.app.CompanyDAO;
import com.hm.minibook.dto.app.CompanyDTO;
import com.hm.minibook.dto.app.YearDTO;
import com.hm.utilities.DatePickerHelper;
import com.hm.utilities.Util;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;

public class AddYearController implements Initializable
{

	@FXML
	DatePicker txtDateFrom;

	@FXML
	DatePicker txtDateTo;

	@FXML
	Button btnSave;

	@FXML
	Button btnCancel;

	CompanyDTO vObjCompany;

	@Override
	public void initialize(URL url, ResourceBundle rb)
	{
		new DatePickerHelper(txtDateFrom);
		new DatePickerHelper(txtDateTo);

		btnSave.setOnAction((event) -> handleClickBtnSave());
		btnCancel.setOnAction((event) -> handleClickBtnCancel());
	}

	public void setCompany(CompanyDTO vObjCompany)
	{
		this.vObjCompany = vObjCompany;
	}

	private boolean checkIsValid()
	{
		if (txtDateFrom.getValue() == null)
		{
			Container.stageHelper.showMessageBox("Financial year date from is required");
			return false;
		}
		else if (txtDateTo.getValue() == null)
		{
			Container.stageHelper.showMessageBox("Financial year date to is required");
			return false;
		}

		return true;
	}

	private void handleClickBtnSave()
	{
		/*
		if (checkIsValid())
		{
			YearDTO vObjYear = new YearDTO(
					Util.getDate(txtDateFrom.getValue()),
					Util.getDate(txtDateTo.getValue())
			);
			vObjCompany.addYear(vObjYear);
			if (new CompanyDAO().mSave(vObjCompany))
			{
				DatabaseHelper.createDB(Integer.toString(vObjYear.getId()));
				Container.stageHelper.showMessageBox("Year created successfully");
				handleClickBtnCancel();
			}
		}
		 */
	}

	private void handleClickBtnCancel()
	{
		Container.stageHelper.loadNodesInExistingScene(StageHelper.getFXMLLoader(FXMLLayouts.GATEWAY));
	}

}
