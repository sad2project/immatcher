package ezgames.immatcher.mocks;

import ezgames.immatcher.Result;
import ezgames.immatcher.Matcher;

public class MockMatcher implements Matcher<String>
{
   // static factories
   public static MockMatcher passes() { return new MockMatcher(false); }
   public static MockMatcher fails() { return new MockMatcher(true); }
   // constructor
   private MockMatcher(boolean fail) { this.fail = fail; }

   public Result match(String actual)
   {
      return new Result(this.fail, "passed", "failed");
   }

   public Result notMatches(String actual) { return new Result(!this.fail, "passed", "failed"); }

   private final boolean fail;
}
