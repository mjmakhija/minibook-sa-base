package com.hm.minibook.controller;

import com.hm.minibook.CommonUIActions;
import com.hm.minibook.Container;
import com.hm.minibook.GV;
import com.hm.minibook.StageHelper;
import com.hm.minibook.dao.CompanyYearInfoSer;
import com.hm.minibook.dao.StateSer;
import com.hm.minibook.dto.StateDTO;
import com.hm.utilities.ComboBoxAutoComplete;
import com.hm.utilities.Util;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class CompanyInfoController implements Initializable
{

	@FXML
	ComboBox<String> cmbState;
	@FXML
	TextArea taBankDetails;
	@FXML
	TextArea taTnC;
	@FXML
	TextField txtAddress;
	@FXML
	TextField txtCity;
	@FXML
	TextField txtContactNo;
	@FXML
	TextField txtContactPerson;
	@FXML
	TextField txtEmail;
	@FXML
	TextField txtGSTNo;
	@FXML
	TextField txtName;
	@FXML
	TextField txtPin;
	//
	@FXML
	ImageView imageView;
	@FXML
	Button btnChangeUpdate;
	@FXML
	Button btnDelete;

	@FXML
	Button btnSave;

	@FXML
	Button btnCancel;

	CompanyYearInfoSer companyYearInfoSer;
	StateSer stateSer;
	List<StateDTO> stateDTOs;
	StateDTO stateDTOSelected;

	String logoPathInDb = "";
	String currentLogoPath = "";

	@Override
	public void initialize(URL url, ResourceBundle rb)
	{
		new ComboBoxAutoComplete<String>(cmbState);

		btnDelete.setOnAction((event) -> handleClickBtnDelete());
		btnChangeUpdate.setOnAction((event) -> handleClickBtnChangeUpdate());

		btnSave.setOnAction((event) -> handleClickBtnSave());
		btnCancel.setOnAction((event) -> handleClickBtnCancel());

		Platform.runLater(() -> txtName.requestFocus());

		myInit();
	}

	void myInit()
	{

		companyYearInfoSer = new CompanyYearInfoSer();
		stateSer = new StateSer();

		stateDTOs = stateSer.retrieve();

		List<String> l = new ArrayList<>();
		for (StateDTO state : stateDTOs)
		{
			l.add(state.getName());
		}
		CommonUIActions.fillComboBox(cmbState, l);

		txtAddress.setText(companyYearInfoSer.get(CompanyYearInfoSer.CompanyYearInfoKey.Address));
		txtCity.setText(companyYearInfoSer.get(CompanyYearInfoSer.CompanyYearInfoKey.City));
		txtName.setText(companyYearInfoSer.get(CompanyYearInfoSer.CompanyYearInfoKey.CompanyName));
		txtContactNo.setText(companyYearInfoSer.get(CompanyYearInfoSer.CompanyYearInfoKey.ContactNo));
		txtEmail.setText(companyYearInfoSer.get(CompanyYearInfoSer.CompanyYearInfoKey.Email));
		txtGSTNo.setText(companyYearInfoSer.get(CompanyYearInfoSer.CompanyYearInfoKey.GSTNo));
		txtContactPerson.setText(companyYearInfoSer.get(CompanyYearInfoSer.CompanyYearInfoKey.PersonName));
		txtPin.setText(companyYearInfoSer.get(CompanyYearInfoSer.CompanyYearInfoKey.Pin));

		String stateId = companyYearInfoSer.get(CompanyYearInfoSer.CompanyYearInfoKey.StateId);

		if (stateId != null && !stateId.equals(""))
		{
			stateDTOSelected = stateSer.getById(Integer.parseInt(stateId));
			cmbState.getSelectionModel().select(stateDTOSelected.getName());

		}
		taBankDetails.setText(companyYearInfoSer.get(CompanyYearInfoSer.CompanyYearInfoKey.BankDetails));
		taTnC.setText(companyYearInfoSer.get(CompanyYearInfoSer.CompanyYearInfoKey.TnC));

		if (companyYearInfoSer.get(CompanyYearInfoSer.CompanyYearInfoKey.LogoPath) == null
				|| companyYearInfoSer.get(CompanyYearInfoSer.CompanyYearInfoKey.LogoPath).equals(""))
		{
			logoPathInDb = "";
			currentLogoPath = "";
		}
		else
		{
			logoPathInDb = GV.IMAGE_DIR + "/" + companyYearInfoSer.get(CompanyYearInfoSer.CompanyYearInfoKey.LogoPath);
			currentLogoPath = GV.IMAGE_DIR + "/" + companyYearInfoSer.get(CompanyYearInfoSer.CompanyYearInfoKey.LogoPath);
			loadImage(currentLogoPath);
		}

	}

	void handleClickBtnSave()
	{

		companyYearInfoSer.set(CompanyYearInfoSer.CompanyYearInfoKey.Address, txtAddress.getText());
		companyYearInfoSer.set(CompanyYearInfoSer.CompanyYearInfoKey.City, txtCity.getText());
		companyYearInfoSer.set(CompanyYearInfoSer.CompanyYearInfoKey.CompanyName, txtName.getText());
		companyYearInfoSer.set(CompanyYearInfoSer.CompanyYearInfoKey.ContactNo, txtContactNo.getText());
		companyYearInfoSer.set(CompanyYearInfoSer.CompanyYearInfoKey.Email, txtEmail.getText());
		companyYearInfoSer.set(CompanyYearInfoSer.CompanyYearInfoKey.GSTNo, txtGSTNo.getText());
		companyYearInfoSer.set(CompanyYearInfoSer.CompanyYearInfoKey.PersonName, txtContactPerson.getText());
		companyYearInfoSer.set(CompanyYearInfoSer.CompanyYearInfoKey.Pin, txtPin.getText());
		companyYearInfoSer.set(
				CompanyYearInfoSer.CompanyYearInfoKey.StateId,
				cmbState.getSelectionModel().getSelectedIndex() == 0
				? ""
				: String.valueOf(stateDTOs.get(cmbState.getSelectionModel().getSelectedIndex() - 1).getId())
		);
		companyYearInfoSer.set(CompanyYearInfoSer.CompanyYearInfoKey.BankDetails, taBankDetails.getText());
		companyYearInfoSer.set(CompanyYearInfoSer.CompanyYearInfoKey.TnC, taTnC.getText());

		if (currentLogoPath.equals(""))
		{
			String logoDestPath = getLogoDestPath();
			File fileDest = new File(logoDestPath);
			fileDest.delete();

			companyYearInfoSer.set(CompanyYearInfoSer.CompanyYearInfoKey.LogoPath, "");
		}
		else if ((logoPathInDb.equals("") && !currentLogoPath.equals(""))
				|| (!logoPathInDb.equals("") && !logoPathInDb.equals(currentLogoPath)))
		{
			copyLogoFile();

			companyYearInfoSer.set(CompanyYearInfoSer.CompanyYearInfoKey.LogoPath, getLogoFileName());
		}

		StageHelper.showMessageBox("Data saved successfully");

	}

	void handleClickBtnCancel()
	{
		//this.showListForm();
	}

	void handleClickBtnDelete()
	{
		currentLogoPath = "";
		imageView.setImage(null);
	}

	void handleClickBtnChangeUpdate()
	{
		File f = CommonUIActions.imageFileChooser();

		if (f == null)
		{
			return;
		}

		if (!f.exists())
		{
			StageHelper.showMessageBox("Invalid file");
			return;
		}

		currentLogoPath = f.getAbsolutePath();
		loadImage(currentLogoPath);

	}

	private void loadImage(String path)
	{
		FileInputStream input = null;
		try
		{
			input = new FileInputStream(path);
		}
		catch (FileNotFoundException ex)
		{
			Container.logger.log(Level.SEVERE, ex.getMessage(), ex);
		}
		imageView.setImage(new Image(input));
	}

	void showListForm()
	{
		//Container.wrapperController.addPane(StageManager.getFXMLLoader(returnSceneType));
	}

	private String getLogoDestPath()
	{
		return GV.IMAGE_DIR + "/" + getLogoFileName();
	}

	private String getLogoFileName()
	{
		return "logo-" + String.valueOf(GV.selectedCompanyYearId) + ".png";
	}

	private void copyLogoFile()
	{
		File f = new File(currentLogoPath);
		String logoDestPath = getLogoDestPath();
		File fileDest = new File(logoDestPath);
		fileDest.delete();
		try
		{
			Util.copyFileUsingStream(f, fileDest);
		}
		catch (IOException ex)
		{
			Container.logger.log(Level.SEVERE, ex.getMessage(), ex);
		}
	}

}
