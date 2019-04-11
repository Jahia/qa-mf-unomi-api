package org.jahia.test.unomiapi.glue;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jahia.test.unomiapi.data.UnomiApiTestRtVariables;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;

public class ResponseSteps
{
	Logger logger = LoggerFactory.getLogger(this.getClass());

	@Given("^I extract the ids \"([^\"]*)\" from the response$")
	public void i_extract_the_ids_from_the_response(List<String> ids) throws Throwable
	{
		for (String id : ids)
		{
			Pattern p = Pattern.compile("\"" + id + "\": \"(.+?)\"");
			Matcher m = p.matcher(UnomiApiTestRtVariables.response.asString());

			// if an occurrence if a pattern was found in a given string...
			if (m.find())
			{
				UnomiApiTestRtVariables.storedIds.put(id, m.group(1));
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
			Matcher m = p.matcher(UnomiApiTestRtVariables.response.headers().toString());

			// if an occurrence if a pattern was found in a given string...
			if (m.find())
			{
				UnomiApiTestRtVariables.storedIds.put(id, m.group(1));
			}
			else
				logger.error(String.format("Could not extract id %s. Found %d occurences using %s",
						id, m.groupCount(), p.pattern()));
		}
	}

	@Then("^the response code is (\\d+)$")
	public void the_response_code_is(int expectedCode) throws Throwable
	{
		Assert.assertEquals(UnomiApiTestRtVariables.response.getStatusCode(), expectedCode,
				"Status code is not as expected");
	}

}
