import java.util.Arrays;

public class ExpressionSyntaxValidator {

	/**
	 * Validates the syntax of the entire expression.
	 *
	 * @param input The expression string to be validated
	 * @return true if the expression has valid syntax, false otherwise
	 */
	public boolean isSyntaxValid(String input) {
		if (TokenValidator.isEmptyExpression(input)) {
			return false;
		}

		String[] tokens = ExpressionUtils.tokenizeExpression(input);

		return validateTokens(tokens);
	}

	/**
	 * Validates the tokens of the expression.
	 * Checks if the first token is a valid variable declaration,
	 * followed by a valid assignment operator and a valid expression.
	 *
	 * @param tokens An array of tokens representing the expression
	 * @return true if the tokens are valid, false otherwise
	 */
	private boolean validateTokens(String[] tokens) {
		if (tokens.length == 1) {
			return TokenValidator.isValidIncrementDecrement(tokens[0]);
		}
		return TokenValidator.isValidVariableDeclaration(tokens[0]) &&
				TokenValidator.isValidAssignmentOperator(tokens[1]) &&
				validateExpression(Arrays.copyOfRange(tokens, 2, tokens.length));
	}

	/**
	 * Validates the expression (the part after the assignment operator).
	 * Checks if the expression has valid operands and operators in correct order.
	 *
	 * @param expressionTokens Array of tokens representing the expression part
	 * @return true if the expression is valid, false otherwise
	 */
	private boolean validateExpression(String[] expressionTokens) {
		boolean isLastTokenOperator = true;
		boolean isLastOperatorDivision = false;
		int openParenthesesCounter = 0;

		for (String token : expressionTokens) {
			if (TokenValidator.isInvalidReservedKeyword(token)) {
				return false;
			}
			if (TokenValidator.isOpeningParenthesis(token)) {
				openParenthesesCounter++;
			} else if (TokenValidator.isClosingParenthesis(token)) {
				if (!TokenValidator.isParenthesisClosable(isLastTokenOperator, openParenthesesCounter)) {
					return false;
				}
				openParenthesesCounter--;
			} else if (TokenValidator.isOperand(token) && isLastTokenOperator) {
				if (TokenValidator.isDivisionByZero(isLastOperatorDivision, token)) {
					return false;
				}
				isLastOperatorDivision = false;
				isLastTokenOperator = false;
			} else if (TokenValidator.isOperator(token) && !isLastTokenOperator) {
				isLastOperatorDivision = TokenValidator.isDivision(token);
				isLastTokenOperator = true;
			} else {
				return false;
			}
		}
		return TokenValidator.isExpressionBalanced(openParenthesesCounter, isLastTokenOperator);
	}
}