package com.hm.minibook.controller;

import com.hm.minibook.APIHelper;
import com.hm.minibook.Container;
import com.hm.minibook.FXMLLayouts;
import com.hm.minibook.GV;
import com.hm.minibook.StageHelper;
import com.hm.minibook.dao.app.AppInfoSer;
import com.hm.minibook.dto.api.RegisterResDTO;
import com.hm.utilities.Util;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class ActivationController implements Initializable
{

	@FXML
	TextField txtAgentKey;
	@FXML
	TextField txtClientKey;
	@FXML
	TextField txtName;

	@FXML
	Button btnCancel;
	@FXML
	Button btnRegister;
	@FXML
	Button btnUnregister;

	FXMLLoader myLoader;

	@Override
	public void initialize(URL url, ResourceBundle rb)
	{
		btnRegister.setOnAction((event) -> handleRegisterBtnClick());
		//btnUnregister.setOnAction((event) -> handleClickBtnDelete());
		btnCancel.setOnAction((event) -> handleCancelBtnClick());

		Platform.runLater(() -> txtAgentKey.requestFocus());

		myInit();
	}

	public void setMyLoader(FXMLLoader myLoader)
	{
		this.myLoader = myLoader;
	}

	private void myInit()
	{

		if (GV.regStatus == GV.RegStatus.REGISTERED)
		{
			AppInfoSer vObjAppInfoSer = new AppInfoSer();

			txtAgentKey.setText(
					vObjAppInfoSer.get(AppInfoSer.AppInfoKey.AgentKey)
			);

			txtClientKey.setText(
					vObjAppInfoSer.get(AppInfoSer.AppInfoKey.ClientKey)
			);

			txtName.setText(
					vObjAppInfoSer.get(AppInfoSer.AppInfoKey.Name)
			);

			txtAgentKey.setEditable(false);
			txtClientKey.setEditable(false);
			txtName.setEditable(false);
			btnRegister.setDisable(true);
		}
	}

	private boolean checkIsValid()
	{
		if (txtAgentKey.getText() == null || txtAgentKey.getText().equals(""))
		{
			StageHelper.showMessageBox("Key1 is required");
			return false;
		}
		else if (!this.mIsValidUUID(txtAgentKey.getText()))
		{
			StageHelper.showMessageBox("Key1 is invalid");
			return false;
		}
		else if (txtClientKey.getText() == null || txtClientKey.getText().equals(""))
		{
			StageHelper.showMessageBox("Key2 is required");
			return false;
		}
		else if (!this.mIsValidUUID(txtClientKey.getText()))
		{
			StageHelper.showMessageBox("Key2 is invalid");
			return false;
		}
		else if (txtName.getText() == null || txtName.getText().equals(""))
		{
			StageHelper.showMessageBox("Name is required");
			return false;
		}
		else if (!this.mIsValidName(txtName.getText()))
		{
			StageHelper.showMessageBox("Name can contain only alpha, numeric, space and dash only");
			return false;
		}

		return true;
	}

	private void handleRegisterBtnClick()
	{
		if (checkIsValid())
		{

			if (Util.mIsNetConnected())
			{

				AppInfoSer vObjAppInfoSer = new AppInfoSer();
				String instanceKey = vObjAppInfoSer.get(AppInfoSer.AppInfoKey.InstanceKey);

				RegisterResDTO vObjRegResModel = new APIHelper(
						txtAgentKey.getText(),
						txtClientKey.getText(),
						instanceKey,
						GV.APP_TYPE_ID,
						txtName.getText(),
						"",
						""
				).register();

				System.out.println(vObjRegResModel.isDone());
				System.out.println(vObjRegResModel.getCode());
				System.out.println(vObjRegResModel.getMsg());

				if (vObjRegResModel.isDone())
				{
					StageHelper.showMessageBox("Registered successfully");

					vObjAppInfoSer.set(AppInfoSer.AppInfoKey.StatusId, "2");
					vObjAppInfoSer.set(AppInfoSer.AppInfoKey.AgentKey, txtAgentKey.getText());
					vObjAppInfoSer.set(AppInfoSer.AppInfoKey.ClientKey, txtClientKey.getText());
					vObjAppInfoSer.set(AppInfoSer.AppInfoKey.Name, txtName.getText());

					GV.regStatus = GV.RegStatus.REGISTERED;

					handleCancelBtnClick();
				}
				else
				{
					StageHelper.showMessageBox(vObjRegResModel.getMsg());
				}
			}
			else
			{
				StageHelper.showMessageBox("Internet connection not available");
			}

		}
	}

	private void handleCancelBtnClick()
	{
		Container.stageHelper.loadNodesInExistingScene(StageHelper.getFXMLLoader(FXMLLayouts.GATEWAY));
	}

	private boolean mIsValidUUID(String vString)
	{
		return (vString.matches("[a-z0-9]{32}"));
	}

	private boolean mIsValidName(String vString)
	{
		return (vString.matches("[a-zA-Z0-9\\s\\-]+"));
	}

}
