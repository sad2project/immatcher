package ezgames.immatcher.matchers;

import static ezgames.immatcher.Matchers.*;
import ezgames.immatcher.Matcher;

/**
 * {@code BasicMatchers} is a collection of static methods for creating
 * {@code Matcher}s that test basic things that apply to all objects or primitives.
 */
public class BasicMatchers {
	/**
	 * Returns a {@code Matcher} that tests that the object under test is null.
	 * @return a {@code Matcher} that tests for null
	 */
	public static <T> Matcher<T> isNull() {
		return IsNull.isNull();
	}
	
	/**
	 * Returns a {@code Matcher} that tests that the object under test is not null.
	 * @return a {@code Matcher} that tests for non-null
	 */
	public static <T> Matcher<T> isNotNull() {
		return not(isNull());
	}
	
	/**
	 * Returns a {@code Matcher} that tests that the object under test is equal to
	 * the given object.
	 * @param object - object to test for equality against
	 * @return a {@code Matcher} that tests for equality
	 */
	public static <T> Matcher<T> isEqualTo(T object) {
		return Equals.isEqualTo(object);
	}
	
	/**
	 * Returns a {@code Matcher} that tests that the object under test is not
	 * equal to the given object.
	 * @param object - object to test for non-equality against
	 * @return a {@code Matcher} that tests for non-equality
	 */
	public static <T> Matcher<T> isNotEqualTo(T object) {
		return invert(isEqualTo(object), "wasn't equal to " + object.toString());
	}
}
