package org.jahia.test.unomiapi.helpers;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.net.URL;

import org.jahia.test.unomiapi.data.TestGlobalConfiguration;
import org.jahia.test.unomiapi.data.UnomiApiTestRtVariables;

import com.mashape.unirest.http.HttpMethod;

import io.restassured.RestAssured;
import io.restassured.filter.log.ErrorLoggingFilter;
import io.restassured.filter.log.LogDetail;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.http.Cookies;
import io.restassured.http.Headers;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class RestRequestHelper
{

	public RequestSpecification buildRequest(ContentType reqContentType)
	{
		RequestSpecification req = RestAssured.given().relaxedHTTPSValidation().redirects()
				.allowCircular(true).and().redirects().max(10).and().redirects().follow(true).with()
				.contentType(reqContentType);

		PrintStream writetoLogFile = getLogFilePrintStream();

		// if required we log request and response or errors only
		if (TestGlobalConfiguration.getLogErrorsOnly())
			req = req.log().ifValidationFails(LogDetail.ALL, true).filter(new ErrorLoggingFilter());
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

	private PrintStream getLogFilePrintStream()
	{
		PrintStream writetoLogFile = null;
		try
		{
			File directory = new File(TestGlobalConfiguration.getLogsDirectory());
			if (!directory.exists())
				directory.mkdirs();

			writetoLogFile = new PrintStream(
					new FileOutputStream(
							TestGlobalConfiguration.getLogsDirectory() + "/"
									+ UnomiApiTestRtVariables.scenarioName.replaceAll("\\s+", "_") + ".log",
							true));
		}
		catch (FileNotFoundException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return writetoLogFile;
	}

	public RequestSpecification buildRequest(ContentType reqContentType, Headers headers)
	{
		return buildRequest(reqContentType).with().headers(headers);
	}

	public RequestSpecification buildRequest(String username, String password,
			ContentType reqContentType, Headers headers)
	{
		return buildRequest(reqContentType).auth().preemptive().basic(username, password).with()
				.headers(headers);
	}

	public RequestSpecification buildRequest(ContentType acceptContentType,
			ContentType reqContentType, Headers headers)
	{
		return buildRequest(reqContentType).with().headers(headers).with()
				.accept(acceptContentType);
	}

	public RequestSpecification buildRequest(ContentType reqContentType, Headers headers,
			Cookies cookies)
	{
		return buildRequest(reqContentType).with().headers(headers).with().cookies(cookies);
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
