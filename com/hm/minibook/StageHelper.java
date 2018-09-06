package com.hm.minibook;

import java.io.IOException;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class StageHelper
{

	Stage stage;

	public StageHelper(Stage stage)
	{
		this.stage = stage;
	}

	public void closeSplashAndShowLogin()
	{	
		stage.close();

		stage = new Stage(StageStyle.DECORATED);
		stage.getIcons().add(getIcon());
		stage.setOnCloseRequest((event) ->
		{
			if (showConfirmBox("Are you sure?"))
			{
				AppHelper.closeApp();
			}
			else
			{
				event.consume();
			}

		});
		FXMLLoader fxmll = getFXMLLoader(FXMLLayouts.LOGIN);
		Parent root = fxmll.getRoot();
		createNewScene(stage, root);
		setTitle();
		stage.setMaximized(true);
		stage.show();
	}

	public void loadNodesInExistingScene(FXMLLoader fXMLLoader)
	{
		Parent root = null;

		root = fXMLLoader.getRoot();
		//root = fXMLLoader.load();

		stage.getScene().setRoot(root);
		setTitle();

	}

	public static void showMessageBox(String str)
	{
		Alert alert = new Alert(Alert.AlertType.INFORMATION);
		alert.setTitle("Message");
		alert.setHeaderText(null);
		alert.setContentText(str);
		Stage s = (Stage) alert.getDialogPane().getScene().getWindow();
		alert.getDialogPane().getScene().getStylesheets().add(getCss());
		s.getIcons().add(getIcon());
		alert.showAndWait();
	}

	public static boolean showConfirmBox(String str)
	{
		Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
		alert.setTitle("Message");
		alert.setHeaderText(null);
		alert.setContentText(str);
		Stage s = (Stage) alert.getDialogPane().getScene().getWindow();
		alert.getDialogPane().getScene().getStylesheets().add(getCss());
		s.getIcons().add(getIcon());
		Optional<ButtonType> result = alert.showAndWait();

		return (result.get() == ButtonType.OK);

	}

	public static FXMLLoader getFXMLLoader(FXMLLayouts sceneType)
	{
		ResourceBundle resources = ResourceBundle.getBundle("language/en_IN");
		FXMLLoader fXMLLoader = new FXMLLoader(Main.class.getClassLoader().getResource(sceneType.toString()), resources);
		try
		{
			fXMLLoader.load();
		}
		catch (IOException ex)
		{
			Container.logger.log(Level.SEVERE,  ex.getMessage(), ex);
		}
		return fXMLLoader;
	}

	public void setTitle()
	{
		stage.setTitle(AppHelper.getTitle());
	}

	public void showSplashScreen(FXMLLoader fXMLLoader)
	{
		stage.initStyle(StageStyle.UNDECORATED);
		Rectangle2D primScreenBounds = Screen.getPrimary().getVisualBounds();
		stage.setX((primScreenBounds.getWidth() - 500) / 2);
		stage.setY((primScreenBounds.getHeight() - 265) / 2);
		stage.setOnCloseRequest((event) -> AppHelper.closeApp());
		stage.getIcons().add(getIcon());
		Parent root = fXMLLoader.getRoot();
		createNewScene(stage, root);
		stage.show();
	}

	private static Image getIcon()
	{
		return new Image(StageHelper.class.getClassLoader().getResourceAsStream("resources/img/icon.png"));
	}

	private static String getCss()
	{
		return StageHelper.class.getClassLoader().getResource("resources/css/style.css").toExternalForm();
	}

	private static void createNewScene(Stage stage, Parent root)
	{
		Scene scene = new Scene(root);
		scene.getStylesheets().add(getCss());
		stage.setScene(scene);
	}

}
