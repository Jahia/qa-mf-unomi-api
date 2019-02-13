package org.jahia.test.data;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.unomi.api.Event;
import org.apache.unomi.api.services.PersonalizationService.PersonalizationRequest;

import io.restassured.response.Response;

public class TestRtVariables
{

	// real time scenario variables
	// initialized each scenario
	public static Map<String, String> storedIds = new HashMap<String, String>();
	public static List<Event> events = new ArrayList<Event>();
	public static List<PersonalizationRequest> personalizationRequests;
	public static boolean fixtureFailed = false;
	public static String siteLocale;
	public static long storedTimeMillis;
	public static String scenarioName;
	public static boolean ensureCleanSession;
	public static File[] previousFiles;
	public static File lastDownloadedFile;
	public static String scope;
	public static Response response;

	// init all with default values
	public static void reset()
	{
		siteLocale = "en";
		storedTimeMillis = 0000;
		scenarioName = "undefined";
		ensureCleanSession = true;
		previousFiles = null;
		lastDownloadedFile = null;
		storedIds = new HashMap<String, String>();
		events = new ArrayList<Event>();
		personalizationRequests = new ArrayList<PersonalizationRequest>();
		scope = "JahiaMfIntegTests";
		response = null;
	}

	public static void clearContextRequestElements()
	{
		events.clear();
		personalizationRequests.clear();
	}

}
