package ezgames.immatcher.matchers.iterables;

import static ezgames.immatcher.Matchers.*;

import ezgames.immatcher.Matcher;

public class IterablesMatchers {
	/**
	 * Returns a {@code Matcher} that checks that the {@code Iterable} under test
	 * has n elements.
	 * @param size - the expected number of elements
	 * @return a {@code Matcher} that checks for a certain size
	 */
	public static Matcher<Iterable<?>> hasSizeOf(int size) {
		return new IterableHasSizeOf(size);
	}
	
	/**
	 * Returns a {@code Matcher} that checks that the {@code Iterable} under test
	 * does not have n elements.
	 * @param size - the number of elements not expected
	 * @return a {@code Matcher} that checks for the lack of a certain size
	 */
	public static Matcher<Iterable<?>> doesNotHaveSizeOf(int size) {
		return invert(hasSizeOf(size), "did not have size of " + size);
	}
}
