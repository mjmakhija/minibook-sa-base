package com.hm.utilities;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelHelper
{

	public static void createExcel(String data[][]) throws FileNotFoundException, java.io.IOException
	{

		XSSFWorkbook wb = new XSSFWorkbook(); //Excell workbook
		XSSFSheet sheet = wb.createSheet(); //WorkSheet
		XSSFRow sheetRow; //Row created at line 3

		sheetRow = createRow(sheet); //Create row at line 0

		for (int tableRow = 0; tableRow < data.length; tableRow++)
		{ //For each table row
			sheetRow = createRow(sheet);

			for (int tableRowCol = 0; tableRowCol < data[tableRow].length; tableRowCol++)
			{ //For each table column

				String value;
				if (data[tableRow][tableRowCol] == null)
				{
					value = "";
				}
				else
				{
					value = String.valueOf(data[tableRow][tableRowCol]);
				}

				createCell(sheetRow).setCellValue(value); //Write value
			}

		}
		wb.write(new FileOutputStream("D:/message_log.xlsx"));//Save the file     
	}

	public static XSSFRow createRow(XSSFSheet sheet)
	{
		if (sheet.getRow(sheet.getLastRowNum()) == null)
		{
			return sheet.createRow(sheet.getLastRowNum());
		}
		else
		{
			return sheet.createRow(sheet.getLastRowNum() + 1);
		}
	}

	public static XSSFCell createCell(XSSFRow row)
	{
		return row.createCell(row.getLastCellNum() == -1 ? 0 : row.getLastCellNum());
	}
}
