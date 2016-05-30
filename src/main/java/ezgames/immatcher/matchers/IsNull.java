package ezgames.immatcher.matchers;

import static ezgames.immatcher.Matchers.not;

import ezgames.immatcher.Matcher;
import ezgames.immatcher.Result;
import ezgames.immatcher.ResultBuilder;

public class IsNull<T> implements Matcher<T>{

	public static <T> Matcher<T> isNull() {
		return new IsNull<T>();
	}
	
	public static <T> Matcher<T> isNotNull() {
		return not(isNull());
	}

	@Override
	public Result match(T actual) {
		if(actual == null)
			return result.pass();
		else
			return result.fail();
	}
	
	private IsNull() {
		result = ResultBuilder.withMessages("was Null", "wasn't Null");
	}
	
	private final ResultBuilder result;
}
