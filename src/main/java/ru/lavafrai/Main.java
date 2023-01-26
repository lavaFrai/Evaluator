package ru.lavafrai;

import ru.lavafrai.compiller.*;
import ru.lavafrai.machine.VirtualMachineInteger;
import ru.lavafrai.tokenizator.Tokenizator;

import java.io.IOException;
import java.util.Scanner;


public class Main {
    private static Expression getTestExpression1() {
        Expression expression = new Expression();
        expression.add(new ExpressionToken("1", ExpressionToken.TokenType.NUMBER));
        expression.add(new ExpressionToken("+", ExpressionToken.TokenType.OPERATOR));
        expression.add(new ExpressionToken("(", ExpressionToken.TokenType.OPEN_BRACKET));
        expression.add(new ExpressionToken("2", ExpressionToken.TokenType.NUMBER));
        expression.add(new ExpressionToken("*", ExpressionToken.TokenType.OPERATOR));
        expression.add(new ExpressionToken("3", ExpressionToken.TokenType.NUMBER));
        expression.add(new ExpressionToken(")", ExpressionToken.TokenType.CLOSE_BRACKET));
        expression.add(new ExpressionToken("*", ExpressionToken.TokenType.OPERATOR));
        expression.add(new ExpressionToken("4", ExpressionToken.TokenType.NUMBER));
        expression.add(new ExpressionToken("\0", ExpressionToken.TokenType.EOF));
        return expression;
    }

    private static Expression getTestExpression2() {
        Expression expression = new Expression();
        expression.add(new ExpressionToken("1", ExpressionToken.TokenType.NUMBER));
        expression.add(new ExpressionToken("+", ExpressionToken.TokenType.OPERATOR));
        expression.add(new ExpressionToken("(", ExpressionToken.TokenType.OPEN_BRACKET));
        expression.add(new ExpressionToken("2", ExpressionToken.TokenType.NUMBER));
        expression.add(new ExpressionToken("*", ExpressionToken.TokenType.OPERATOR));
        expression.add(new ExpressionToken("(", ExpressionToken.TokenType.OPEN_BRACKET));
        expression.add(new ExpressionToken("2", ExpressionToken.TokenType.NUMBER));
        expression.add(new ExpressionToken("*", ExpressionToken.TokenType.OPERATOR));
        expression.add(new ExpressionToken("2", ExpressionToken.TokenType.NUMBER));
        expression.add(new ExpressionToken(")", ExpressionToken.TokenType.CLOSE_BRACKET));
        expression.add(new ExpressionToken("-", ExpressionToken.TokenType.OPERATOR));
        expression.add(new ExpressionToken("3", ExpressionToken.TokenType.NUMBER));
        expression.add(new ExpressionToken(")", ExpressionToken.TokenType.CLOSE_BRACKET));
        expression.add(new ExpressionToken("*", ExpressionToken.TokenType.OPERATOR));
        expression.add(new ExpressionToken("7", ExpressionToken.TokenType.NUMBER));
        expression.add(new ExpressionToken("\0", ExpressionToken.TokenType.EOF));
        return expression;
    }

    private static Expression getTestExpression3() {
        Expression expression = new Expression();
        expression.add(new ExpressionToken("1", ExpressionToken.TokenType.NUMBER));
        expression.add(new ExpressionToken("+", ExpressionToken.TokenType.OPERATOR));
        expression.add(new ExpressionToken("(", ExpressionToken.TokenType.OPEN_BRACKET));
        expression.add(new ExpressionToken("2", ExpressionToken.TokenType.NUMBER));
        expression.add(new ExpressionToken("*", ExpressionToken.TokenType.OPERATOR));
        expression.add(new ExpressionToken("(", ExpressionToken.TokenType.OPEN_BRACKET));
        expression.add(new ExpressionToken("2", ExpressionToken.TokenType.NUMBER));
        expression.add(new ExpressionToken("*", ExpressionToken.TokenType.OPERATOR));
        expression.add(new ExpressionToken("2", ExpressionToken.TokenType.NUMBER));
        expression.add(new ExpressionToken(")", ExpressionToken.TokenType.CLOSE_BRACKET));
        expression.add(new ExpressionToken("-", ExpressionToken.TokenType.OPERATOR));
        expression.add(new ExpressionToken("(", ExpressionToken.TokenType.OPEN_BRACKET));
        expression.add(new ExpressionToken("2", ExpressionToken.TokenType.NUMBER));
        expression.add(new ExpressionToken("/", ExpressionToken.TokenType.OPERATOR));
        expression.add(new ExpressionToken("2", ExpressionToken.TokenType.NUMBER));
        expression.add(new ExpressionToken(")", ExpressionToken.TokenType.CLOSE_BRACKET));
        expression.add(new ExpressionToken(")", ExpressionToken.TokenType.CLOSE_BRACKET));
        expression.add(new ExpressionToken("*", ExpressionToken.TokenType.OPERATOR));
        expression.add(new ExpressionToken("-", ExpressionToken.TokenType.OPERATOR));
        expression.add(new ExpressionToken("7", ExpressionToken.TokenType.NUMBER));
        expression.add(new ExpressionToken("\0", ExpressionToken.TokenType.EOF));
        return expression;
    }

    private static Expression getTestExpression4() {
        Expression expression = new Expression();
        expression.add(new ExpressionToken("2", ExpressionToken.TokenType.NUMBER));
        expression.add(new ExpressionToken("-", ExpressionToken.TokenType.OPERATOR));
        expression.add(new ExpressionToken("-", ExpressionToken.TokenType.OPERATOR));
        expression.add(new ExpressionToken("3", ExpressionToken.TokenType.NUMBER));
        expression.add(new ExpressionToken("\0", ExpressionToken.TokenType.EOF));
        return expression;
    }


    public static void main(String[] args) throws IOException {
        String stringExpression = new Scanner(System.in).nextLine();
        Expression expression = Tokenizator.parseString(stringExpression);

        System.out.print("Parsing expression: ");
        System.out.println(expression);

        Assembler assembler = new Assembler(expression).compile();

        VirtualMachineInteger vm = new VirtualMachineInteger();
        int result = vm.execute(assembler);
        System.out.println("Result: " + result);
    }
}
