package org.jahia.test.runners;

import cucumber.api.CucumberOptions;
import cucumber.api.testng.AbstractTestNGCucumberTests;

@CucumberOptions(features = "src/test/resources/features", plugin = {"pretty",
		"html:target/cucumber",
		"json:target/cucumber.json"}, glue = "org.jahia.test.glue", tags = {
				"~@ignore"})
public class CukeRunnerAll extends AbstractTestNGCucumberTests {
}
