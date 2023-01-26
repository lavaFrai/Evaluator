package ru.lavafrai.compiller;

import java.util.ArrayList;
import java.util.List;

public class AssemblerExpression {
    // public int requiredStackSize = 0;
    public int requiredMemSize = 0;
    private static List<List<String>> operatorsPriority = new ArrayList<List<String>>() {
        {
            add(new ArrayList<String>() {
                {
                    add("*");
                    add("/");
                }
            });
            add(new ArrayList<String>() {
                {
                    add("+");
                    add("-");
                }
            });
        }
    };

    public final List<AssemblerInstruction> instructions;

    public AssemblerExpression() {
        this.instructions = new ArrayList<AssemblerInstruction>();
    }

    public void addInstruction(AssemblerInstruction instruction){
        instructions.add(instruction);
    }

    public String toString(){
        StringBuilder sb = new StringBuilder();
        for (AssemblerInstruction instruction : instructions) {
            sb.append("\t");
            sb.append(instruction);
            sb.append("\n");
        }
        // sb.append("Required stack size: ");
        // sb.append(requiredStackSize);
        // sb.append("\n");
        sb.append("Required memory size: ");
        sb.append(requiredMemSize);
        sb.append("\n");
        return sb.toString();
    }

    public static AssemblerExpression getOneFromOneLevelExpression(Expression expression) {
        AssemblerExpression assemblerExpression = new AssemblerExpression();

        Expression curExp = expression;
        int k = 0;

        // loading all constants
        for (ExpressionToken token : curExp) {
            if (token.getType() == ExpressionToken.TokenType.NUMBER) {
                assemblerExpression.addInstruction(new AssemblerInstruction("LOAD", token.getToken(), "R" + String.valueOf(k++)));
                token.setToken("R" + (k - 1));
            }
            if (token.getType() == ExpressionToken.TokenType.EXPRESSION) {
                assemblerExpression.addInstruction(new AssemblerInstruction("CALL", token.getToken(), null));
                assemblerExpression.addInstruction(new AssemblerInstruction("POP", "R" + String.valueOf(k++), null));
                token.setToken("R" + (k - 1));
            }
        }
        assemblerExpression.requiredMemSize = k;

        // first - unary operators
        for (int i = curExp.size() - 1; i > 0; i--) {
            if (curExp.get(i).getType() != ExpressionToken.TokenType.EOF) {
                if (curExp.get(i).getType() == ExpressionToken.TokenType.OPERATOR &&
                        (curExp.get(i - 1).getType() != ExpressionToken.TokenType.NUMBER &&
                        curExp.get(i - 1).getType() != ExpressionToken.TokenType.CLOSE_BRACKET &&
                        curExp.get(i - 1).getType() != ExpressionToken.TokenType.EXPRESSION)) {
                    // System.out.println("Unary operator found: " + curExp.get(i).getToken());
                    assemblerExpression.addInstruction(new AssemblerInstruction("UNARY" + curExp.get(i).getToken(), curExp.get(i + 1).getToken(), null));
                    curExp.remove(i);
                    // i--;

                }
            }
        }
        if (curExp.get(0).getType() == ExpressionToken.TokenType.OPERATOR) {
            // System.out.println("Unary operator found: " + curExp.get(0).getToken());
            assemblerExpression.addInstruction(new AssemblerInstruction("UNARY" + curExp.get(0).getToken(), curExp.get(1).getToken(), null));
            curExp.remove(0);
        }
        // System.out.println(curExp);
        // second - binary operators
        int resultAddress = 0;
        for (List<String> operators : operatorsPriority) {
            for (int i = 0; i < curExp.size(); i++) {
                if (curExp.get(i).getType() == ExpressionToken.TokenType.OPERATOR) {
                    if (operators.contains(curExp.get(i).getToken())) {
                        // System.out.println("Binary operator found: " + curExp.get(i).getToken());
                        assemblerExpression.addInstruction(new AssemblerInstruction("BINARY" + curExp.get(i).getToken(), curExp.get(i - 1).getToken(), curExp.get(i + 1).getToken()));
                        resultAddress = curExp.get(i - 1).getToken().charAt(1) - '0';
                        curExp.remove(i + 1);
                        curExp.get(i).setToken(curExp.get(i - 1).getToken());
                        curExp.remove(i - 1);
                        i--;
                    }
                }
            }
        }
        assemblerExpression.addInstruction(new AssemblerInstruction("PUSH", "R" + String.valueOf(resultAddress), null));

        return assemblerExpression;
    }
}
