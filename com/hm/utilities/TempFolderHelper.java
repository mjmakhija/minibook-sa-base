package com.hm.utilities;

import com.hm.minibook.GV;
import java.io.File;

public class TempFolderHelper
{

	private static TempFolderHelper tempFolderHelper = null;
	private String tempFolderPath;

	public static TempFolderHelper getInstance()
	{
		if (tempFolderHelper == null)
		{
			tempFolderHelper = new TempFolderHelper(GV.TEMP_DIR);
		}
		return tempFolderHelper;
	}

	public TempFolderHelper(String tempFolderPath)
	{
		this.tempFolderPath = tempFolderPath;
	}

	public String getNextAvlblPdfFileName()
	{
		String fileName;
		File file;
		int i = 0;
		boolean run = true;

		do
		{
			fileName = tempFolderPath + "/file" + String.valueOf(i) + ".pdf";
			file = new File(fileName);

			if (file.exists())
			{
				if (file.delete())
				{
					run = false;
				}
				else
				{
					i++;
				}

			}
			else
			{
				run = false;
			}

		}
		while (run);

		return fileName;
	}

}
