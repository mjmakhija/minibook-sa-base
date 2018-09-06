package com.hm.minibook.controller;

import com.hm.minibook.Container;
import com.hm.minibook.GV;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class SplashScreenController implements Initializable
{

	@FXML
	ImageView imageView;

	@FXML
	Label label;

	@Override
	public void initialize(URL url, ResourceBundle rb)
	{
		FileInputStream input = null;
		try
		{
			input = new FileInputStream(GV.IMAGE_DIR + "/splash.jpg");
			imageView.setImage(new Image(input));
		}
		catch (FileNotFoundException ex)
		{
			Container.logger.log(Level.SEVERE, ex.getMessage(), ex);
		}
		finally
		{
			try
			{
				input.close();
			}
			catch (IOException ex)
			{
				Container.logger.log(Level.SEVERE, ex.getMessage(), ex);
			}
		}
	}

	public Label getLabel()
	{
		return label;
	}

}
