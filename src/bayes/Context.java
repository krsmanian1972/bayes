package bayes;

import java.util.HashMap;
import java.util.Map;

public class Context
{
	private final Map<String, Object> contextMap = new HashMap<>();

	private String probabilityFeature;

	public String getProbabilityFeature()
	{
		return probabilityFeature;
	}

	public void probabilityOn(String featureLabel)
	{
		this.probabilityFeature = featureLabel;
	}

	public void put(String featureLabel, Object value)
	{
		contextMap.put(featureLabel, value);
	}

	public Map<String, Object> getContextMap()
	{
		return contextMap;
	}

}
