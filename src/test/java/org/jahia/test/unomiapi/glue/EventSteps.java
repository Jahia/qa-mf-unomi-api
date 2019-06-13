package org.jahia.test.unomiapi.glue;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.unomi.api.CustomItem;
import org.apache.unomi.api.Event;
import org.apache.unomi.api.Profile;
import org.apache.unomi.api.Session;
import org.jahia.test.commonutils.misc.BaseSteps;
import org.jahia.test.unomiapi.data.TestGlobalConfiguration;
import org.jahia.test.unomiapi.data.UnomiApiScenarioRuntimeData;

import cucumber.api.java.en.Given;
import io.cucumber.datatable.DataTable;

public class EventSteps extends BaseSteps {
    private UnomiApiScenarioRuntimeData unomiApiScenarioRuntimeData;

    public EventSteps(UnomiApiScenarioRuntimeData unomiApiScenarioRuntimeData) {
        this.unomiApiScenarioRuntimeData = unomiApiScenarioRuntimeData;
    }

    @Given("^I define a unomi context event with the following parameters$")
    public void i_define_a_unomi_context_event_with_the_following_parameters(DataTable eventParamsDt) throws Throwable {
        String profileId = unomiApiScenarioRuntimeData.getStoredIds().get("context-profile-id") != null
                ? unomiApiScenarioRuntimeData.getStoredIds().get("context-profile-id")
                        : "";
                Profile profile = new Profile(profileId);

                Session session;
        if (unomiApiScenarioRuntimeData.getStoredIds().get("wem-session-id") == null)
                    session = null;
                else
            session = new Session(unomiApiScenarioRuntimeData.getStoredIds().get("wem-session-id"), profile, new Date(),
                    unomiApiScenarioRuntimeData.getScope());

                Map<String, String> eventParams = eventParamsDt.asMap(String.class, String.class);
                Event event;

                if (eventParams.get("eventType").equals("view")) {
                    // Initialize context like if you display the first page on the website

            CustomItem target = new CustomItem(unomiApiScenarioRuntimeData.getStoredIds().get("pageID"), "page");
            target.setScope(unomiApiScenarioRuntimeData.getScope());

                    Map<String, Object> pageInfo = new HashMap<>();
            pageInfo.put("pageID", unomiApiScenarioRuntimeData.getStoredIds().get("pageID"));
                    pageInfo.put("nodeType", "jnt:page");
                    pageInfo.put("pageName", eventParams.get("pageName"));
                    pageInfo.put("pagePath", eventParams.get("pagePath"));
                    pageInfo.put("templateName", eventParams.get("templateName"));
            pageInfo.put("language", unomiApiScenarioRuntimeData.getSiteLocale());
                    pageInfo.put("referringURL", "");

                    pageInfo.put("destinationUrl", TestGlobalConfiguration.getBaseUrl() + eventParams.get("pagePath"));

                    pageInfo.put("nodeType", "jnt:page");

                    String[] categories = new String[0];
                    if (eventParams.get("categories") != null)
                        categories = eventParams.get("categories").split(",");
                    pageInfo.put("categories", categories);

                    String[] tags = new String[0];
                    if (eventParams.get("tags") != null)
                        tags = eventParams.get("tags").split(",");
                    pageInfo.put("tags", tags);

                    if (eventParams.get("isContentTemplate") != null)
                        pageInfo.put("isContentTemplate", Boolean.parseBoolean(eventParams.get("isContentTemplate")));
                    else
                        pageInfo.put("isContentTemplate", false);

                    Map<String, Object> properties = new HashMap<>();
                    properties.put("pageInfo", pageInfo);
                    target.setProperties(properties);

            CustomItem source = new CustomItem(unomiApiScenarioRuntimeData.getStoredIds().get("siteID"), "site");
            source.setScope(unomiApiScenarioRuntimeData.getScope());

            event = new Event(eventParams.get("EventType"), session, profile, unomiApiScenarioRuntimeData.getScope(), source, target,
                    new Date());
                }

                else if (eventParams.get("eventType").equals("updateProperties")) {
                    Map<String, Object> properties = new HashMap<>();
                    properties.put("targetType", "profile");
            properties.put("targetId", unomiApiScenarioRuntimeData.getStoredIds().get("context-profile-id"));

                    Map<String, Object> addUpdate = new HashMap<>();
                    for (Map.Entry<String, String> entry : eventParams.entrySet()) {
                        if (entry.getKey().startsWith("properties."))
                            addUpdate.put(entry.getKey(), entry.getValue());
                    }
                    properties.put(eventParams.get("updateType"), addUpdate);

                    CustomItem source = new CustomItem("wemProfile", "wemProfile");
            source.setScope(unomiApiScenarioRuntimeData.getScope());

            event = new Event(eventParams.get("eventType"), session, profile, unomiApiScenarioRuntimeData.getScope(), source, null,
                    properties,
                            new Date(), true);
                }

                else
                    throw new RuntimeException("EventType unknown, please check the feature file");

        unomiApiScenarioRuntimeData.getEvents().add(event);

    }

}
