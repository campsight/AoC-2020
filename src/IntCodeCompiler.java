import exception.InvalidOperatorException;

import java.math.BigInteger;
import java.util.LinkedHashMap;
import java.util.List;

public class IntCodeCompiler {
    private List<BigInteger> program;
    private int instructionPointer = 0;
    private LinkedHashMap<Integer, Integer> opcodeInstructionCount;
    private static final int OP_ADD = 1;
    private static final int OP_MUL = 2;
    private static final int OP_END = 99;
    public static final int RETURNCODE_OK = 1;
    public static final int RETURNCODE_NOK = -1;
    public static final int RETURNCODE_HALT = 99;

    public IntCodeCompiler(List<BigInteger> program) {
        this.program = program;
        this.opcodeInstructionCount = new LinkedHashMap<>();
        this.opcodeInstructionCount.put(OP_ADD, 4); // Opcode 1 = SUM; add 1 + 2, store in 3
        this.opcodeInstructionCount.put(OP_MUL, 4); // Opcode 1 = MUTIPLICATION; multiply 1 * 2, store in 3
        this.opcodeInstructionCount.put(OP_END, 1); // Opcode 99 = HALT
    }

    public List<BigInteger> getProgram() {
        return program;
    }

    public void executeNextInstruction() {
        executeNextInstruction(false);
    }

    public int executeNextInstruction(boolean printOut) throws InvalidOperatorException {
        int opcode = program.get(instructionPointer).intValue();
        switch(opcode) {
            case OP_ADD:
                program.set(program.get(instructionPointer + 3).intValue(), program.get(program.get(instructionPointer+1).intValue()).add(program.get(program.get(instructionPointer+2).intValue())));
                instructionPointer += opcodeInstructionCount.get(OP_ADD);
                return RETURNCODE_OK;
            case OP_MUL:
                program.set(program.get(instructionPointer + 3).intValue(), program.get(program.get(instructionPointer+1).intValue()).multiply(program.get(program.get(instructionPointer+2).intValue())));
                instructionPointer += opcodeInstructionCount.get(OP_MUL);
                return RETURNCODE_OK;
            case OP_END:
                return RETURNCODE_HALT;
            default:
                throw new InvalidOperatorException(opcode, getInstructionPointer(), "Unknown case in executeNextInstruction.");
        }
    }

    public void setProgram(List<BigInteger> program) {
        this.program = program;
    }

    public int getInstructionPointer() {
        return instructionPointer;
    }

    public void setInstructionPointer(int instructionPointer) {
        this.instructionPointer = instructionPointer;
    }
}
