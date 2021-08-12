package org.jahia.test.unomiapi.helpers;

import com.mashape.unirest.http.HttpMethod;
import io.restassured.specification.RequestSpecification;
import org.jahia.test.unomiapi.data.TestGlobalConfiguration;
import org.jahia.test.unomiapi.data.UnomiApiScenarioRuntimeData;

import java.sql.Timestamp;

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
        Instant yesterday = now.minus(1, ChronoUnit.DAYS);
        yesterday.toEpochMilli();

        Instant tomorrow = now.plus(1, ChronoUnit.DAYS);
        tomorrow.toEpochMilli();

        // TODO : change to object
        String requestBody = "{\"condition\": {\n" + "\t\t\"parameterValues\": {\n" + "\t\t\t\"comparisonOperator\": \"between\",\n"
                + "\t\t\t\t\"propertyName\": \"timeStamp\",\n" + "\t\t\t\t\t\"propertyValues\": [\n" + "" + yesterday.toEpochMilli()
                + ","+tomorrow.toEpochMilli()+"\n" + "\t\t\t\t\t\t]\n" + "\t\t\t\t\t},\n"
                + "\"type\": \"sessionPropertyCondition\"}}";

        RestRequestHelper reqHelper = new RestRequestHelper(unomiApiScenarioRuntimeData);
        unomiApiScenarioRuntimeData.setResponse(reqHelper.sendRequest(req,
                new URL(TestGlobalConfiguration.getUnomiUrl() + "/cxs/query/event/target.properties.pageInfo.destinationURL"),
                requestBody, HttpMethod.POST));

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
