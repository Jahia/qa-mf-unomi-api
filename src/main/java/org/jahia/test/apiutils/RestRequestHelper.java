package org.jahia.test.apiutils;

import java.net.URL;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.http.Headers;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class RestRequestHelper
{

	public RequestSpecification buildRequest(ContentType acceptContentType,
			ContentType reqContentType, Headers headers)
	{
		return RestAssured.given().relaxedHTTPSValidation().redirects().allowCircular(true).and()
				.redirects().max(10).and().redirects().follow(true).headers(headers)
				.accept(acceptContentType).contentType(reqContentType);
	}

	public Response sendRequest(RequestSpecification requestSpec, URL url, String body,
			RequestType type)
	{
		Response response = null;

		if (body != null && !body.isEmpty())
			requestSpec = requestSpec.body(body);

		if (type.equals(RequestType.GET))
			response = requestSpec.get(url);
		else if (type.equals(RequestType.POST))
			response = requestSpec.post(url);
		else if (type.equals(RequestType.DELETE))
			response = requestSpec.delete(url);
		else if (type.equals(RequestType.PUT))
			response = requestSpec.put(url);

		return response;
	}
}
