package bayes;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class BayesProbability
{
	private FeatureTable featureTable;
	
	public void setFeatureTable(FeatureTable table)
	{
		this.featureTable = table;
	}

	/**
	 * The conditional probability evaluation. 
	 * @param context
	 * @return
	 */
	public BayesResponse whatIf(Context context)
	{
		Map<String,List<ConditionalProbability>> probabilityMap = new HashMap<>();

		String probabilityFor = context.getProbabilityFeature();
		Map<String,Integer> pivotMap = featureTable.getEventCount(probabilityFor);
		Set<String> pivotValues = pivotMap.keySet();
	
		Map<String,Object> contextMap = context.getContextMap();
		Set<String> labels = contextMap.keySet();
		for(String contextLabel:labels)
		{
			String contextValue = (String)contextMap.get(contextLabel);
			
			Set<String> events = featureTable.getDistinctEvents(contextLabel);
			
			for(String event:events)
			{
				if(!event.equalsIgnoreCase(contextValue))
				{
					continue;
				}
	
				for(String pivotValue:pivotValues)
				{
					int count = featureTable.getRelativeFrequency(contextLabel, event, probabilityFor, pivotValue);
					int pivotCount = pivotMap.get(pivotValue);
					
					ConditionalProbability cp = new ConditionalProbability();
					cp.setEventLabel(contextLabel);
					cp.setEventValue(event);
					cp.setEventCount(count);
					
					cp.setPivotLabel(probabilityFor);
					cp.setPivotValue(pivotValue);
					cp.setPivotCount(pivotCount);
					
					capture(probabilityMap,cp);
				}
			}
		}
		
		BayesResponse bayesResponse = new BayesResponse();
		bayesResponse.setConditionalProbabilities(probabilityMap);
		bayesResponse.setPivotMap(pivotMap);
		
		return bayesResponse;
	}

	private void capture(Map<String, List<ConditionalProbability>> probabilityMap, ConditionalProbability cp)
	{
		String pivotValue = cp.getPivotValue();
		
		List<ConditionalProbability> cps = probabilityMap.get(pivotValue);
		if(cps == null)
		{
			cps = new ArrayList<>();
			probabilityMap.put(pivotValue,cps);
		}
		cps.add(cp);
	}

	
	
	
}
