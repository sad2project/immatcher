package ezgames.immatcher.matchers.collections;

import java.util.Collection;

import ezgames.immatcher.Matcher;
import ezgames.immatcher.Result;
import ezgames.immatcher.ResultBuilder;

public class CollectionIsEmpty implements Matcher<Collection<?>> {

	public static CollectionIsEmpty isEmpty()
	{
		return new CollectionIsEmpty();
	}
	
	@Override
	public Result match(Collection<?> actual) {
		ResultBuilder result = ResultBuilder.withMessages(expected, String.format(actualFormat, actual.size()));
		if(actual.isEmpty())
			return result.pass();
		else
			return result.fail();
	}
	
	CollectionIsEmpty() {}
	
	private static final String expected = "was empty";
	private static final String actualFormat = "had %d elements";

}
