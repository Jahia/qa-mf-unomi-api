package org.jahia.test.unomiapi.helpers;

import com.mashape.unirest.http.HttpMethod;
import io.restassured.specification.RequestSpecification;
import org.apache.commons.lang3.StringUtils;
import org.jahia.test.unomiapi.data.TestGlobalConfiguration;
import org.jahia.test.unomiapi.data.UnomiApiScenarioRuntimeData;
import org.json.JSONObject;

import java.net.URL;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.Iterator;

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

        JSONObject conditionParameters = new JSONObject();
        conditionParameters.put("propertyName", "timeStamp");
        conditionParameters.put("comparisonOperator", "between");
        conditionParameters.put("propertyValues", Arrays.asList(minTime, maxTime));

        JSONObject condition = new JSONObject();
        condition.put("type", "sessionPropertyCondition");
        condition.put("parameterValues", conditionParameters);

        JSONObject query = new JSONObject();
        query.put("condition", condition);

        String requestBodyJson = query.toString();

        RestRequestHelper reqHelper = new RestRequestHelper(unomiApiScenarioRuntimeData);
        unomiApiScenarioRuntimeData.setResponse(reqHelper.sendRequest(req,
                new URL(TestGlobalConfiguration.getUnomiUrl() + "/cxs/query/event/target.properties.pageInfo.destinationURL"),
                requestBodyJson, HttpMethod.POST));

        String response = this.unomiApiScenarioRuntimeData.getResponse().asString();
        JSONObject responseJson = new JSONObject(response);
        Iterator<String> keys = responseJson.keys();

        // As we dont know the full url here we have to find it from the partialPath

        String lookupKey = null;

        while (keys.hasNext()) {
            String key =  keys.next();
            if (StringUtils.endsWith(key, partialPagePath)) {
                lookupKey = key;
                break;
            }
        }

        // When the page has never been seen, it is not displayed in the list of page views
        return lookupKey !=null ? Integer.parseInt(responseJson.get(lookupKey).toString()) : 0;

    }

    private RequestSpecification buildNbofViewRequestSpec(String pagePath, String user, String password) {
        RequestSpecification req;

        RestRequestHelper reqHelper = new RestRequestHelper(unomiApiScenarioRuntimeData);
        req = reqHelper.buildRequest(user, password);
        return req;
    }

}
