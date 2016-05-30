package ezgames.immatcher.matchers;

public class ResultMatchers
{
   public static ResultFailed failed()
   {
      return ResultFailed.failed();
   }

   public static ResultPassed passed()
   {
      return ResultPassed.passed();
   }

   public static ResultFailedWithMessage failedWithMessage(String message)
   {
      return ResultFailedWithMessage.failedWithMessage(message);
   }
}
