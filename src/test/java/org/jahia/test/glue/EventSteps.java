package org.jahia.test.glue;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.unomi.api.CustomItem;
import org.apache.unomi.api.Event;
import org.apache.unomi.api.Profile;
import org.apache.unomi.api.Session;
import org.jahia.test.data.TestGlobalConfiguration;
import org.jahia.test.data.TestRtVariables;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cucumber.api.DataTable;
import cucumber.api.java.en.Given;
import io.restassured.response.Response;

public class EventSteps
{
	Logger logger = LoggerFactory.getLogger(this.getClass());
	Response response;

	@Given("^I define a unomi context event with the following parameters$")
	public void i_define_a_unomi_context_event_with_the_following_parameters(
			DataTable eventParamsDt) throws Throwable
	{
		String profileId = TestRtVariables.storedIds.get("context-profile-id") != null
				? TestRtVariables.storedIds.get("context-profile-id")
				: "";
		Profile profile = new Profile(profileId);

		Session session;
		if (TestRtVariables.storedIds.get("wem-session-id") == null)
			session = null;
		else
			session = new Session(TestRtVariables.storedIds.get("wem-session-id"), profile,
					new Date(), TestRtVariables.scope);

		Map<String, String> eventParams = eventParamsDt.asMap(String.class, String.class);
		Event event;

		if (eventParams.get("eventType").equals("view"))
		{
			// Initialize context like if you display the first page on the website

			CustomItem target = new CustomItem(TestRtVariables.storedIds.get("pageID"), "page");
			target.setScope(TestRtVariables.scope);

			Map<String, Object> pageInfo = new HashMap<>();
			pageInfo.put("pageID", TestRtVariables.storedIds.get("pageID"));
			pageInfo.put("nodeType", "jnt:page");
			pageInfo.put("pageName", eventParams.get("pageName"));
			pageInfo.put("pagePath", eventParams.get("pagePath"));
			pageInfo.put("templateName", eventParams.get("templateName"));
			pageInfo.put("language", TestRtVariables.siteLocale);
			pageInfo.put("referringURL", "");

			pageInfo.put("destinationUrl",
					TestGlobalConfiguration.getBaseUrl() + eventParams.get("pagePath"));

			pageInfo.put("nodeType", "jnt:page");

			String[] categories = new String[0];
			if (eventParams.get("categories") != null)
				categories = eventParams.get("categories").split(",");
			pageInfo.put("categories", categories);

			String[] tags = new String[0];
			if (eventParams.get("tags") != null)
				tags = eventParams.get("tags").split(",");
			pageInfo.put("tags", tags);

			if (eventParams.get("isContentTemplate") != null)
				pageInfo.put("isContentTemplate",
						Boolean.parseBoolean(eventParams.get("isContentTemplate")));
			else
				pageInfo.put("isContentTemplate", false);

			Map<String, Object> properties = new HashMap<>();
			properties.put("pageInfo", pageInfo);
			target.setProperties(properties);

			CustomItem source = new CustomItem(TestRtVariables.storedIds.get("siteID"), "site");
			source.setScope(TestRtVariables.scope);

			event = new Event(eventParams.get("EventType"), session, profile, TestRtVariables.scope,
					source, target, new Date());
		}

		else if (eventParams.get("eventType").equals("updateProperties"))
		{
			Map<String, Object> properties = new HashMap<>();
			properties.put("targetType", "profile");
			properties.put("targetId", TestRtVariables.storedIds.get("context-profile-id"));

			Map<String, Object> addUpdate = new HashMap<>();
			for (Map.Entry<String, String> entry : eventParams.entrySet())
			{
				if (entry.getKey().startsWith("properties."))
					addUpdate.put(entry.getKey(), entry.getValue());
			}
			properties.put(eventParams.get("updateType"), addUpdate);

			CustomItem source = new CustomItem("wemProfile", "wemProfile");
			source.setScope(TestRtVariables.scope);

			event = new Event(eventParams.get("eventType"), session, profile, TestRtVariables.scope,
					source, null, properties, new Date(), true);
		}

		else
			throw new RuntimeException("EventType unknown, please check the feature file");

		TestRtVariables.events.add(event);

	}

}
