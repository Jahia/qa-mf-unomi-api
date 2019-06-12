package org.jahia.test.unomiapi.helpers;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;

import org.apache.unomi.api.ContextRequest;
import org.apache.unomi.api.CustomItem;
import org.jahia.test.unomiapi.data.TestGlobalConfiguration;
import org.jahia.test.unomiapi.data.UnomiApiTestRtVariables;

import com.mashape.unirest.http.HttpMethod;

import io.restassured.http.Header;
import io.restassured.http.Headers;
import io.restassured.specification.RequestSpecification;

public class ContextHelper {
    private final String contextJsonUrl = TestGlobalConfiguration.getUnomiUrl() + "/context.json";
    private UnomiApiTestRtVariables unomiApiTestRtVariables;

    public ContextHelper(UnomiApiTestRtVariables unomiApiTestRtVariables) {
        this.unomiApiTestRtVariables = unomiApiTestRtVariables;
    }

    public RequestSpecification buildContextRequestSpec() {
        Header header = new Header("X-Unomi-Peer", TestGlobalConfiguration.getUnomiKey());

        RestRequestHelper reqHelper = new RestRequestHelper(unomiApiTestRtVariables);
        return reqHelper.buildRequest(new Headers(header));
    }

    public void sendContextRequest(ContextRequest contextRequest) throws MalformedURLException {
        RequestSpecification req = buildContextRequestSpec();
        RestRequestHelper reqHelper = new RestRequestHelper(unomiApiTestRtVariables);
        unomiApiTestRtVariables.response = reqHelper.sendRequest(req, new URL(contextJsonUrl), contextRequest, HttpMethod.POST);
    }

    public ContextRequest buildContextRequestObject() {
        ContextRequest contextRequest = new ContextRequest();
        contextRequest.setEvents(unomiApiTestRtVariables.events);
        contextRequest.setRequiredProfileProperties(Arrays.asList("j:nodename"));
        contextRequest.setSessionId(unomiApiTestRtVariables.storedIds.get("wem-session-id"));

        CustomItem sourceItem = new CustomItem(unomiApiTestRtVariables.storedIds.get("pageID"), "page");
        sourceItem.setScope(unomiApiTestRtVariables.scope);
        contextRequest.setSource(sourceItem);

        contextRequest.setPersonalizations(unomiApiTestRtVariables.personalizationRequests);

        return contextRequest;

    }
}
