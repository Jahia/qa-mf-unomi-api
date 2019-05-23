package org.jahia.test.unomiapi.glue;

import org.jahia.test.unomiapi.data.UnomiApiTestRtVariables;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cucumber.api.Result.Type;
import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;

public class ScenarioHookSteps
{
	Logger logger = LoggerFactory.getLogger(this.getClass());

	@Before
	public void before(Scenario s)
	{
		// reset default values for each scenario further steps in the scenario might change them
		UnomiApiTestRtVariables.init();
		UnomiApiTestRtVariables.scenarioName = s.getName();
	}

	@After
	public void after(Scenario s)
	{
        if (!s.getStatus().equals(Type.PASSED))
		{
			logger.info(String.format("Scenario %s did not pass", s.getName()));
		}
	}
}
