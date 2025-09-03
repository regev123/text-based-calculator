public class TokenValidator {

    public static boolean isMultiplication(String token) {
        return token.equals(Constants.MULTIPLICATION_OPERATOR);
    }

    public static boolean isDivision(String token) {
        return token.equals(Constants.DIVISION_OPERATOR);
    }

    public static boolean isAddition(String token) {
        return token.equals(Constants.ADDITION_OPERATOR);
    }

    public static boolean isOperand(String token) {
        return token.matches(Constants.OPERAND_REGEX);
    }

    public static boolean isOperator(String token) {
        return token.matches(Constants.OPERATOR_REGEX);
    }

    public static boolean isEmptyExpression(String input) {
        return input == null || input.trim().isEmpty();
    }

    public static boolean isOpeningParenthesis(String token) {
        return token.equals(Constants.OPEN_PARENTHESES);
    }

    public static boolean isClosingParenthesis(String token) {
        return token.equals(Constants.CLOSE_PARENTHESES);
    }

    public static boolean isValidVariableDeclaration(String token) {
        return token.matches(Constants.VARIABLE_DECLARATION_REGEX) && !isInvalidReservedKeyword(token);
    }

    public static boolean isValidIncrementDecrement(String token) {
        return token.matches(Constants.INCREMENT_DECREMENT_REGEX);
    }

    public static boolean isValidAssignmentOperator(String token) {
        return token.matches(Constants.ASSIGNMENT_OPERATOR_REGEX);
    }

    public static boolean isInvalidReservedKeyword(String token) {
        return Constants.RESERVED_KEYWORDS.contains(token);
    }

    public static boolean isTokenVariable(String token) {
        return token.matches(Constants.VARIABLE_PATTERN_WITH_INCREMENTS_DECREMENTS);
    }

    public static boolean isDivisionByZero(boolean isLastOperatorDivision, String token) {
        return isLastOperatorDivision && token.equals("0");
    }

    public static boolean isExpressionBalanced(int openParenthesesCounter, boolean isLastTokenOperator) {
        return openParenthesesCounter == 0 && !isLastTokenOperator;
    }

    public static boolean isParenthesisClosable(boolean isLastTokenOperator, int openParenthesesCounter) {
        return !isLastTokenOperator && openParenthesesCounter > 0;
    }
}
