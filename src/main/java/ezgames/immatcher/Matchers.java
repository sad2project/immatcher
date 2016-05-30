package ezgames.immatcher;

/**
 * {@code Matchers} is a collection of static utility methods for working with
 * matchers
 */
public class Matchers
{
   /**
    * Effectively returns a {@link Matcher} that checks the reverse of the wrapped
    * {@code Matcher}.  This is just a quick-use inverter, meant to be used short-term,
    * then replaced with an official inverted version of the {@code Matcher}.
    * <p>
    * The official inverted version could still be created using {@code not()},
    * though. To do so, create a new factory method on the matcher class with
    * the appropriate inverted name (e.g. isOpen becomes isClosed) that calls
    * the first, which is passed to {@code not()} and returned.</p>
    * <p>
    * It is also not meant to wrap around the collective wrappers such as those
    * from allOf(), anyOf(), and(), and or().</p>
    * @see #invert(Matcher, String)
    * @param original the {@code Matcher} to invert
    * @param <T> the type of the {@code Matcher}
    * @return a new, inverted version of the given {@code Matcher}
    */
   public static <T> Matcher<T> not(Matcher<T> original)
   {
      return new SimpleInvertedMatcher<T>(original);
   }
   
   /**
    * {@code invert()} is very much like {@link #not(Matcher)}, but it allows you
    * to enter a new `expected` message for the result, since using the uninverted
    * version's `onFailure` message is rarely useful. If it is, using
    * {@code not()} is recommended.
    * <p>
    * Because {@code invert()} is slightly verbose, it isn't recommended for
    * inline use in an assert. Instead, it should be used to provide an additional
    * factory method without needing to provide an additional class or
    * constructor.</p>
    * <p>
    * For example, if a matcher has a factory method called {@code isOpen()},
    * it can provide an additional factory method called {@code isClosed()}
    * which returns the same thing, but wrapped using {@code invert()}.</p>
    * @see #not(Matcher)
    * @param original
    * @param newExpectedMessage
    * @return
    */
   public static <T> Matcher<T> invert(Matcher<T> original, String newExpectedMessage)
   {
	   return new InvertingMatcher<T>(original, newExpectedMessage);
   }

   /**
    * Creates a new wrapper {@link Matcher} that passes only if all the given
    * {@code Matcher}s pass. Strings the {@link Result} together as well.
    * <p>
    * `first` and `second` are there to ensure that at least two {@code Matcher}s
    * are given for combining together</p>
    * @param first the first {@code Matcher} to combine
    * @param second the second {@code Matcher} to combine
    * @param others any other {@code Matcher}s that may need to be combined
    * @param <T> the type of the object being tested
    * @return a new {@code Matcher} that wraps all the other {@code Matcher}s
    */
   @SafeVarargs
   public static <T> Matcher<T> allOf(Matcher<? super T> first,
                                      Matcher<? super T> second,
                                      Matcher<? super T>... others)
   {
      Matcher<T> combined = new ANDChainedMatcher<T>(first, second);
      for(Matcher<? super T> next : others)
      {
         combined = new ANDChainedMatcher<T>(combined, next);
      }
      return combined;
   }

   /**
    * Creates a new wrapper {@link Matcher} that passes if any of the given
    * {@code Matcher}s pass. Strings the {@link Result} together as well.
    * <p>
    * `first` and `second` are there to ensure that at least two {@code Matcher}s
    * are given for combining together</p>
    * @param first the first {@code Matcher} to combine
    * @param second the second {@code Matcher} to combine
    * @param others any other {@code Matcher}s that may need to be combined
    * @param <T> the type of the object being tested
    * @return a new {@code Matcher} that wraps all the other {@code Matcher}s
    */
   public static <T> Matcher<T> anyOf(Matcher<? super T> first,
                                      Matcher<? super T> second,
                                      @SuppressWarnings("unchecked")  Matcher<? super T>... others)
   {
      Matcher<T> combined = new ORChainedMatcher<T>(first, second);
      for(Matcher<? super T> next : others)
      {
         combined = new ORChainedMatcher<T>(combined, next);
      }
      return combined;
   }

   private static class SimpleInvertedMatcher<T> implements Matcher<T>
   {
      SimpleInvertedMatcher(Matcher<T> wrapped)
      {
         this.wrapped = wrapped;
      }

      public Result match(T actual)
      {
      return invertResult(wrapped.match(actual));
      }

      private Result invertResult(Result original)
      {
         return new Result(!original.failed(),
                               original.getOnFailure(),
                               original.getExpected());
      }

      private final Matcher<T> wrapped;
   }
   
   private static class InvertingMatcher<T> implements Matcher<T> {
		
		@Override
		public Result match(T actual) {
			Result originalResult = original.match(actual);
			return new Result(!originalResult.failed(), newExpectedMessage, originalResult.getExpected());
			
		}
		
		private final String newExpectedMessage;
		private final Matcher<T> original;

		InvertingMatcher(Matcher<T> original, String newExpectedMessage){
			this.original = original;
			this.newExpectedMessage = newExpectedMessage;
		}

	}

   private static class ANDChainedMatcher<T> implements Matcher<T>
   {
      ANDChainedMatcher(Matcher<? super T> original, Matcher<? super T> next)
      {
         this.original = original;
         this.next = next;
      }

      public Result match(T actual)
      {
         Result baseResult = original.match(actual);
         Result nextResult = next.match(actual);
         return ANDChainResult(baseResult, nextResult);
      }

      private Result ANDChainResult(Result one, Result two)
      {
         return new Result(one.failed() || two.failed(),
                               one.getExpected() + "\n" + two.getExpected(),
                               one.getOnFailure() + "\n" + two.getOnFailure(),
                               one.getActual() + "\n" + two.getActual());
      }

      private final Matcher<? super T> original;
      private final Matcher<? super T> next;
   }

   private static class ORChainedMatcher<T> implements Matcher<T>
   {
      ORChainedMatcher(Matcher<? super T> original, Matcher<? super T> next)
      {
         this.original = original;
         this.next = next;
      }

      public Result match(T actual)
      {
    	  Result originalResult = original.match(actual);
    	  if(originalResult.failed())
    		  return ORChainResult(original.match(actual), next.match(actual));
    	  else
    		  return originalResult;
      }

      private Result ORChainResult(Result one, Result two)
      {
         return new Result(one.failed() && two.failed(),
                               one.getExpected() + "\tor\n" + two.getExpected(),
                               one.getOnFailure() + "\tor\n" + two.getOnFailure(),
                               one.getActual() + "\n" + two.getActual());
      }

      private final Matcher<? super T> original;
      private final Matcher<? super T> next;
   }
}