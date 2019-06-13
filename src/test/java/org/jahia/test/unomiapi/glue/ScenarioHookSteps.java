package org.jahia.test.unomiapi.glue;

import org.jahia.test.commonutils.misc.BaseSteps;
import org.jahia.test.unomiapi.data.UnomiApiScenarioRuntimeData;

import cucumber.api.Result.Type;
import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;

public class ScenarioHookSteps extends BaseSteps {
    private UnomiApiScenarioRuntimeData unomiApiScenarioRuntimeData;

    public ScenarioHookSteps(UnomiApiScenarioRuntimeData unomiApiScenarioRuntimeData) {
        this.unomiApiScenarioRuntimeData = unomiApiScenarioRuntimeData;
    }

    @Before
    public void before(Scenario s) {
        unomiApiScenarioRuntimeData.setScenarioName(s.getName());
    }

    @After
    public void after(Scenario s) {
        if (!s.getStatus().equals(Type.PASSED)) {
            logger.info(String.format("Scenario %s did not pass", s.getName()));
        }
    }
}
