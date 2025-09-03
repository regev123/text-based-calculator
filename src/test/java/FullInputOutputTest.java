import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class FullInputOutputTest {

    private Calculator calculator;
    private ExpressionSyntaxValidator expressionSyntaxValidator;

    @BeforeEach
    public void setUp() {
        calculator = new Calculator();
        expressionSyntaxValidator = new ExpressionSyntaxValidator();
    }

    @Test
    public void testIncrementVariable() {
        String[] expressions = {
                "i = 0",
                "j = ++i",
                "x = i++ + 5",
                "y = 5 + 3 * 10",
                "i += y",
        };

        processExpressions(expressions);

        assertEquals("(i=37,j=1,x=6,y=35)", calculator.toString());
    }

    @Test
    public void testBasicAddition() {
        String[] expressions = {
                "a = 1",
                "b = 2",
                "c = a + b",
        };

        processExpressions(expressions);

        assertEquals("(a=1,b=2,c=3)", calculator.toString());
    }

    @Test
    public void testBasicSubtraction() {
        String[] expressions = {
                "a = 10",
                "b = 5",
                "c = a - b",
        };

        processExpressions(expressions);

        assertEquals("(a=10,b=5,c=5)", calculator.toString());
    }

    @Test
    public void testBasicMultiplication() {
        String[] expressions = {
                "a = 2",
                "--a",
                "b = 3",
                "b++",
                "c = a * b",
        };

        processExpressions(expressions);

        assertEquals("(a=1,b=4,c=4)", calculator.toString());
    }

    @Test
    public void testBasicDivision() {
        String[] expressions = {
                "a = 10",
                "a--",
                "b = 2",
                "++b",
                "c = a / b",
        };

        processExpressions(expressions);

        assertEquals("(a=9,b=3,c=3)", calculator.toString());
    }

    @Test
    public void testMixedOperations() {
        String[] expressions = {
                "a = 5",
                "b = a + 10 - 3 * 2",
        };

        processExpressions(expressions);

        assertEquals("(a=5,b=9)", calculator.toString());
    }

    @Test
    public void testParentheses() {
        String[] expressions = {
                "a = 5",
                "b = ( a + 10 ) * 2",
        };

        processExpressions(expressions);

        assertEquals("(a=5,b=30)", calculator.toString());
    }

    @Test
    public void testVariableReassignment() {
        String[] expressions = {
                "a = 5",
                "a += 3",
        };

        processExpressions(expressions);

        assertEquals("(a=8)", calculator.toString());
    }

    @Test
    public void testIncrementDecrement() {
        String[] expressions = {
                "a = 5",
                "a++",
                "b = --a",
        };

        processExpressions(expressions);

        assertEquals("(a=5,b=5)", calculator.toString());
    }

    @Test
    public void testNestedExpressions() {
        String[] expressions = {
                "a = 5",
                "b = 3",
                "c = a + ( b * 2 )",
        };

        processExpressions(expressions);

        assertEquals("(a=5,b=3,c=11)", calculator.toString());
    }

    @Test
    public void testDivisionByZero() {
        String[] expressions = {
                "a = 5",
                "b = 0",
                "c = a / b",
        };

        assertThrows(ArithmeticException.class, () -> processExpressions(expressions));
    }

    @Test
    public void testNonDeclaredIncrementVariable() {
        String[] expressions = {
                "a = 5",
                "b = 0",
                "d++",
                "c = a / b",
        };

        assertThrows(IllegalArgumentException.class, () -> processExpressions(expressions));
    }

    @Test
    public void testMultipleIncrements() {
        String[] expressions = {
                "a = 1",
                "b = ++a + ++a",
        };

        processExpressions(expressions);

        assertEquals("(a=3,b=5)", calculator.toString());
    }

    @Test
    public void testMultipleDecrements() {
        String[] expressions = {
                "a = 5",
                "b = --a - --a",
        };

        processExpressions(expressions);

        assertEquals("(a=3,b=1)", calculator.toString());
    }

    @Test
    public void testComplexExpression() {
        String[] expressions = {
                "a = 5",
                "b = ( a + 10 ) * 2 - 3 / 1",
        };

        processExpressions(expressions);

        assertEquals("(a=5,b=27)", calculator.toString());
    }

    @Test
    public void testZeroOperations() {
        String[] expressions = {
                "a = 0",
                "b = 0 + 0",
        };

        processExpressions(expressions);

        assertEquals("(a=0,b=0)", calculator.toString());
    }

    @Test
    public void testNegativeNumbers() {
        String[] expressions = {
                "a = -5",
                "b = a * 2",
        };

        processExpressions(expressions);

        assertEquals("(a=-5,b=-10)", calculator.toString());
    }

    @Test
    public void testIncrementWithExpression() {
        String[] expressions = {
                "a = 2",
                "b = a++ + 3",
        };

        processExpressions(expressions);

        assertEquals("(a=3,b=5)", calculator.toString());
    }

    @Test
    public void testDecrementWithExpression() {
        String[] expressions = {
                "a = 5",
                "b = a-- - 3",
        };

        processExpressions(expressions);

        assertEquals("(a=4,b=2)", calculator.toString());
    }

    @Test
    public void testComplexIncrementAndDecrement() {
        String[] expressions = {
                "a = 5",
                "b = ++a + a-- + --a + a++",
        };

        processExpressions(expressions);

        assertEquals("(a=5,b=20)", calculator.toString());
    }

    @Test
    public void testCompoundAssignment() {
        String[] expressions = {
                "a = 5",
                "a += 10",
                "a -= 3",
                "a *= 2",
                "a /= 4",
        };

        processExpressions(expressions);

        assertEquals("(a=6)", calculator.toString());
    }

    @Test
    public void testNestedParentheses() {
        String[] expressions = {
                "a = 5",
                "b = ( ( a + 10 ) * 2 ) - 3",
        };

        processExpressions(expressions);

        assertEquals("(a=5,b=27)", calculator.toString());
    }

    @Test
    public void testMultipleNestedParentheses() {
        String[] expressions = {
                "a = 5",
                "b = ( ( ( a + 10 ) * 2 ) - 3 ) / 2",
        };

        processExpressions(expressions);

        assertEquals("(a=5,b=13)", calculator.toString()); // (27 / 2 = 13.5, rounded down to 13)
    }

    @Test
    public void testParenthesesWithDivision() {
        String[] expressions = {
                "a = 6",
                "b = ( a + 6 ) / ( 3 - 1 )",
        };

        processExpressions(expressions);

        assertEquals("(a=6,b=6)", calculator.toString());
    }

    @Test
    public void testComplexExpressionWithNegativeResult() {
        String[] expressions = {
                "a = 5",
                "b = ( a - 10 ) * ( 2 - 1 )",
        };

        processExpressions(expressions);

        assertEquals("(a=5,b=-5)", calculator.toString());
    }

    @Test
    public void testMultipleExpressionsWithParentheses() {
        String[] expressions = {
                "x = 8",
                "y = ( x - 2 ) * 2",
                "z = ( y + 10 ) / 2",
        };

        processExpressions(expressions);

        assertEquals("(x=8,y=12,z=11)", calculator.toString());
    }

    @Test
    public void testSingleElementParentheses() {
        String[] expressions = {
                "a = 5",
                "b = ( a ) * 2",
        };

        processExpressions(expressions);

        assertEquals("(a=5,b=10)", calculator.toString());
    }

    @Test
    public void testThreeComplexExpressions() {
        String[] expressions = {
                "a = 2 * 3 + ( -12 / ( 3 + 4 ) )",
                "b = ( --a + 5 ) * ( ( a++ * a-- ) - 7 )",
                "c = ( ( a - b ) * ( b / ( a + 2 ) ) ) + ( ++a )"
        };

        processExpressions(expressions);

        assertEquals("(a=5,b=117,c=-2142)", calculator.toString());
    }

    @Test
    public void testFourComplexExpressionsWithSubtractionInsideAndOutsideParentheses() {
        String[] expressions = {
                "tempValue1 = 3 + ( 4 - ( -5 + 2 ) )",
                "temp_Value2 = ( tempValue1 + 2 ) * ( 6 - 3 )",
                "_intermediate_Value$ = ( temp_Value2 / 3 ) + ( --tempValue1 )",
                "finalResult = ( ( tempValue1 + 4 ) * ( 2 - temp_Value2 ) )"
        };

        processExpressions(expressions);

        assertEquals("(tempValue1=9,temp_Value2=36,_intermediate_Value$=21,finalResult=-442)", calculator.toString());
    }

    @Test
    public void testSubtractionInsideAndOutsideParentheses() {
        String[] expressions = {
                "var1$ = -5",
                "var2_ = var1$ + 3",
                "var3A = var2_ * 2",
                "result1 = var3A - ( var1$ * 2 )"
        };

        processExpressions(expressions);

        assertEquals("(var1$=-5,var2_=-2,var3A=-4,result1=6)", calculator.toString());
    }

    @Test
    public void testComplexIncrementsDecrementsAndParentheses() {
        String[] expressions = {
                "a = 3",
                "b = ( a++ + 5 ) * ( --a - 2 )",
                "c = ( ( ++b * 3 ) + ( a-- * 2 ) )",
                "d = c / ( b - a++ ) + a"
        };

        processExpressions(expressions);

        assertEquals("(a=3,b=9,c=33,d=7)", calculator.toString());
    }

    @Test
    public void testDeeplyNestedExpressions() {
        String[] expressions = {
                "x = 4",
                "y = ( x + ( 2 * ( 6 + ( -3 / 1 ) ) ) )",
                "z = ( y - 4 ) * ( ( 8 / ( x - 2 ) ) + x )",
                "result = z * ( ( x++ + y-- ) - z++ )"
        };

        processExpressions(expressions);

        assertEquals("(x=5,y=9,z=49,result=-1632)", calculator.toString());
    }

    @Test
    public void testComplexExpressionWithCompoundAssignment() {
        String[] expressions = {
                "a = 7",
                "b = a * ( ( 10 - 3 ) + ( 2 * --a ) )",
                "c = ( b / a ) * 5",
                "a += ( c * 2 ) / b",
                "finalResult = ( a * b ) + c - a++"
        };

        processExpressions(expressions);

        assertEquals("(a=8,b=133,c=110,finalResult=1034)", calculator.toString());
    }

    // Helper method for processing expressions
    private void processExpressions(String[] expressions) {
        for (String expression : expressions) {
            if (!expressionSyntaxValidator.isSyntaxValid(expression)) {
                throw new IllegalArgumentException("Invalid syntax in the expression: " + expression);
            }
        }

        for (String expression : expressions) {
            calculator.calculateExpression(expression);
        }
    }
}
