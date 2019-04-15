package org.jahia.test.unomiapi.glue;

import org.apache.unomi.api.ContextRequest;
import org.jahia.test.unomiapi.data.UnomiApiTestRtVariables;
import org.jahia.test.unomiapi.helpers.ContextHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cucumber.api.java.en.Given;

public class ContextRequestSteps
{
	Logger logger = LoggerFactory.getLogger(this.getClass());

	@Given("^I post a context request to Unomi server with the previously defined elements$")
	public void i_post_a_context_request_to_Unomi_server_with_the_previously_defined_elements()
			throws Throwable
	{
		ContextHelper contextHelper = new ContextHelper();
		ContextRequest contextRequest = contextHelper.buildContextRequestObject();
		contextHelper.sendContextRequest(contextRequest);

		// clear events and perso after send to avoid overlap if called again in same scenario
		UnomiApiTestRtVariables.clearContextRequestElements();
	}

}
