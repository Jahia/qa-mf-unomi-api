package org.jahia.test.unomiapi.data;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.unomi.api.Event;
import org.apache.unomi.api.services.PersonalizationService.PersonalizationRequest;

import io.restassured.response.Response;

public class UnomiApiScenarioRuntimeData {

    // real time scenario variables
    // initialized each scenario
    private Map<String, String> storedIds = new HashMap<>();
    private List<Event> events = new ArrayList<>();
    private List<PersonalizationRequest> personalizationRequests;
    private boolean fixtureFailed = false;
    private String siteLocale;
    private long scenarioStartTimeMillis;
    private String scenarioName;
    private boolean ensureCleanSession;
    private File[] previousFiles;
    private File lastDownloadedFile;
    private String scope;
    private Response response;

    // init all with default values
    public UnomiApiScenarioRuntimeData() {
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

    public Map<String, String> getStoredIds() {
        return storedIds;
    }

    public void setStoredIds(Map<String, String> storedIds) {
        this.storedIds = storedIds;
    }

    public List<Event> getEvents() {
        return events;
    }

    public void setEvents(List<Event> events) {
        this.events = events;
    }

    public List<PersonalizationRequest> getPersonalizationRequests() {
        return personalizationRequests;
    }

    public void setPersonalizationRequests(List<PersonalizationRequest> personalizationRequests) {
        this.personalizationRequests = personalizationRequests;
    }

    public boolean isFixtureFailed() {
        return fixtureFailed;
    }

    public void setFixtureFailed(boolean fixtureFailed) {
        this.fixtureFailed = fixtureFailed;
    }

    public String getSiteLocale() {
        return siteLocale;
    }

    public void setSiteLocale(String siteLocale) {
        this.siteLocale = siteLocale;
    }

    public long getScenarioStartTimeMillis() {
        return scenarioStartTimeMillis;
    }

    public void setScenarioStartTimeMillis(long scenarioStartTimeMillis) {
        this.scenarioStartTimeMillis = scenarioStartTimeMillis;
    }

    public String getScenarioName() {
        return scenarioName;
    }

    public void setScenarioName(String scenarioName) {
        this.scenarioName = scenarioName;
    }

    public boolean isEnsureCleanSession() {
        return ensureCleanSession;
    }

    public void setEnsureCleanSession(boolean ensureCleanSession) {
        this.ensureCleanSession = ensureCleanSession;
    }

    public File[] getPreviousFiles() {
        return previousFiles;
    }

    public void setPreviousFiles(File[] previousFiles) {
        this.previousFiles = previousFiles;
    }

    public File getLastDownloadedFile() {
        return lastDownloadedFile;
    }

    public void setLastDownloadedFile(File lastDownloadedFile) {
        this.lastDownloadedFile = lastDownloadedFile;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public Response getResponse() {
        return response;
    }

    public void setResponse(Response response) {
        this.response = response;
    }

}
