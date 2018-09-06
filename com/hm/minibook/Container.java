package com.hm.minibook;

import com.hm.AutoWhatsapp.WhatsappHelper;
import com.hm.minibook.controller.WrapperController;
import com.hm.miniorm.MiniORM;
import com.hm.sqlite.SqliteDB;
import java.sql.Connection;
import java.util.logging.Logger;

public class Container
{

	public static StageHelper stageHelper;
	public static WrapperController wrapperController;
	public static Logger logger;
	public static WhatsappHelper whatsappHelper;

	//
	public static SqliteDB sqliteDBAPP;
	public static SqliteDB sqliteDBCompanyYear;

	public static Connection connApp;
	public static Connection connCompanyYear;

	public static MiniORM myORMApp;
	public static MiniORM myORMCompanyYear;

}
