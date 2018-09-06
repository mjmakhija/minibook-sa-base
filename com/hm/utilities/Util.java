package com.hm.utilities;

import com.hm.minibook.Container;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.Map;
import java.util.logging.Level;

public class Util
{

	final static String APP_DATE_FORMAT = "dd-MM-yyyy";
	final static String DB_DATE_FORMAT = "yyyy-MM-dd";

	public static boolean isDateValid(String date)
	{
		try
		{
			DateFormat df = new SimpleDateFormat(APP_DATE_FORMAT);
			df.setLenient(false);
			df.parse(date);
			return true;
		}
		catch (Exception ex)
		{
			Container.logger.log(Level.SEVERE, ex.getMessage(), ex);
			return false;
		}
	}

	public static String getDBDateString(Date date)
	{
		return new SimpleDateFormat(DB_DATE_FORMAT).format(date);
	}

	public static Date mConvertStringToDate(String vDate)
	{
		try
		{
			Date objDate = new SimpleDateFormat(APP_DATE_FORMAT).parse(vDate);
			return objDate;
		}
		catch (Exception ex)
		{
			Container.logger.log(Level.SEVERE, ex.getMessage(), ex);
			return null;
		}
	}

	public static LocalDate getLocalDate(Date date)
	{
		return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
	}

	public static Date getDate(LocalDate localDate)
	{
		Instant instant = Instant.from(localDate.atStartOfDay(ZoneId.systemDefault()));
		Date date = Date.from(instant);
		return date;
	}

	public static Date mConvertStringToDate2(String vDate)
	{
		try
		{
			Date objDate = new SimpleDateFormat("d-M-y").parse(vDate);
			return objDate;
		}
		catch (Exception ex)
		{
			Container.logger.log(Level.SEVERE, ex.getMessage(), ex);
			return null;
		}
	}

	public static Date mTemp(String date)
	{
		try
		{
			Date objDate = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss a").parse(date);
			return objDate;
		}
		catch (Exception ex)
		{
			Container.logger.log(Level.SEVERE, ex.getMessage(), ex);
			return null;
		}
	}

	public static String mGetLocalDateFormat(Date vDate)
	{
		return new SimpleDateFormat("dd-MM-yyyy").format(vDate);
	}

	public static String mGetLocalDateTime(Date vDate)
	{
		return new SimpleDateFormat("dd-MM-yyyy hh:mm:ss a").format(vDate);
	}

	public static String getMonthName(int month)
	{
		String[] monthNames = new String[]
		{
			"Jan",
			"Feb",
			"Mar",
			"Apr",
			"May",
			"Jun",
			"Jul",
			"Aug",
			"Sep",
			"Oct",
			"Nov",
			"Dec",
		};

		if (month < 0 || month > 12)
		{
			return "";
		}

		return monthNames[month - 1];

	}

	public static boolean mIsNetConnected(String vUrl)
	{
		try
		{
			try
			{
				URL url = new URL(vUrl);
				//System.out.println(url.getHost());
				HttpURLConnection con = (HttpURLConnection) url
						.openConnection();
				con.connect();
				if (con.getResponseCode() == 200)
				{
					//System.out.println("Connection established!!");
					return true;
				}

				return false;
			}
			catch (Exception ex)
			{
				Container.logger.log(Level.SEVERE, ex.getMessage(), ex);
				return false;
			}
		}
		catch (Exception ex)
		{
			Container.logger.log(Level.SEVERE, ex.getMessage(), ex);
			return false;
		}

	}

	public static boolean mIsNetConnected()
	{
		return Util.mIsNetConnected("https://www.google.com");
	}

	public static boolean isNumeric(String s)
	{
		return s != null && s.matches("[-+]?\\d*\\.?\\d+");
	}

	public static String readFile(String filePath) throws IOException
	{
		return new String(Files.readAllBytes(Paths.get(filePath)));
	}

	public static boolean isDouble(String s)
	{
		try
		{
			Double.parseDouble(s);
			return true;
		}
		catch (Exception e)
		{
		}

		return false;
	}

	public static void copyFileUsingStream(File source, File dest) throws IOException
	{
		InputStream is = null;
		OutputStream os = null;
		try
		{
			is = new FileInputStream(source);
			os = new FileOutputStream(dest);
			byte[] buffer = new byte[1024];
			int length;
			while ((length = is.read(buffer)) > 0)
			{
				os.write(buffer, 0, length);
			}
		}
		finally
		{
			is.close();
			os.close();
		}
	}

	public static String converNumberToLocalFormat(double d)
	{
		DecimalFormat df = new DecimalFormat("##,##,##,##0.00");
		//df.setRoundingMode(RoundingMode.HALF_UP);
		return df.format(d);
	}

	public static String converRateToLocalFormat(double d)
	{
		DecimalFormat df = new DecimalFormat("##,##,##,###.###");
		//df.setRoundingMode(RoundingMode.HALF_UP);
		return df.format(d);
	}

	public static String converPercentageToLocalFormat(double d)
	{
		DecimalFormat df = new DecimalFormat("##########.##");
		//df.setRoundingMode(RoundingMode.HALF_UP);
		return df.format(d);
	}

	public static String formatString(String pattern, Map arguments)
	{
		Map<String, Object> entries = arguments;
		for (Map.Entry<String, Object> entry : entries.entrySet())
		{
			pattern = pattern.replace("{" + entry.getKey() + "}", String.valueOf(entry.getValue()));
		}

		return pattern;
	}

}
