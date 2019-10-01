package bayes;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.StringJoiner;

/**
 * This represents the entire data matrix. Where each of the columns are an
 * instance of feature.
 * 
 * @author raja
 *
 */
public class FeatureTable
{

	private final List<Feature> features = new ArrayList<>();

	private final Map<String, Feature> featureMap = new HashMap<>();

	private static final String ARB_FEATURE_LABEL = "FEATURE_";

	public void add(Feature feature)
	{
		features.add(feature);
		featureMap.put(feature.getLabel(), feature);
	}

	public Feature getFeature(String label)
	{
		return featureMap.get(label);
	}

	public void put(String csvInput)
	{

		if (csvInput == null)
		{
			return;
		}

		String[] values = csvInput.split(",");
		for (int index = 0; index < features.size(); index++)
		{
			String value = index < values.length ? values[index] : "?";
			value = value.trim().isEmpty() ? "?" : value;

			Feature feature = features.get(index);
			feature.set(value);
		}

	}

	/**
	 * Will clear all the data from Feature Table. Will Create
	 * fresh columns of feature.
	 *  
	 * @param csvInput
	 */
	public void setFeatures(String csvInput)
	{
		features.clear();
		featureMap.clear();

		if (csvInput == null)
		{
			return;
		}

		String[] featureLabels = csvInput.split(",");
		for (int index = 0; index < featureLabels.length; index++)
		{
			String label = featureLabels[index];
			label = label.trim().isEmpty() ? getArbLabel(index) : label;

			this.add(new Feature(label));
		}
	}

	private String getArbLabel(int index)
	{
		StringBuilder builder = new StringBuilder();
		builder.append(ARB_FEATURE_LABEL);
		builder.append(String.valueOf(index));
		return builder.toString();
	}

	public String getFeatureLabels()
	{
		StringJoiner joiner = new StringJoiner(",");
		for (Feature feature : features)
		{
			joiner.add(feature.getLabel());
		}
		return joiner.toString();
	}

	public Set<String> getDistinctEvents(String featureLabel)
	{
		Feature feature = featureMap.get(featureLabel);
		return feature.getDistinctEvents();
	}

	public Map<String, Integer> getEventCount(String featureLabel)
	{
		Feature feature = featureMap.get(featureLabel);
		return feature.getEventCount();
	}

	/**
	 * The Number of times a feature has a value; when the pivot feature has a
	 * particular value
	 * 
	 * @param featureLabel
	 * @param featureValue
	 * @param pivotLabel
	 * @param pivotValue
	 * @return
	 */
	public int getRelativeFrequency(String featureLabel, String featureValue, String pivotLabel, String pivotValue)
	{
		int sum = 0;

		Feature feature = featureMap.get(featureLabel);
		Feature pivotFeature = featureMap.get(pivotLabel);

		for (int index = 0; index < pivotFeature.size(); index++)
		{
			String pValue = pivotFeature.get(index);
			String fValue = feature.get(index);

			if (pivotValue.equalsIgnoreCase(pValue) && featureValue.equalsIgnoreCase(fValue))
			{
				sum++;
			}
		}

		return sum;
	}

}
