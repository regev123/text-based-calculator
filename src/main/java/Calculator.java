import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.stream.Collectors;

public class Calculator {

	private final LinkedHashMap<String, Integer> variablesMap;

	public Calculator() {
		variablesMap = new LinkedHashMap<>();
	}

	/**
	 * Parses and evaluates a given expression, then updates the corresponding variable with the result.
	 * Handles both simple and complex expressions, accounting for assignment operators.
	 *
	 * @param input The string input representing a variable and expression (e.g., "a = 5 + 3").
	 */
	public void calculateExpression(String input) {
		String[] tokens = ExpressionUtils.tokenizeExpression(input);
		VariableTokenProcessor tokenProcessor = new VariableTokenProcessor();
		if (tokens.length == 1) {
			tokenProcessor.evaluateTokens(tokens, variablesMap);
		} else {
			String[] expressionTokens = Arrays.copyOfRange(tokens, 2, tokens.length);

			String[] evaluatedTokens = tokenProcessor.evaluateTokens(expressionTokens, variablesMap);
			ExpressionEvaluator expressionEvaluator = new ExpressionEvaluator();
			Integer result = expressionEvaluator.evaluate(evaluatedTokens);

			assignResultToVariable(ExpressionUtils.getVariableName(tokens), ExpressionUtils.getAssignmentOperator(tokens), result);
		}
	}

	/**
	 * Updates the value of the variable based on the assignment operator (e.g., =, +=, -=).
	 *
	 * @param variableName The name of the variable to update.
	 * @param assignmentOperator The operator used for assignment (e.g., =, +=, -=).
	 * @param result The calculated value to assign to the variable.
	 */
	private void assignResultToVariable(String variableName, String assignmentOperator, Integer result) {
		switch (assignmentOperator) {
			case Constants.ASSIGNMENT_OPERATOR_EQUALS ->
					variablesMap.put(variableName, result);
			case Constants.ASSIGNMENT_OPERATOR_ADD ->
					ExpressionUtils.applyOperationToVariable(variableName, result, (currentValue, res) -> currentValue + res, variablesMap);
			case Constants.ASSIGNMENT_OPERATOR_SUBTRACT ->
					ExpressionUtils.applyOperationToVariable(variableName, result, (currentValue, res) -> currentValue - res, variablesMap);
			case Constants.ASSIGNMENT_OPERATOR_MULTIPLY ->
					ExpressionUtils.applyOperationToVariable(variableName, result, (currentValue, res) -> currentValue * res, variablesMap);
			case Constants.ASSIGNMENT_OPERATOR_DIVIDE ->
					ExpressionUtils.applyOperationToVariable(variableName, result, (currentValue, res) -> currentValue / res, variablesMap);
		}
	}

	@Override
	public String toString() {
		return "(" + variablesMap.entrySet().stream()
				.map(entry -> entry.getKey() + "=" + entry.getValue())
				.collect(Collectors.joining(",")) + ")";
	}
}
