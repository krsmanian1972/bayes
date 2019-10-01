package bayes;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Map;
import java.util.Set;

import org.junit.Test;

public class WhatIfShould
{

	/**
	 * Return the distinct events caputred against a feature
	 */
	@Test
	public void getDistinctEvents()
	{

		FeatureTable table = buildTestData();
		Set<String> values = table.getDistinctEvents("play");
		assertTrue(2 == values.size());
	}

	/**
	 * Obtain the frequency of a type of event
	 */
	@Test
	public void getEventCount()
	{
		FeatureTable table = buildTestData();
		Map<String, Integer> values = table.getEventCount("play");
		assertEquals(9, values.get("yes").intValue());
		assertEquals(5, values.get("no").intValue());
	}

	@Test
	public void getBayesProbability()
	{
		FeatureTable table = buildTestData();

		Context context = new Context();
		context.put("outlook", "sunny");
		context.put("temperature", "cool");
		context.put("humidity", "high");
		context.put("windy", "true");
		
		context.probabilityOn("play");

		BayesProbability model = new BayesProbability();
		model.setFeatureTable(table);
		
		BayesResponse response = model.whatIf(context);
		Set<String> events = response.getEvents();
		double yes = response.getLikelihood("yes");
		double no = response.getLikelihood("no");
		
		System.out.println(response.getExplanation("yes"));
		System.out.println(yes);

		System.out.println(no);

		assertEquals(2,events.size());
	}

	private FeatureTable buildTestData()
	{

		FeatureTable table = new FeatureTable();

		table.setFeatures("outlook,temperature,humidity,windy,play");

		table.put("overcast,hot,high,FALSE,yes");
		table.put("overcast,cool,normal,TRUE,yes");
		table.put("overcast,mild,high,TRUE,yes");
		table.put("overcast,hot,normal,FALSE,yes");
		table.put("rainy,mild,high,FALSE,yes");
		table.put("rainy,cool,normal,FALSE,yes");
		table.put("rainy,cool,normal,TRUE,no");
		table.put("rainy,mild,normal,FALSE,yes");
		table.put("rainy,mild,high,TRUE,no");
		table.put("sunny,hot,high,FALSE,no");
		table.put("sunny,hot,high,TRUE,no");
		table.put("sunny,mild,high,FALSE,no");
		table.put("sunny,cool,normal,FALSE,yes");
		table.put("sunny,mild,normal,TRUE,yes");

		return table;
	}
}
