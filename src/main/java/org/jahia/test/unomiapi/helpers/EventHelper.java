package org.jahia.test.unomiapi.helpers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.mashape.unirest.http.HttpMethod;
import io.restassured.specification.RequestSpecification;
import org.jahia.test.unomiapi.data.Event.Condition;
import org.jahia.test.unomiapi.data.Event.EventListRequest;
import org.jahia.test.unomiapi.data.TestGlobalConfiguration;
import org.jahia.test.unomiapi.data.UnomiApiScenarioRuntimeData;
import org.jahia.test.unomiapi.data.Event.ParameterValues;

import java.net.URL;
import java.time.Instant;
import java.time.temporal.ChronoUnit;

public class EventHelper {

    private UnomiApiScenarioRuntimeData unomiApiScenarioRuntimeData;

    public EventHelper(UnomiApiScenarioRuntimeData unomiApiScenarioRuntimeData) {
        this.unomiApiScenarioRuntimeData = unomiApiScenarioRuntimeData;
    }

    public int getNumberOfViewForPage(String partialPagePath, String user, String password) throws Throwable {

        RequestSpecification req = buildNbofViewRequestSpec(partialPagePath, user, password);

        Instant now = Instant.now();
        Long minTime = now.minus(1, ChronoUnit.DAYS).toEpochMilli();
        Long maxTime = now.plus(1, ChronoUnit.DAYS).toEpochMilli();

        ParameterValues parameterValues = new ParameterValues("between", "timeStamp", minTime, maxTime);
        Condition condition = new Condition("sessionPropertyCondition", parameterValues);
        EventListRequest eventRequest = new EventListRequest(condition);

        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String requestBodyJson = ow.writeValueAsString(eventRequest);

        RestRequestHelper reqHelper = new RestRequestHelper(unomiApiScenarioRuntimeData);
        unomiApiScenarioRuntimeData.setResponse(reqHelper.sendRequest(req,
                new URL(TestGlobalConfiguration.getUnomiUrl() + "/cxs/query/event/target.properties.pageInfo.destinationURL"),
                requestBodyJson, HttpMethod.POST));

        String response = this.unomiApiScenarioRuntimeData.getResponse().asString();

        // TODO : read response as object
        if(response.indexOf(partialPagePath) <0 ) {return 0; }
        else {
            String test = response.split(partialPagePath + "\":")[1];
            return Integer.parseInt(test.split(",")[0]);
        }
    }

    private RequestSpecification buildNbofViewRequestSpec(String pagePath, String user, String password) {
        RequestSpecification req;

        RestRequestHelper reqHelper = new RestRequestHelper(unomiApiScenarioRuntimeData);
        req = reqHelper.buildRequest(user, password);
        return req;
    }

}
