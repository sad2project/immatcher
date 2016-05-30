package ezgames.immatcher.matchers.collections;

import java.util.Collection;

import ezgames.immatcher.Matcher;
import ezgames.immatcher.Result;
import ezgames.immatcher.ResultBuilder;

public class CollectionContains implements Matcher<Collection<?>> {

	public static CollectionContains contains(Object object)
	{
		return new CollectionContains(object);
	}
	
	public Result match(Collection<?> actual)
	{
		ResultBuilder result = ResultBuilder.withMessages(expected, onFailure);
		
		if(actual.contains(object))
			return result.pass();
		else
			return result.fail();
	}
	
	CollectionContains(Object object)
	{
		this.object = object;
		this.expected = "contained " + object.toString();
		this.onFailure = "didn't contain " + object.toString();
	}

	private Object object;
	private String expected;
	private String onFailure;
}
