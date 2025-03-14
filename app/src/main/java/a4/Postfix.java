package a4;

import java.util.ArrayDeque;

/**
 * This class provides methods for evaluating postfix mathematical expressions.
 * It uses a stack-based algorithm to process operands and operators.
 */
public class Postfix {

    /**
     * Evaluates a postfix expression, which is a mathematical expression where 
     * operators follow their operands. The expression is given as a deque of tokens,
     * where each token is either a Double (operand) or a Character (operator).
     * @param tokens An ArrayDeque of Objects representing the infix expression, 
     *               where each element is either a Double (operand) or a Character (operator).
     * @return A Double representing the result of evaluating the postfix expression
     * @throws IllegalArgumentException If there are not enough operands for an operator, or if there is 
     *                                  an unknown operator in the expression.
     * @throws ArithmeticException If division by zero is attempted in the expression.
     */
    public static Double postfix(ArrayDeque<Object> tokens) {
        ArrayDeque<Double> stack = new ArrayDeque<>();

    while(!tokens.isEmpty()) {
        Object token = tokens.poll(); //This should get the next token

        if (token instanceof Double) {
            stack.push((Double) token); //Pushes numbers onto the stack
        } else if (token instanceof Character) {
            char op = (Character) token;

            if (stack.size() < 2) {
                throw new IllegalArgumentException("Not enough operands for operatior: " + op);
            }

            double b = stack.pop();
            double a = stack.pop();
            double result = applyOperator(a, b, op);
            stack.push(result);
        }
    }
        if (stack.size() != 1) {
            throw new IllegalArgumentException("Invalid postfix expression.");
        }

        return stack.pop();
    }

    /**
     * Applies the given operator to two operands
     * @param a The first operand
     * @param b The second operand
     * @param op The operator to apply
     * @return The result of applying the operator to the operands
     * @throws IllegalArgumentException If an unknown operator is encountered.
     * @throws ArithmeticException If division by zero is attempted.
     */
    private static double applyOperator(double a, double b, char op) {
        switch (op) {
            case '+': return a + b;
            case '-': return a - b;
            case '*': return a * b;
            case '/':
                if (b == 0) throw new ArithmeticException("Division by zero");
                return a / b;
            case '^': return Math.pow(a, b);
            default: throw new IllegalArgumentException("Unknown operator: " + op);
        }
    }

    /**
     * Simple main method to test the code
     */
    public static void main(String[] args) {
        // Example: (3 + 5) * 2
        ArrayDeque<Object> tokens = new ArrayDeque<>();
        tokens.add(3.0);  // First operands
        tokens.add(5.0);  // Second operand
        tokens.add('+');  // Operator
        tokens.add(2.0);  // Third operand
        tokens.add('*');  // Operator

        // Call postfix evaluation
        double result = postfix(tokens);
        System.out.println("Result: " + result);  // Should print 16.0
    }
}