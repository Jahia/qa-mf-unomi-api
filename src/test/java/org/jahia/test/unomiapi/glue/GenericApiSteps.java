package org.jahia.test.unomiapi.glue;

import static io.restassured.RestAssured.given;

import org.jahia.test.unomiapi.data.TestGlobalConfiguration;
import org.jahia.test.unomiapi.data.TestRtVariables;
import org.jahia.test.unomiapi.utils.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cucumber.api.java.en.When;
import io.restassured.filter.log.ErrorLoggingFilter;

public class GenericApiSteps
{
	Logger logger = LoggerFactory.getLogger(this.getClass());

	@When("^I delete the site \"([^\"]*)\"$")
	public void i_delete_the_site(String siteKey) throws Throwable
	{
		try
		{
			String deleteUrl = TestGlobalConfiguration.getBaseUrl()
					+ "/modules/seleniumTests/api/site/" + siteKey;
			logger.info(String.format("Calling df-tests api to delete %s site. Url is: %s", siteKey,
					deleteUrl));
			TestRtVariables.response = given().relaxedHTTPSValidation()
					.header("Accept", "application/json").filter(new ErrorLoggingFilter())
					.contentType("application/json").delete(deleteUrl);
			Util.waitForMillis(30000);
			logger.info(String.format("Delete %s site response code is %d  and body is %s", siteKey,
					TestRtVariables.response.getStatusCode(),
					TestRtVariables.response.getBody().asString()));

		}
		catch (Exception e)
		{
			logger.error(e.getMessage(), e);
		}
	}

}
