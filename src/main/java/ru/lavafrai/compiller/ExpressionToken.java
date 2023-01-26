package ru.lavafrai.compiller;

public class ExpressionToken {
    public enum TokenType {
        NUMBER,
        OPERATOR,
        OPEN_BRACKET,
        CLOSE_BRACKET,
        EXPRESSION,
        EOF
    }

    public void setToken(String token) {
        this.token = token;
    }

    private String token;
    private final TokenType type;

    public ExpressionToken(String token, TokenType type) {
        this.token = token;
        this.type = type;
    }

    public String getToken() {
        return token;
    }

    public TokenType getType() {
        return type;
    }
}
