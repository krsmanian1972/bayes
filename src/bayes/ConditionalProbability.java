package bayes;

public class ConditionalProbability
{
	private String pivotLabel;
	private String pivotValue;
	private int pivotCount;

	private String eventValue;
	private String eventLabel;
	private int eventCount;
	
	
	public String getPivotLabel()
	{
		return pivotLabel;
	}

	public void setPivotLabel(String pivotLabel)
	{
		this.pivotLabel = pivotLabel;
	}

	public String getPivotValue()
	{
		return pivotValue;
	}

	public void setPivotValue(String pivotValue)
	{
		this.pivotValue = pivotValue;
	}

	public int getPivotCount()
	{
		return pivotCount;
	}

	public void setPivotCount(int pivotCount)
	{
		this.pivotCount = pivotCount;
	}

	public String getEventLabel()
	{
		return eventLabel;
	}

	public void setEventLabel(String eventLabel)
	{
		this.eventLabel = eventLabel;
	}

	public String getEventValue()
	{
		return eventValue;
	}

	public void setEventValue(String eventValue)
	{
		this.eventValue = eventValue;
	}

	public int getEventCount()
	{
		return eventCount;
	}

	public void setEventCount(int eventCount)
	{
		this.eventCount = eventCount;
	}
}
