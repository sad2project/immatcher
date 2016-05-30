package ezgames.immatcher;

/**
 * {@code ResultBuilder} helps with creating {@link Result}s. It makes the
 * creation a two-step process, allowing you to enter the messages right away,
 * then indicate whether it is a passing or failing result later.
 * <p>
 * This turns code that generally looks like this:</p>
 * <pre><code>
 * if(testPasses)
 * &nbsp; &nbsp;return new BaseResult(false, "expected", "onFailure");
 * else
 * &nbsp; &nbsp;return new BaseResult(true, "expected", "onFailure"); 
 * </code></pre>
 * Into something like this:
 * <pre><code>
 * ResultBuilder result = ResultBuilder.withMessages("expected", "onFailure");
 * if(testPasses)
 * &nbsp; &nbsp;return result.pass();
 * else
 * &nbsp; &nbsp;return result.fail();
 * </code></pre>
 * Thus removing some duplicate code.
 * <p>
 * Instead of calling {@link #pass()} or {@link #fail()}, the builder can take a
 * parametric value with {@link #buildWithPassStatusOf(boolean)}.
 * @see Result
 * @see Result
 */
public class ResultBuilder
{
	/**
	 * Starts the builder with the messages of the expected and failure messages.
	 * @param expected - The message of what the matcher is expecting in order to
	 * pass
	 * @param onFailure - The message of what the matcher found or is a failure. 
	 * @return a new {@code ResultBuilder} with the given messages
	 */
   public static ResultBuilder withMessages(String expected, String onFailure)
   {
      return new ResultBuilder(expected, onFailure);
   }

   /**
    * Returns a passing {@link Result} with the messages given earlier. 
    * @return a passing {@code Result}
    */
   public Result pass()
   {
      return buildWithPassStatusOf(true);
   }

   /**
    * Returns a failing {@link Result} with the messages given earlier.
    * @return a failing {@code Result}
    */
   public Result fail()
   {
      return buildWithPassStatusOf(false);
   }

   /**
    * Returns a {@link Result} that passes if {@code didPass == true}, otherwise
    * a failing {@code Result}. The result uses the messages given earlier.
    * @param didPass - whether the returned {@code Result} passes
    * @return a {@code Result} that passes if {@code didPass == true}, otherwise
    *  a failing {@code Result}
    */
   public Result buildWithPassStatusOf(boolean didPass)
   {
      return new Result(!didPass, expected, onFailure);
   }

   ResultBuilder(String expected, String onFailure)
   {
      this.expected = expected;
      this.onFailure = onFailure;
   }

   private final String expected;
   private final String onFailure;
}
