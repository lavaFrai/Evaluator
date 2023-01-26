package ru.lavafrai.compiller;

import java.util.ArrayList;

import static java.lang.Math.max;

public class Expression extends ArrayList<ExpressionToken> {
    public Expression() {
        super();
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        for (ExpressionToken token : this) {
            if (token.getType() == ExpressionToken.TokenType.EOF) {
                break;
            }
            result.append(token.getToken());
        }
        return result.toString();
    }

    private int getMaximalScanlineValue() {
        int scanCounter = 0;
        int result = 0;
        for (ExpressionToken token : this) {
            if (token.getType() == ExpressionToken.TokenType.OPEN_BRACKET) {
                scanCounter++;
            }
            if (token.getType() == ExpressionToken.TokenType.CLOSE_BRACKET) {
                scanCounter--;
            }
            result = max(scanCounter,result);
        }
        return result;
    }

    public SimplifiedExpression Process() {
        int deepness = getMaximalScanlineValue();
        // System.out.println("Deepness: " + getMaximalScanlineValue());
        SimplifiedExpression result = new SimplifiedExpression();
        Expression currentExpression = new Expression();
        int lastTokenIndex = 0;

        for (; deepness >= 0; deepness--) {
            int scanCounter = 0;
            int scanCounterLast = -1;
            boolean reading = false;

            for (int i = 0; i < this.size(); i++) {
                ExpressionToken token = this.get(i);
                if (token.getType() == ExpressionToken.TokenType.OPEN_BRACKET) {
                    scanCounter++;
                }
                if (token.getType() == ExpressionToken.TokenType.CLOSE_BRACKET) {
                    scanCounter--;
                }

                if (scanCounterLast < scanCounter && deepness == scanCounter) {
                    // Start new expression

                }
                if (scanCounterLast < scanCounter && deepness + 1 == scanCounter) {
                    // Start included expression
                    currentExpression.add(new ExpressionToken("%" + lastTokenIndex, ExpressionToken.TokenType.EXPRESSION));
                    lastTokenIndex++;
                }
                if (scanCounterLast > scanCounter && deepness - 1 == scanCounter) {
                    // Stop current and start new expression
                    result.addExpression(currentExpression);
                    currentExpression = new Expression();
                }
                if (deepness == scanCounter) {
                    if (token.getType() != ExpressionToken.TokenType.OPEN_BRACKET && token.getType() != ExpressionToken.TokenType.CLOSE_BRACKET) {
                        currentExpression.add(token);
                    }
                }


                scanCounterLast = scanCounter;
            }
        }
        result.addExpression(currentExpression);
        result.setEntryPoint(result.getSize() - 1);

        return result;
    }
}
