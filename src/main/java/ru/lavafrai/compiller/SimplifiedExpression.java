package ru.lavafrai.compiller;

import java.util.ArrayList;
import java.util.List;

public class SimplifiedExpression {
    private int entryPoint;

    public int getEntryPointIndex() {
        return entryPoint;
    }

    public void setEntryPoint(int entryPoint) {
        this.entryPoint = entryPoint;
    }

    private final List<Expression> expressions = new ArrayList<Expression>();
    public List<Expression> getExpressions() {
        return expressions;
    }

    public void addExpression(Expression curExp){
        expressions.add(curExp);
    }

    public Expression getExpression(int idx){
        return expressions.get(idx);
    }

    public int getSize(){
        return expressions.size();
    }
}
