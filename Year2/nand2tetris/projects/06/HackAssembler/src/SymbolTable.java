import java.util.HashMap;

public class SymbolTable extends HashMap<String, Integer>{

    private HashMap<String, Integer> symbolTable;

//   construct symbol table
    public SymbolTable () {
        this.symbolTable = new HashMap<String, Integer>();
        for (int i = 0; i < 16; i++) {
            this.symbolTable.put('R' + String.valueOf(i), i);
        }
        this.symbolTable.put("SCREEN", 16384);
        this.symbolTable.put("KBD", 24576);
        this.symbolTable.put("SP", 0);
        this.symbolTable.put("LCL", 1);
        this.symbolTable.put("ARG", 2);
        this.symbolTable.put("THIS", 3);
        this.symbolTable.put("THAT", 4);
    }

    // adding new value to the symbol value
    public void addEntry (String symbol, Integer value) {
        symbolTable.put(symbol, value);
    }

    // check if given value is in the symbol table
    public boolean contains (String symbol) {
        return symbolTable.containsKey(symbol);
    }

    // return the value of a given key on the symbol table
    public Integer getAddress (String symbol) {
        return symbolTable.get(symbol);
    }
}
