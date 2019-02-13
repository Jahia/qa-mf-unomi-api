package org.jahia.test.glue;

import org.jahia.test.data.RestApiRtVariables;
import org.jahia.test.data.TestGlobalConfiguration;
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

		skipCurrentContextOnTag(s);

		TestRtVariables.browserStackTestName = s.getName();
	}

	// current context (= cuke runner) + should be retrieved dynamically instead of trying all
	// context and tags but I'll let it like this for now
	private void skipCurrentContextOnTag(Scenario s)
	{
		skipGivenContextOnTag(s, "browser", "IE", "@noIe");
		skipGivenContextOnTag(s, "browser", "safari", "@noSafari");
		skipGivenContextOnTag(s, "browser", "firefox", "@noFirefox");
		skipGivenContextOnTag(s, "platform", "ANDROID", "@noAndroid");

		// necessary to skip anthracite tagged tests if required
		if (s.getSourceTagNames().contains("@anthracite")
				&& TestGlobalConfiguration.getSkipAnthraciteTests())
			throw new cucumber.api.PendingException(String.format(
					"Detected @anthracite tag but selenium.skip.anthracite.tests is true. Skipping %s",
					s.getName()));
	}

	private void skipGivenContextOnTag(Scenario s, String contextType, String contextPropValue,
			String tagToIgnore)
	{
		if (contextType.equals("browser"))
		{
			if (TestGlobalConfiguration.getCapBrowser().equals(contextPropValue)
					&& s.getSourceTagNames().contains(tagToIgnore))
				throw new cucumber.api.PendingException(
						String.format("Detected %s tag. Skipping %s", tagToIgnore, s.getName()));
		}
		else if (contextType.equals("platform"))
		{
			if (TestGlobalConfiguration.getCapPlatform().equals(contextPropValue)
					&& s.getSourceTagNames().contains(tagToIgnore))
				throw new cucumber.api.PendingException(
						String.format("Detected %s tag. Skipping %s", tagToIgnore, s.getName()));
		}
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
