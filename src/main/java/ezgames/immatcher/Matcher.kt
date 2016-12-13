@file:JvmName("Matchers")

package ezgames.immatcher

import java.util.function.BiFunction

/**
 * `Matcher` is the basis of the entire library and works similarly to
 * Hamcrest matchers (http://hamcrest.org/). The major difference is in the fact
 * that, in this library, `Matcher`s only do the work of checking if the
 * expected result and the actual result matches. Then it returns a [Result]
 * object that provides the information about whether it passed or failed and what
 * expected and actual results were.
 *
 *
 * Benefits of matchers working this way are that they never have any need to be
 * mutable, and they can be expressed as lambdas as well if a quick version is
 * required.
 * @param <T> the type of object that is being checked
</T> */
interface Matcher<in T> {
    /**
     * Checks that `actual` matches what this `Matcher` is meant to check,
     * then returns a corresponding `Result`.
     * @param actual the object being tested
     * *
     * @return the `Result` of the test
     */
    fun match(actual: T): Result

    fun notMatches(actual: T): Result
}

/**
 * Effectively returns a [Matcher] that checks the reverse of the wrapped
 * `Matcher`.  This is just a quick-use inverter, meant to be used short-term,
 * then replaced with an official inverted version of the `Matcher`.
 *
 *
 * The official inverted version could still be created using `not()`,
 * though. To do so, create a new factory method on the matcher class with
 * the appropriate inverted name (e.g. isOpen becomes isClosed) that calls
 * the first, which is passed to `not()` and returned.
 *
 *
 * It is also not meant to wrap around the collective wrappers such as those
 * from allOf(), anyOf(), and(), and or().
 * @see .invert
 * @param original the `Matcher` to invert
 * *
 * @param <T> the type of the `Matcher`
 * *
 * @return a new, inverted version of the given `Matcher`
</T> */
fun <T> not(original: Matcher<T>): Matcher<T> {
    return InvertedMatcher(original)
}

/**
 * Creates a new wrapper [Matcher] that passes only if all the given
 * `Matcher`s pass. Strings the [Result] together as well.
 *
 *
 * `first` and `second` are there to ensure that at least two `Matcher`s
 * are given for combining together
 * @param first the first `Matcher` to combine
 * *
 * @param second the second `Matcher` to combine
 * *
 * @param others any other `Matcher`s that may need to be combined
 * *
 * @param <T> the type of the object being tested
 * *
 * @return a new `Matcher` that wraps all the other `Matcher`s
</T> */
@SafeVarargs
fun <T> allOf(first: Matcher<T>,
              second: Matcher<T>,
              vararg others: Matcher<T>): Matcher<T> {
    return combine(BiFunction<Matcher<T>, Matcher<T>, Matcher<T>> { first, second -> first and second }, first, second, *others)
}

/**
 * Creates a new wrapper [Matcher] that passes if any of the given
 * `Matcher`s pass. Strings the [Result] together as well.
 *
 *
 * `first` and `second` are there to ensure that at least two `Matcher`s
 * are given for combining together
 * @param first the first `Matcher` to combine
 * *
 * @param second the second `Matcher` to combine
 * *
 * @param others any other `Matcher`s that may need to be combined
 * *
 * @param <T> the type of the object being tested
 * *
 * @return a new `Matcher` that wraps all the other `Matcher`s
</T> */
fun <T> anyOf(first: Matcher<T>,
              second: Matcher<T>,
              vararg others: Matcher<T>): Matcher<T> {
    return combine(BiFunction<Matcher<T>, Matcher<T>, Matcher<T>> { first, second -> first or second }, first, second, *others)
}

fun <T> both(first: Matcher<T>, second: Matcher<T>): Matcher<T> {
    return ANDChainedMatcher(first, second)
}

fun <T> either(first: Matcher<T>, second: Matcher<T>): Matcher<T> {
    return ORChainedMatcher(first, second)
}

infix fun <T> Matcher<T>.and(second: Matcher<T>): Matcher<T> {
    return ANDChainedMatcher(this, second)
}

infix fun <T> Matcher<T>.or(second: Matcher<T>): Matcher<T> {
    return ORChainedMatcher(this, second)
}

private fun <T> combine(
        combiner: BiFunction<Matcher<T>, Matcher<T>, Matcher<T>>,
        first: Matcher<T>, second: Matcher<T>,
        vararg others: Matcher<T>): Matcher<T> {
    var combined = combiner.apply(first, second)
    for (next in others) {
        combined = combiner.apply(combined, next)
    }
    return combined
}

private class InvertedMatcher<T> internal constructor(private val wrapped: Matcher<T>) : Matcher<T> {

    override fun match(actual: T): Result {
        return wrapped.notMatches(actual)
    }

    override fun notMatches(actual: T): Result {
        return wrapped.match(actual)
    }
}

private class ANDChainedMatcher<T> internal constructor(private val original: Matcher<T>, private val next: Matcher<T>) : Matcher<T> {

    override fun match(actual: T): Result {
        val baseResult = original.match(actual)
        val nextResult = next.match(actual)
        return ANDChainResult(baseResult, nextResult)
    }

    override fun notMatches(actual: T): Result {
        val baseResult = original.match(actual)
        val nextResult = original.match(actual)
        return NANDChainResult(baseResult, nextResult)
    }

    private fun ANDChainResult(one: Result, two: Result): Result {
        return Result(one.failed() || two.failed(),
                "AND(\n${one.expected}\n${two.expected})",
                "AND(\n${one.onFailure}\n${two.onFailure})",
                "AND(\n${one.actual}\n${two.actual})")
    }

    private fun NANDChainResult(one: Result, two: Result): Result {
        return Result(!one.failed() && !two.failed(),
                "NOT AND(\n${one.expected}\n${two.expected})",
                "NOT AND(\n${one.onFailure}\n${two.onFailure})",
                "NOT AND(\n${one.actual}\n${two.actual})")
    }
}

private class ORChainedMatcher<T> internal constructor(private val original: Matcher<T>, private val next: Matcher<T>) : Matcher<T> {

    override fun match(actual: T): Result {
        val baseResult = original.match(actual)
        val nextResult = next.match(actual)
        return ORChainResult(baseResult, nextResult)
    }

    override fun notMatches(actual: T): Result {
        val baseResult = original.match(actual)
        val nextResult = next.match(actual)
        return NORChainResult(baseResult, nextResult)
    }

    private fun ORChainResult(one: Result, two: Result): Result {
        return Result(one.failed() && two.failed(),
                "OR(\n${one.expected}\n${two.expected})",
                "OR(\n${one.onFailure}\n${two.onFailure})",
                "OR(\n${one.actual}\n${two.actual})")
    }

    private fun NORChainResult(one: Result, two: Result): Result {
        return Result(!(!one.failed() && !two.failed()),
                "NOT OR(\n${one.expected}\n${two.expected})",
                "NOT OR(\n${one.onFailure}\n${two.onFailure})",
                "NOT OR(\n${one.actual}\n${two.actual})")
    }
}