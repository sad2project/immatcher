/**
 * Matcherlib is a library of/for "matchers" used with unit tests. They're used
 * in assertions in order to make the assertion's purpose more clear by being
 * more readable and succinct. 
 * <p>
 * It gets many of its ideas from the <a href="http://www.hamcrest.org">Hamcrest
 * matcher library</a>, but attempts to make up for some of its shortcomings. 
 * One of those shortcomings is that it's not easily composable to do multiple 
 * assertions on one line. Hamcrest also used output parameters to help generate
 * its failure messages.</p>
 * <p>
 * The main places to look for how the library works is in {@link Assertions}
 * and {@link Matcher}. {@code Assertion} just contains the {@code assertThat()}
 * methods that use the {@code Matcher}s to create {@link Result}s, which are
 * then used to determine success or failure and create the failure message as
 * necessary.
 */
package ezgames.immatcher;