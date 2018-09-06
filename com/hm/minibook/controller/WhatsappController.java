package com.hm.minibook.controller;

import com.hm.minibook.Container;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class WhatsappController implements Initializable
{

	@FXML
	TextField txtWhatsappStatus;

	@FXML
	TextField txtBrowserStatus;

	@FXML
	Button btnStart;

	@Override
	public void initialize(URL url, ResourceBundle rb)
	{
		btnStart.setOnAction((event) -> handleClickBtnSave());

		showStatus();
		
		Platform.runLater(() -> btnStart.requestFocus());
		
	}

	private void handleClickBtnSave()
	{
		
		Container.whatsappHelper.loadWhatsapp();
		
		showStatus();
		
	}

	private void showStatus()
	{
		if (Container.whatsappHelper.isWhatsappLoaded())
		{
			txtWhatsappStatus.setText("Loaded");
		}
		else
		{
			txtWhatsappStatus.setText("Not Loaded");
		}
	}
	
}
