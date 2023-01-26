package ru.lavafrai.tokenizator;

import ru.lavafrai.compiller.Expression;
import ru.lavafrai.compiller.ExpressionToken;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class Tokenizator {
    public static Expression parseString(String str) throws IOException {
        Expression expression = new Expression();

        InputStream stream = new ByteArrayInputStream(str.getBytes(StandardCharsets.UTF_8));

        Scanner scanner = new Scanner(stream);
        String token = null;
        while (stream.available() > 0) {
            if (token == null) {
                token = "" + (char) stream.read();
            }

            // token = "" + ((char)stream.read());

            if (token.matches("[0-9]+")) {
                StringBuilder n = new StringBuilder(new String(token));

                while (stream.available() > 0 && (token = "" + ((char)stream.read())).matches("[0-9]+")) {
                    n.append(token);
                }
                expression.add(new ExpressionToken(n.toString(), ExpressionToken.TokenType.NUMBER));
            }
            else if (token.matches("[+\\-*/]")) {
                expression.add(new ExpressionToken(token, ExpressionToken.TokenType.OPERATOR));
                token = null;
            }
            else if (token.matches("[()]")) {
                if (token.equals("(")) {
                    expression.add(new ExpressionToken(token, ExpressionToken.TokenType.OPEN_BRACKET));
                }
                else {
                    expression.add(new ExpressionToken(token, ExpressionToken.TokenType.CLOSE_BRACKET));
                }
                token = null;
            }
            else {
                throw new RuntimeException("Unknown token: " + token);
            }

        }

        /*expression.add(new ExpressionToken("2", ExpressionToken.TokenType.NUMBER));
        expression.add(new ExpressionToken("+", ExpressionToken.TokenType.OPERATOR));
        expression.add(new ExpressionToken("2", ExpressionToken.TokenType.NUMBER));*/
        return expression;
    }
}
