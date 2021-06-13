import java.util.InputMismatchException;
import java.io.*;
import net.fornwall.jelf.*;

class Disassembler {
    final ElfFile file;

    public Disassembler(ElfFile file) {
        if (file.arch != 0xF3) {
            throw new InputMismatchException("This file isn't for RISC-V");
        }
        if (file.objectSize != ElfFile.CLASS_32) {
            throw new InputMismatchException("This file isn't 32-bit");
        }
        this.file = file;
    }

    public void printDisassembler(OutputStreamWriter output) {
        PrintWriter writer = new PrintWriter(output);
        disassemble(writer);
    }

    String registerToString(int register) {
        if (register == 0)
            return "zero";
        else if (register == 1)
            return "ra";
        else if (register == 2)
            return "sp";
        else if (register == 3)
            return "gp";
        else if (register == 4)
            return "tp";
        else if (5 <= register && register <= 7)
            return "t" + (register - 5);
        else if (register == 8)
            return "s0";
        else if (register == 9)
            return "s1";
        else if (10 <= register && register <= 17)
            return "a" + (register - 10);
        else if (18 <= register && register <= 27)
            return "s" + (register - 16);
        else if (28 <= register && register <= 31)
            return "t" + (register - 25);
        else
            throw new AssertionError(register + " isn't in file");
    }

    private String symbolToString(long a) {
        ElfSymbol symb = file.getELFSymbol(a);
        String res = String.format("0x%08X", a);
        if (symb != null && symb.st_value == a && symb.section_type == ElfSymbol.STT_FUNC) {
            res += " <" + symb.getName() + ">";
        }
        return res;
    }

    private void disassemble(PrintWriter out) {
        ElfSection section = file.firstSectionByName(".text");
        if (section == null) {
            throw new InputMismatchException("No .text found");
        }
        file.getDynamicSymbolTableSection();
        file.getSymbolTableSection();
        file.parser.seek(section.header.section_offset);
        long address = 0;
        while (address < section.header.size) {
            long realAddress = address + section.header.address;
            out.print(String.format("%08X:", realAddress));
            int registers = file.parser.readInt();
            ElfSymbol symbol = file.getELFSymbol(realAddress);
            if (symbol != null && symbol.st_value == realAddress && symbol.section_type == ElfSymbol.STT_FUNC) {
                out.printf("<%8s>", symbol.getName());
            } else {
                out.print("          ");
            }
            int opcode = registers & ((1 << 7) - 1); // 0 - 6
            int rd = registers >> 7 & ((1 << 5) - 1); // 7 - 11
            int funct3 = registers >> 12 & ((1 << 3) - 1); // 12 - 14
            int rs1 = registers >> 15 & ((1 << 5) - 1); // 15 - 19
            int rs2 = registers >> 20 & ((1 << 5) - 1); // 20 - 24
            int funct7 = registers >> 25; // 25 - 31
            int imm110 = registers >> 20 & ((1 << 12) - 1); // 20 - 31
            String uC = "unknown command";
            if (registers == 0b1110011) {
                out.printf("%6s%n", "ecall");
            } else if (imm110 == 0b000000000001 && opcode == 0b1110011) {
                out.printf("%6s%n", "ebreak");
            } else if (opcode == 0b0110111) {
                out.printf("%6s %s, %s%n", "lui", registerToString(rd), Integer.toUnsignedString((registers >>> 12) << 12));
            } else if (opcode == 0b0010111) {
                out.printf("%6s %s, %s%n", "auipc", registerToString(rd), Integer.toUnsignedString((registers >>> 12) << 12));
            } else if (opcode == 0b1101111) {
                int imm = registers >> 12;
                int registersSet = (((imm >>> 9) & ((1 << 10) - 1)) << 1) | (((imm >>> 8) & 1) << 11) | ((imm & ((1 << 8) - 1)) << 12) | (((imm >>> 19) & 1) << 20);
                if ((registersSet & (1 << 20)) != 0) {
                    registersSet = -registersSet & ((1 << 20) - 1);
                }
                out.printf("%6s %s, %d #%s%n", "jal", registerToString(rd), registersSet, symbolToString(realAddress + registersSet));
            } else if (opcode == 0b1100111 && funct3 == 0b000) {
                if ((imm110 & (1 << 11)) != 0) {
                    imm110 = -imm110 & ((1 << 11) - 1);
                }
                out.printf("%6s %s, %s, %d%n", "jalr", registerToString(rd), registerToString(rs1), imm110);
            } else if (opcode == 0b1100011) {
                int registersSet = (((registers >>> 8) & ((1 << 4) - 1)) << 1) | (((registers >>> 25) & ((1 << 6) - 1)) << 5) | (((registers >>> 7) & 1) << 11) | (((registers >>> 31) & 1) << 12);
                if ((registersSet & (1 << 12)) != 0) {
                    registersSet = -registersSet & ((1 << 12) - 1);
                }
                String comand = new String[]{"beq", "bne", uC, uC, "blt", "bge", "bltu", "bgeu"}[funct3];
                out.printf("%6s %s, %s, %d #%s %n", comand, registerToString(rs1), registerToString(rs2), registersSet, symbolToString(realAddress + registersSet));
            } else if (opcode == 0b0000011) {
                String comand = new String[]{"lb", "lh", "lw", uC, "lbu", "lhu", uC, uC}[funct3];
                out.printf("%6s %s, %s, %d%n", comand, registerToString(rd), registerToString(rs1), imm110);
            } else if (opcode == 0b0100011) {
                String comand = new String[]{"sb", "sh", "sw", uC, uC, uC, uC, uC}[funct3];
                int imm = rd | ((imm110 >>> 5) << 5);
                out.printf("%6s %s, %d(%s)%n", comand, registerToString(rs2), imm, registerToString(rs1));
            } else if (opcode == 0b0010011) {
                if (funct3 == 0b001) {
                    out.printf("%6s %s, %s, %d%n", "slli", registerToString(rd), registerToString(rs1), imm110);
                } else if (funct3 == 0b101) {
                    if (funct7 == 0b0100000) {
                        out.printf("%6s %s, %s, %d%n", "srai", registerToString(rd), registerToString(rs1), imm110 & ((1 << 5) - 1));
                    } else {
                        out.printf("%6s %s, %s, %d%n", "srli", registerToString(rd), registerToString(rs1), imm110);
                    }
                } else {
                    String comand = new String[]{"addi", uC, "slti", "sltiu", "xori", uC, "ori", "andi"}[funct3];
                    out.printf("%6s %s, %s, %d%n", comand, registerToString(rd), registerToString(rs1), imm110);
                }
            } else if (opcode == 0b110011) {
                if (funct7 == 0b0100000) {
                    String comand = new String[]{"sub", uC, uC, uC, uC, "sra", uC, uC}[funct3];
                    out.printf("%6s %s, %s, %s%n", comand, registerToString(rd), registerToString(rs2), registerToString(rs1));
                } else if (funct7 == 0b0000000) {
                    String comand = new String[]{"add", "sll", "slt", "sltu", "xor", "srl", "or", "and"}[funct3];
                    out.printf("%6s %s, %s, %s%n", comand, registerToString(rd), registerToString(rs2), registerToString(rs1));
                } else if (funct7 == 0b0000001) {
                    String comand = new String[]{"mul", "mulh", "mulhsu", "mulhu", "div", "divu", "rem", "remu"}[funct3];
                    out.printf("%6s %s, %s, %s%n", comand, registerToString(rd), registerToString(rs2), registerToString(rs1));
                }
            } else {
                out.print(uC + "\n");
            }
            address += 4;
        }
    }
}

public class Main {
    public static void main(String[] args) {
        if (args.length < 1) {
            System.err.println("Please, enter input file");
        } else {
            try {
                OutputStreamWriter output = null;
                try (BufferedInputStream input = new BufferedInputStream(new FileInputStream(args[0]))) {
                    if (args.length > 1) {
                        output = new OutputStreamWriter(new FileOutputStream(args[1]));
                    } else {
                        output = new OutputStreamWriter(System.out);
                    }
                    new Disassembler(ElfFile.from(input)).printDisassembler(output);
                } finally {
                    if (output != null) {
                        output.close();
                    }
                }
            } catch (FileNotFoundException e) {
                System.err.println("File isn't found");
            } catch (IOException e) {
                System.err.println(e.getMessage());
            }
        }
    }
}