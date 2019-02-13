package org.jahia.test.utils;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.jahia.test.data.TestGlobalConfiguration;
import org.jahia.test.data.TestRtVariables;

public class Util
{
	public static void waitForMillis(int milliSeconds)
	{
		try
		{
			Thread.sleep(milliSeconds);
		}
		catch (InterruptedException e)
		{
			e.printStackTrace();
		}
	}

	public static boolean isInteger(String str)
	{
		if (str == null)
		{
			return false;
		}
		if (str.isEmpty())
		{
			return false;
		}
		int i = 0;
		if (str.charAt(0) == '-')
		{
			if (str.length() == 1)
			{
				return false;
			}
			i = 1;
		}
		for (; i < str.length(); i++)
		{
			char c = str.charAt(i);
			if (c < '0' || c > '9')
			{
				return false;
			}
		}
		return true;
	}

	public static void writeFile(byte[] byteArray, File file)
	{
		try
		{
			File directory = file.getParentFile();
			if (!directory.exists())
				directory.mkdirs();

			FileUtils.writeByteArrayToFile(file, byteArray);
			TestRtVariables.lastDownloadedFile = file;
		}
		catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void writeFile(byte[] byteArray, File directory, String fileName)
	{
		try
		{
			if (!directory.exists())
				directory.mkdirs();

			File file = new File(TestGlobalConfiguration.getDownloadsPath() + fileName);
			FileUtils.writeByteArrayToFile(file, byteArray);
			TestRtVariables.lastDownloadedFile = file;
		}
		catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
