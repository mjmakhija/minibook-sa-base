package com.hm.minibook.controller;

import com.hm.minibook.Container;
import com.hm.minibook.DatabaseHelper;
import com.hm.minibook.FXMLLayouts;
import com.hm.minibook.StageHelper;
import com.hm.minibook.dao.app.CompanyDAO;
import com.hm.minibook.dao.app.YearDAO;
import com.hm.minibook.dto.app.CompanyDTO;
import com.hm.minibook.dto.app.YearDTO;
import com.hm.utilities.DatePickerHelper;
import com.hm.utilities.Util;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

public class AddCompanyYearController implements Initializable
{

	@FXML
	TextField txtName;
	@FXML
	DatePicker txtDateFrom;
	@FXML
	DatePicker txtDateTo;

	@FXML
	Button btnCancel;
	@FXML
	Button btnSave;

	@Override
	public void initialize(URL url, ResourceBundle rb)
	{
		new DatePickerHelper(txtDateFrom);
		new DatePickerHelper(txtDateTo);

		btnSave.setOnAction((event) -> handleClickBtnSave());
		btnCancel.setOnAction((event) -> handleClickBtnCancel());

		Platform.runLater(() -> txtName.requestFocus());
	}

	private void handleClickBtnSave()
	{

		if (checkIsValid())
		{
			CompanyDAO companyDAO = new CompanyDAO();
			YearDAO yearDAO = new YearDAO();

			CompanyDTO objCompany = new CompanyDTO(txtName.getText());
			if (companyDAO.create(objCompany))
			{
				YearDTO yearDTO = new YearDTO();
				yearDTO.setDateFrom(Util.getDate(txtDateFrom.getValue()));
				yearDTO.setDateTo(Util.getDate(txtDateTo.getValue()));
				yearDTO.setCompanyId(objCompany.getId());

				if (yearDAO.create(yearDTO))
				{
					DatabaseHelper.createDB(Integer.toString(yearDTO.getId()));
					StageHelper.showMessageBox("Company created successfully");
					Container.stageHelper.loadNodesInExistingScene(StageHelper.getFXMLLoader(FXMLLayouts.GATEWAY));
				}
				else
				{
					StageHelper.showMessageBox("Error creating year");
				}

			}
			else
			{
				StageHelper.showMessageBox("Error creating company");
			}

		}

	}

	private void handleClickBtnCancel()
	{
		this.showListForm();
	}

	private void showListForm()
	{
		Container.stageHelper.loadNodesInExistingScene(StageHelper.getFXMLLoader(FXMLLayouts.GATEWAY));
	}

	private boolean checkIsValid()
	{
		if (txtName.getText() == null || txtName.getText().equals(""))
		{
			StageHelper.showMessageBox("Name is required");
			return false;
		}
		else if (txtDateFrom.getValue() == null)
		{
			StageHelper.showMessageBox("Financial year date from is required");
			return false;
		}
		else if (txtDateTo.getValue() == null)
		{
			StageHelper.showMessageBox("Financial year date to is required");
			return false;
		}

		return true;
	}

}
