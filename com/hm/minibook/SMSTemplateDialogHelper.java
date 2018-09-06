package com.hm.minibook;

import com.hm.minibook.controller.SMSTemplateDialogController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class SMSTemplateDialogHelper
{

	public static SMSTemplateDialogController getSMSTemplateDialog()
	{
		FXMLLoader loader = StageHelper.getFXMLLoader(FXMLLayouts.SMS_TEMPLATE_DIALOG);
		SMSTemplateDialogController smstdc = null;
		// initializing the controller
		AnchorPane layout;

		layout = loader.getRoot();
		smstdc = loader.getController();
		// this is the popup stage
		Scene scene = new Scene(layout);
		Stage popupStage = new Stage();
		popupStage.setScene(scene);
		popupStage.initOwner(Container.stageHelper.stage);
		// Giving the popup controller access to the popup stage (to allow the controller to close the stage) 
		popupStage.initModality(Modality.WINDOW_MODAL);
		smstdc.setStage(popupStage);

		return smstdc;

	}

	public static String getSMSTemplate()
	{
		SMSTemplateDialogController smstdc = getSMSTemplateDialog();
		String message;
		if (smstdc != null && smstdc.showAndWait())
		{
			message = smstdc.getMessage();
			if (message == null || message.isEmpty())
			{
				StageHelper.showMessageBox("No message");
				return null;
			}
			return message;
		}
		return null;
	}

}
