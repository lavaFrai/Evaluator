package ru.lavafrai.compiller;

import java.util.ArrayList;
import java.util.List;

public class Assembler {
    public int getRequiredMemory() {
        return requiredMemSize;
    }

    private int requiredMemSize = 0;
    private final SimplifiedExpression expression;
    private final List<AssemblerExpression> functions = new ArrayList<>();
    private int entryPoint = 0;

    public Assembler(Expression expression) {
        this.expression = expression.Process();
    }
    public Assembler(SimplifiedExpression simplifiedExpression) {
        this.expression = simplifiedExpression;
    }

    public Assembler compile() {
        for (Expression curExp : expression.getExpressions()) {
            AssemblerExpression assemblerExpression = AssemblerExpression.getOneFromOneLevelExpression(curExp);
            functions.add(assemblerExpression);
            requiredMemSize += assemblerExpression.requiredMemSize;
        }
        entryPoint = expression.getEntryPointIndex();
        return this;
    }

    public int getFunctionsCount() {
        return functions.size();
    }

    public AssemblerExpression getFunction(int idx) {
        return functions.get(idx);
    }

    public int getEntryPoint() {
        return entryPoint;
    }
}
