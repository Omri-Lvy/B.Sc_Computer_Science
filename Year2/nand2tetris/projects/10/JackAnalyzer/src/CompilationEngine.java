import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;

public class CompilationEngine {


    private JackTokenizer jackTokenizer;
    private FileWriter outFile;
    private String className;

    //Creates a new compilation engine with the given input and output
    public CompilationEngine (File input, FileWriter output) throws FileNotFoundException {
        jackTokenizer = new JackTokenizer(input);

        outFile = output;
    }

    //Compiles  a complete class
    public void compileClass () throws Exception {
        jackTokenizer.advance();
        outFile.write("<class>\n");
        if (jackTokenizer.tokenType() != TokenTypeEnum.KEYWORD || !jackTokenizer.keyWord().getType().equals("class")) {
            throw new Exception("Syntax Error");
        }
        outFile.write("<keyword> ");
        outFile.write(jackTokenizer.keyWord().getType());
        outFile.write(" </keyword>\n");
        jackTokenizer.advance();
        if (jackTokenizer.tokenType() != TokenTypeEnum.IDENTIFIER) {
            throw new Exception("Syntax Error");
        }
        outFile.write("<identifier> ");
        outFile.write(jackTokenizer.identifier());
        outFile.write(" </identifier>\n");
        className = jackTokenizer.identifier();
        jackTokenizer.advance();
        if (jackTokenizer.tokenType() != TokenTypeEnum.SYMBOL || jackTokenizer.symbol() != '{') {
            throw new Exception("Syntax Error");
        }
        outFile.write("<symbol> ");
        outFile.write(jackTokenizer.symbol());
        outFile.write(" </symbol>\n");
        jackTokenizer.advance();
        while (jackTokenizer.tokenType() == TokenTypeEnum.KEYWORD) {
            if (jackTokenizer.keyWord() == KeywordsEnum.STATIC || jackTokenizer.keyWord() == KeywordsEnum.FIELD) {
                compileClassVarDec();
            } else if (jackTokenizer.keyWord() == KeywordsEnum.CONSTRUCTOR || jackTokenizer.keyWord() == KeywordsEnum.METHOD || jackTokenizer.keyWord() == KeywordsEnum.FUNCTION) {
                compileSubroutine();
            }
            jackTokenizer.advance();
        }
        if (jackTokenizer.tokenType() != TokenTypeEnum.SYMBOL && jackTokenizer.symbol() != '}') {
            throw new Exception("Syntax Error");
        }
        outFile.write("<symbol> ");
        outFile.write(jackTokenizer.symbol());
        outFile.write(" </symbol>\n");
        outFile.write("</class>\n");
        outFile.close();
    }

    //Compiles a static variable declaration or a field declaration
    public void compileClassVarDec () throws Exception {
        boolean isCommaLast = false;
        if (jackTokenizer.tokenType() != TokenTypeEnum.KEYWORD || (jackTokenizer.keyWord() != KeywordsEnum.FIELD  && jackTokenizer.keyWord() != KeywordsEnum.STATIC)) {
            throw new Exception("Syntax Error");
        }
        outFile.write("<classVarDec>\n");
        outFile.write("<keyword> ");
        outFile.write(jackTokenizer.keyWord().getType());
        outFile.write(" </keyword>\n");
        jackTokenizer.advance();
        if (jackTokenizer.tokenType() != TokenTypeEnum.KEYWORD ||
                (jackTokenizer.isReturningIntBoolCharVoid() == null ||
                jackTokenizer.isReturningIntBoolCharVoid() == KeywordsEnum.VOID)
                && !jackTokenizer.getToken().equals(className)) {
            throw new Exception("Syntax Error");
        }
        outFile.write("<keyword> ");
        outFile.write(jackTokenizer.keyWord().getType());
        outFile.write(" </keyword>\n");
        jackTokenizer.advance();
        if (jackTokenizer.tokenType() != TokenTypeEnum.IDENTIFIER) {
            throw new Exception("Syntax Error");
        }
        outFile.write("<identifier> ");
        outFile.write(jackTokenizer.identifier());
        outFile.write(" </identifier>\n");
        jackTokenizer.advance();
        while (jackTokenizer.tokenType() != TokenTypeEnum.SYMBOL || jackTokenizer.symbol()!=';') {
            if (jackTokenizer.tokenType() == TokenTypeEnum.IDENTIFIER) {
                if (isCommaLast) {
                    outFile.write("<identifier> ");
                    outFile.write(jackTokenizer.identifier());
                    outFile.write(" </identifier>\n");
                    isCommaLast = false;
                }
                else {
                    throw new Exception("Syntax Error");
                }
            }
            else if (jackTokenizer.tokenType() == TokenTypeEnum.SYMBOL && jackTokenizer.symbol() == ',') {
                if (isCommaLast) {
                    throw new Exception("Syntax Error");
                }
                else {
                    outFile.write("<symbol> ");
                    outFile.write(jackTokenizer.symbol());
                    outFile.write(" </symbol>\n");
                    isCommaLast = true;
                }
            }
            else {
                throw new Exception("Syntax Error");
            }
            jackTokenizer.advance();
        }
        if (jackTokenizer.tokenType() != TokenTypeEnum.SYMBOL || jackTokenizer.symbol()!=';') {
            throw new Exception("Syntax Error");
        }
        outFile.write("<symbol> ");
        outFile.write(jackTokenizer.symbol());
        outFile.write(" </symbol>\n");
        outFile.write("</classVarDec>\n");
    }

    //Compiles a complete method, function or constructor
    public void compileSubroutine () throws Exception {
        if (jackTokenizer.tokenType() != TokenTypeEnum.KEYWORD ||
            (jackTokenizer.keyWord() != KeywordsEnum.CONSTRUCTOR  &&
            jackTokenizer.keyWord() != KeywordsEnum.METHOD &&
            jackTokenizer.keyWord() != KeywordsEnum.FUNCTION)) {
            throw new Exception("Syntax Error");
        }
        outFile.write("<subroutineDec>\n");
        outFile.write("<keyword> ");
        outFile.write(jackTokenizer.keyWord().getType());
        outFile.write(" </keyword>\n");
        jackTokenizer.advance();
        if (jackTokenizer.tokenType() != TokenTypeEnum.KEYWORD &&
            jackTokenizer.isReturningIntBoolCharVoid() == null
            && !jackTokenizer.getToken().equals(className)) {
            throw new Exception("Syntax Error");
        }
        outFile.write("<keyword> ");
        if (jackTokenizer.getToken().equals(className)) {
            outFile.write(jackTokenizer.getToken());
        }
        else {
            outFile.write(jackTokenizer.keyWord().getType());
        }
        outFile.write(" </keyword>\n");
        jackTokenizer.advance();
        if (jackTokenizer.tokenType() != TokenTypeEnum.IDENTIFIER) {
            throw new Exception("Syntax Error");
        }
        outFile.write("<identifier> ");
        outFile.write(jackTokenizer.identifier());
        outFile.write(" </identifier>\n");
        jackTokenizer.advance();
        compileParameterList();
        jackTokenizer.advance();
        compilerSubroutineBody();
        jackTokenizer.advance();
        outFile.write("</subroutineDec>\n");
    }

    //Compiles a parameter list
    public void compileParameterList () throws Exception {
        TokenTypeEnum lastTokenType = TokenTypeEnum.SYMBOL;
        if (jackTokenizer.tokenType() != TokenTypeEnum.SYMBOL || jackTokenizer.symbol() != '(') {
            throw new Exception("Syntax Error");
        }
        outFile.write("<symbol> ");
        outFile.write(jackTokenizer.symbol());
        outFile.write(" </symbol>\n");
        outFile.write("<parameterList>\n");
        jackTokenizer.advance();
        while ((jackTokenizer.tokenType() == TokenTypeEnum.SYMBOL && jackTokenizer.symbol() == ',') ||
                jackTokenizer.tokenType() == TokenTypeEnum.IDENTIFIER ||
                (jackTokenizer.tokenType() == TokenTypeEnum.KEYWORD && jackTokenizer.isReturningIntBoolCharVoid() != null && jackTokenizer.isReturningIntBoolCharVoid() != KeywordsEnum.VOID)) {
            if (jackTokenizer.tokenType() == TokenTypeEnum.IDENTIFIER) {
                if (lastTokenType != TokenTypeEnum.KEYWORD) {
                    throw new Exception("Syntax Error");
                }
                outFile.write("<identifier> ");
                outFile.write(jackTokenizer.identifier());
                outFile.write(" </identifier>\n");
                lastTokenType = TokenTypeEnum.IDENTIFIER;
            }
            else if (jackTokenizer.tokenType() == TokenTypeEnum.SYMBOL) {
                if (lastTokenType != TokenTypeEnum.IDENTIFIER) {
                    throw new Exception("Syntax Error");
                }
                outFile.write("<symbol> ");
                outFile.write(jackTokenizer.symbol());
                outFile.write(" </symbol>\n");
                lastTokenType = TokenTypeEnum.SYMBOL;
            }
            else if (jackTokenizer.tokenType() == TokenTypeEnum.KEYWORD) {
                if (lastTokenType != TokenTypeEnum.SYMBOL) {
                    throw new Exception("Syntax Error");
                }
                outFile.write("<keyword> ");
                outFile.write(jackTokenizer.keyWord().getType());
                outFile.write(" </keyword>\n");
                lastTokenType = TokenTypeEnum.KEYWORD;
            }
            jackTokenizer.advance();
        }
        outFile.write("</parameterList>\n");
        if (jackTokenizer.tokenType() != TokenTypeEnum.SYMBOL || jackTokenizer.symbol() != ')') {
            throw new Exception("Syntax Error");
        }
        outFile.write("<symbol> ");
        outFile.write(jackTokenizer.symbol());
        outFile.write(" </symbol>\n");

    }

    //Compiles a subroutine's body
    public void compilerSubroutineBody () throws Exception {
        outFile.write("<subroutineBody>\n");
        if (jackTokenizer.tokenType() != TokenTypeEnum.SYMBOL || jackTokenizer.symbol() != '{') {
            throw new Exception("Syntax Error");
        }
        outFile.write("<symbol> ");
        outFile.write(jackTokenizer.symbol());
        outFile.write(" </symbol>\n");
        jackTokenizer.advance();
        while (jackTokenizer.tokenType() != TokenTypeEnum.SYMBOL || jackTokenizer.symbol() != '}') {
            if (jackTokenizer.tokenType() != TokenTypeEnum.KEYWORD) {
                throw new Exception("Syntax Error");
            }
            if (jackTokenizer.keyWord() == KeywordsEnum.VAR) {
                compileVarDec();
            }
            else {
                compileStatements();
            }
        }

        if (jackTokenizer.tokenType() != TokenTypeEnum.SYMBOL || jackTokenizer.symbol() != '}') {
            throw new Exception("Syntax Error");
        }
        outFile.write("<symbol> ");
        outFile.write(jackTokenizer.symbol());
        outFile.write(" </symbol>\n");
        outFile.write("</subroutineBody>\n");
    }

    //Compiles var declaration
    public void compileVarDec () throws Exception {
        outFile.write("<varDec>\n");
        boolean isCommaLast = false;
        if (jackTokenizer.tokenType() != TokenTypeEnum.KEYWORD) {
            throw new Exception("Syntax Error");
        }
        outFile.write("<keyword> ");
        outFile.write(jackTokenizer.keyWord().getType());
        outFile.write(" </keyword>\n");
        jackTokenizer.advance();
        if (jackTokenizer.tokenType() != TokenTypeEnum.KEYWORD ||
                (jackTokenizer.isReturningIntBoolCharVoid() == null ||
                        jackTokenizer.isReturningIntBoolCharVoid() == KeywordsEnum.VOID)
                        && !jackTokenizer.getToken().equals(className)) {
            throw new Exception("Syntax Error");
        }
        outFile.write("<keyword> ");
        outFile.write(jackTokenizer.keyWord().getType());
        outFile.write(" </keyword>\n");
        jackTokenizer.advance();
        if (jackTokenizer.tokenType() != TokenTypeEnum.IDENTIFIER) {
            throw new Exception("Syntax Error");
        }
        outFile.write("<identifier> ");
        outFile.write(jackTokenizer.identifier());
        outFile.write(" </identifier>\n");
        jackTokenizer.advance();
        while (jackTokenizer.tokenType() != TokenTypeEnum.SYMBOL || jackTokenizer.symbol()!=';') {
            if (jackTokenizer.tokenType() == TokenTypeEnum.IDENTIFIER) {
                if (isCommaLast) {
                    outFile.write("<identifier> ");
                    outFile.write(jackTokenizer.identifier());
                    outFile.write(" </identifier>\n");
                    isCommaLast = false;
                }
                else {
                    throw new Exception("Syntax Error");
                }
            }
            else if (jackTokenizer.tokenType() == TokenTypeEnum.SYMBOL && jackTokenizer.symbol() == ',') {
                if (isCommaLast) {
                    throw new Exception("Syntax Error");
                }
                else {
                    outFile.write("<symbol> ");
                    outFile.write(jackTokenizer.symbol());
                    outFile.write(" </symbol>\n");
                    isCommaLast = true;
                }
            }
            else {
                throw new Exception("Syntax Error");
            }
            jackTokenizer.advance();
        }
        if (jackTokenizer.tokenType() != TokenTypeEnum.SYMBOL || jackTokenizer.symbol()!=';') {
            throw new Exception("Syntax Error");
        }
        outFile.write("<symbol> ");
        outFile.write(jackTokenizer.symbol());
        outFile.write(" </symbol>\n");
        outFile.write("</varDec>\n");
        jackTokenizer.advance();
    }

    //Compiles a sequence of statements
    public void compileStatements () throws Exception {
        outFile.write("<statements>\n");
        while (jackTokenizer.tokenType() != TokenTypeEnum.SYMBOL && jackTokenizer.symbol() != '}') {
            if (jackTokenizer.tokenType() != TokenTypeEnum.KEYWORD) {
                throw new Exception("Syntax Error");
            }
            switch (jackTokenizer.keyWord()) {
                case LET -> {
                    compileLet();
                    break;
                }
                case IF -> compileIf();
                case WHILE -> compileWhile();
                case DO -> compileDo();
                case RETURN -> compileReturn();
            }
        }



        jackTokenizer.advance();
    }

    //Compiles a let statement
    public void compileLet () {
    }

    //Compiles an if statement
    public void compileIf () {
    }

    //Compiles while statement
    public void compileWhile () {
    }

    //Compiles do statement
    public void compileDo () {
    }

    //Compiles a return statement
    public void compileReturn () {
    }

    //Compiles an expression
    public void compileExpression () {
    }

    //Compiles a term
    public void compileTerm () {
    }

    //Compiles a comma-separated list of expression
    public void compileExpressionList () {
    }


}
