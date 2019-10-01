package bayes;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.StringJoiner;

/**
 * Represents a vertical column in a matrix of data. The Label is the header and
 * the events are the values.
 * 
 * @author raja
 *
 */
public class Feature
{
	private String label;
	private FeatureType featureType;

	private final List<String> events = new ArrayList<>();

	public Feature(String label)
	{
		this.label = label;
	}

	public String getLabel()
	{
		return this.label;
	}

	public void set(String event)
	{
		events.add(event);
	}

	public void setType(FeatureType type)
	{
		this.featureType = type;
	}

	public FeatureType getType()
	{
		return featureType;
	}

	public String asCSV()
	{
		StringJoiner joiner = new StringJoiner(",");
		for (String event : events)
		{
			joiner.add(event);
		}
		return joiner.toString();
	}

	/**
	 * As Set removes the duplicate and will offer the distinct events.
	 * 
	 * @return
	 */
	public Set<String> getDistinctEvents()
	{
		Set<String> values = new HashSet<>();
		for (String event : events)
		{
			values.add(event);
		}
		return values;
	}

	/**
	 * How many times the event occurred in the column of a matrix. Key is the
	 * event value. Value is the number of occurrences.
	 * 
	 * @return
	 */
	public Map<String, Integer> getEventCount()
	{
		Map<String, Integer> countMap = new HashMap<>();

		for (String event : events)
		{
			String anEvent = event.toLowerCase();

			if (countMap.containsKey(anEvent))
			{
				int sum = countMap.get(anEvent);
				countMap.put(anEvent, sum + 1);
			}
			else
			{
				countMap.put(anEvent, 1);
			}
		}

		return countMap;
	}

	/**
	 * The size of the column
	 * 
	 * @return
	 */
	public int size()
	{
		return events.size();
	}

	/**
	 * The event at the specified row
	 * 
	 * @param index
	 * @return
	 */
	public String get(int index)
	{
		return events.get(index);
	}
}
