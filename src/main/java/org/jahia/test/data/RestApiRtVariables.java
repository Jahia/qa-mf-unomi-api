package org.jahia.test.data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.unomi.api.Event;
import org.apache.unomi.api.services.PersonalizationService.PersonalizationRequest;

public class RestApiRtVariables
{
	// real time scenario variables
	// initialized each scenario
	public static Map<String, String> storedIds = new HashMap<String, String>();
	public static List<Event> events = new ArrayList<Event>();
	public static List<PersonalizationRequest> personalizationRequests;

	// init all with default values
	public static void init()
	{
		storedIds = new HashMap<String, String>();
		events = new ArrayList<Event>();
		personalizationRequests = new ArrayList<PersonalizationRequest>();
	}

	public static void clearContextRequestElements()
	{
		events.clear();
		personalizationRequests.clear();
	}

}
