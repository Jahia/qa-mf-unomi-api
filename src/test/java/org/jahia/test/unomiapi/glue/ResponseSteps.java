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
			Pattern p = Pattern.compile("\"" + id + "\"\\s*:\\s*\"(.+?)\"");
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

	@Given("^I extract the variant id corresponding to the displayable name \"([^\"]*)\" from the response$")
	public void i_extract_the_variant_id_corresponding_to_the_displayable_name_from_the_response(
			String displayableName) throws Throwable
	{

		// ex: "displayableName":"qa automaton","id":"42889b74-06d4-4a64-bf1e-74642625367e"
		Pattern p = Pattern.compile(String.format(
				"\"displayableName\"\\s*:\\s*\"%s\",\"id\"\\s*:\\s*\"(.+?)\"", displayableName));
		Matcher m = p.matcher(UnomiApiTestRtVariables.response.asString());

		// if an occurrence if a pattern was found in a given string...
		if (m.find())
		{
			UnomiApiTestRtVariables.storedIds.put(displayableName, m.group(1));
		}
		else
			throw new RuntimeException(String.format(
					"Could not extract variant id corresponding to displaybale name %s using regexp %s",
					displayableName, p.pattern()));
	}

	@Then("^I extract the displayed variant (\\d+) id from the response$")
	public void i_extract_the_displayed_variant_id_from_the_response(int variantIndex)
			throws Throwable
	{
		// ex: dispatchVariantJSEvent(variants['25cfea0d-49d9-4757-8a0d-d40b596dc585'],
		// 'personalization');
		Pattern p = Pattern.compile("dispatchVariantJSEvent\\(variants\\['(.+?)'");
		Matcher m = p.matcher(UnomiApiTestRtVariables.response.asString());

		int index = 1;
		boolean found = false;
		while (m.find())
		{
			if (index == variantIndex)
			{
				found = true;
				UnomiApiTestRtVariables.storedIds.put("displayedVariantId", m.group(1));
			}
			index++;
		}

		if (!found)
			throw new RuntimeException(
					String.format("Could not extract displayed variant %d id using regexp %s",
							variantIndex, p.pattern()));
	}

	@Then("^The displayed variant id corresponds to the variant id of \"([^\"]*)\"$")
	public void the_displayed_variant_id_corresponds_to_the_variant_id_of(String displayableName)
			throws Throwable
	{
		Assert.assertEquals(UnomiApiTestRtVariables.storedIds.get("displayedVariantId"),
				UnomiApiTestRtVariables.storedIds.get(displayableName));
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
