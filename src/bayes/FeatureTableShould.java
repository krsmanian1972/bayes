package bayes;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class FeatureTableShould {

	@Test
	public void buildFeatureTable() {
		Feature outlook = new Feature("outlook");
		Feature temperature = new Feature("temperature");
		Feature humidity = new Feature("humidity");
		Feature windy = new Feature("windy");
		Feature play = new Feature("play");

		FeatureTable table = new FeatureTable();
		table.add(outlook);
		table.add(temperature);
		table.add(humidity);
		table.add(windy);
		table.add(play);

		table.put("overcast,hot,high,FALSE,yes");
		table.put("overcast,cool,normal,TRUE,yes");

		assertEquals("overcast,overcast", outlook.asCSV());
		assertEquals("hot,cool", temperature.asCSV());
		assertEquals("high,normal", humidity.asCSV());
		assertEquals("FALSE,TRUE", windy.asCSV());
		assertEquals("yes,yes", play.asCSV());
	}

	@Test
	public void handleMissingValues() {
		Feature outlook = new Feature("outlook");
		Feature temperature = new Feature("temperature");
		Feature humidity = new Feature("humidity");
		Feature windy = new Feature("windy");
		Feature play = new Feature("play");

		FeatureTable table = new FeatureTable();
		table.add(outlook);
		table.add(temperature);
		table.add(humidity);
		table.add(windy);
		table.add(play);

		table.put("overcast,,,,");
		table.put(",hot,,,");

		assertEquals("overcast,?", outlook.asCSV());
		assertEquals("?,hot", temperature.asCSV());
		assertEquals("?,?", humidity.asCSV());
		assertEquals("?,?", windy.asCSV());
		assertEquals("?,?", play.asCSV());
	}

	@Test
	public void addFeatures() {

		FeatureTable table = new FeatureTable();

		table.setFeatures("outlook,temperature,humidity,windy,play");
		assertEquals("outlook,temperature,humidity,windy,play", table.getFeatureLabels());

		table.setFeatures("outlook,,humidity,,play");
		assertEquals("outlook,FEATURE_1,humidity,FEATURE_3,play", table.getFeatureLabels());
	}

	@Test
	public void getFeatureByLabel() {
		FeatureTable table = new FeatureTable();
		table.setFeatures("outlook,temperature,humidity,windy,play");
		Feature feature = table.getFeature("outlook");
		assertEquals("outlook", feature.getLabel());
	}

	@Test
	public void setFeatureType() {
		FeatureTable table = new FeatureTable();
		table.setFeatures("outlook,temperature,humidity,windy,play");
		Feature feature = table.getFeature("temperature");
		feature.setType(FeatureType.Numeric);
		assertEquals(FeatureType.Numeric, feature.getType());
	}
	
	/**
	 * An important method to aid in conditional probability
	 * 
	 * How many times an event occurs with respect to another event.
	 * 
	 */
	@Test
	public void relativeFrequency() {
		
		FeatureTable featureTable = buildTestData();
		
		int coolPlayNo = featureTable.getRelativeFrequency("temperature", "cool", "play", "no");
		int coolPlayYes = featureTable.getRelativeFrequency("temperature", "cool", "play", "yes");
	
		assertEquals(1,coolPlayNo);
		assertEquals(3,coolPlayYes);
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
