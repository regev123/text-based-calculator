import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ExpressionSyntaxValidatorTest {
	
	private ExpressionSyntaxValidator expressionSyntaxValidator;
	
	@BeforeEach
	public void setUp(){
		expressionSyntaxValidator = new ExpressionSyntaxValidator();
	}
	
	@Test
	public void testValidIncrementDecrementExpressions() {
		assertTrue(expressionSyntaxValidator.isSyntaxValid("a++"));
		assertTrue(expressionSyntaxValidator.isSyntaxValid("++b"));
		assertTrue(expressionSyntaxValidator.isSyntaxValid("c--"));
		assertTrue(expressionSyntaxValidator.isSyntaxValid("--d"));
	}

	@Test
	public void testInvalidIncrementDecrementExpressions() {
		assertFalse(expressionSyntaxValidator.isSyntaxValid("a+"));
		assertFalse(expressionSyntaxValidator.isSyntaxValid("a+-"));
		assertFalse(expressionSyntaxValidator.isSyntaxValid("++a+-"));
		assertFalse(expressionSyntaxValidator.isSyntaxValid("++--c"));
		assertFalse(expressionSyntaxValidator.isSyntaxValid("a--b"));
		assertFalse(expressionSyntaxValidator.isSyntaxValid("++a++"));
		assertFalse(expressionSyntaxValidator.isSyntaxValid("-+a"));
		assertFalse(expressionSyntaxValidator.isSyntaxValid("++"));
		assertFalse(expressionSyntaxValidator.isSyntaxValid("i++ = 5"));
		assertFalse(expressionSyntaxValidator.isSyntaxValid("i++ += 5"));
		assertFalse(expressionSyntaxValidator.isSyntaxValid("i++ += ( 5 * 2 )"));
	}

	@Test
	public void testInvalidExpressionsWithOperatorWrong() {
		assertFalse(expressionSyntaxValidator.isSyntaxValid("c = + ( 3 + 2 ) * 5"));
		assertFalse(expressionSyntaxValidator.isSyntaxValid("d = + 10"));
		assertFalse(expressionSyntaxValidator.isSyntaxValid("e =  ( b + 5 ) * ( 2 - )"));
		assertFalse(expressionSyntaxValidator.isSyntaxValid("e =  b + 5 *"));
		assertFalse(expressionSyntaxValidator.isSyntaxValid("e =  ( + 5 ) * ( 2 - 1 )"));
		assertFalse(expressionSyntaxValidator.isSyntaxValid("e =  ( 4 + 5 ) ** ( 2 - 1 )"));
	}

	@Test
	public void testValidExpressionWithAssignment() {
		assertTrue(expressionSyntaxValidator.isSyntaxValid("a = 5"));
		assertTrue(expressionSyntaxValidator.isSyntaxValid("b += 10"));
		assertTrue(expressionSyntaxValidator.isSyntaxValid("c -= 2"));
		assertTrue(expressionSyntaxValidator.isSyntaxValid("d *= a"));
		assertTrue(expressionSyntaxValidator.isSyntaxValid("e /= b"));
	}

	@Test
	public void testInvalidExpressionWithMismatchedParentheses() {
		assertFalse(expressionSyntaxValidator.isSyntaxValid("a = ( 5 + 3"));
		assertFalse(expressionSyntaxValidator.isSyntaxValid("b = 3 + 2 ) * 5"));
		assertFalse(expressionSyntaxValidator.isSyntaxValid("c = ( ( 2 + 3 ) * 2"));
		assertFalse(expressionSyntaxValidator.isSyntaxValid("c = ( ( 2 + 3 ( ) * 2"));
		assertFalse(expressionSyntaxValidator.isSyntaxValid("c = 2 + 3 ( 4 4 + 4 ) * 2"));
		assertFalse(expressionSyntaxValidator.isSyntaxValid("c = 2 + 3 ( 4 ( 4 + 4 ) ) * 2"));
		assertFalse(expressionSyntaxValidator.isSyntaxValid("c = ( 2 + 3 + ( 4 + ( 4 + 4 ) ) * 2"));
	}

	@Test
	public void testInvalidExpressionWithInvalidOperators() {
		assertFalse(expressionSyntaxValidator.isSyntaxValid("a = 5 ** 3"));
		assertFalse(expressionSyntaxValidator.isSyntaxValid("a = 5 ! 3"));
		assertFalse(expressionSyntaxValidator.isSyntaxValid("a = 5 # 3"));
		assertFalse(expressionSyntaxValidator.isSyntaxValid("a = 5 @ 3"));
		assertFalse(expressionSyntaxValidator.isSyntaxValid("a = 5 ++ 3"));
		assertFalse(expressionSyntaxValidator.isSyntaxValid("a = 5 -- 3"));
		assertFalse(expressionSyntaxValidator.isSyntaxValid("a = 5 // 3"));
	}

	@Test
	public void testExpressionWithDivisionByZero() {
		assertFalse(expressionSyntaxValidator.isSyntaxValid("a = 5 / 0"));
	}

	@Test
	public void testEmptyOrNullExpression() {
		assertFalse(expressionSyntaxValidator.isSyntaxValid(""));
		assertFalse(expressionSyntaxValidator.isSyntaxValid(null));
	}

	@Test
	public void testValidIncrementDecrementExpressionsWithComplexNames() {
		assertTrue(expressionSyntaxValidator.isSyntaxValid("varVar_1++"));
		assertTrue(expressionSyntaxValidator.isSyntaxValid("++var2_abC"));
		assertTrue(expressionSyntaxValidator.isSyntaxValid("var2_abC--"));
		assertTrue(expressionSyntaxValidator.isSyntaxValid("--varVar_1"));
	}

	@Test
	public void testInvalidIncrementDecrementExpressionsWithComplexNames() {
		assertFalse(expressionSyntaxValidator.isSyntaxValid("varVar_1+"));
		assertFalse(expressionSyntaxValidator.isSyntaxValid("++"));
		assertFalse(expressionSyntaxValidator.isSyntaxValid("varVar_1++ = 5"));
		assertFalse(expressionSyntaxValidator.isSyntaxValid("var2_abC++ += 5"));
		assertFalse(expressionSyntaxValidator.isSyntaxValid("var2_abC++ += ( 5 * 2 )"));
	}

	@Test
	public void testValidExpressionWithOperators() {
		assertTrue(expressionSyntaxValidator.isSyntaxValid("var1 = 5 + 3"));
		assertTrue(expressionSyntaxValidator.isSyntaxValid("var2 = var1 * 10 / 2"));
		assertTrue(expressionSyntaxValidator.isSyntaxValid("var3 = ( 3 + 2 ) * 5"));
		assertTrue(expressionSyntaxValidator.isSyntaxValid("var4 = ( var1 - 10 ) / 2"));
		assertTrue(expressionSyntaxValidator.isSyntaxValid("var5 = ( var2 + 5 ) * ( 2 - 1 )"));
	}

	@Test
	public void testComplexExpressions2() {
		// Expressions with nested parentheses
		assertTrue(expressionSyntaxValidator.isSyntaxValid("var1 = ( 5 + ( var2 - 3 ) ) * 2"));
		assertTrue(expressionSyntaxValidator.isSyntaxValid("var2 = ( ( 3 * ( 2 + 1 ) ) - 1 )"));
		assertTrue(expressionSyntaxValidator.isSyntaxValid("var3 = ( ( var1 + var2 ) * ( 5 / 2 ) )"));

		// Expressions with mixed increment/decrement operators
		assertTrue(expressionSyntaxValidator.isSyntaxValid("var1 = ( 5 + var2++ ) * ( --var3 - 2 )"));
		assertTrue(expressionSyntaxValidator.isSyntaxValid("var2 = ( ( 3 + 2 ) * ++var1 ) - var4--"));
		assertTrue(expressionSyntaxValidator.isSyntaxValid("var3 = ( var1-- + ( var2++ * 2 ) ) / ++var4"));

		// More complex arithmetic combinations
		assertTrue(expressionSyntaxValidator.isSyntaxValid("var1 = 10 * ( var2 / ( 3 + 5 ) )"));
		assertTrue(expressionSyntaxValidator.isSyntaxValid("var2 = ( ( 7 - 2 ) + ( 4 * 3 ) )"));
		assertTrue(expressionSyntaxValidator.isSyntaxValid("var3 = ( ( var1 + var2 ) - ( var4 * var5 ) ) / ( var6 - var7 )"));

		// Handling nested increments and decrements inside parentheses
		assertTrue(expressionSyntaxValidator.isSyntaxValid("var1 = ( ++var2 * --var3 ) + ( var4-- - var5++ )"));
		assertTrue(expressionSyntaxValidator.isSyntaxValid("var2 = ( ( ++var1 - --var4 ) * ( var5++ + var6-- ) )"));

		// Complex nested arithmetic and division by a result
		assertTrue(expressionSyntaxValidator.isSyntaxValid("var1 = ( ( 10 + 2 ) * ( 5 / ( 1 + 1 ) ) )"));
		assertTrue(expressionSyntaxValidator.isSyntaxValid("var2 = ( var1 * ( 3 + ( 2 - 1 ) ) ) / ( ( var3 + var4 ) * 2 )"));

		// Mixed valid operators and increments
		assertTrue(expressionSyntaxValidator.isSyntaxValid("var1 = var2++ * 3 - ( --var3 / ( var4 + 4 ) )"));
		assertTrue(expressionSyntaxValidator.isSyntaxValid("var2 = ( --var1 + 5 ) * ( ( var3++ * var4-- ) - 7 )"));
		assertTrue(expressionSyntaxValidator.isSyntaxValid("var3 = ( ( var1 - var2 ) * ( var4 / ( var5 + 2 ) ) ) + ( ++var6 )"));
	}


	// Test valid variable names
	@Test
	public void testValidVariableNames() {
		// Valid variables with letters, digits, _ and $
		assertTrue(expressionSyntaxValidator.isSyntaxValid("var1 = 5"));
		assertTrue(expressionSyntaxValidator.isSyntaxValid("Var_2 = 10"));
		assertTrue(expressionSyntaxValidator.isSyntaxValid("_myVar = 15"));
		assertTrue(expressionSyntaxValidator.isSyntaxValid("$specialVar = 20"));
		assertTrue(expressionSyntaxValidator.isSyntaxValid("myVar123 = 30"));
		assertTrue(expressionSyntaxValidator.isSyntaxValid("my_var$ = 35"));
		assertTrue(expressionSyntaxValidator.isSyntaxValid("$_var = 40"));
		assertTrue(expressionSyntaxValidator.isSyntaxValid("$var1_ = 45"));
		assertTrue(expressionSyntaxValidator.isSyntaxValid("MY_VAR = 50"));
	}

	// Test invalid variable names
	@Test
	public void testInvalidVariableNames() {
		// Variables that start with digits
		assertFalse(expressionSyntaxValidator.isSyntaxValid("1var = 5")); // Invalid: starts with a number
		assertFalse(expressionSyntaxValidator.isSyntaxValid("2myVariable = 10"));

		// Variables with special characters
		assertFalse(expressionSyntaxValidator.isSyntaxValid("myVar@123 = 15")); // Invalid: contains '@'
		assertFalse(expressionSyntaxValidator.isSyntaxValid("my#var = 20")); // Invalid: contains '#'
		assertFalse(expressionSyntaxValidator.isSyntaxValid("var&Name = 25")); // Invalid: contains '&'
		assertFalse(expressionSyntaxValidator.isSyntaxValid("var*Name = 30")); // Invalid: contains '*'
		assertFalse(expressionSyntaxValidator.isSyntaxValid("var!Name = 35")); // Invalid: contains '!'

		// Variables with spaces
		assertFalse(expressionSyntaxValidator.isSyntaxValid("var name = 40")); // Invalid: contains space
		assertFalse(expressionSyntaxValidator.isSyntaxValid("my Var = 45")); // Invalid: contains space
		assertFalse(expressionSyntaxValidator.isSyntaxValid("myVar1 = 50 myVar2 = 60")); // Invalid: contains space between expressions

		// Variables with reserved keywords
		assertFalse(expressionSyntaxValidator.isSyntaxValid("int = 55")); // Invalid: 'int' is a reserved keyword
		assertFalse(expressionSyntaxValidator.isSyntaxValid("class = 60")); // Invalid: 'class' is a reserved keyword
		assertFalse(expressionSyntaxValidator.isSyntaxValid("for = 65")); // Invalid: 'for' is a reserved keyword
		assertFalse(expressionSyntaxValidator.isSyntaxValid("public = 70")); // Invalid: 'public' is a reserved keyword
	}

	// Test valid expressions with mixed case variable names
	@Test
	public void testMixedCaseVariableNames() {
		assertTrue(expressionSyntaxValidator.isSyntaxValid("varName = 5"));
		assertTrue(expressionSyntaxValidator.isSyntaxValid("myVar = varName + 10"));
		assertTrue(expressionSyntaxValidator.isSyntaxValid("MyVar = ( myVar + 5 ) * 2"));
		assertTrue(expressionSyntaxValidator.isSyntaxValid("another_Var = 20 + MyVar"));
		assertTrue(expressionSyntaxValidator.isSyntaxValid("SOME_VAR = another_Var - 10"));
	}

	// Test invalid expressions due to variable naming violations
	@Test
	public void testInvalidVariableNamingInExpressions() {
		assertFalse(expressionSyntaxValidator.isSyntaxValid("if = 10"));
		assertFalse(expressionSyntaxValidator.isSyntaxValid("while = 20"));
		assertFalse(expressionSyntaxValidator.isSyntaxValid("do = 30"));
		assertFalse(expressionSyntaxValidator.isSyntaxValid("switch = 40"));
	}

	// Test valid complex variable names
	@Test
	public void testComplexValidVariableNames() {
		assertTrue(expressionSyntaxValidator.isSyntaxValid("_$_varName123 = 5")); // Starts with '_'
		assertTrue(expressionSyntaxValidator.isSyntaxValid("$myVariable_456 = 10")); // Starts with '$'
		assertTrue(expressionSyntaxValidator.isSyntaxValid("Var$Name_789 = 15")); // Contains '$' and '_'
		assertTrue(expressionSyntaxValidator.isSyntaxValid("_$var123 = 20")); // Starts with '_$'
		assertTrue(expressionSyntaxValidator.isSyntaxValid("var_name_with_underscores = 25")); // Valid use of underscores
		assertTrue(expressionSyntaxValidator.isSyntaxValid("mixed123CASE_var = 30")); // Mixed case and numbers
	}
}
