package org.jahia.test.glue;

import java.net.URL;

import org.jahia.test.apiutils.AuthenticationHelper;
import org.jahia.test.apiutils.RestRequestHelper;
import org.jahia.test.data.TestGlobalConfiguration;
import org.jahia.test.data.TestRtVariables;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mashape.unirest.http.HttpMethod;

import cucumber.api.java.en.Given;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

public class DxSteps
{
	Logger logger = LoggerFactory.getLogger(this.getClass());

	@Given("^I get the \"([^\"]*)\" page from DX server$")
	public void i_get_the_page_from_DX_server(String page) throws Throwable
	{
		if (!page.equals("home"))
			page = "home/" + page;

		RestRequestHelper reqHelper = new RestRequestHelper();
		RequestSpecification req = reqHelper.buildRequest(ContentType.JSON);

		TestRtVariables.response = reqHelper
				.sendRequest(req,
						new URL(TestGlobalConfiguration.getBaseUrl() + "/sites/"
								+ TestRtVariables.scope + "/" + page + ".html"),
						null, HttpMethod.GET);
	}

	@Given("^I login with user \"([^\"]*)\" and password \"([^\"]*)\"$")
	public void i_login_with_user_and_password(String user, String password) throws Throwable
	{
		AuthenticationHelper auth = new AuthenticationHelper();
		String cookie = auth.getAuthenticationCookie(user, password);

		TestRtVariables.storedIds.put("Cookie",
				"JSESSIONID=" + cookie + "; Path=/; Secure; HttpOnly");
	}

}
