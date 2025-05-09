package tum.i2.cma;

import tum.i2.common.VirtualMachine;

public class CMa implements VirtualMachine {
    // TODO
    // Describe the CMa architecture here
    int MAX = 100;
    int[] S; // stack
    int SP; // stack pointer

    CMaInstruction[] C;
    int PC; // program counter
    CMaInstruction IR; // instruction register

    public CMa(CMaInstruction[] instructions) {
        // TODO Initialize the CMa architecture here
        S = new int[MAX];
        C = instructions;
        SP = 0;
        PC = 0;
        IR = null;
    }

    @Override
    public void step() {
        // TODO Implement one execution step
        if (PC >= C.length) {
            throw new RuntimeException("No more instructions.");
        }
        IR = C[PC];
        PC++;
        execute(IR);
    }

    @Override
    public int run() {
        // TODO Implement the main loop of the VM
        // as introduced in the lecture
        // We have defined the step() method here,
        // because it might make it easier to debug,
        // and would be required if you wish to implement
        // an interface with step function
        while (true) {
            step();
        }
    }

    public void execute(CMaInstruction instruction) {
        // CMaInstructionType enum contains comments,
        // describing where the operations are defined
        switch (instruction.getType()) {
        // TODO Implement the instruction set as far as introduced
        case LOADC:
            SP++;
            S[SP] = instruction.getFirstArg();
            break;
        case ADD:
            S[SP - 1] = S[SP - 1] + S[SP];
            SP--;
            break;
        case SUB:
            S[SP - 1] = S[SP - 1] - S[SP];
            SP--;
            break;
        case MUL:
            S[SP - 1] = S[SP - 1] * S[SP];
            SP--;
            break;
        case DIV:
            S[SP - 1] = S[SP - 1] / S[SP];
            SP--;
            break;
        case MOD:
            S[SP - 1] = S[SP - 1] % S[SP];
            SP--;
        case AND:
            S[SP - 1] = S[SP - 1] & S[SP];
            SP--;
            break;
        case OR:
            S[SP - 1] = S[SP - 1] | S[SP];
            SP--;
            break;
        case EQ:
            S[SP - 1] = S[SP - 1] == S[SP] ? 1 : 0;
            SP--;
            break;
        case NEQ:
            S[SP - 1] = S[SP - 1] != S[SP] ? 1 : 0;
            SP--;
            break;
        case LE:
            S[SP - 1] = S[SP - 1] < S[SP] ? 1 : 0;
            SP--;
            break;
        case LEQ:
            S[SP - 1] = S[SP - 1] <= S[SP] ? 1 : 0;
            SP--;
            break;
        case GR:
            S[SP - 1] = S[SP - 1] > S[SP] ? 1 : 0;
            SP--;
            break;
        case GEQ:
            S[SP - 1] = S[SP - 1] >= S[SP] ? 1 : 0;
            SP--;
            break;
        case NOT:
        case NEG:
            S[SP] = -S[SP];
            break;
        case LOAD:
            // old
            // S[SP] = S[S[SP]];
            // generalised
            int m = instruction.getFirstArg();
            for (int i = m - 1; i >= 0; i--) {
                S[SP + i] = S[S[SP] + i];
            }
            SP = SP + m - 1;
            break;
        case STORE:
            // old
            // S[S[SP]] = S[SP - 1];
            // SP--;
            // generalised
            int n = instruction.getFirstArg();
            for (int i = n - 1; i >= 0; i--) {
                S[S[SP] + i] = S[SP - n + i];
            }
            SP = SP + n - 1;
            break;
        case LOADA:
            //idgi
            break;
        case STOREA:
            //idgi
            break;
        case POP:
            SP--;
            break;
        case JUMP:
            PC = instruction.getFirstArg();
            break;
        case JUMPZ:
            if (S[SP] == 0) {
                PC = instruction.getFirstArg();
            }
            SP--; // because S[SP] is consumed
            break;
        case JUMPI:
            PC = S[SP] + instruction.getFirstArg();
            break;
        case DUP:
            S[SP + 1] = S[SP];
            SP++;
            break;
        case ALLOC:
            break;
        default:
            throw new UnsupportedOperationException("Unknown instruction type: " + instruction.getType());
        }
    }

    // TODO: If you wish, you can implement each instruction as a method
}
