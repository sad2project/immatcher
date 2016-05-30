package ezgames.immatcher.matchers.collections;

import java.util.Collection;
import ezgames.immatcher.Matcher;
import ezgames.immatcher.Result;

public class CollectionContainsOnly implements Matcher<Collection<?>> {

	public static Matcher<Collection<?>> containsOnly(Collection<?> contained) {
		return new CollectionContainsOnly(contained);
	}
	
	@Override
	public Result match(Collection<?> actual) {
		if(actual.containsAll(contained))
			if(actual.size() == contained.size())
				return new Result(false, expected, "didn't contain only the given elements");
			else
				return new Result(false, expected, "contained other elements too");
		else
			return new Result(false, expected, "didn't contain all of the elements");
	}

	private CollectionContainsOnly(Collection<?> contained) {
		this.contained = contained;
	}

	private final Collection<?> contained;
	private final String expected = "contained only the given elements";
}
