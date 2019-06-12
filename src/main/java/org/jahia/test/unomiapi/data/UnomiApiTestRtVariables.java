package org.jahia.test.unomiapi.data;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.unomi.api.Event;
import org.apache.unomi.api.services.PersonalizationService.PersonalizationRequest;

import io.restassured.response.Response;

public class UnomiApiTestRtVariables {

    // real time scenario variables
    // initialized each scenario
    public Map<String, String> storedIds = new HashMap<>();
    public List<Event> events = new ArrayList<>();
    public List<PersonalizationRequest> personalizationRequests;
    public boolean fixtureFailed = false;
    public String siteLocale;
    public long scenarioStartTimeMillis;
    public String scenarioName;
    public boolean ensureCleanSession;
    public File[] previousFiles;
    public File lastDownloadedFile;
    public String scope;
    public Response response;

    // init all with default values
    public void init() {
        siteLocale = "en";
        scenarioStartTimeMillis = System.currentTimeMillis();
        scenarioName = "undefined";
        ensureCleanSession = true;
        previousFiles = null;
        lastDownloadedFile = null;
        storedIds = new HashMap<>();
        events = new ArrayList<>();
        personalizationRequests = new ArrayList<>();
        scope = "JahiaMfIntegTests";
        response = null;
    }

    public void clearContextRequestElements() {
        events.clear();
        personalizationRequests.clear();
    }

}
