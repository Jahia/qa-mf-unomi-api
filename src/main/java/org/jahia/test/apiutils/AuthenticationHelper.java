package org.jahia.test.apiutils;

import java.net.MalformedURLException;
import java.net.URL;

import org.jahia.test.data.TestGlobalConfiguration;

import io.restassured.RestAssured;

public class AuthenticationHelper
{

	public String getAuthenticationCookie(String user, String password) throws MalformedURLException
	{
		String cookie = null;
		cookie = RestAssured.given().redirects().allowCircular(true).and().redirects().max(10).and()
				.redirects().follow(true).param("user", user).param("password", password)
				.post(new URL(TestGlobalConfiguration.getBaseUrl() + "/cms/login"))
				.getCookie("JSESSIONID");

		return cookie;
	}

}
