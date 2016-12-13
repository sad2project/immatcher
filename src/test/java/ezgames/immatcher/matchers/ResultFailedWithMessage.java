package ezgames.immatcher.matchers;

import ezgames.immatcher.Result;
import ezgames.immatcher.Matcher;
import ezgames.immatcher.ResultBuilder;

public class ResultFailedWithMessage implements Matcher<Result>
{
   public static ResultFailedWithMessage failedWithMessage(String message)
   {
      return new ResultFailedWithMessage(message);
   }

   public Result match(Result actual)
   {
      if(actual.failed())
      {
         ResultBuilder result = ResultBuilder.withMessages(expected, "was a failing test with message \"" + actual.getActual() + "\"");
         if(actual.getActual().equals(message))
            return result.pass();
         return result.fail();
      }
      else
         return new Result(true, expected, "was a passing Result" );
   }

   public Result notMatches(Result actual) {
      if(actual.failed())
         return new Result(true, expected, "was a passing Result");
      else
      {
         ResultBuilder result = ResultBuilder.withMessages(expected, "was a failing test with message \"" + actual.getActual() + "\"");
         if(actual.getActual().equals(message))
            return result.pass();
         return result.fail();
      }
   }

   private ResultFailedWithMessage(String message)
   {
      this.message = message;
      this.expected = "was a failing Result with message \"" + message + "\"";
   }

   private final String message;
   private final String expected;
}
