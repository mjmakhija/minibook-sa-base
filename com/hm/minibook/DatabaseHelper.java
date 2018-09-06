package com.hm.minibook;

import com.hm.miniorm.MiniORM;
import com.hm.sqlite.SqliteDB;
import com.hm.utilities.Util;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Level;

public class DatabaseHelper
{

	public static int vSelectedYear;

	public static boolean connectDBApp()
	{
		try
		{
			Container.sqliteDBAPP = new SqliteDB(GV.DB_DIR + "/" + GV.DB_APP + GV.DB_EXT, Container.logger);
			Container.sqliteDBAPP.createConnection();
			Container.connApp = Container.sqliteDBAPP.getConnection();
			Container.myORMApp = new MiniORM(Container.connApp, Container.logger);

			return true;
		}
		catch (Exception ex)
		{
			Container.logger.log(Level.SEVERE, ex.getMessage(), ex);
			return false;
		}
	}

	public static void connectDBCompanyYear(String vDBName)
	{
		try
		{
			if (Container.connCompanyYear != null && !Container.connCompanyYear.isClosed())
			{
				Container.connCompanyYear.close();
			}

			Container.sqliteDBCompanyYear = new SqliteDB(GV.DB_DIR + "/" + vDBName + GV.DB_EXT, Container.logger);
			Container.sqliteDBCompanyYear.createConnection();
			Container.connCompanyYear = Container.sqliteDBCompanyYear.getConnection();
			Container.myORMCompanyYear = new MiniORM(Container.connCompanyYear, Container.logger);

		}
		catch (SQLException ex)
		{
			Container.logger.log(Level.SEVERE, ex.getMessage(), ex);
		}

	}

	public static void createDB(String dbId)
	{
		try
		{
			Util.copyFileUsingStream(new File(GV.DB_DIR + "/" + GV.DB_BLANK + GV.DB_EXT), new File(GV.DB_DIR + "/" + dbId + GV.DB_EXT));
		}
		catch (IOException ex)
		{
			Container.logger.log(Level.SEVERE, ex.getMessage(), ex);
		}
	}

	public static void deleteDB(String dbId)
	{
		new File(GV.DB_DIR + "/" + dbId).delete();
	}

}
