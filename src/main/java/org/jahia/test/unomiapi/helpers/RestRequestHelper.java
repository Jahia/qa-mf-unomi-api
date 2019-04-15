package org.jahia.test.unomiapi.helpers;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.jahia.test.unomiapi.data.TestGlobalConfiguration;
import org.jahia.test.unomiapi.data.UnomiApiTestRtVariables;

import com.mashape.unirest.http.HttpMethod;

import io.restassured.RestAssured;
import io.restassured.filter.log.ErrorLoggingFilter;
import io.restassured.filter.log.LogDetail;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.http.Cookie;
import io.restassured.http.Cookies;
import io.restassured.http.Headers;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class RestRequestHelper
{

	private PrintStream getLogFilePrintStream()
	{
		PrintStream writetoLogFile = null;
		try
		{
			File directory = new File(TestGlobalConfiguration.getLogsDirectory());
			if (!directory.exists())
				directory.mkdirs();

			FileOutputStream fos = new FileOutputStream(
					TestGlobalConfiguration.getLogsDirectory() + "/"
							+ UnomiApiTestRtVariables.scenarioName.replaceAll("\\s+", "_") + ".log",
					true);

			// adding lines of ===== before each request log for visibility
			for (int i = 0; i < 3; i++)
			{
				fos.write(
						"=============================================================================================== \n"
								.getBytes());
			}
			// fos.close();

			writetoLogFile = new PrintStream(fos);
		}
		catch (FileNotFoundException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return writetoLogFile;
	}

	private RequestSpecification basicBuildRequest(ContentType reqContentType)
	{
		RequestSpecification req = RestAssured.given().relaxedHTTPSValidation().redirects()
				.allowCircular(true).and().redirects().max(10).and().redirects().follow(true).with()
				.contentType(reqContentType);

		addCookiesIfAny(req);

		PrintStream writetoLogFile = getLogFilePrintStream();

		// if required we log request and response or errors only
		if (TestGlobalConfiguration.getLogErrorsOnly())
			if (writetoLogFile != null)
				req = req.log().ifValidationFails(LogDetail.ALL, true)
						.filter(new ErrorLoggingFilter(writetoLogFile));
			else
				req = req.log().ifValidationFails(LogDetail.ALL, true)
						.filter(new ErrorLoggingFilter());
		else
		{
			// write all reqs and resps to a file
			if (writetoLogFile != null)
				req = req.filter(new RequestLoggingFilter(LogDetail.ALL, writetoLogFile))
						.filter(new ResponseLoggingFilter(LogDetail.ALL, writetoLogFile));
			// or directly to system.out if can't
			else
				req = req.filter(new RequestLoggingFilter()).filter(new ResponseLoggingFilter());
		}

		return req;
	}

	private void addCookiesIfAny(RequestSpecification req)
	{
		List<Cookie> cookies = new ArrayList<Cookie>();

		addCookieIfAny(cookies, "context-profile-id", "profileId");
		addCookieIfAny(cookies, "JSESSIONID", "JSESSIONID");
		addCookieIfAny(cookies, "wem-session-id", "wem-session-id");

		req = req.with().cookies(new Cookies(cookies));

	}

	public void addCookieIfAny(List<Cookie> cookies, String cookieName, String idStored)
	{
		if (UnomiApiTestRtVariables.storedIds.get(idStored) != null)
		{
			Cookie contextProfileIdCookie = new Cookie.Builder(cookieName,
					UnomiApiTestRtVariables.storedIds.get(idStored)).setSecured(false).build();
			cookies.add(contextProfileIdCookie);
		}
	}

	public RequestSpecification buildRequest()
	{
		return basicBuildRequest(ContentType.JSON);
	}

	public RequestSpecification buildRequest(Headers headers)
	{
		return basicBuildRequest(ContentType.JSON).with().headers(headers);
	}

	public RequestSpecification buildRequest(String username, String password)
	{
		return basicBuildRequest(ContentType.JSON).auth().preemptive().basic(username, password);
	}

	public Response sendRequest(RequestSpecification requestSpec, URL url, Object body,
			HttpMethod httpMethod)
	{
		Response response = null;

		if (body != null)
			requestSpec = requestSpec.body(body);

		if (httpMethod.equals(HttpMethod.GET))
			response = requestSpec.get(url);
		else if (httpMethod.equals(HttpMethod.POST))
			response = requestSpec.post(url);
		else if (httpMethod.equals(HttpMethod.DELETE))
			response = requestSpec.delete(url);
		else if (httpMethod.equals(HttpMethod.PUT))
			response = requestSpec.put(url);

		return response;
	}

	public Response sendRequest(RequestSpecification requestSpec, URL url, String body,
			HttpMethod httpMethod)
	{
		Response response = null;

		if (body != null && !body.isEmpty())
			requestSpec = requestSpec.body(body);

		if (httpMethod.equals(HttpMethod.GET))
			response = requestSpec.get(url);
		else if (httpMethod.equals(HttpMethod.POST))
			response = requestSpec.post(url);
		else if (httpMethod.equals(HttpMethod.DELETE))
			response = requestSpec.delete(url);
		else if (httpMethod.equals(HttpMethod.PUT))
			response = requestSpec.put(url);

		return response;
	}

}
