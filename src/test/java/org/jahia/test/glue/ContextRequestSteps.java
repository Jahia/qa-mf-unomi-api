package org.jahia.test.glue;

import java.net.URL;
import java.util.Arrays;

import org.apache.unomi.api.ContextRequest;
import org.apache.unomi.api.CustomItem;
import org.jahia.test.apiutils.RestRequestHelper;
import org.jahia.test.data.TestGlobalConfiguration;
import org.jahia.test.data.TestRtVariables;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mashape.unirest.http.HttpMethod;

import cucumber.api.java.en.Given;
import io.restassured.http.ContentType;
import io.restassured.http.Cookie;
import io.restassured.http.Cookies;
import io.restassured.http.Header;
import io.restassured.http.Headers;
import io.restassured.specification.RequestSpecification;

public class ContextRequestSteps
{
	Logger logger = LoggerFactory.getLogger(this.getClass());
	private final String contextJsonUrl = TestGlobalConfiguration.getUnomiUrl() + "/context.json";

	@Given("^I post a context request to Unomi server with the previously defined elements$")
	public void i_post_a_context_request_to_Unomi_server_with_the_previously_defined_elements()
			throws Throwable
	{
		ContextRequest contextRequest = new ContextRequest();
		contextRequest.setEvents(TestRtVariables.events);
		contextRequest.setRequiredProfileProperties(Arrays.asList("j:nodename"));
		contextRequest.setSessionId(TestRtVariables.storedIds.get("wem-session-id"));

		CustomItem sourceItem = new CustomItem(TestRtVariables.storedIds.get("pageID"), "page");
		sourceItem.setScope(TestRtVariables.scope);
		contextRequest.setSource(sourceItem);

		contextRequest.setPersonalizations(TestRtVariables.personalizationRequests);

		RestRequestHelper reqHelper = new RestRequestHelper();
		RequestSpecification req = buildContextRequestSpec(reqHelper);
		TestRtVariables.response = reqHelper.sendRequest(req, new URL(contextJsonUrl),
				contextRequest, HttpMethod.POST);

		TestRtVariables.clearContextRequestElements();
	}

	private RequestSpecification buildContextRequestSpec(RestRequestHelper reqHelper)
	{
		RequestSpecification req;
		Header header = new Header("X-Unomi-Peer", TestGlobalConfiguration.getUnomiKey());
		Cookie cookie = null;
		if (TestRtVariables.storedIds.get("context-profile-id") != null)
			cookie = new Cookie.Builder("context-profile-id",
					TestRtVariables.storedIds.get("context-profile-id")).setSecured(false).build();

		if (cookie != null)
			req = reqHelper.buildRequest(ContentType.JSON, new Headers(header),
					new Cookies(cookie));
		else
			req = reqHelper.buildRequest(ContentType.JSON, new Headers(header));
		return req;
	}

}
