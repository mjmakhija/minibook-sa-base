package com.hm.minibook.controller;

import com.hm.minibook.Container;
import com.hm.minibook.FXMLLayouts;
import com.hm.minibook.StageHelper;
import com.hm.minibook.dao.app.LoginDAO;
import com.hm.minibook.dao.app.LoginDAOImpl;
import com.hm.minibook.dto.app.LoginDTO;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class LoginController implements Initializable
{

	@FXML
	TextField txtUsername;

	@FXML
	PasswordField txtPassword;

	@FXML
	Button btnLogin;

	LoginDAO loginDAO;

	@Override
	public void initialize(URL url, ResourceBundle rb)
	{
		btnLogin.setOnAction((event) -> handle());

		this.loginDAO = new LoginDAOImpl(Container.myORMApp);
	}

	public void handle()
	{
		if (checkIsValid())
		{
			LoginDTO l = loginDAO.findByUsername(txtUsername.getText());
			if (l != null && l.getPassword().equals(txtPassword.getText()))
			{
				Container.stageHelper.loadNodesInExistingScene(StageHelper.getFXMLLoader(FXMLLayouts.GATEWAY));
			}
			else
			{
				StageHelper.showMessageBox("Invalid username or password");
			}
		}
	}

	private boolean checkIsValid()
	{
		if (txtUsername.getText() == null || txtUsername.getText().equals(""))
		{
			StageHelper.showMessageBox("Username is required");
			return false;
		}
		else if (txtPassword.getText() == null || txtPassword.getText().equals(""))
		{
			StageHelper.showMessageBox("Password is required");
			return false;
		}

		return true;
	}

}
