package org.jahia.test.unomiapi.data;

import org.apache.commons.lang.StringUtils;

/**
 * Selenium tests global configuration Looks for a property from jahia
 * properties, they should be found in jahia.custom.properties If the property
 * is empty or doesn't exist, defines a default value
 */
public class TestGlobalConfiguration
{

	/** The default Jahia host. */
	private static String defaultJahiaHost = "localhost";

	/** The default Jahia protocol. */
	private static String defaultJahiaProtocol = "http";

	/** The default Jahia port. */
	private static String defaultJahiaPort = "";

	/** The default Unomi host. */
	private static String defaultUnomiHost = "localhost";

	/** The default Unomi protocol. */
	private static String defaultUnomiProtocol = "https";

	/** The default Unomi port. */
	private static String defaultUnomiPort = "";

	/** The default Jahia context. */
	private static String defaultJahiaContext = "";

	/** The default browser language. */
	private static String defaultTestLanguage = "en_US";

	private static String defaultLogsDirectory = "target/logs";

	private static boolean defaultLogErrorsOnly = false;

	private static String defaultUnomiKey = "670c26d1cc413346c3b2fd9ce65dab41";

	/**
	 * Gets the property value.
	 *
	 * @param propertyName
	 *            the property name
	 * @param defaultValue
	 *            the default value
	 * @return the property value
	 */
	private static String getPropertyValue(String propertyName, String defaultValue)
	{
		String value = System.getProperty(propertyName);
		if (StringUtils.isEmpty(value))
		{
			return defaultValue;
		}
		else
		{
			return value;
		}
	}

	/**
	 * Gets the property value.
	 *
	 * @param propertyName
	 *            the property name
	 * @param defaultValue
	 *            the default value
	 * @return the property value
	 */
	private static boolean getPropertyValue(String propertyName, boolean defaultValue)
	{
		String value = System.getProperty(propertyName);
		if (StringUtils.isEmpty(value))
		{
			return defaultValue;
		}
		else
		{
			return Boolean.valueOf(value);
		}
	}

	/**
	 * Gets the jahia server base url.
	 *
	 * @return the jahia server base url
	 */
	public static String getBaseUrl()
	{
		return getJahiaProtocol() + "://" + getJahiaHost() + getJahiaPort();
	}

	public static String getUnomiUrl()
	{
		return getUnomiProtocol() + "://" + getUnomiHost() + getUnomiPort();
	}

	/**
	 * Gets the jahia server graphQL url.
	 *
	 * @return the jahia server base url +
	 */
	public static String getGraphQlUrl()
	{
		return getBaseUrl() + "/modules/graphql";
	}

	/**
	 * Gets the jahia host.
	 *
	 * @return the jahia host
	 */
	public static String getJahiaHost()
	{
		return getPropertyValue("jahia.host", defaultJahiaHost);
	}

	/**
	 * Gets the jahia port. Adds ":" before the actual port if not empty
	 *
	 * @return the jahia port
	 */
	public static String getJahiaPort()
	{
		String jahiaPort = getPropertyValue("jahia.port", defaultJahiaPort);
		if (StringUtils.isNotEmpty(jahiaPort))
		{
			jahiaPort = ":" + jahiaPort;
		}
		return jahiaPort;
	}

	/**
	 * Gets the jahia protocol.
	 *
	 * @return the jahia protocol
	 */
	public static String getJahiaProtocol()
	{
		String jahiaProtocol = getPropertyValue("jahia.protocol", defaultJahiaProtocol);
		return jahiaProtocol;
	}

	/**
	 * Gets the unomi host.
	 *
	 * @return the unomi host
	 */
	public static String getUnomiHost()
	{
		return getPropertyValue("unomi.host", defaultUnomiHost);
	}

	/**
	 * Gets the unomi port. Adds ":" before the actual port if not empty
	 *
	 * @return the unomi port
	 */
	public static String getUnomiPort()
	{
		String unomiPort = getPropertyValue("unomi.port", defaultUnomiPort);
		if (StringUtils.isNotEmpty(unomiPort))
		{
			unomiPort = ":" + unomiPort;
		}
		return unomiPort;
	}

	/**
	 * Gets the unomi protocol.
	 *
	 * @return the unomi protocol
	 */
	public static String getUnomiProtocol()
	{
		return getPropertyValue("unomi.protocol", defaultUnomiProtocol);
	}

	/**
	 * Gets the unomi key.
	 *
	 * @return the unomi key
	 */
	public static String getUnomiKey()
	{
		return getPropertyValue("unomi.key", defaultUnomiKey);
	}

	/**
	 * Gets the jahia context. Adds a / prefix if missing
	 *
	 * @return the jahia context
	 */
	public static String getJahiaContext()
	{
		String jahiaContext = getPropertyValue("jahia.context", defaultJahiaContext);
		if (StringUtils.isNotEmpty(jahiaContext) && !jahiaContext.startsWith("/"))
		{
			jahiaContext = "/" + jahiaContext;
		}
		return jahiaContext;
	}

	public static String getLogsDirectory()
	{
		return getPropertyValue("logs.directory", defaultLogsDirectory);
	}

	public static boolean getLogErrorsOnly()
	{
		return getPropertyValue("log.errors.only", defaultLogErrorsOnly);
	}

	/**
	 * Gets the test language.
	 *
	 * @return the test language
	 */
	public static String getTestLanguage()
	{
		return getPropertyValue("selenium.test.language", defaultTestLanguage);
	}

	/**
	 * Properties to string.
	 *
	 * @return the string
	 */
	public static String propertiesToString()
	{
		StringBuffer sb = new StringBuffer();
		sb.append(" - Jahia host: " + getJahiaHost() + "\n");
		sb.append(" - Jahia port: " + getJahiaPort() + "\n");
		sb.append(" - Jahia context: " + getJahiaContext() + "\n");
		sb.append(" - Test language: " + getTestLanguage() + "\n");
		return sb.toString();
	}
}
