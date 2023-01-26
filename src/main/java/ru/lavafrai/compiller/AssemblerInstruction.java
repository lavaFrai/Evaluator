package ru.lavafrai.compiller;

public class AssemblerInstruction {
    private final String instruction;
    private final String operand1;

    public String getOperand1() {
        return operand1;
    }

    public String getOperand2() {
        return operand2;
    }

    public String toString(){
        if (operand1 == null){
            return instruction;
        }
        if (operand2 == null){
            return instruction + " " + operand1;
        }
        return instruction + " " + operand1 + " " + operand2;
    }

    private final String operand2;

    public AssemblerInstruction(String instruction, String operand1, String operand2) {
        this.instruction = instruction;
        this.operand1 = operand1;
        this.operand2 = operand2;
    }

    public String getInstruction() {
        return instruction;
    }

    public int parseMemoryAddress(int operandNumber){
        if (operandNumber == 1){
            return Integer.parseInt(operand1.substring(1));
        }
        if (operandNumber == 2){
            return Integer.parseInt(operand2.substring(1));
        }
        throw new IllegalArgumentException("Operand number must be 1 or 2");
    }

    public boolean isMemoryAddress(int operandNumber){
        if (operandNumber == 1){
            return operand1.startsWith("R");
        }
        if (operandNumber == 2){
            return operand2.startsWith("R");
        }
        throw new IllegalArgumentException("Operand number must be 1 or 2");
    }

    public int parseConstant(int operandNumber){
            if (operandNumber == 1) {
                try {
                    return Integer.parseInt(operand1);
                } catch (NumberFormatException e){
                    throw new IllegalArgumentException("Invalid integer constant '" + operand1 + "'");
                }
            }
            if (operandNumber == 2) {
                try {
                    return Integer.parseInt(operand2);
                } catch (NumberFormatException e){
                    throw new IllegalArgumentException("Invalid integer constant '" + operand2 + "'");
                }
            }
        throw new IllegalArgumentException("Operand number must be 1 or 2");
    }

    public int parseCodeAddress(int operandNumber){
        if (operandNumber == 1){
            return Integer.parseInt(operand1.substring(1));
        }
        if (operandNumber == 2){
            return Integer.parseInt(operand2.substring(1));
        }
        throw new IllegalArgumentException("Operand number must be 1 or 2");
    }
}
