import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class HackAssembler {

    private static Integer varCounter = 16;
    private static Integer lineCounter = 0;

    public static void main (String[] args) throws IOException {
        File code = new File(args[0]); // open the provided code file
        FileWriter compiled = new FileWriter(args[0].replace(".asm", ".hack"), false); // create new file
        SymbolTable symbolTable = new SymbolTable(); // initialize the symbol table
        Parser parserFirstPass = new Parser(code); // initialize the parser
        firstPass(parserFirstPass, symbolTable); // run the first pass on the code
        Parser parserSecondPass = new Parser(code); // initialize new parser in order to parse the file from the beginning
        secondPass(parserSecondPass, symbolTable, compiled); // run the second pass on the code
    }

    // first pass on the code in order to save any label instruction
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

    // second pass on the code and convert any line into binary code
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
        }
        compiled.close();
    }
}