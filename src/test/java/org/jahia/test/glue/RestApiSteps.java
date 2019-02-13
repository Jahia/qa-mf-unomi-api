package org.jahia.test.glue;

import static io.restassured.RestAssured.given;

import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.unomi.api.ContextRequest;
import org.apache.unomi.api.ContextResponse;
import org.apache.unomi.api.CustomItem;
import org.apache.unomi.api.Event;
import org.apache.unomi.api.Profile;
import org.apache.unomi.api.Session;
import org.apache.unomi.api.services.PersonalizationService.PersonalizationRequest;
import org.apache.unomi.persistence.spi.CustomObjectMapper;
import org.jahia.test.apiutils.AuthenticationHelper;
import org.jahia.test.data.RestApiRtVariables;
import org.jahia.test.data.TestGlobalConfiguration;
import org.jahia.test.utils.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;

import cucumber.api.DataTable;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import io.restassured.filter.log.ErrorLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class RestApiSteps
{
	Logger logger = LoggerFactory.getLogger(this.getClass());
	Response response;

	@When("^I delete the site \"([^\"]*)\" using the API$")
	public void i_delete_the_site_using_the_API(String siteKey) throws Throwable
	{
		try
		{
			String deleteUrl = TestGlobalConfiguration.getBaseUrl()
					+ "/modules/seleniumTests/api/site/" + siteKey;
			logger.info(String.format("Calling df-tests api to delete %s site. Url is: %s", siteKey,
					deleteUrl));
			response = given().relaxedHTTPSValidation().header("Accept", "application/json")
					.filter(new ErrorLoggingFilter()).contentType("application/json")
					.delete(deleteUrl);
			Util.waitForMillis(30000);
			logger.info(String.format("Delete %s site response code is %d  and body is %s", siteKey,
					response.getStatusCode(), response.getBody().asString()));

		}
		catch (Exception e)
		{
			logger.error(e.getMessage(), e);
		}
	}

	@Given("^I get the \"([^\"]*)\" page from DX server using REST$")
	public void i_get_the_page_from_DX_server_using_REST(String page) throws Throwable
	{
		if (!page.equals("home"))
			page = "home/" + page;

		response = given().relaxedHTTPSValidation().log().all().filter(new ResponseLoggingFilter())
				.contentType("application/json").get(TestGlobalConfiguration.getBaseUrl()
						+ "/sites/JahiaMfIntegTests/" + page + ".html");
	}

	@Given("^I post a context request to Unomi server with the previously defined elements and the following source$")
	public void i_post_a_context_request_to_Unomi_server_with_the_previously_defined_elements_and_the_following_source(
			DataTable sourceParamsDt) throws Throwable
	{
		ContextRequest contextRequest = new ContextRequest();
		contextRequest.setEvents(RestApiRtVariables.events);
		contextRequest.setRequiredProfileProperties(Arrays.asList("j:nodename"));
		contextRequest.setSessionId(RestApiRtVariables.storedIds.get("wem-session-id"));

		CustomItem sourceItem = new CustomItem(RestApiRtVariables.storedIds.get("pageID"), "page");
		sourceItem.setScope("JahiaMfIntegTests");
		contextRequest.setSource(sourceItem);

		contextRequest.setPersonalizations(RestApiRtVariables.personalizationRequests);

		RequestSpecification req = given().relaxedHTTPSValidation().log().all()
				.filter(new ResponseLoggingFilter());

		if (RestApiRtVariables.storedIds.get("context-profile-id") != null)
			req.cookie("context-profile-id",
					RestApiRtVariables.storedIds.get("context-profile-id"));
		req.header("X-Unomi-Peer", "670c26d1cc413346c3b2fd9ce65dab41");

		response = req.contentType("application/json").body(contextRequest).when()
				.post(TestGlobalConfiguration.getUnomiUrl() + "/context.json");
		RestApiRtVariables.clearContextRequestElements();
	}

	@Given("^I define a unomi context event with the following parameters$")
	public void i_define_a_unomi_context_event_with_the_following_parameters(
			DataTable eventParamsDt) throws Throwable
	{
		String profileId = RestApiRtVariables.storedIds.get("context-profile-id") != null
				? RestApiRtVariables.storedIds.get("context-profile-id")
				: "";
		Profile profile = new Profile(profileId);

		Session session;
		if (RestApiRtVariables.storedIds.get("wem-session-id") == null)
			session = null;
		else
			session = new Session(RestApiRtVariables.storedIds.get("wem-session-id"), profile,
					new Date(), "JahiaMfIntegTests");

		Map<String, String> eventParams = eventParamsDt.asMap(String.class, String.class);
		Event event;

		if (eventParams.get("EventType").equals("view"))
		{
			// Initialize context like if you display the first page on the website

			CustomItem target = new CustomItem(RestApiRtVariables.storedIds.get("pageID"), "page");
			target.setScope("JahiaMfIntegTests");

			Map<String, Object> pageInfo = new HashMap<>();
			pageInfo.put("pageID", RestApiRtVariables.storedIds.get("pageID"));
			pageInfo.put("nodeType", "jnt:page");
			pageInfo.put("pageName", eventParams.get("PageName"));
			pageInfo.put("pagePath", eventParams.get("PagePath"));
			pageInfo.put("templateName", "home");
			pageInfo.put("language", "en");
			pageInfo.put("referringURL", "");
			pageInfo.put("destinationUrl",
					"https://sneaker.jahia.com/sites/JahiaMfIntegTests/home.html");
			pageInfo.put("nodeType", "jnt:page");
			pageInfo.put("categories", Arrays.asList("/sites/systemsite/categories/qacathome"));
			pageInfo.put("tags", Arrays.asList("home"));
			pageInfo.put("isContentTemplate", false);

			Map<String, Object> properties = new HashMap<>();
			properties.put("pageInfo", pageInfo);
			target.setProperties(properties);

			CustomItem source = new CustomItem(RestApiRtVariables.storedIds.get("siteID"), "site");
			source.setScope("JahiaMfIntegTests");

			event = new Event(eventParams.get("EventType"), session, profile, "JahiaMfIntegTests",
					source, target, new Date());
		}

		else
		{
			Map<String, Object> properties = new HashMap<>();
			properties.put("targetType", "profile");
			properties.put("targetId", RestApiRtVariables.storedIds.get("context-profile-id"));

			Map<String, Object> addUpdate = new HashMap<>();
			addUpdate.put("properties.firstName", eventParams.get("FirstName"));
			addUpdate.put("properties.lastName", eventParams.get("LastName"));
			properties.put("add", addUpdate);

			CustomItem source = new CustomItem("wemProfile", "wemProfile");
			source.setScope("JahiaMfIntegTests");

			event = new Event(eventParams.get("EventType"), session, profile, "JahiaMfIntegTests",
					source, null, properties, new Date(), true);
		}

		RestApiRtVariables.events.add(event);

	}

	@Given("^I extract the personalization (\\d+) from the response$")
	public void i_extract_the_personalization_from_the_response(int personalizationIndex)
			throws Throwable
	{
		Pattern p = Pattern.compile("var personalization = (.+);");
		Matcher m = p.matcher(response.asString());

		int index = 1;
		while (m.find())
		{
			if (index == personalizationIndex)
			{
				RestApiRtVariables.personalizationRequests.add(CustomObjectMapper.getObjectMapper()
						.readValue(m.group(1), PersonalizationRequest.class));
			}
			index++;
		}
	}

	@Then("^The first personalization in the response returns (\\d+) variants ids$")
	public void the_first_personalization_in_the_response_returns_variants_ids(
			int expectedNbVariantsInFirstPerso) throws Throwable
	{
		ContextResponse contextResponse = CustomObjectMapper.getObjectMapper()
				.readValue(response.asString(), ContextResponse.class);
		if (contextResponse.getPersonalizations().size() == 0)
			throw new RuntimeException(
					String.format("Cannot get variants as there is no personalization in : %s",
							response.asString()));

		for (Map.Entry<String, List<String>> entry : contextResponse.getPersonalizations()
				.entrySet())
		{
			Assert.assertEquals(entry.getValue().size(), expectedNbVariantsInFirstPerso,
					String.format("Variants returned in perso %s are %s", entry.getKey(),
							entry.getValue()));
			break;
		}

	}

	@Given("^I extract the ids \"([^\"]*)\" from the response$")
	public void i_extract_the_ids_from_the_response(List<String> ids) throws Throwable
	{
		for (String id : ids)
		{
			Pattern p = Pattern.compile("\"" + id + "\": \"(.+?)\"");
			Matcher m = p.matcher(response.asString());

			// if an occurrence if a pattern was found in a given string...
			if (m.find())
			{
				RestApiRtVariables.storedIds.put(id, m.group(1));
			}
			else
				logger.error(String.format("Could not extract id %s. Found %d occurences using %s",
						id, m.groupCount(), p.pattern()));
		}
	}

	@Given("^I extract the ids \"([^\"]*)\" from the response headers$")
	public void i_extract_the_ids_from_the_response_headers(List<String> ids) throws Throwable
	{
		for (String id : ids)
		{
			Pattern p = Pattern.compile(id + "=(.+?);");
			Matcher m = p.matcher(response.headers().toString());

			// if an occurrence if a pattern was found in a given string...
			if (m.find())
			{
				RestApiRtVariables.storedIds.put(id, m.group(1));
			}
			else
				logger.error(String.format("Could not extract id %s. Found %d occurences using %s",
						id, m.groupCount(), p.pattern()));
		}
	}

	@Then("^the response code is (\\d+)$")
	public void the_response_code_is(int expectedCode) throws Throwable
	{
		Assert.assertEquals(response.getStatusCode(), expectedCode,
				"Status code is not as expected");
	}

	@Given("^I login with user \"([^\"]*)\" and password \"([^\"]*)\"$")
	public void i_login_with_user_and_password(String user, String password) throws Throwable
	{
		AuthenticationHelper auth = new AuthenticationHelper();
		String cookie = auth.getAuthenticationCookie(user, password);

		RestApiRtVariables.storedIds.put("Cookie",
				"JSESSIONID=" + cookie + "; Path=/; Secure; HttpOnly");
	}

}
