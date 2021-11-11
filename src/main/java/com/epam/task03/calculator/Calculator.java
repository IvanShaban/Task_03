package com.epam.task03.calculator;

import java.util.Stack;

public class Calculator {
    public static void main(String[] args) {
        Calculator calculator = new Calculator();
        System.out.println(calculator.decide("(10-1+5*4)/3"));
    }

    public double decide(String expression) {
        return rpnToAnswer(expressionToRpn(expression));
    }

    private String expressionToRpn(String expression) {
        StringBuilder current = new StringBuilder();
        Stack<Character> stack = new Stack<>();
        int priority;

        for (int i = 0; i < expression.length(); i++) {
            priority = getPriority(expression.charAt(i));

            if (priority == -1) {
                current.append(" ");
                while (getPriority(stack.peek()) != 1) {
                    current.append(stack.pop());
                }
                stack.pop();
            } else if (priority == 0) {
                current.append(expression.charAt(i));
            } else if (priority == 1) {
                stack.push(expression.charAt(i));
            } else {
                current.append(" ");
                while (!stack.isEmpty()) {
                    if (getPriority(stack.peek()) >= priority) {
                        current.append(stack.pop());
                    } else {
                        break;
                    }
                }
                stack.push(expression.charAt(i));
            }
        }
        while (!stack.isEmpty()) {
            current.append(stack.pop());
        }
        return current.toString();
    }

    private double rpnToAnswer(String rpn) {
        String operand;
        Stack<Double> stack = new Stack<>();

        for (int i = 0; i < rpn.length(); i++) {
            if (rpn.charAt(i) == ' ') {
                continue;
            }
            if (getPriority(rpn.charAt(i)) == 0) {
                operand = "";
                while (rpn.charAt(i) != ' ' && getPriority(rpn.charAt(i)) == 0) {
                    operand += rpn.charAt(i++);
                    if (i == rpn.length()) {
                        break;
                    }
                    stack.push(Double.parseDouble(operand));
                }
            }
            if (getPriority(rpn.charAt(i)) > 1) {
                double a = stack.pop();
                double b = stack.pop();
                switch (rpn.charAt(i)) {
                    case '+':
                        stack.push(b + a);
                        break;
                    case '-':
                        stack.push(b - a);
                        break;
                    case '*':
                        stack.push(b * a);
                        break;
                    case '/':
                        stack.push(b / a);
                        break;
                }
            }
        }
        return stack.pop();
    }

    private int getPriority(char token) {
        if (token == '*' || token == '/') {
            return 3;
        } else if (token == '+' || token == '-') {
            return 2;
        } else if (token == '(') {
            return 1;
        } else if (token == ')') {
            return -1;
        } else {
            return 0;
        }
    }
}
