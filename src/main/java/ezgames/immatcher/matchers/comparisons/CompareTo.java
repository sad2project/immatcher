package ezgames.immatcher.matchers.comparisons;

import java.util.Comparator;
import java.util.function.IntPredicate;
import ezgames.immatcher.Matcher;
import ezgames.immatcher.Result;
import ezgames.immatcher.ResultBuilder;

public class CompareTo<T> implements Matcher<T> {

	private final T other;
	private final IntPredicate check;
	private final String output;
	private final Comparator<T> comparator;

	public CompareTo(T other, IntPredicate check, String output, Comparator<T> comparator) {
		this.other = other;
		this.check = check;
		this.output = output;
		this.comparator = comparator;
	}

	@Override
	public Result match(T actual) {
		ResultBuilder result = ResultBuilder.withMessages("was " + output, "was not " + output);
		int comparison = comparator.compare(actual, other);
		if(check.test(comparison))
			return result.pass();
		else
			return result.fail();
	}

}
