package com.hm.minibook.controller;

import com.hm.minibook.Container;
import com.hm.minibook.FXMLLayouts;
import com.hm.minibook.StageHelper;
import com.hm.minibook.dao.app.CompanyDAO;
import com.hm.minibook.dto.app.CompanyDTO;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class EditCompanyController implements Initializable
{

	@FXML
	TextField txtName;

	@FXML
	Button btnSave;

	@FXML
	Button btnCancel;

	CompanyDTO objCompany;

	@Override
	public void initialize(URL url, ResourceBundle rb)
	{
		btnSave.setOnAction((event) -> btnSaveClickHandler());
		btnCancel.setOnAction((event) -> btnCancelClickHandler());
	}

	public void setCompany(CompanyDTO objCompany)
	{
		this.objCompany = objCompany;

		txtName.setText(objCompany.getName());
	}

	private void btnSaveClickHandler()
	{

		if (checkIsValid())
		{
			objCompany.setName(txtName.getText());
			if (new CompanyDAO().update(objCompany))
			{
				Container.stageHelper.showMessageBox("Company updated successfully");
				btnCancelClickHandler();
			}
		}

	}

	private boolean checkIsValid()
	{
		if (txtName.getText() == null || txtName.getText().equals(""))
		{
			Container.stageHelper.showMessageBox("Name is required");
			return false;
		}

		return true;
	}

	private void btnCancelClickHandler()
	{
		Container.stageHelper.loadNodesInExistingScene(StageHelper.getFXMLLoader(FXMLLayouts.GATEWAY));
	}

}
