import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.LinkedHashMap;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class VariableTokenProcessorTest {

	private LinkedHashMap<String, Integer> variablesMap;
	private VariableTokenProcessor variableTokenProcessor;

	@BeforeEach
	public void setUp() {
		variableTokenProcessor = new VariableTokenProcessor();

		variablesMap = new LinkedHashMap<>();
		variablesMap.put("a", 1);
		variablesMap.put("b", 2);
		variablesMap.put("c", 3);
	}

	@Test
	public void testReplaceAllWithValue_incrementVariable() {
		String[] expectedOutput = { "1","+","5","*","3","+","10","+","1"};
		String[] fullExpression = ExpressionUtils.tokenizeExpression("x = a + 5 * ++b + 10 + a++");
		String[] actualOutput = variableTokenProcessor.evaluateTokens(Arrays.copyOfRange(fullExpression, 2, fullExpression.length), variablesMap);
		assertArrayEquals(expectedOutput, actualOutput);

		LinkedHashMap<String,Integer> expectedMap = new LinkedHashMap<>();
		expectedMap.put("b",3);
		expectedMap.put("a",2);
		expectedMap.put("c", 3);
		assertEquals(expectedMap, variablesMap);
	}

	@Test
	public void testReplaceAllWithValue_decrementVariable() {
		String[] expectedOutput = { "3", "-", "1", "+", "1" };
		String[] fullExpression = ExpressionUtils.tokenizeExpression("y = c - --b + a--");
		String[] actualOutput = variableTokenProcessor.evaluateTokens(Arrays.copyOfRange(fullExpression, 2, fullExpression.length), variablesMap);
		assertArrayEquals(expectedOutput, actualOutput);

		LinkedHashMap<String, Integer> expectedMap = new LinkedHashMap<>();
		expectedMap.put("b", 1);
		expectedMap.put("a", 0);
		expectedMap.put("c", 3);
		assertEquals(expectedMap, variablesMap);
	}

	@Test
	public void testReplaceAllWithValue_noIncrementsOrDecrements() {
		String[] expectedOutput = { "1", "*", "3", "+", "2" };
		String[] fullExpression = ExpressionUtils.tokenizeExpression("z = a * c + b");
		String[] actualOutput = variableTokenProcessor.evaluateTokens(Arrays.copyOfRange(fullExpression, 2, fullExpression.length), variablesMap);
		assertArrayEquals(expectedOutput, actualOutput);

		LinkedHashMap<String, Integer> expectedMap = new LinkedHashMap<>();
		expectedMap.put("a", 1);
		expectedMap.put("b", 2);
		expectedMap.put("c", 3);
		assertEquals(expectedMap, variablesMap);
	}

	@Test
	public void testReplaceAllWithValue_allIncrement() {
		String[] expectedOutput = { "2", "+", "3", "+", "3" };
		String[] fullExpression = ExpressionUtils.tokenizeExpression("z = ++a + ++b + c++");
		String[] actualOutput = variableTokenProcessor.evaluateTokens(Arrays.copyOfRange(fullExpression, 2, fullExpression.length), variablesMap);
		assertArrayEquals(expectedOutput, actualOutput);

		LinkedHashMap<String, Integer> expectedMap = new LinkedHashMap<>();
		expectedMap.put("a", 2);
		expectedMap.put("b", 3);
		expectedMap.put("c", 4);
		assertEquals(expectedMap, variablesMap);
	}

	@Test
	public void testReplaceAllWithValue_allDecrement() {
		String[] expectedOutput = { "0", "+", "1", "+", "3" };
		String[] fullExpression = ExpressionUtils.tokenizeExpression("z = --a + --b + c--");
		String[] actualOutput = variableTokenProcessor.evaluateTokens(Arrays.copyOfRange(fullExpression, 2, fullExpression.length), variablesMap);
		assertArrayEquals(expectedOutput, actualOutput);

		LinkedHashMap<String, Integer> expectedMap = new LinkedHashMap<>();
		expectedMap.put("a", 0);
		expectedMap.put("b", 1);
		expectedMap.put("c", 2);
		assertEquals(expectedMap, variablesMap);
	}

	@Test
	public void testReplaceAllWithValue_incrementAndDecrementPreFixAndPostFix() {
		String[] expectedOutput = { "0", "+", "2", "+", "4" , "-" , "4"};
		String[] fullExpression = ExpressionUtils.tokenizeExpression("z = --a + b-- + ++c - c++");
		String[] actualOutput = variableTokenProcessor.evaluateTokens(Arrays.copyOfRange(fullExpression, 2, fullExpression.length), variablesMap);
		assertArrayEquals(expectedOutput, actualOutput);

		LinkedHashMap<String, Integer> expectedMap = new LinkedHashMap<>();
		expectedMap.put("a", 0);
		expectedMap.put("b", 1);
		expectedMap.put("c", 5);
		assertEquals(expectedMap, variablesMap);
	}

	@Test
	public void testReplaceAllWithValue_VariableNotDeclared() {
		String[] fullExpression = ExpressionUtils.tokenizeExpression("z = --x + --b + c--");
		Exception exception = assertThrows(IllegalArgumentException.class,() -> {variableTokenProcessor.evaluateTokens(Arrays.copyOfRange(fullExpression, 2, fullExpression.length), variablesMap);});
		assertEquals("Variable 'x' is not declared.", exception.getMessage());
	}
}
