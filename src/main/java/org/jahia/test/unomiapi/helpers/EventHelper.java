package org.jahia.test.unomiapi.helpers;

import com.mashape.unirest.http.HttpMethod;
import io.restassured.specification.RequestSpecification;
import org.apache.commons.lang3.StringUtils;
import org.jahia.test.unomiapi.data.TestGlobalConfiguration;
import org.jahia.test.unomiapi.data.UnomiApiScenarioRuntimeData;
import org.json.JSONObject;
import org.slf4j.LoggerFactory;

import java.net.URL;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

public class EventHelper {

    private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(EventHelper.class);

    private UnomiApiScenarioRuntimeData unomiApiScenarioRuntimeData;

    public EventHelper(UnomiApiScenarioRuntimeData unomiApiScenarioRuntimeData) {
        this.unomiApiScenarioRuntimeData = unomiApiScenarioRuntimeData;
    }

    public int getNumberOfViewForPage(String partialPagePath, String user, String password) throws Throwable {

        RequestSpecification req = buildNbofViewRequestSpec(user, password);

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
                new URL(TestGlobalConfiguration.getUnomiUrl() + "/cxs/query/event/target.properties.pageInfo"
                        + ".destinationURL?optimizedQuery=true"),
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

    public int getNumberOfOccurenceForReferrer(String referrer, String user, String password) throws Throwable {

        RequestSpecification req = buildNbofViewRequestSpec(user, password);

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
                new URL(TestGlobalConfiguration.getUnomiUrl() + "/cxs/query/event/target.properties.pageInfo.referringURL?optimizedQuery=true"),
                requestBodyJson, HttpMethod.POST));

        String response = this.unomiApiScenarioRuntimeData.getResponse().asString();
        JSONObject responseJson = new JSONObject(response);
        Iterator<String> keys = responseJson.keys();

        // As we dont know the full url here we have to find it from the partialPath

        String lookupKey = null;

        while (keys.hasNext()) {
            String key =  keys.next();
            if (StringUtils.contains(key, referrer)) {
                lookupKey = key;
                break;
            }
            LOGGER.info("referrer key : " + key);
        }

        // When the page has never been seen, it is not displayed in the list of page views
        return lookupKey !=null ? Integer.parseInt(responseJson.get(lookupKey).toString()) : 0;

    }

    /**
     * Retrieve the number of occurence for a searched keyword
     * @param keyword the word to search
     * @param lang the language filter set it empty for not using it
     * @param scope the language filter set it empty for not using it
     * @param user karaf username
     * @param password karaf password
     * @return the number of occurence for the keyword
     * @throws Throwable
     */
    public int getNumberOfSearchesForKeyword(String keyword, String lang, String scope, String user, String password) throws Throwable {

        RequestSpecification req = buildNbofViewRequestSpec( user, password);


        // Build time condition

        Instant now = Instant.now();
        Long minTime = now.minus(1, ChronoUnit.DAYS).toEpochMilli();
        Long maxTime = now.plus(1, ChronoUnit.DAYS).toEpochMilli();
        JSONObject timeConditionParameters = new JSONObject();
        timeConditionParameters.put("propertyName", "timeStamp");
        timeConditionParameters.put("comparisonOperator", "between");
        timeConditionParameters.put("propertyValues", Arrays.asList(minTime, maxTime));

        JSONObject timeCondition = new JSONObject();
        timeCondition.put("type", "sessionPropertyCondition");
        timeCondition.put("parameterValues", timeConditionParameters);

        // build the subcondition list on which we can add more subcondition if required
        ArrayList<JSONObject> subConditionList = new ArrayList<JSONObject>();
        subConditionList.add(timeCondition);

        // build scope condition


        if (!scope.isEmpty()){

            JSONObject scopeConditionParameters = new JSONObject();
            scopeConditionParameters.put("propertyName", "scope");
            scopeConditionParameters.put("comparisonOperator", "equals");
            scopeConditionParameters.put("propertyValue", scope);

            JSONObject scopeCondition = new JSONObject();
            scopeCondition.put("type", "sessionPropertyCondition");
            scopeCondition.put("parameterValues", scopeConditionParameters);

            subConditionList.add(scopeCondition);
        }

        // build language condition

        if (!lang.isEmpty()){

            JSONObject languageConditionParameters = new JSONObject();
            languageConditionParameters.put("propertyName", "properties.language");
            languageConditionParameters.put("comparisonOperator", "equals");
            languageConditionParameters.put("propertyValue", lang);

            JSONObject languageCondition = new JSONObject();
            languageCondition.put("type", "eventPropertyCondition");
            languageCondition.put("parameterValues", languageConditionParameters);

            subConditionList.add(languageCondition);
        }

        JSONObject parameterValuesCondition = new JSONObject();
        parameterValuesCondition.put("operator", "and");
        parameterValuesCondition.put("subConditions", subConditionList);

        JSONObject condition = new JSONObject();
        condition.put("type", "booleanCondition");
        condition.put("parameterValues", parameterValuesCondition);

        JSONObject query = new JSONObject();
        query.put("condition", condition);

        String requestBodyJson = query.toString();

        RestRequestHelper reqHelper = new RestRequestHelper(unomiApiScenarioRuntimeData);
        unomiApiScenarioRuntimeData.setResponse(reqHelper.sendRequest(req,
                new URL(TestGlobalConfiguration.getUnomiUrl() + "/cxs/query/event/properties.keyword"),
                requestBodyJson, HttpMethod.POST));

        String response = this.unomiApiScenarioRuntimeData.getResponse().asString();
        JSONObject responseJson = new JSONObject(response);
        Iterator<String> keys = responseJson.keys();

        String lookupKey = null;

        while (keys.hasNext()) {
            String key =  keys.next();
            if (StringUtils.endsWith(key, keyword)) {
                lookupKey = key;
                break;
            }
        }

        // When the page has never been seen, it is not displayed in the list of page views
        return lookupKey !=null ? Integer.parseInt(responseJson.get(keyword).toString()) : 0;

    }

    private RequestSpecification buildNbofViewRequestSpec(String user, String password) {
        RequestSpecification req;

        RestRequestHelper reqHelper = new RestRequestHelper(unomiApiScenarioRuntimeData);
        req = reqHelper.buildRequest(user, password);
        return req;
    }

}
