package com.hm.minibook;

import com.hm.AutoWhatsapp.WhatsappHelper;
import java.io.IOException;
import java.util.logging.Level;
import javafx.application.Platform;

public class AppHelper
{

	public static void closeApp()
	{
		if (Container.sqliteDBAPP != null)
		{
			Container.sqliteDBAPP.closeConnection();
		}
		if (Container.sqliteDBCompanyYear != null)
		{
			Container.sqliteDBCompanyYear.closeConnection();
		}
		WhatsappHelper.close();
		Platform.exit();
		System.exit(0);
	}

	public static void startUpdateManager()
	{
		try
		{
			String[] vExecuteCmd = new String[]
			{
				"cmd.exe",
				"/c",
				GV.INSTALLATION_DIR + "/um.exe"
			};

			Runtime.getRuntime().exec(vExecuteCmd);

			closeApp();
		}
		catch (IOException ex)
		{
			Container.logger.log(Level.SEVERE, ex.getMessage(), ex);
		}
	}

	public static String getTitle()
	{
		String res = GV.APP_NAME;
		res = res + (GV.CompanyName.equals("") ? "" : " - " + GV.CompanyName);
		res = res + (GV.Year.equals("") ? "" : " - " + GV.Year);
		res = res + (GV.CurrentFormTitle.equals("") ? "" : " - " + GV.CurrentFormTitle);
		return res;
	}

}
