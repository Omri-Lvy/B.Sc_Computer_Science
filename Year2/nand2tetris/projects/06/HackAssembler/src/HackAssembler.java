import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class HackAssembler {

    private static Integer varCounter = 16;
    private static Integer lineCounter = 0;

    public static void main (String[] args) throws IOException {
        File code = new File(args[0]);
        FileWriter compiled = new FileWriter(args[0].replace(".asm", ".hack"), false);
        SymbolTable symbolTable = new SymbolTable();
        Parser parserFirstPass = new Parser(code);
        firstPass(parserFirstPass, symbolTable);
        Parser parserSecondPass = new Parser(code);
        secondPass(parserSecondPass, symbolTable, compiled);
    }

    private static void firstPass (Parser parser, SymbolTable symbolTable) {
        while (parser.hasMoreLine()) {
            parser.advance();
            if (parser.instructionType() == InstructionsEnum.L_INSTRUCTIONS.getType()) {
                symbolTable.addEntry(parser.symbol(), lineCounter);
            }
            else {
                lineCounter++;
            }
        }
    }

    private static void secondPass (Parser parser, SymbolTable symbolTable, FileWriter compiled) throws IOException {
        while (parser.hasMoreLine()) {
            parser.advance();
            if (parser.instructionType().equals(InstructionsEnum.A_INSTRUCTIONS.getType())) {
                if (!symbolTable.contains(parser.symbol())) {
                    try {
                        Integer addr = Integer.parseInt(parser.symbol());
                        symbolTable.addEntry(parser.symbol(), addr);
                    } catch (NumberFormatException e) {
                        symbolTable.addEntry(parser.symbol(), varCounter);
                        varCounter++;
                    }
                }
                compiled.write( "0" + String.format("%15s", Integer.toBinaryString(symbolTable.getAddress(parser.symbol()))).replaceAll(" ", "0") + "\n");
            } else if (parser.instructionType().equals(InstructionsEnum.C_INSTRUCTIONS.getType())) {
                compiled.write( "111" + parser.comp() + parser.dest() + parser.jump() + "\n");
            }
//            else if (parser.instructionType().equals(InstructionsEnum.L_INSTRUCTIONS.getType())) {
//                compiled.write( "0" + String.format("%15s", Integer.toBinaryString(symbolTable.getAddress(parser.symbol()))).replaceAll(" ", "0") + "\n");
//            }
        }
        compiled.close();
    }
}