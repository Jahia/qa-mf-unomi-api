package org.jahia.test.unomiapi.glue;

import java.net.URL;

import org.jahia.test.unomiapi.apiutils.RestRequestHelper;
import org.jahia.test.unomiapi.data.TestGlobalConfiguration;
import org.jahia.test.unomiapi.data.TestRtVariables;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mashape.unirest.http.HttpMethod;

import io.restassured.http.ContentType;
import io.restassured.http.Header;
import io.restassured.http.Headers;
import io.restassured.specification.RequestSpecification;

public class ProfileSteps
{
	Logger logger = LoggerFactory.getLogger(this.getClass());

	public void getProfile(String profileId) throws Throwable
	{
		RestRequestHelper reqHelper = new RestRequestHelper();
		RequestSpecification req = buildProfileRequestSpec(reqHelper);
		TestRtVariables.response = reqHelper.sendRequest(req,
				new URL(TestGlobalConfiguration.getUnomiUrl() + "/cxs/profiles/" + profileId), null,
				HttpMethod.GET);
	}

	private RequestSpecification buildProfileRequestSpec(RestRequestHelper reqHelper)
	{
		RequestSpecification req;
		Header header = new Header("X-Unomi-Peer", TestGlobalConfiguration.getUnomiKey());

		req = reqHelper.buildRequest(ContentType.JSON, new Headers(header));
		return req;
	}

}
