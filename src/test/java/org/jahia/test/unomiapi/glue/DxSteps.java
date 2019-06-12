package org.jahia.test.unomiapi.glue;

import java.net.URL;

import org.jahia.test.commonutils.misc.BaseSteps;
import org.jahia.test.unomiapi.data.TestGlobalConfiguration;
import org.jahia.test.unomiapi.data.UnomiApiTestRtVariables;
import org.jahia.test.unomiapi.helpers.RestRequestHelper;

import com.mashape.unirest.http.HttpMethod;

import cucumber.api.java.en.Given;
import io.restassured.specification.RequestSpecification;

public class DxSteps extends BaseSteps {

    private UnomiApiTestRtVariables unomiApiTestRtVariables;

    public DxSteps(UnomiApiTestRtVariables unomiApiTestRtVariables) {
        this.unomiApiTestRtVariables = unomiApiTestRtVariables;
    }

    @Given("^I get the \"([^\"]*)\" page from DX server$")
    public void i_get_the_page_from_DX_server(String page) throws Throwable {
        if (!page.equals("home"))
            page = "home/" + page;

        RestRequestHelper reqHelper = new RestRequestHelper(unomiApiTestRtVariables);
        RequestSpecification req = reqHelper.buildRequest();

        unomiApiTestRtVariables.response = reqHelper.sendRequest(req,
                new URL(TestGlobalConfiguration.getBaseUrl() + "/sites/" + unomiApiTestRtVariables.scope + "/" + page + ".html"), null,
                HttpMethod.GET);
    }

}
