import java.util.LinkedHashMap;
import java.util.regex.Matcher;

public class ExpressionUtils {

	public static String[] tokenizeExpression(String expression) {
		return expression.split(Constants.SPACE_SEPARATOR);
	}

	public static void checkVariableDeclared(String variable, LinkedHashMap<String, Integer> variablesMap) {
		if (!variablesMap.containsKey(variable)) {
			throw new IllegalArgumentException("Variable '" + variable + "' is not declared.");
		}
	}

	public static String extractVariableName(String token) {
		Matcher matcher = Constants.VARIABLE_PATTERN.matcher(token);
		return matcher.matches() ? matcher.group(1) : null;
	}

	public interface Operation {
		Integer apply(Integer leftOperand, Integer rightOperand);
	}

	public static void applyOperationToVariable(String variableName, Integer result, Operation operation, LinkedHashMap<String, Integer> variablesMap) {
		checkVariableDeclared(variableName, variablesMap);
		variablesMap.put(variableName, operation.apply(variablesMap.get(variableName), result));
	}

	public static String getVariableName(String[] tokens) {
		return tokens[0];
	}

	public static String getAssignmentOperator(String[] tokens) {
		return tokens[1];
	}
}
