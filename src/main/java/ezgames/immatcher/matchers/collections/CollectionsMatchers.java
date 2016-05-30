package ezgames.immatcher.matchers.collections;

import static ezgames.immatcher.Matchers.*;

import java.util.Collection;
import ezgames.immatcher.Matcher;

/**
 * {@code CollectionsMatchers} contains methods for creating {@code Matcher}s
 * specifically used with {@code Collection}s. 
 */
public class CollectionsMatchers {
	/**
	 * A {@code Matcher} that tests that the {@code Collection} under
	 * test is empty.
	 */
	public static Matcher<Collection<?>> isEmpty = new CollectionIsEmpty();
	
	/**
	 * A {@code Matcher} that tests that the {@code Collection} under
	 * test is not empty.
	 */
	public static Matcher<Collection<?>> isNotEmpty = invert(isEmpty, "was not empty");
	
	/**
	 * Returns a {@code Matcher} that tests that the {@code Collection} under
	 * test contains the given object.
	 * @param object - the object to look for in the {@code Collection}
	 * @return a {@code Matcher} that looks for 'object' in the {@code Collection}
	 */
	public static Matcher<Collection<?>> contains(Object object){
		return new CollectionContains(object);
	}
	
	/**
	 * Returns a {@code Matcher} that tests that the {@code Collection} under
	 * test does not contain the given object.
	 * @param object - the object to make sure isn't in the {@code Collection}
	 * @return a {@code Matcher} that checks that 'object' isn't in the 
	 * {@code Collection}
	 */
	public static Matcher<Collection<?>> doesNotContain(Object object){
		return not(contains(object));
	}
	
	/**
	 * Returns a {@code Matcher} that tests that the {@code Collection} under
	 * test contains all the elements in the given {@code Collection}
	 * @param contained - a {@code Collection} of objects that should all be in
	 * the tested {@code Collection}
	 * @return a {@code Matcher} that tests that all the objects in the given
	 * {@code Collection} are in the {@code Collection} under test
	 */
	public static Matcher<Collection<?>> containsAll(Collection<?> contained) {
		return new CollectionContainsAll(contained);
	}
	
	/**
	 * Returns a {@code Matcher} that tests that the {@code Collection} under
	 * test does not contain all of the elements in the given {@code Collection}
	 * @param contained - a {@code Collection} of objects that should not all be
	 * in the tested {@code Collection}
	 * @return a {@code Matcher} that tests that all the objects in the given
	 * {@code Collection} are not in the {@code Collection} under test
	 */
	public static Matcher<Collection<?>> doesNotContainAll(Collection<?> contained) {
		return not(containsAll(contained));
	}
	
	/**
	 * Returns a {@code Matcher} that tests that the {@code Collection} under
	 * test contains only the elements in the given {@code Collection}
	 * @param contained - a {@code Collection} of objects that should be the only
	 * elements in the tested {@code Collection}
	 * @return a {@code Matcher} that tests that the objects in the given
	 * {@code Collection} are the only elements in the {@code Collection} under test
	 */
	public static Matcher<Collection<?>> containsOnly(Collection<?> contained) {
		return CollectionContainsOnly.containsOnly(contained);
	}
}
