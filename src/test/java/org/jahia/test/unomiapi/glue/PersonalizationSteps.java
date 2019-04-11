package org.jahia.test.unomiapi.glue;

import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.unomi.api.ContextResponse;
import org.apache.unomi.api.services.PersonalizationService.PersonalizationRequest;
import org.apache.unomi.persistence.spi.CustomObjectMapper;
import org.jahia.test.unomiapi.data.UnomiApiTestRtVariables;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;

public class PersonalizationSteps
{
	Logger logger = LoggerFactory.getLogger(this.getClass());

	@Given("^I extract the personalization (\\d+) from the response$")
	public void i_extract_the_personalization_from_the_response(int personalizationIndex)
			throws Throwable
	{
		String regExp = "var personalization = (.+);";
		Pattern p = Pattern.compile(regExp);
		Matcher m = p.matcher(UnomiApiTestRtVariables.response.asString());

		int index = 1;
		int nbPerso = 0;
		while (m.find())
		{
			nbPerso++;
			if (index == personalizationIndex)
			{
				UnomiApiTestRtVariables.personalizationRequests.add(CustomObjectMapper
						.getObjectMapper().readValue(m.group(1), PersonalizationRequest.class));
			}
			index++;
		}

		if (nbPerso == 0)
			throw new RuntimeException(
					"Could not find any personalization matching regexp: " + regExp);
	}

	@Then("^The first personalization in the response returns (\\d+) variants ids$")
	public void the_first_personalization_in_the_response_returns_variants_ids(
			int expectedNbVariantsInFirstPerso) throws Throwable
	{
		ContextResponse contextResponse = CustomObjectMapper.getObjectMapper()
				.readValue(UnomiApiTestRtVariables.response.asString(), ContextResponse.class);
		if (contextResponse.getPersonalizations().size() == 0)
			throw new RuntimeException(
					String.format("Cannot get variants as there is no personalization in : %s",
							UnomiApiTestRtVariables.response.asString()));

		for (Map.Entry<String, List<String>> entry : contextResponse.getPersonalizations()
				.entrySet())
		{
			Assert.assertEquals(entry.getValue().size(), expectedNbVariantsInFirstPerso,
					String.format("Variants returned in perso %s are %s", entry.getKey(),
							entry.getValue()));
			break;
		}

	}

}
