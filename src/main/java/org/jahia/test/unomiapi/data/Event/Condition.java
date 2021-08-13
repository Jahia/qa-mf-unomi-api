package org.jahia.test.unomiapi.data.Event;

public class Condition
{
	private ParameterValues parameterValues;
	private String type;


	public Condition(String type, ParameterValues parameterValues)
	{
		this.type = type;
		this.parameterValues = parameterValues;
	}

	public String getType()
	{
		return type;
	}
	public void setType(String type)
	{
		this.type = type;
	}

	public ParameterValues getParameterValues()
	{
		return parameterValues;
	}
	public void setParameterValues(ParameterValues parameterValues)
	{
		this.parameterValues = parameterValues;
	}

}