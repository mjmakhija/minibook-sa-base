package com.hm.minibook.thread;

import com.hm.minibook.Container;
import com.hm.minibook.DatabaseHelper;
import com.hm.minibook.GV;
import com.hm.minibook.RegistrationHelper;
import java.io.File;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.scene.control.Label;
import org.apache.commons.io.FileUtils;

public class StartupThread extends Task<Void>
{

	private final Label label;

	public StartupThread(Label label)
	{
		this.label = label;
	}

	@Override
	protected Void call() throws Exception
	{
		setLabelText("Cleaning temperory directory...");
		cleanTempDirectory();
		setLabelText("Starting database...");
		DatabaseHelper.connectDBApp();

		new RegistrationHelper().checkRegistration();

		Platform.runLater(new Runnable()
		{
			@Override
			public void run()
			{
				Container.stageHelper.closeSplashAndShowLogin();
			}
		});

		//new Thread(new UpdateThread(Database.getMyORMApp())).start();
		//new Thread(new VerifyOnServerThread(Database.getMyORMApp())).start();
		return null;
	}

	private void cleanTempDirectory()
	{
		try
		{
			FileUtils.cleanDirectory(new File(GV.TEMP_DIR));
		}
		catch (Exception e)
		{
		}
	}

	private void setLabelText(String string)
	{
		Platform.runLater(new Runnable()
		{
			@Override
			public void run()
			{
				label.setText(string);
			}
		});
	}

}
