import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ExpressionEvaluatorTest {

    private ExpressionEvaluator expressionEvaluator;

    @BeforeEach
    public void setUp(){
        expressionEvaluator = new ExpressionEvaluator();
    }

    @Test
    public void testEvaluateExpression_basicOperations() {
        // Test basic arithmetic expression: 5 + 10 * 12 / 2 - 10 = 55
        String[] tokens = {"5", "+", "10", "*", "12", "/", "2", "-", "10"};
        Integer result = expressionEvaluator.evaluate(tokens);
        assertEquals(55, result);
    }

    @Test
    public void testEvaluateExpression_withOnlyAdditionAndSubtraction() {
        // Test with only addition and subtraction: 5 + 10 - 3 = 12
        String[] tokens = {"5", "+", "10", "-", "3"};
        Integer result = expressionEvaluator.evaluate(tokens);
        assertEquals(12, result);
    }

    @Test
    public void testEvaluateExpression_withOnlyMultiplicationAndDivision() {
        // Test with only multiplication and division: 6 * 4 / 2 = 12
        String[] tokens = {"6", "*", "4", "/", "2"};
        Integer result = expressionEvaluator.evaluate(tokens);
        assertEquals(12, result);
    }

    @Test
    public void testEvaluateExpression_withComplexExpression() {
        // Test complex expression: 10 * 2 + 3 * 4 / 2 - 1 = 23
        String[] tokens = {"10", "*", "2", "+", "3", "*", "4", "/", "2", "-", "1"};
        Integer result = expressionEvaluator.evaluate(tokens);
        assertEquals(25, result);
    }

    @Test
    public void testEvaluateExpression_withZero() {
        // Test expression with zero: 10 + 0 * 5 - 3 = 7
        String[] tokens = {"10", "+", "0", "*", "5", "-", "3"};
        Integer result = expressionEvaluator.evaluate(tokens);
        assertEquals(7, result);
    }

    @Test
    public void testEvaluateExpression_withNegativeNumbers() {
        // Test expression with negative numbers: -5 + 10 * -2 = -25
        String[] tokens = {"-5", "+", "10", "*", "-2"};
        Integer result = expressionEvaluator.evaluate(tokens);
        assertEquals(-25, result);
    }

    @Test
    public void testEvaluateExpression_withLargeNumbers() {
        // Test large numbers: 1000000 + 2000000 * 3 = 7000000
        String[] tokens = {"1000000", "+", "2000000", "*", "3"};
        Integer result = expressionEvaluator.evaluate(tokens);
        assertEquals(7000000, result);
    }

    @Test
    public void testEvaluateExpression_withMixedPositiveAndNegativeNumbers() {
        // Test mixed positive and negative numbers: -5 * -10 + 3 * 2 = 56
        String[] tokens = {"-5", "*", "-10", "+", "3", "*", "2"};
        Integer result = expressionEvaluator.evaluate(tokens);
        assertEquals(56, result);
    }

    @Test
    public void testEvaluateExpression_withSingleOperand() {
        // Test single operand: 42 = 42
        String[] tokens = {"42"};
        Integer result = expressionEvaluator.evaluate(tokens);
        assertEquals(42, result);
    }

    @Test
    public void testEvaluateExpression_withNegativeSingleOperand() {
        // Test single negative operand: -42 = -42
        String[] tokens = {"-42"};
        Integer result = expressionEvaluator.evaluate(tokens);
        assertEquals(-42, result);
    }

    @Test
    public void testEvaluateExpression_withMultipleDivisions() {
        // Test multiple divisions: 100 / 2 / 5 = 10
        String[] tokens = {"100", "/", "2", "/", "5"};
        Integer result = expressionEvaluator.evaluate(tokens);
        assertEquals(10, result);
    }

    @Test
    public void testEvaluateExpression_withMultipleMultiplications() {
        // Test multiple multiplications: 2 * 3 * 4 = 24
        String[] tokens = {"2", "*", "3", "*", "4"};
        Integer result = expressionEvaluator.evaluate(tokens);
        assertEquals(24, result);
    }

    @Test
    public void testEvaluateExpression_withParenthesesBasic() {
        String[] tokens = {"(", "5", "+", "10", ")", "*", "(", "12", "/", "2", ")", "-", "10"};
        Integer result = expressionEvaluator.evaluate(tokens);
        assertEquals(80, result);
    }

    @Test
    public void testEvaluateExpression_withNestedParentheses() {
        // Test with nested parentheses: 5 + ((10 - 3) * (4 + 2)) = 47
        String[] tokens = {"5", "+", "(", "(", "10", "-", "3", ")", "*", "(", "4", "+", "2", ")", ")"};
        Integer result = expressionEvaluator.evaluate(tokens);
        assertEquals(47, result);
    }

    @Test
    public void testEvaluateExpression_withComplexParentheses() {
        // Test complex expression with multiple parentheses: (10 * (2 + 3)) + ((4 * 2) - 1) = 51
        String[] tokens = {"(", "10", "*", "(", "2", "+", "3", ")", ")", "+", "(", "(", "4", "*", "2", ")", "-", "1", ")"};
        Integer result = expressionEvaluator.evaluate(tokens);
        assertEquals(57, result);
    }
}
