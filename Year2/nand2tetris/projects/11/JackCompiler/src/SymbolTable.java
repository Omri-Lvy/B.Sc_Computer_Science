import java.util.Hashtable;

public class SymbolTable {

    private int staticCounter;
    private int fieldCounter;
    private int varCounter;
    private int argsCounter;

    private Hashtable<String,Symbol> table;

    public SymbolTable() {
        staticCounter = 0;
        fieldCounter = 0;
        varCounter = 0;
        argsCounter = 0;
        table = new Hashtable<String,Symbol>();
    }

    public void reset() {
        staticCounter = 0;
        fieldCounter = 0;
        varCounter = 0;
        argsCounter = 0;
        table.clear();
    }

    public void define (String name,String type, String kind) {
        int index = 0;
        SymbolKindEnum symbolType = SymbolKindEnum.NONE;
        if (kind.equals(SymbolKindEnum.STATIC.getKind())) {
            index = ++staticCounter;
            symbolType = SymbolKindEnum.STATIC;
        }
        else if (kind.equals(SymbolKindEnum.FIELD.getKind())) {
            index = ++fieldCounter;
            symbolType = SymbolKindEnum.FIELD;
        }
        else if (kind.equals(SymbolKindEnum.VAR.getKind())) {
            index = ++varCounter;
            symbolType = SymbolKindEnum.VAR;
        }
        else if (kind.equals(SymbolKindEnum.ARG.getKind())) {
            index = ++argsCounter;
            symbolType = SymbolKindEnum.ARG;
        }
        Symbol newSymbol = new Symbol(name,type,symbolType,index);
        table.put(name,newSymbol);
    }

    public int varCount(String kind) {
        if (kind.equals(SymbolKindEnum.STATIC.getKind())) { return staticCounter; }
        else if (kind.equals(SymbolKindEnum.FIELD.getKind())) { return fieldCounter; }
        else if (kind.equals(SymbolKindEnum.VAR.getKind())) { return varCounter; }
        else if (kind.equals(SymbolKindEnum.ARG.getKind())) { return argsCounter; }
        return 0;
    }

    public String kindOf(String name) {
        if (table.get(name) != null) {
            return table.get(name).kind();
        }
        return null;
    }

    public String typeOf(String name) {
        if (table.get(name) != null) {
            return table.get(name).type();
        }
        return null;
    }

    public int indexOf(String name) {
        if (table.get(name) != null) {
            return table.get(name).index();
        }
        return -1;
    }

}
