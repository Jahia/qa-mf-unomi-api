package org.jahia.test.unomiapi.data;

public class Variant
{
	private String contentType;
	private String argument1;
	private String argument2;

	public Variant(String contentType, String argument1, String argument2)
	{
		this.setContentType(contentType);
		this.setArgument1(argument1);
		this.setArgument2(argument2);
	}

	public String getContentType()
	{
		return contentType;
	}

	public void setContentType(String contentType)
	{
		this.contentType = contentType;
	}

	public String getArgument1()
	{
		return argument1;
	}

	public void setArgument1(String argument1)
	{
		this.argument1 = argument1;
	}

	public String getArgument2()
	{
		return argument2;
	}

	public void setArgument2(String argument2)
	{
		this.argument2 = argument2;
	}

}