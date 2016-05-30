package ezgames.immatcher;

/**
 * {@code Result}s store the result of a test; Whether it failed, what the
 * expected result was, what a failure looks like, and what the actual result
 * is.
 * <p>
 * The convention for the tense of the result messages is in past tense. For
 * example, the `expected` message could be "was empty" and the `onFailure`
 * message could be "contained 5 elements". The final output from the matcher
 * assertion on a failure would then be:</p>
 * <pre><code>
 * Expected that it:
 *     was empty
 * But it:
 *     contained 5 elements
 * </code></pre>
 * This maintains a consistent tense for messages to
 * <ul>
 * <li>make it easier to make a inversion with</li>
 * <li>make it easier to remember</li>
 * </ul>
 * <p>
 * This library doesn't pretend that there will be any output type other than
 * {@code String} output, the way Hamcrest does.</p>
 * For a nicer way of creating {@code Results}, check out {@link ResultBuilder}.
 */
public class Result
{
   public Result(boolean failed, String expected, String onFailure)
   {
      this(failed, expected, onFailure, failed ? onFailure : expected);
   }

   public Result(boolean failed, String expected, String onFailure, String actual)
   {
      this.failed = failed;
      this.expected = tabIt(expected);
      this.onFailure = tabIt(onFailure);
      this.actual = tabIt(actual);
   }

   /**
    * Returns whether the result is a failing result
    * @return whether the result is a failing result
    */
   public boolean failed()
   {
      return failed;
   }

   /**
    * Returns the string that states the expected result
    * @return the string that states the expected result
    */
   public String getExpected()
   {
      return expected;
   }

   /**
    * Returns the string that states a failure result
    * @return the string that states a failure result
    */
   public String getOnFailure()
   {
      return onFailure;
   }

   /**
    * Returns the string that states the actual result
    * @return the string that states the actual result
    */
   public String getActual()
   {
      return actual;
   }

   private String tabIt(String message)
   {
      if(message.startsWith("\t"))
         return message;
      else
         return "\t" + message;
   }

   private final boolean failed;
   private final String expected;
   private final String onFailure;
   private final String actual;
}
