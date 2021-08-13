package org.jahia.test.unomiapi.data.Event;

import java.util.ArrayList;
import java.util.List;

public class ParameterValues
{
	private String comparisonOperator;
	private String propertyName;
	private List<Long> propertyValues;


	public ParameterValues(String comparisonOperator, String propertyName, long minValue, long maxValue )
	{
		this.comparisonOperator = comparisonOperator;
		this.propertyName = propertyName;
		this.propertyValues = new ArrayList<Long>();
		this.propertyValues.add(minValue);
		this.propertyValues.add(maxValue);
	}

	public String getComparisonOperator()
	{
		return comparisonOperator;
	}
	public void setComparisonOperator(String comparisonOperator)
	{
		this.comparisonOperator = comparisonOperator;
	}
	public String getPropertyName()
	{
		return propertyName;
	}
	public void setPropertyName(String propertyName)
	{
		this.propertyName = propertyName;
	}
	public List<Long> getPropertyValues()
	{
		return propertyValues;
	}
	public void setPropertyValues(List<Long> propertyValues)
	{
		this.propertyValues = propertyValues;
	}


}