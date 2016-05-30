package ezgames.immatcher.matchers.comparisons;

import java.util.Comparator;
import ezgames.immatcher.Matcher;

public class ComparisonMatchers {
	public static <T extends Comparable<T>> Matcher<T> isLessThan(T other) {
		return new CompareTo<>(other, i -> i < 0, "less than", (obj1, obj2) -> obj1.compareTo(obj2));
	}
	
	public static <T extends Comparable<T>> Matcher<T> isGreaterThan(T other) {
		return new CompareTo<>(other, i -> i > 0, "greater than", (obj1, obj2) -> obj1.compareTo(obj2));
	}
	
	public static <T extends Comparable<T>> Matcher<T> isEquivalentTo(T other) {
		return new CompareTo<>(other, i -> i == 0, "equivalent to", (obj1, obj2) -> obj1.compareTo(obj2));
	}
	
	public static <T extends Comparable<T>> Matcher<T> isLessThanOrEqualTo(T other) {
		return new CompareTo<>(other, i -> i <= 0, "less than or equal to", (obj1, obj2) -> obj1.compareTo(obj2));
	}
	
	public static <T extends Comparable<T>> Matcher<T> isGreaterThanOrEqualTo(T other) {
		return new CompareTo<>(other, i -> i >= 0, "greater than or equal to", (obj1, obj2) -> obj1.compareTo(obj2));
	}
	
	public static <T extends Comparable<T>> Matcher<T> isNotEquivalentTo(T other) {
		return new CompareTo<>(other, i -> i != 0, "not equivalent to", (obj1, obj2) -> obj1.compareTo(obj2));
	}
	
	public static <T> Matcher<T> isLessThan(T other, Comparator<T> comparator) {
		return new CompareTo<>(other, i -> i < 0, "less than", comparator);
	}
	
	public static <T> Matcher<T> isGreaterThan(T other, Comparator<T> comparator) {
		return new CompareTo<>(other, i -> i > 0, "greater than", comparator);
	}
	
	public static <T> Matcher<T> isEquivalentTo(T other, Comparator<T> comparator) {
		return new CompareTo<>(other, i -> i == 0, "equivalent to", comparator);
	}
	
	public static <T> Matcher<T> isLessThanOrEqualTo(T other, Comparator<T> comparator) {
		return new CompareTo<>(other, i -> i <= 0, "less than or equal to", comparator);
	}
	
	public static <T> Matcher<T> isGreaterThanOrEqualTo(T other, Comparator<T> comparator) {
		return new CompareTo<>(other, i -> i >= 0, "greater than or equal to", comparator);
	}
	
	public static <T> Matcher<T> isNotEquivalentTo(T other, Comparator<T> comparator) {
		return new CompareTo<>(other, i -> i != 0, "not equivalent to", comparator);
	}
}
