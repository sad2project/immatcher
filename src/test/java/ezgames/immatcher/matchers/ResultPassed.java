package ezgames.immatcher.matchers;

import ezgames.immatcher.Matcher;
import ezgames.immatcher.Result;
import ezgames.immatcher.ResultBuilder;

public class ResultPassed implements Matcher<Result>
{
   public static ResultPassed passed()
   {
      if(instance == null)
      {
         instance = new ResultPassed();
      }
      return instance;
   }

   public Result match(Result actual)
   {
      return actual.failed() ? result.fail() : result.pass();
   }

   public Result notMatches(Result actual) { return actual.failed() ? result.pass() : result.fail(); }

   private ResultPassed() {}

   private static ResultPassed instance = null;
   private final ResultBuilder result = ResultBuilder.withMessages("passed", "failed");
}
