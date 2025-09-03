import java.util.LinkedHashMap;

public class VariableTokenProcessor {

	/**
	 * Replaces all variable tokens in the expression with their respective values from the LinkedHashMap.
	 * Also handles increment and decrement operations on variables.
	 *
	 * @param expressionTokens Array of tokens representing the expression
	 * @param variablesMap LinkedHashMap containing variable names and their corresponding values
	 * @return Updated array of tokens where variable tokens are replaced with their current values
	 */
	public String[] evaluateTokens(String[] expressionTokens, LinkedHashMap<String, Integer> variablesMap) {
		for (int i = 0; i < expressionTokens.length; i++) {
			String token = expressionTokens[i];

			if (TokenValidator.isTokenVariable(token)) {
				String variable = ExpressionUtils.extractVariableName(token);
				ExpressionUtils.checkVariableDeclared(variable, variablesMap);
				expressionTokens[i] = resolveVariableValues(token, variable, variablesMap);
			}
		}
		return expressionTokens;
	}

	/**
	 * Updates the value of the variable in the expression token, handling increments and decrements.
	 * It checks the type of increment or decrement operation and resolves the variable value accordingly.
	 *
	 * @param token The token containing the variable (with optional increments/decrements)
	 * @param variable The actual variable name
	 * @param variablesMap LinkedHashMap containing variable names and their values
	 * @return The updated token value as a String
	 */
	private String resolveVariableValues(String token, String variable, LinkedHashMap<String, Integer> variablesMap) {
		if (IncrementDecrementHandler.isPreIncrement(token)) {
			return IncrementDecrementHandler.handlePreIncrement(variable, variablesMap);
		} else if (IncrementDecrementHandler.isPreDecrement(token)) {
			return IncrementDecrementHandler.handlePreDecrement(variable, variablesMap);
		} else if (IncrementDecrementHandler.isPostIncrement(token)) {
			return IncrementDecrementHandler.handlePostIncrement(variable, variablesMap);
		} else if (IncrementDecrementHandler.isPostDecrement(token)) {
			return IncrementDecrementHandler.handlePostDecrement(variable, variablesMap);
		} else {
			return IncrementDecrementHandler.getVariableValueString(variable, variablesMap);
		}
	}
}
