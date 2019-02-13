package org.jahia.test.glue;

import org.jahia.test.data.RestApiRtVariables;
import org.jahia.test.data.TestRtVariables;
import org.jahia.test.utils.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.When;

public class FrameworkSteps
{
	Logger logger = LoggerFactory.getLogger(this.getClass());

	@Before
	public void before(Scenario s)
	{
		// setting default values for each scenario, further steps in the scenario might change them
		TestRtVariables.init();
		RestApiRtVariables.init();
		TestRtVariables.browserStackTestName = s.getName();
	}

	@After
	public void after(Scenario s)
	{
		if (!s.getStatus().equalsIgnoreCase("passed"))
		{
			logger.info(String.format("Scenario %s did not pass", s.getName()));
		}
	}

	@Given("^The site locale is \"([^\"]*)\"$")
	public void the_site_locale_is(String locale) throws Throwable
	{
		TestRtVariables.siteLocale = locale;
	}

	@Given("^I store the current time$")
	public void i_store_the_current_time() throws Throwable
	{
		TestRtVariables.storedTimeMillis = System.currentTimeMillis();
	}

	@When("^I wait (\\d+) ms$")
	public void i_wait_ms(int milliSeconds) throws Throwable
	{
		Util.waitForMillis(milliSeconds);
	}

	@When("^I wait (\\d+) s$")
	public void i_wait_s(int seconds) throws Throwable
	{
		Util.waitForMillis(seconds * 1000);
	}
}
