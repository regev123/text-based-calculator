public class Main {

	public static void main(String[] args) {
		String[] expressions = {
				"i = 0",
				"j = ++i",
				"x = i++ + 5",
				"y = 5 + 3 * 10",
				"i += y",
		};


		validateExpressions(expressions);

        Calculator calculator = new Calculator();
		processExpressions(expressions, calculator);

		System.out.println(calculator);
	}
	/**
	 * Validates the syntax of each expression in the given array.
	 *
	 * @param expressions The array of expressions to validate.
	 */
	private static void validateExpressions(String[] expressions) {
		ExpressionSyntaxValidator expressionSyntaxValidator = new ExpressionSyntaxValidator();
		for (String expression : expressions) {
			if (!expressionSyntaxValidator.isSyntaxValid(expression)) {
				throw new IllegalArgumentException("Invalid syntax in the expression: " + expression);
			}
		}
	}

	/**
	 * Processes each valid expression using the calculator.
	 *
	 * @param expressions The array of expressions to process.
	 * @param calculator  The calculator to use for processing.
	 */
	private static void processExpressions(String[] expressions, Calculator calculator) {
		for (String expression : expressions) {
			calculator.calculateExpression(expression);
		}
	}
}
