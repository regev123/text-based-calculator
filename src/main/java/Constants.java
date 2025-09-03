import java.util.Set;
import java.util.regex.Pattern;

public class Constants {
    public static final String SPACE_SEPARATOR  = "\\s+";

    public static final String VARIABLE_DECLARATION_REGEX = "([a-zA-Z_$][a-zA-Z0-9_$]*)";

    private static final String INCREMENT_DECREMENT_SUB_STRING = "\\+\\+|--";
    private static final String POST_FIX_PRE_FIX_OPTIONAL = "(?:" + INCREMENT_DECREMENT_SUB_STRING + ")?";
    private static final String EXTRACT_VARIABLE_REGEX = POST_FIX_PRE_FIX_OPTIONAL + "(" + VARIABLE_DECLARATION_REGEX + ")" + POST_FIX_PRE_FIX_OPTIONAL;
    public static final Pattern VARIABLE_PATTERN = Pattern.compile(EXTRACT_VARIABLE_REGEX);

    public static final String ASSIGNMENT_OPERATOR_EQUALS = "=";
    public static final String ASSIGNMENT_OPERATOR_ADD = "+=";
    public static final String ASSIGNMENT_OPERATOR_SUBTRACT = "-=";
    public static final String ASSIGNMENT_OPERATOR_MULTIPLY = "*=";
    public static final String ASSIGNMENT_OPERATOR_DIVIDE = "/=";

    public static final String ADDITION_OPERATOR = "+";
    public static final String SUBTRACTION_OPERATOR = "-";
    public static final String MULTIPLICATION_OPERATOR = "*";
    public static final String DIVISION_OPERATOR = "/";
    public static final String OPEN_PARENTHESES = "(";
    public static final String CLOSE_PARENTHESES = ")";

    public static final Set<String> RESERVED_KEYWORDS = Set.of(
            "abstract", "assert", "boolean", "break", "byte", "case", "catch", "char",
            "class", "const", "continue", "default", "do", "double", "else", "enum",
            "extends", "final", "finally", "float", "for", "goto", "if", "implements",
            "import", "instanceof", "int", "interface", "long", "native", "new", "null",
            "package", "private", "protected", "public", "return", "short", "static",
            "strictfp", "super", "switch", "synchronized", "this", "throw", "throws",
            "transient", "try", "void", "volatile", "while", "true", "false"
    );

    public static final String INCREMENT_DECREMENT_REGEX = "(?:("+INCREMENT_DECREMENT_SUB_STRING+")\\s*" + VARIABLE_DECLARATION_REGEX + "|" + VARIABLE_DECLARATION_REGEX + "\\s*(" + INCREMENT_DECREMENT_SUB_STRING +"))";
    public static final String VARIABLE_PATTERN_WITH_INCREMENTS_DECREMENTS = "("+ INCREMENT_DECREMENT_REGEX + "|"+ VARIABLE_DECLARATION_REGEX +")";
    public static final String ASSIGNMENT_OPERATOR_REGEX = "(=|\\+=|-=|/=|\\*=)";
    public static final String OPERAND_REGEX = "(" + VARIABLE_PATTERN_WITH_INCREMENTS_DECREMENTS + "|-?\\d+|[()])";
    public static final String OPERATOR_REGEX = "^[+\\-*/]$";

}
