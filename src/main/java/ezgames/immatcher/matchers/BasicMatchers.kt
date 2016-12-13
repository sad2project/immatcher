@file:JvmName("BasicMatchers")

package ezgames.immatcher.matchers

import ezgames.immatcher.*
import ezgames.immatcher.Matcher
import ezgames.immatcher.not
import ezgames.immatcher.Result
import ezgames.immatcher.ResultBuilder

class IsNull<in T> internal constructor() : Matcher<T?> {
    override fun match(actual: T?): Result {
        val result = ResultBuilder.withMessages("was Null", "wasn't Null")
        if (actual == null)
            return result.pass()
        else
            return result.fail()
    }

    override fun notMatches(actual: T?): Result {
        val result = ResultBuilder.withMessages("wasn't Null", "was Null")
        if (actual == null)
            return result.fail()
        else
            return result.pass()
    }
}

class Equals<in T> internal constructor(private val obj: T) : Matcher<T> {

    private val expected: String

    init {
        this.expected = "equaled $obj"
    }

    override fun match(actual: T): Result {
        val result = ResultBuilder.withMessages(expected, "equaled $actual")
        if (obj == actual)
            return result.pass()
        else
            return result.fail()
    }

    override fun notMatches(actual: T): Result {
        val result = ResultBuilder.withMessages("equaled $actual", expected)
        if (obj == actual)
            return result.fail()
        else
            return result.pass()
    }
}

/**
 * Returns a `Matcher` that tests that the object under test is null.
 * @return a `Matcher` that tests for null
 */
fun <T> isNull(): Matcher<T> {
    return IsNull()
}

/**
 * Returns a `Matcher` that tests that the object under test is not null.
 * @return a `Matcher` that tests for non-null
 */
fun <T> isNotNull(): Matcher<T> {
    return not(isNull<T>())
}

/**
 * Returns a `Matcher` that tests that the object under test is equal to
 * the given object.
 * @param object - object to test for equality against
 * *
 * @return a `Matcher` that tests for equality
 */
fun <T> isEqualTo(expected: T): Matcher<T> {
    return Equals(expected)
}

/**
 * Returns a `Matcher` that tests that the object under test is not
 * equal to the given object.
 * @param object - object to test for non-equality against
 * *
 * @return a `Matcher` that tests for non-equality
 */
fun <T> isNotEqualTo(expected: T): Matcher<T> {
    return not(Equals(expected))
}
