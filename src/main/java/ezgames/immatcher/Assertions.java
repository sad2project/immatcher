package ezgames.immatcher;

/**
 * {@code Assertions} is a class with static methods for running the assertions
 * with {@link Matcher}s.
 */
public class Assertions
{
   /**
    * The basic assertion to use with {@link Matcher}s.
    * <p>
    * It delivers `actual` to the `match()` method on the {@code Matcher}, then
    * throws an {@code AssertionError} if the {@link Result} is a failure.</p>
    * @param actual the object being tested by the {@code Matcher}
    * @param matcher the {@code Matcher} that will test `actual`
    * @param <T> the type of the object being tested
    */
   public static <T> void assertThat(T actual, Matcher<? super T> matcher)
   {
      Result result = matcher.match(actual);
      if(result.failed())
         throw new AssertionError(buildMessage(result));
   }

   /**
    * The same as {@link #assertThat(Object, Matcher)} except that is uses the
    * provides `onFailureMessage` when throwing the {@code AssertionError}
    * instead of one derived from the {@code Result}
    * @param actual the object being tested by the {@code Matcher}
    * @param matcher the {@code Matcher} that will test `actual`
    * @param onFailureMessage the message the {@code AssertionError} will contain
    *                         on a failure
    * @param <T> the type of the object being tested
    */
   public static <T> void assertThat(T actual, Matcher<? super T> matcher, String onFailureMessage)
   {
      Result result = matcher.match(actual);
      if(result.failed())
         throw new AssertionError(onFailureMessage);
   }

   static String buildMessage(Result result)
   {
      StringBuilder builder = new StringBuilder();
      builder.append("Expected that it:\n");
      builder.append(result.getExpected());
      builder.append("\nbut it:\n");
      builder.append(result.getActual());
      return builder.toString();
   }


}
