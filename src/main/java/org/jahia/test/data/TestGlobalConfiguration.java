package org.jahia.test.data;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Selenium tests global configuration Looks for a property from jahia
 * properties, they should be found in jahia.custom.properties If the property
 * is empty or doesn't exist, defines a default value
 */
public class TestGlobalConfiguration
{

	private static final Logger logger = LoggerFactory.getLogger(TestGlobalConfiguration.class);

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

	/** The default Selenium Hub protocol. */
	private static String defaultHubProtocol = "http";

	/** The default Selenium Hub host. */
	private static String defaultHubHost = "seleniumgrid.jahia.com";

	/** The default Selenium Hub host. */
	private static String defaultHubPort = "";

	/** The default html capture directory */
	private static String defaultHtmlCaptureDirectory = "target/seleniumCapture";

	/** The default htmlc capture on error boolean */
	private static boolean defaultHtmlCaptureOnError = true;

	/** The default downloads path. */
	private static String defaultDownloadsPath = "target/downloads";

	/** The default browser language. */
	private static String defaultTestLanguage = "en_US";

	/** The default wait for publish. */
	private static boolean defaultWaitForPublish = true;

	/** The default selenium local identifier. */
	private static String defaultSeleniumLocalId = "112233";

	/** The default selenium local identifier. */
	private static String defaultSeleniumGeoLocation = "FR";

	/** The default real mobile device mode boolean */
	private static boolean defaultRealMobile = false;

	/** The default skip anthracite tests boolean */
	private static boolean defaultSkipAnthraciteTests = false;

	/** The default skip next fixture on error boolean */
	private static boolean defaultSkipNextFixturesOnError = false;

	/** The default wait timeout delay. */
	private static int defaultWaitTimeoutSec = 30;

	/** The default page load timeout delay. */
	private static int defaultPageLoadTimeoutSec = 360;

	/** The default NSPR_LOG_MODULES environment variable value. */
	private static String defaultNsprLogModules = "timestamp,nsHttp:3,nsStreamPump:5";

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
	 * Gets the selenium hub url.
	 *
	 * @return the selenium hub url
	 */
	public static URL getSeleniumHubUrl()
	{
		try
		{
			return new URL(getHubProtocol() + "://" + getHubHost() + getHubPort() + "/wd/hub");
		}
		catch (MalformedURLException e)
		{
			logger.error("Error building selenium Hub Url");
		}
		return null;
	}

	/**
	 * Gets the jahia host.
	 *
	 * @return the jahia host
	 */
	public static String getJahiaHost()
	{
		return getPropertyValue("selenium.jahia.host", defaultJahiaHost);
	}

	/**
	 * Gets the jahia port. Adds ":" before the actual port if not empty
	 *
	 * @return the jahia port
	 */
	public static String getJahiaPort()
	{
		String jahiaPort = getPropertyValue("selenium.jahia.port", defaultJahiaPort);
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
		String jahiaProtocol = getPropertyValue("selenium.jahia.protocol", defaultJahiaProtocol);
		return jahiaProtocol;
	}

	/**
	 * Gets the unomi host.
	 *
	 * @return the unomi host
	 */
	public static String getUnomiHost()
	{
		return getPropertyValue("selenium.unomi.host", defaultUnomiHost);
	}

	/**
	 * Gets the unomi port. Adds ":" before the actual port if not empty
	 *
	 * @return the unomi port
	 */
	public static String getUnomiPort()
	{
		String unomiPort = getPropertyValue("selenium.unomi.port", defaultUnomiPort);
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
		return getPropertyValue("selenium.unomi.protocol", defaultUnomiProtocol);
	}

	/**
	 * Gets the selenium hub protocol.
	 *
	 * @return the selenium hub protocol
	 */
	public static String getHubProtocol()
	{
		return getPropertyValue("selenium.hub.protocol", defaultHubProtocol);
	}

	/**
	 * Gets the selenium hub host.
	 *
	 * @return the selenium hub host
	 */
	public static String getHubHost()
	{
		return getPropertyValue("selenium.hub.host", defaultHubHost);
	}

	/**
	 * Gets the selenium hub port. Adds ":" before the actual port if not empty
	 *
	 * @return the selenium hub port
	 */
	public static String getHubPort()
	{
		String jahiaHubPort = getPropertyValue("selenium.hub.port", defaultHubPort);
		if (StringUtils.isNotEmpty(jahiaHubPort))
		{
			jahiaHubPort = ":" + jahiaHubPort;
		}
		return jahiaHubPort;
	}

	/**
	 * Gets the jahia context. Adds a / prefix if missing
	 *
	 * @return the jahia context
	 */
	public static String getJahiaContext()
	{
		String jahiaContext = getPropertyValue("selenium.jahia.context", defaultJahiaContext);
		if (StringUtils.isNotEmpty(jahiaContext) && !jahiaContext.startsWith("/"))
		{
			jahiaContext = "/" + jahiaContext;
		}
		return jahiaContext;
	}

	/**
	 * Gets the default wait timeout
	 *
	 * @return the default wait timeout
	 */
	public static int getDefaultWaitTimeoutSec()
	{
		return Integer.parseInt(
				getPropertyValue("selenium.wait.timeout", String.valueOf(defaultWaitTimeoutSec)));
	}

	/**
	 * Gets the default page load timeout
	 *
	 * @return the default wait timeout
	 */
	public static int getDefaultPageLoadTimeoutSec()
	{
		return Integer.parseInt(getPropertyValue("selenium.page.load.timeout",
				String.valueOf(defaultPageLoadTimeoutSec)));
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
	 * Gets the browser capability
	 *
	 * @return the browser capability
	 */
	public static String getCapBrowser()
	{
		return getPropertyValue("selenium.cap.browser", "");
	}

	/**
	 * Gets the browser version capability
	 *
	 * @return the browser version capability
	 */
	public static String getCapBrowserVersion()
	{
		return getPropertyValue("selenium.cap.browser.version", "");
	}

	/**
	 * Gets the os capability
	 *
	 * @return the os capability
	 */
	public static String getCapOs()
	{
		return getPropertyValue("selenium.cap.os", "");
	}

	/**
	 * Gets the os version capability
	 *
	 * @return the os versio capability
	 */
	public static String getCapOsVersion()
	{
		return getPropertyValue("selenium.cap.os.version", "");
	}

	/**
	 * Gets the platform capability
	 *
	 * @return the platform capability
	 */
	public static String getCapPlatform()
	{
		return getPropertyValue("selenium.cap.platform", "");
	}

	/**
	 * Gets the device capability
	 *
	 * @return the device capability
	 */
	public static String getCapDevice()
	{
		return getPropertyValue("selenium.cap.device", "");
	}

	/**
	 * Gets the real mobile device capability
	 *
	 * @return the real mobile device capability
	 */
	public static boolean getRealMobile()
	{
		return getPropertyValue("selenium.cap.real.mobile", defaultRealMobile);
	}

	public static boolean getSkipAnthraciteTests()
	{
		return getPropertyValue("selenium.skip.anthracite.tests", defaultSkipAnthraciteTests);
	}

	public static boolean getSkipNextFixturesOnError()
	{
		return getPropertyValue("selenium.skip.next.fixtures.on.error",
				defaultSkipNextFixturesOnError);
	}

	public static boolean getSeleniumWaitForPublish()
	{
		return Boolean.valueOf(
				getPropertyValue("selenium.waitForPublish", String.valueOf(defaultWaitForPublish)));
	}

	public static String getSeleniumLocalId()
	{
		return getPropertyValue("selenium.local.id", defaultSeleniumLocalId);
	}

	public static String getSeleniumGeoLocation()
	{
		return getPropertyValue("selenium.geoLocation", defaultSeleniumGeoLocation);
	}

	/**
	 * Gets the downloads path
	 *
	 * @return
	 */
	public static String getDownloadsPath()
	{
		return getPropertyValue("selenium.downloads.path", defaultDownloadsPath)
				+ System.getProperty("file.separator");
	}

	/**
	 * Gets the downloads absolute path
	 *
	 * @return
	 */
	public static String getDownloadsAbsolutePath()
	{
		File downloadDirectory = new File(TestGlobalConfiguration.getDownloadsPath());
		if (!downloadDirectory.exists())
		{
			downloadDirectory.mkdirs();
		}
		return downloadDirectory.getAbsolutePath();
	}

	/**
	 * Gets the downloads path
	 *
	 * @return
	 */
	public static String getHtmlCaptureDirectory()
	{
		return getPropertyValue("selenium.capture.directory", defaultHtmlCaptureDirectory)
				+ System.getProperty("file.separator");
	}

	public static boolean getHtmlCaptureOnError()
	{
		return Boolean.valueOf(getPropertyValue("selenium.htmlCaptureOnError",
				String.valueOf(defaultHtmlCaptureOnError)));
	}

	/**
	 * Gets the downloads path
	 *
	 * @return
	 */
	public static String getRecordingRegExp()
	{
		return getPropertyValue("selenium.recording.regexp", "");
	}

	/**
	 * Gets the downloads path
	 *
	 * @return
	 */
	public static String getDebugLogRegExp()
	{
		return getPropertyValue("selenium.debuglog.regexp", "");
	}

	/**
	 * Gets the downloads path
	 *
	 * @return
	 */
	public static String getNsprLogModules()
	{
		return getPropertyValue("selenium.nspr.log.modules", defaultNsprLogModules);
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
		sb.append(" - Downloads path: " + getDownloadsPath() + "\n");
		if (!getRecordingRegExp().isEmpty())
		{
			sb.append(" - Recording regular expression: " + getRecordingRegExp() + "\n");
		}
		if (!getDebugLogRegExp().isEmpty())
		{
			sb.append(" - Debug-Log regular expression: " + getDebugLogRegExp() + "\n");
		}
		sb.append(" - Test language: " + getTestLanguage() + "\n");
		sb.append(" - Browser: " + getCapBrowser() + "\n");
		return sb.toString();
	}
}
