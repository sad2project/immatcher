package ezgames.immatcher.matchers;

import ezgames.immatcher.Matcher;
import ezgames.immatcher.Result;
import ezgames.immatcher.ResultBuilder;

public class ResultFailed implements Matcher<Result>
{
   public static ResultFailed failed()
   {
      if(instance == null)
      {
         instance = new ResultFailed();
      }
      return instance;
   }

   public Result match(Result actual)
   {
      return actual.failed() ? result.pass() : result.fail();
   }

   private ResultFailed(){}

   private static ResultFailed instance = null;
   private final ResultBuilder result = ResultBuilder.withMessages("failed", "passed");
}
