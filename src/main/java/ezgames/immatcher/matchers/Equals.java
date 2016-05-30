package ezgames.immatcher.matchers;

import ezgames.immatcher.Matcher;
import ezgames.immatcher.Result;
import ezgames.immatcher.ResultBuilder;

public class Equals<T> implements Matcher<T>
{
   public static <T> Equals<T> isEqualTo(T obj) { return new Equals<T>(obj); }

   public Result match(T actual)
   {
      ResultBuilder result = ResultBuilder.withMessages(expected, equaled + actual.toString());
      if(obj.equals(actual))
         return result.pass();
      else
         return result.fail();
   }

   Equals(T obj)
   {
      this.obj = obj;
      this.expected = equaled + obj.toString();
   }

   private static final String equaled = "equaled ";

   private final T obj;
   private final String expected;
}
