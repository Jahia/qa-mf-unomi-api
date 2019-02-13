package org.jahia.test.data;

public class PersonaProperty
{
	private String propertyName;
	private String category;
	private String type;
	private String value;

	public PersonaProperty(String propertyName, String category, String type, String value)
	{
		this.propertyName = propertyName;
		this.category = category;
		this.type = type;
		this.value = value;
	}

	public String getPropertyName()
	{
		return propertyName;
	}

	public void setPropertyName(String propertyName)
	{
		this.propertyName = propertyName;
	}

	public String getCategory()
	{
		return category;
	}

	public void setCategory(String category)
	{
		this.category = category;
	}

	public String getType()
	{
		return type;
	}

	public void setType(String type)
	{
		this.type = type;
	}

	public String getValue()
	{
		return value;
	}

	public void setValue(String value)
	{
		this.value = value;
	}

}
