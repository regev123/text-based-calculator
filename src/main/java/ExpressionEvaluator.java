import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class ExpressionEvaluator {

    private List<String> processedTokens;

    public ExpressionEvaluator() {
        processedTokens = new ArrayList<>();
    }

    /**
     * Evaluates a mathematical expression given as an array of tokens.
     * It first resolves parentheses, processes multiplication/division,
     * and finally handles addition/subtraction.
     *
     * @param tokens An array of string tokens representing the expression.
     * @return The final evaluated result of the expression as an Integer.
     */
    public Integer evaluate(String[] tokens) {
        processedTokens = resolveParentheses(tokens);
        processedTokens = processMultiplicationAndDivision(processedTokens);
        return processAdditionAndSubtraction(processedTokens);
    }

    /**
     * Resolves and evaluates expressions within parentheses recursively.
     * Sub-expressions inside parentheses are evaluated and replaced by their results.
     *
     * @param tokens An array of string tokens representing the expression.
     * @return A list of tokens with parentheses resolved and evaluated.
     */
    private List<String> resolveParentheses(String[] tokens) {
        Stack<List<String>> stack = new Stack<>();
        processedTokens = new ArrayList<>();

        for (String token : tokens) {
            if (TokenValidator.isOpeningParenthesis(token)) {
                stack.push(processedTokens);
                processedTokens = new ArrayList<>();
            } else if (TokenValidator.isClosingParenthesis(token)) {
                Integer result = evaluate(processedTokens.toArray(new String[0]));
                processedTokens = stack.isEmpty() ? new ArrayList<>() : stack.pop();
                processedTokens.add(String.valueOf(result));
            } else {
                processedTokens.add(token);
            }
        }
        return processedTokens;
    }

    /**
     * Processes all multiplication and division operations in the expression.
     * It handles them from left to right, updating the list of tokens with results as it goes.
     *
     * @param tokens A list of tokens representing the expression.
     * @return A list of tokens after resolving all multiplication and division operations.
     */
    private List<String> processMultiplicationAndDivision(List<String> tokens) {
        processedTokens = new ArrayList<>();

        for (int i = 0; i < tokens.size(); i++) {
            String token = tokens.get(i);
            if (TokenValidator.isMultiplication(token)) {
                int result = performOperationMultiplicationAndDivision(tokens.get(++i),true);
                processedTokens.add(String.valueOf(result));
            } else if (TokenValidator.isDivision(token)) {
                int result = performOperationMultiplicationAndDivision(tokens.get(++i), false);
                processedTokens.add(String.valueOf(result));
            } else {
                processedTokens.add(token);
            }
        }
        return processedTokens;
    }

    /**
     * Performs either a multiplication or division operation on two operands.
     * The left operand is retrieved from the last element in the `processedTokens` list,
     * and the right operand is provided as `nextToken`. Depending on the `isMultiplication` flag,
     * either multiplication or division is performed.
     *
     * @param nextToken The string representing the next token to be used as the right operand.
     * @param isMultiplication A boolean flag indicating whether to perform multiplication (true) or division (false).
     * @return The result of the multiplication or division operation between the left and right operands.
     */
    private int performOperationMultiplicationAndDivision(String nextToken, boolean isMultiplication) {
        int leftOperand = Integer.parseInt(processedTokens.remove(processedTokens.size() - 1));
        int rightOperand = Integer.parseInt(nextToken);
        return isMultiplication ? leftOperand * rightOperand : leftOperand / rightOperand;
    }

    /**
     * Processes addition and subtraction operations in the given list of tokens.
     * It evaluates the expression from left to right and returns the final result.
     *
     * @param processedTokens A list of tokens after handling multiplication and division operations.
     * @return The final evaluated result of the expression as an Integer.
     */
    private Integer processAdditionAndSubtraction(List<String> processedTokens) {
        int result = Integer.parseInt(processedTokens.get(0));

        for (int i = 1; i < processedTokens.size(); i += 2) {
            String operator = processedTokens.get(i);
            int rightOperand = Integer.parseInt(processedTokens.get(i + 1));
            result = TokenValidator.isAddition(operator) ? result + rightOperand : result - rightOperand;
        }
        return result;
    }
}
