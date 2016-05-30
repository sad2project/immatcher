package ezgames.immatcher.matchers.iterables;

import static ezgames.immatcher.Matchers.invert;
import ezgames.immatcher.Matcher;
import ezgames.immatcher.Result;
import ezgames.immatcher.ResultBuilder;

public class IterableHasSizeOf implements Matcher<Iterable<?>> {
	
	public static IterableHasSizeOf hasSizeOf(int size) {
		return new IterableHasSizeOf(size);
	}
	
	public static Matcher<Iterable<?>> doesNotHaveSizeOf(int size) {
		return invert(hasSizeOf(size), "did not have size of " + size);
	}

	private final int size;
	private final String messageStart;
	private final String expectedMessage;
	
	IterableHasSizeOf(int size) {
		this.size = size;
		messageStart = "had size of ";
		expectedMessage = messageStart + size;
	}
	
	@SuppressWarnings("unused")
	public Result match(Iterable<?> actual) {
		int count = 0;
		for(Object el : actual) {
			count++;
		}
		
		ResultBuilder result = ResultBuilder.withMessages(expectedMessage, messageStart + count);
		
		if(count == size) 
			return result.pass();
		else
			return result.fail();
	}

}
