package ru.lavafrai.machine;

import ru.lavafrai.compiller.Assembler;
import ru.lavafrai.compiller.AssemblerExpression;
import ru.lavafrai.compiller.AssemblerInstruction;

import java.util.Stack;

public class VirtualMachineInteger {
    private final Stack<Integer> stack = new Stack<>();

    public VirtualMachineInteger() {
    }

    public void push(int value) {
        stack.push(value);
    }

    public int pop() {
        return stack.pop();
    }

    void _run(Assembler assembler, int entryPoint) {
        AssemblerExpression expression = assembler.getFunction(entryPoint);
        int[] memory = new int[expression.requiredMemSize + 5];
        int pc = 0;
        while (pc < expression.instructions.size()) {
            AssemblerInstruction instruction = expression.instructions.get(pc);

            switch (instruction.getInstruction()) {
                case "UNARY-":
                    memory[instruction.parseMemoryAddress(1)] = -memory[instruction.parseMemoryAddress(1)];
                    break;
                case "UNARY+":
                    memory[instruction.parseMemoryAddress(1)] = +memory[instruction.parseMemoryAddress(1)];
                    break;
                case "LOAD":
                    memory[instruction.parseMemoryAddress(2)] = instruction.parseConstant(1);
                    break;
                case "PUSH":
                    stack.push(memory[instruction.parseMemoryAddress(1)]);
                    break;
                case "POP":
                    memory[instruction.parseMemoryAddress(1)] = stack.pop();
                    break;
                case "BINARY+":
                    memory[instruction.parseMemoryAddress(1)] = memory[instruction.parseMemoryAddress(1)] + memory[instruction.parseMemoryAddress(2)];
                    break;
                case "BINARY-":
                    memory[instruction.parseMemoryAddress(1)] = memory[instruction.parseMemoryAddress(1)] - memory[instruction.parseMemoryAddress(2)];
                    break;
                case "BINARY*":
                    memory[instruction.parseMemoryAddress(1)] = memory[instruction.parseMemoryAddress(1)] * memory[instruction.parseMemoryAddress(2)];
                    break;
                case "BINARY/":
                    memory[instruction.parseMemoryAddress(1)] = memory[instruction.parseMemoryAddress(1)] / memory[instruction.parseMemoryAddress(2)];
                    break;
                case "BINARY%":
                    memory[instruction.parseMemoryAddress(1)] = memory[instruction.parseMemoryAddress(1)] % memory[instruction.parseMemoryAddress(2)];
                    break;
                case "CALL":
                    _run(assembler, instruction.parseCodeAddress(1));
                    break;
            }
            pc++;
        }
    }

    public int execute(Assembler assembler) {
        if (assembler == null) {
            throw new IllegalArgumentException("Assembler is null");
        }
        _run(assembler, assembler.getEntryPoint());
        return stack.pop();
    }
}
