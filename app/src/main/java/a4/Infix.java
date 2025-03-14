package a4;

import java.util.ArrayDeque;
import java.util.Arrays;

/**
 * This class provides methods for converting infix mathematical expressions to postfix 
 * notation and evaluating the result using a stack-based algorithm.
 */
public class Infix {

    /**
     * Converts an infix expression (given as a deque of tokens) into postfix notation.
     * The expression can contain operators, operands, and parentheses.
     * 
     * @param tokens An ArrayDeque of Objects representing the infix expression, 
     *               where each element is either a Double (operand) or a Character (operator).
     * @return Double representing the result of the postfix expression evaluation.
     * @throws IllegalArgumentException If there are mismatched parentheses or an invalid token in the input.
     */
    public static Double infixToPostfix(ArrayDeque<Object> tokens) {
        ArrayDeque<Object> outputQueue = new ArrayDeque<>();
        ArrayDeque<Character> operatorStack = new ArrayDeque<>();

        while(!tokens.isEmpty()) {
            Object token = tokens.poll();

            if (token instanceof Double) {
                outputQueue.add(token);
            } else if (token instanceof Character) {
                char op = (Character) token;

                if (op == '(') {
                    operatorStack.push(op);
                } else if (op == ')') {
                    while(!operatorStack.isEmpty() && operatorStack.peek() != '(') {
                        outputQueue.add(operatorStack.pop());
                    }
                    if (operatorStack.isEmpty()) {
                        throw new IllegalArgumentException("Mismatched parentheses.");
                    }
                    operatorStack.pop(); //This removes "(" without adding it to the output queue
             } else {
                while(!operatorStack.isEmpty() && 
                operatorStack.peek() != '(' && 
                shouldPop(operatorStack.peek(), op)) {
                    outputQueue.add(operatorStack.pop());
                }
                operatorStack.push(op);
             }
            }
        }
        while (!operatorStack.isEmpty()) {
            char remainingOp = operatorStack.pop();
            if(remainingOp == '(' || remainingOp == ')') {
                throw new IllegalArgumentException("Mismatched parentheses.");
            }
            outputQueue.add(remainingOp);
        }

        return Postfix.postfix(outputQueue); 
    }

    /**
     * Returns the precedence of the given operator.
     * Precedence determines the order of operations in an expression.
     * @param op The operator whose precedence is to be evaluated.
     * @return An integer value representing the precedence level of the operator.
     *         Higher values = higher precedence
     */
    private static int precedence(char op) {
        switch(op) {
            case '+': case '-': return 1;
            case '*': case '/': return 2;
            case '^': return 3; //Exponents have the highest precedence
            default: return -1;
        }
    }


    /**
     * Determines if a given operator is right-associative
     * @param op The operator to check
     * @return true if the operator is right-associative, otherwise, returns false
     */
    public static boolean isRightAssociative(char op) {
        return op == '^'; //Since expenentiation is right associative
    }

    /**
     * Determines whether the operator at the top of the stack should be popped in order to 
     * respect operator precedence and associativity.
     * @param stackOp The operator at the top of the stack
     * @param queueOp The operator to be added to the output queue
     * @return true if the operator on the stack should be pushed, otherwise, false
     */
    private static boolean shouldPop(char stackOp, char queueOp) {
        if (isRightAssociative(queueOp)) {
            return precedence(stackOp) > precedence(queueOp); //Right associativeity: strictly greater precedence
        }

        return precedence(stackOp) >= precedence(queueOp); //Left associativeity: greater than or equal precedence
    }
}



