package org.jahia.test.unomiapi.data.Event;

public class EventListRequest
{
	private Condition condition;


	public EventListRequest(Condition condition)
	{
		this.condition = condition;
	}

	public Condition getCondition()
	{
		return condition;
	}
	public void setCondition(Condition condition)
	{
		this.condition = condition;
	}

}
