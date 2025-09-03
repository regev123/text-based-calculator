import java.util.LinkedHashMap;

public class IncrementDecrementHandler {
    public static String handlePreIncrement(String variable, LinkedHashMap<String, Integer> variablesMap) {
        incrementVariable(variable, variablesMap);
        return getVariableValueString(variable, variablesMap);
    }

    public static String handlePreDecrement(String variable, LinkedHashMap<String, Integer> variablesMap) {
        decrementVariable(variable, variablesMap);
        return getVariableValueString(variable, variablesMap);
    }

    public static String handlePostIncrement(String variable, LinkedHashMap<String, Integer> variablesMap) {
        String valueBeforeIncrement = getVariableValueString(variable, variablesMap);
        incrementVariable(variable, variablesMap);
        return valueBeforeIncrement;
    }

    public static String handlePostDecrement(String variable, LinkedHashMap<String, Integer> variablesMap) {
        String valueBeforeDecrement = getVariableValueString(variable, variablesMap);
        decrementVariable(variable, variablesMap);
        return valueBeforeDecrement;
    }

    public static void incrementVariable(String variable, LinkedHashMap<String, Integer> variablesMap) {
        variablesMap.put(variable, variablesMap.get(variable) + 1);
    }

    public static void decrementVariable(String variable, LinkedHashMap<String, Integer> variablesMap) {
        variablesMap.put(variable, variablesMap.get(variable) - 1);
    }

    public static boolean isPreIncrement(String token) {
        return token.startsWith(Constants.ADDITION_OPERATOR);
    }

    public static boolean isPreDecrement(String token) {
        return token.startsWith(Constants.SUBTRACTION_OPERATOR);
    }

    public static boolean isPostIncrement(String token) {
        return token.endsWith(Constants.ADDITION_OPERATOR);
    }

    public static boolean isPostDecrement(String token) {
        return token.endsWith(Constants.SUBTRACTION_OPERATOR);
    }

    public static String getVariableValueString(String variable, LinkedHashMap<String, Integer> variablesMap) {
        return variablesMap.get(variable).toString();
    }
}
