package bayes;

import java.util.List;
import java.util.Map;
import java.util.Set;

public class BayesResponse
{
	private Map<String, Integer> pivotMap;
	private Map<String, List<ConditionalProbability>> probabilityMap;

	public Set<String> getEvents()
	{
		return pivotMap.keySet();
	}

	public String getExplanation(String event)
	{
		StringBuilder builder = new StringBuilder();
		
		builder.append("Likelihood Table for ").append(":").append(event).append("\n");
		
		List<ConditionalProbability> cps = probabilityMap.get(event);
		for (ConditionalProbability cp : cps)
		{
			builder.append(cp.getEventLabel());
			builder.append("\t\t\t");
			builder.append(cp.getEventValue());
			builder.append("\t\t");
			builder.append(String.valueOf(cp.getEventCount()));
			builder.append("/");
			builder.append(String.valueOf(cp.getPivotCount()));
			builder.append("\n");
		}
		
		double eventCount = (double) pivotMap.get(event);
		double pivotSum = getPivotSum();
	
		builder.append("expected event ");
		builder.append("\t\t\t");
		builder.append(event);
		builder.append("\t\t");
		builder.append(String.valueOf(eventCount));
		builder.append("/");
		builder.append(String.valueOf(pivotSum));
		builder.append("\n");
	
	
		return builder.toString();
	}

	public double getLikelihood(String event)
	{
		double eventCount = (double) pivotMap.get(event);
		double prE = eventCount / getPivotSum();

		double likelihood = prE;

		List<ConditionalProbability> cps = probabilityMap.get(event);
		for (ConditionalProbability cp : cps)
		{
			double cpLikelihood = cp.getEventCount() / eventCount;
			likelihood = likelihood * cpLikelihood;
		}

		return likelihood;
	}

	public void setConditionalProbabilities(Map<String, List<ConditionalProbability>> probabilityMap)
	{
		this.probabilityMap = probabilityMap;

	}

	public void setPivotMap(Map<String, Integer> pivotMap)
	{
		this.pivotMap = pivotMap;
	}

	public int getPivotSum()
	{
		int sum = 0;
		Set<String> pivotLabels = pivotMap.keySet();
		for (String key : pivotLabels)
		{
			sum = sum + pivotMap.get(key);
		}
		return sum;
	}

}
