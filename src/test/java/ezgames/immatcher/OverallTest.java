package ezgames.immatcher;

import org.junit.Test;

import static ezgames.immatcher.Assertions.*;
import static ezgames.immatcher.Matchers.*;
import static ezgames.immatcher.matchers.ResultMatchers.*;
import static ezgames.immatcher.mocks.MockMatcher.*;

import static org.junit.Assert.*;

@SuppressWarnings("unchecked")
public class OverallTest
{
   @Test public void testPasses()
   {
      assertThat("aString", passes());
   }

   @Test public void testFails()
   {
      try
      {
         assertThat("aString", fails());
      }
      catch(AssertionError ae)
      {
         return;
      }
      fail("assertion didn't fail when it should have");
   }

   @Test public void testBasicFailureMessage()
   {
      try
      {
         assertThat("aString", fails());
      }
      catch(AssertionError ae)
      {
         assertEquals(ae.getMessage(), "Expected that it:\n\tpassed\nbut it:\n\tfailed");
      }
   }

   @Test public void testAllOfPasses()
   {
      Matcher<String> matcher = allOf(passes(), passes(), passes());

      Result result = matcher.match("");

      assertThat(result, passed());
   }

   @Test public void testAllOfFails1()
   {
      Matcher<String> matcher = allOf(fails(), passes(), passes());

      Result result = matcher.match("");

      assertThat(result, failedWithMessage("\tfailed\n\tpassed\n\tpassed"));
   }

   @Test public void testAllOfFails2()
   {
      Matcher<String> matcher = allOf(passes(), fails(), passes());

      Result result = matcher.match("");

      assertThat(result, failedWithMessage("\tpassed\n\tfailed\n\tpassed"));
   }

   @Test public void testAllOfFails3()
   {
      Matcher<String> matcher = allOf(fails(), passes(), fails());

      Result result = matcher.match("");

      assertThat(result, failedWithMessage("\tfailed\n\tpassed\n\tfailed"));
   }

   @Test public void testAnyOfPasses1()
   {
      Matcher<String> matcher = anyOf(passes(), passes(), passes());

      Result result = matcher.match("");

      assertThat(result, passed());
   }

   @Test public void testAnyOfPasses2()
   {
      Matcher<String> matcher = anyOf(fails(), fails(), passes());

      Result result = matcher.match("");

      assertThat(result, passed());
   }

   @Test public void testAnyOfFails()
   {
      Matcher<String> matcher = anyOf(fails(), fails(), fails());

      Result result = matcher.match("");

      assertThat(result, failedWithMessage("\tfailed\n\tfailed\n\tfailed"));
   }
}
