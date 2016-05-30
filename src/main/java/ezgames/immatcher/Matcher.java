package ezgames.immatcher;

/**
 * {@code Matcher} is the basis of the entire library and works similarly to
 * Hamcrest matchers (http://hamcrest.org/). The major difference is in the fact
 * that, in this library, {@code Matcher}s only do the work of checking if the
 * expected result and the actual result matches. Then it returns a {@link Result}
 * object that provides the information about whether it passed or failed and what
 * expected and actual results were.
 * <p>
 * Benefits of matchers working this way are that they never have any need to be
 * mutable, and they can be expressed as lambdas as well if a quick version is
 * required.</p>
 * @param <T> the type of object that is being checked
 */
public interface Matcher<T>
{
   /**
    * Checks that `actual` matches what this {@code Matcher} is meant to check,
    * then returns a corresponding {@code Result}.
    * @param actual the object being tested
    * @return the {@code Result} of the test
    */
   Result match(T actual);
}

