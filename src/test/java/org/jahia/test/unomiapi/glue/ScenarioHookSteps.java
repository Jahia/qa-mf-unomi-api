package org.jahia.test.unomiapi.glue;

import org.jahia.test.commonutils.misc.BaseSteps;
import org.jahia.test.unomiapi.data.UnomiApiTestRtVariables;

import cucumber.api.Result.Type;
import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;

public class ScenarioHookSteps extends BaseSteps
{
    private UnomiApiTestRtVariables unomiApiTestRtVariables;

    public ScenarioHookSteps(UnomiApiTestRtVariables unomiApiTestRtVariables) {
        this.unomiApiTestRtVariables = unomiApiTestRtVariables;
    }

    @Before
    public void before(Scenario s)
    {
        // reset default values for each scenario further steps in the scenario might change them
        unomiApiTestRtVariables.init();
        unomiApiTestRtVariables.scenarioName = s.getName();
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
