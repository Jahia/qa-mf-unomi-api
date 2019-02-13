package org.jahia.test.data;

import java.io.File;

public class TestRtVariables
{
	public static boolean fixtureFailed = false;
	public static String siteLocale;
	public static long storedTimeMillis;
	public static String browserStackTestName;
	public static boolean ensureCleanSession;
	public static File[] previousFiles;
	public static File lastDownloadedFile;

	// init all with default values
	public static void init()
	{
		siteLocale = "en";
		storedTimeMillis = 0000;
		browserStackTestName = "undefined";
		ensureCleanSession = true;
		previousFiles = null;
		lastDownloadedFile = null;
	}
}
