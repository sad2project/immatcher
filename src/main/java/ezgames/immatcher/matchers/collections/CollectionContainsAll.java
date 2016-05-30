package ezgames.immatcher.matchers.collections;

import static ezgames.immatcher.Matchers.not;

import java.util.Collection;
import ezgames.immatcher.Matcher;
import ezgames.immatcher.Result;
import ezgames.immatcher.ResultBuilder;

public class CollectionContainsAll implements Matcher<Collection<?>> {

	public static Matcher<Collection<?>> containsAll(Collection<?> contained) {
		return new CollectionContainsAll(contained);
	}
	
	public static Matcher<Collection<?>> doesNotContainAll(Collection<?> contained) {
		return not(containsAll(contained));
	}
	
	public Result match(Collection<?> actual) {
		if(actual.containsAll(contained))
			return result.pass();
		else
			return result.fail();
	}
	
	private final Collection<?> contained;
	private final ResultBuilder result = ResultBuilder.withMessages("contained all given elements", "didn't contain all the given elements");
	
	
	CollectionContainsAll(Collection<?> contained) {
		this.contained = contained;
	}
}
