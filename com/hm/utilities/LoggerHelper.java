package com.hm.utilities;

import java.io.File;
import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;
import org.apache.commons.io.FileUtils;

public class LoggerHelper
{

	private static Logger logger = null;
	private static Handler fileHandler = null;

	public static Logger getLogger()
	{
		return logger;
	}

	public static void setupLogger(String appName, String folderPath, String fileName)
	{
		logger = Logger.getLogger(appName);

		//Handler consoleHandler = null;
		SimpleFormatter simpleFormatter = null;

		try
		{

			File folderPathFile = new File(folderPath);
			if (!folderPathFile.exists())
			{
				FileUtils.forceMkdir(folderPathFile);
			}

			// Creating FileHandler
			//consoleHandler = new ConsoleHandler();
			fileHandler = new FileHandler(folderPath + "/" + fileName, true);

			//Assigning handlers to logger object
			//logger.addHandler(consoleHandler);
			logger.addHandler(fileHandler);

			//Setting levels to handlers and logger
			//consoleHandler.setLevel(Level.ALL);
			fileHandler.setLevel(Level.ALL);
			logger.setLevel(Level.ALL);

			// Creating SimpleFormatter
			simpleFormatter = new SimpleFormatter();
			// Setting formatter to the handler
			fileHandler.setFormatter(simpleFormatter);

			// Logging message of Level info (this should be publish in the default format i.e. XMLFormat)
		}
		catch (IOException exception)
		{
			logger.log(Level.SEVERE, "Error occur in FileHandler.", exception);
		}
	}

	public static void close()
	{
		if (logger == null)
		{
			return;
		}

		try
		{
			fileHandler.close();
			logger.removeHandler(fileHandler);
		}
		catch (Exception e)
		{
			logger.log(Level.SEVERE, "Problem closing and removing logger file handler", e);
		}

	}

}
