package com.hm.minibook.controller;

import com.hm.minibook.APIHelper;
import com.hm.minibook.AppHelper;
import com.hm.minibook.Container;
import com.hm.minibook.DatabaseHelper;
import com.hm.minibook.FXMLLayouts;
import com.hm.minibook.GV;
import com.hm.minibook.StageHelper;
import com.hm.minibook.dao.app.AppInfoSer;
import com.hm.minibook.dao.app.CompanyDAO;
import com.hm.minibook.dao.app.YearDAO;
import com.hm.minibook.dto.api.UpdaterResDTO;
import com.hm.minibook.dto.app.CompanyDTO;
import com.hm.minibook.dto.app.YearDTO;
import com.hm.utilities.Download;
import com.hm.utilities.Util;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;

public class GatewayController implements Initializable
{

	@FXML
	ListView lstCompany;

	@FXML
	ListView lstYear;

	@FXML
	Button btnAddCompany;

	@FXML
	Button btnEditCompany;

	@FXML
	Button btnDeleteCompany;

	@FXML
	Button btnAddYear;

	@FXML
	Button btnEditYear;

	@FXML
	Button btnDeleteYear;

	@FXML
	Button btnSelect;

	@FXML
	Button btnActivation;

	@FXML
	Button btnChangePassword;

	@FXML
	Button btnUpdate;

	List<CompanyDTO> vLstObjCompany;
	List<YearDTO> years = new ArrayList<>();

	@Override
	public void initialize(URL url, ResourceBundle rb)
	{
		lstCompany.getSelectionModel().getSelectedItems().addListener(new ListChangeListener()
		{
			@Override
			public void onChanged(ListChangeListener.Change c)
			{
				mLstCompanySelectionChangeHandler();
			}
		});

		btnAddCompany.setOnAction((event) -> mBtnAddCompanyClickHandler());
		btnEditCompany.setOnAction((event) -> mBtnEditCompanyClickHandler());
		btnDeleteCompany.setOnAction((event) -> mBtnDeleteCompanyClickHandler());

		btnAddYear.setOnAction((event) -> mBtnAddYearClickHandler());
		//btnEditYear.setOnAction((event) -> mBtnEditYearClickHandler());
		btnDeleteYear.setOnAction((event) -> mBtnDeleteYearClickHandler());

		btnSelect.setOnAction((event) -> mBtnSelectClickHandler());

		btnActivation.setOnAction((event) -> mBtnActivationClickHandler());
		btnChangePassword.setOnAction((event) -> mBtnChangePasswordClickHandler());
		btnUpdate.setOnAction((event) -> mBtnUpdateClickHandler());

		mLoadCompanies();

	}

	private void mLoadCompanies()
	{
		vLstObjCompany = new CompanyDAO().retrieve();

		ObservableList<String> seasonList = FXCollections.observableArrayList();
		for (CompanyDTO company : vLstObjCompany)
		{
			seasonList.add(company.getName());
		}
		lstCompany.setItems(seasonList);

		if (lstCompany.getItems().size() > 0)
		{
			lstCompany.getSelectionModel().select(0);
		}

	}

	private void mLstCompanySelectionChangeHandler()
	{
		ObservableList<String> seasonList = FXCollections.observableArrayList();
		YearDAO yearDAO = new YearDAO();

		years = yearDAO.retrieveByCompanyId(vLstObjCompany.get(lstCompany.getSelectionModel().getSelectedIndex()).getId());
		for (YearDTO year : years)
		{
			seasonList.add(Util.mGetLocalDateFormat(year.getDateFrom()) + " to " + Util.mGetLocalDateFormat(year.getDateTo()));
		}
		lstYear.setItems(seasonList);

		if (lstYear.getItems().size() > 0)
		{
			lstYear.getSelectionModel().select(0);
		}

	}

	private void mBtnAddCompanyClickHandler()
	{
		Container.stageHelper.loadNodesInExistingScene(StageHelper.getFXMLLoader(FXMLLayouts.ADD_COMPANY_YEAR));
	}

	private void mBtnEditCompanyClickHandler()
	{
		if (lstCompany.getSelectionModel().getSelectedIndex() == -1)
		{
			Container.stageHelper.showMessageBox("Select a company to edit");
		}
		else
		{
			FXMLLoader fXMLLoader = StageHelper.getFXMLLoader(FXMLLayouts.EDIT_COMPANY);
			EditCompanyController editCompanyController = fXMLLoader.getController();
			editCompanyController.setCompany(vLstObjCompany.get(lstCompany.getSelectionModel().getSelectedIndex()));
			Container.stageHelper.loadNodesInExistingScene(fXMLLoader);
		}
	}

	private void mBtnDeleteCompanyClickHandler()
	{
		if (lstCompany.getSelectionModel().getSelectedIndex() == -1)
		{
			Container.stageHelper.showMessageBox("Select a company to delete");
		}
		else
		{
			CompanyDTO vSelectedObjCompany = vLstObjCompany.get(lstCompany.getSelectionModel().getSelectedIndex());

			List<YearDTO> years = new YearDAO().retrieveByCompanyId(vSelectedObjCompany.getId());
			if (years.size() > 0)
			{
				Container.stageHelper.showMessageBox("First delete all financial years for this company");
			}
			else
			{
				if (StageHelper.showConfirmBox("Are you sure?"))
				{
					if (new CompanyDAO().delete(vSelectedObjCompany))
					{
						Container.stageHelper.showMessageBox("Company deleted successfully");
						mLoadCompanies();
					}
				}
			}
		}
	}

	private void mBtnAddYearClickHandler()
	{

		if (lstCompany.getSelectionModel().getSelectedIndex() == -1)
		{
			Container.stageHelper.showMessageBox("Select a company first");
		}
		else
		{
			FXMLLoader fxmll = StageHelper.getFXMLLoader(FXMLLayouts.ADD_YEAR);
			AddYearController addYearController = fxmll.getController();
			addYearController.setCompany(vLstObjCompany.get(lstCompany.getSelectionModel().getSelectedIndex()));
			Container.stageHelper.loadNodesInExistingScene(fxmll);
		}

	}

	private void mBtnDeleteYearClickHandler()
	{

		if (lstYear.getSelectionModel().getSelectedIndex() == -1)
		{
			Container.stageHelper.showMessageBox("Select a year to delete");
		}
		else
		{
			CompanyDTO vSelectedObjCompany = vLstObjCompany.get(lstCompany.getSelectionModel().getSelectedIndex());
			YearDTO vSelectedObjYear = years.get(lstYear.getSelectionModel().getSelectedIndex());

			if (StageHelper.showConfirmBox("Data for selected year will be deleted. \n Are you sure?"))
			{

				YearDAO yearDAO = new YearDAO();
				if (yearDAO.delete(vSelectedObjYear))
				{
					DatabaseHelper.deleteDB(Integer.toString(vSelectedObjYear.getId()));

					Container.stageHelper.showMessageBox("Year deleted successfully");
					mLstCompanySelectionChangeHandler();
				}

			}
		}

	}

	private void mBtnSelectClickHandler()
	{
		CompanyDTO selectedCompany = vLstObjCompany.get(lstCompany.getSelectionModel().getSelectedIndex());
		YearDTO selecteYear = new YearDAO().retrieveByCompanyId(selectedCompany.getId()).get(lstYear.getSelectionModel().getSelectedIndex());

		GV.selectedCompanyYearId = selecteYear.getId();

		DatabaseHelper.connectDBCompanyYear(Integer.toString(GV.selectedCompanyYearId));

		GV.CompanyName = selectedCompany.getName();
		GV.Year = Util.mGetLocalDateFormat(selecteYear.getDateFrom()) + " to " + Util.mGetLocalDateFormat(selecteYear.getDateTo());

		FXMLLoader fxmll = StageHelper.getFXMLLoader(FXMLLayouts.WRAPPER);
		Container.wrapperController = fxmll.getController();
		Container.stageHelper.loadNodesInExistingScene(fxmll);
	}

	private void mBtnChangePasswordClickHandler()
	{
	}

	private void mBtnActivationClickHandler()
	{
		Container.stageHelper.loadNodesInExistingScene(StageHelper.getFXMLLoader(FXMLLayouts.ACTIVATION));
	}

	private void mBtnUpdateClickHandler()
	{

		AppInfoSer appInfoSer = new AppInfoSer();
		String updaterVersion = appInfoSer.get(AppInfoSer.AppInfoKey.UpdaterVersion);

		UpdaterResDTO updaterResDTO = new APIHelper("", "", "", GV.APP_TYPE_ID, "", "", "").getNewUpdater();

		if (updaterResDTO.isDone())
		{
			if (!updaterVersion.equals(updaterResDTO.getData().getVersion()))
			{
				if (Download.Download(updaterResDTO.getData().getFile_path(), GV.INSTALLATION_DIR + "/um.exe"))
				{
					appInfoSer.set(AppInfoSer.AppInfoKey.UpdaterVersion, updaterResDTO.getData().getVersion());
				}

			}

			AppHelper.startUpdateManager();

		}

	}

}
