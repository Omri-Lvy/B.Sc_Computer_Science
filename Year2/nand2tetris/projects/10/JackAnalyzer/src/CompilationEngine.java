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
        outFile.write(jackTokenizer.getSymbolString(jackTokenizer.symbol()));
        outFile.write(" </symbol>\n");
        jackTokenizer.advance();
        while (jackTokenizer.tokenType() == TokenTypeEnum.KEYWORD && jackTokenizer.hasMoreTokens()) {
            if (jackTokenizer.keyWord() == KeywordsEnum.STATIC || jackTokenizer.keyWord() == KeywordsEnum.FIELD) {
                compileClassVarDec();
                jackTokenizer.advance();
            } else if (jackTokenizer.keyWord() == KeywordsEnum.CONSTRUCTOR || jackTokenizer.keyWord() == KeywordsEnum.METHOD || jackTokenizer.keyWord() == KeywordsEnum.FUNCTION) {
                compileSubroutine();
                jackTokenizer.advance();
            }
        }
        if (jackTokenizer.tokenType() != TokenTypeEnum.SYMBOL && jackTokenizer.symbol() != '}') {
            throw new Exception("Syntax Error");
        }
        outFile.write("<symbol> ");
        outFile.write(jackTokenizer.getSymbolString(jackTokenizer.symbol()));
        outFile.write(" </symbol>\n");
        outFile.write("</class>\n");
        outFile.close();
    }

    //Compiles a static variable declaration or a field declaration
    public void compileClassVarDec () throws Exception {
        boolean isCommaLast = false;
        if (jackTokenizer.tokenType() != TokenTypeEnum.KEYWORD || (jackTokenizer.keyWord() != KeywordsEnum.FIELD && jackTokenizer.keyWord() != KeywordsEnum.STATIC)) {
            throw new Exception("Syntax Error");
        }
        outFile.write("<classVarDec>\n");
        outFile.write("<keyword> ");
        outFile.write(jackTokenizer.keyWord().getType());
        outFile.write(" </keyword>\n");
        jackTokenizer.advance();
        if (jackTokenizer.tokenType() != TokenTypeEnum.KEYWORD ||
                (jackTokenizer.isReturningIntBoolCharVoid() == null ||
                        jackTokenizer.isReturningIntBoolCharVoid() == KeywordsEnum.VOID)) {
            if (jackTokenizer.tokenType() != TokenTypeEnum.IDENTIFIER) {
                throw new Exception("Syntax Error");
            }
        }
        if (jackTokenizer.tokenType() == TokenTypeEnum.IDENTIFIER) {
            outFile.write("<identifier> ");
            outFile.write(jackTokenizer.identifier());
            outFile.write(" </identifier>\n");
            jackTokenizer.advance();
        } else {
            outFile.write("<keyword> ");
            outFile.write(jackTokenizer.keyWord().getType());
            outFile.write(" </keyword>\n");
            jackTokenizer.advance();
        }
        if (jackTokenizer.tokenType() != TokenTypeEnum.IDENTIFIER) {
            throw new Exception("Syntax Error");
        }
        outFile.write("<identifier> ");
        outFile.write(jackTokenizer.identifier());
        outFile.write(" </identifier>\n");
        jackTokenizer.advance();
        while (jackTokenizer.tokenType() != TokenTypeEnum.SYMBOL || jackTokenizer.symbol() != ';') {
            if (jackTokenizer.tokenType() == TokenTypeEnum.IDENTIFIER) {
                if (isCommaLast) {
                    outFile.write("<identifier> ");
                    outFile.write(jackTokenizer.identifier());
                    outFile.write(" </identifier>\n");
                    isCommaLast = false;
                } else {
                    throw new Exception("Syntax Error");
                }
            } else if (jackTokenizer.tokenType() == TokenTypeEnum.SYMBOL && jackTokenizer.symbol() == ',') {
                if (isCommaLast) {
                    throw new Exception("Syntax Error");
                } else {
                    outFile.write("<symbol> ");
                    outFile.write(jackTokenizer.getSymbolString(jackTokenizer.symbol()));
                    outFile.write(" </symbol>\n");
                    isCommaLast = true;
                }
            } else {
                throw new Exception("Syntax Error");
            }
            jackTokenizer.advance();
        }
        if (jackTokenizer.tokenType() != TokenTypeEnum.SYMBOL || jackTokenizer.symbol() != ';') {
            throw new Exception("Syntax Error");
        }
        outFile.write("<symbol> ");
        outFile.write(jackTokenizer.getSymbolString(jackTokenizer.symbol()));
        outFile.write(" </symbol>\n");
        outFile.write("</classVarDec>\n");
    }

    //Compiles a complete method, function or constructor
    public void compileSubroutine () throws Exception {
        if (jackTokenizer.tokenType() != TokenTypeEnum.KEYWORD ||
                (jackTokenizer.keyWord() != KeywordsEnum.CONSTRUCTOR &&
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
        if (jackTokenizer.getToken().equals(className)) {
            outFile.write("<identifier> ");
            outFile.write(jackTokenizer.identifier());
            outFile.write(" </identifier>\n");
        } else {
            outFile.write("<keyword> ");
            outFile.write(jackTokenizer.keyWord().getType());
            outFile.write(" </keyword>\n");
        }
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
        outFile.write("</subroutineDec>\n");
    }

    //Compiles a parameter list
    public void compileParameterList () throws Exception {
        TokenTypeEnum lastTokenType = TokenTypeEnum.SYMBOL;
        if (jackTokenizer.tokenType() != TokenTypeEnum.SYMBOL || jackTokenizer.symbol() != '(') {
            throw new Exception("Syntax Error");
        }
        outFile.write("<symbol> ");
        outFile.write(jackTokenizer.getSymbolString(jackTokenizer.symbol()));
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
            } else if (jackTokenizer.tokenType() == TokenTypeEnum.SYMBOL) {
                if (lastTokenType != TokenTypeEnum.IDENTIFIER) {
                    throw new Exception("Syntax Error");
                }
                outFile.write("<symbol> ");
                outFile.write(jackTokenizer.getSymbolString(jackTokenizer.symbol()));
                outFile.write(" </symbol>\n");
                lastTokenType = TokenTypeEnum.SYMBOL;
            } else if (jackTokenizer.tokenType() == TokenTypeEnum.KEYWORD) {
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
        outFile.write(jackTokenizer.getSymbolString(jackTokenizer.symbol()));
        outFile.write(" </symbol>\n");

    }

    //Compiles a subroutine's body
    public void compilerSubroutineBody () throws Exception {
        outFile.write("<subroutineBody>\n");
        if (jackTokenizer.tokenType() != TokenTypeEnum.SYMBOL || jackTokenizer.symbol() != '{') {
            throw new Exception("Syntax Error");
        }
        outFile.write("<symbol> ");
        outFile.write(jackTokenizer.getSymbolString(jackTokenizer.symbol()));
        outFile.write(" </symbol>\n");
        jackTokenizer.advance();
        while (jackTokenizer.tokenType() != TokenTypeEnum.SYMBOL || jackTokenizer.symbol() != '}') {
            if (jackTokenizer.tokenType() != TokenTypeEnum.KEYWORD) {
                throw new Exception("Syntax Error");
            }
            if (jackTokenizer.keyWord() == KeywordsEnum.VAR) {
                compileVarDec();
            } else {
                compileStatements();
            }
        }

        if (jackTokenizer.tokenType() != TokenTypeEnum.SYMBOL || jackTokenizer.symbol() != '}') {
            throw new Exception("Syntax Error");
        }
        outFile.write("<symbol> ");
        outFile.write(jackTokenizer.getSymbolString(jackTokenizer.symbol()));
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
                        jackTokenizer.isReturningIntBoolCharVoid() == KeywordsEnum.VOID)) {
            if (jackTokenizer.tokenType() != TokenTypeEnum.IDENTIFIER) {
                throw new Exception("Syntax Error");
            }
        }
        if (jackTokenizer.tokenType() == TokenTypeEnum.IDENTIFIER) {
            outFile.write("<identifier> ");
            outFile.write(jackTokenizer.identifier());
            outFile.write(" </identifier>\n");
            jackTokenizer.advance();
        } else {
            outFile.write("<keyword> ");
            outFile.write(jackTokenizer.keyWord().getType());
            outFile.write(" </keyword>\n");
            jackTokenizer.advance();
        }
        if (jackTokenizer.tokenType() != TokenTypeEnum.IDENTIFIER) {
            throw new Exception("Syntax Error");
        }
        outFile.write("<identifier> ");
        outFile.write(jackTokenizer.identifier());
        outFile.write(" </identifier>\n");
        jackTokenizer.advance();
        while (jackTokenizer.tokenType() != TokenTypeEnum.SYMBOL || jackTokenizer.symbol() != ';') {
            if (jackTokenizer.tokenType() == TokenTypeEnum.IDENTIFIER) {
                if (isCommaLast) {
                    outFile.write("<identifier> ");
                    outFile.write(jackTokenizer.identifier());
                    outFile.write(" </identifier>\n");
                    isCommaLast = false;
                } else {
                    throw new Exception("Syntax Error");
                }
            } else if (jackTokenizer.tokenType() == TokenTypeEnum.SYMBOL && jackTokenizer.symbol() == ',') {
                if (isCommaLast) {
                    throw new Exception("Syntax Error");
                } else {
                    outFile.write("<symbol> ");
                    outFile.write(jackTokenizer.getSymbolString(jackTokenizer.symbol()));
                    outFile.write(" </symbol>\n");
                    isCommaLast = true;
                }
            } else {
                throw new Exception("Syntax Error");
            }
            jackTokenizer.advance();
        }
        if (jackTokenizer.tokenType() != TokenTypeEnum.SYMBOL || jackTokenizer.symbol() != ';') {
            throw new Exception("Syntax Error");
        }
        outFile.write("<symbol> ");
        outFile.write(jackTokenizer.getSymbolString(jackTokenizer.symbol()));
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
                case LET:
                    compileLet();
                    break;
                case IF:
                    compileIf();
                    break;
                case WHILE:
                    compileWhile();
                    break;
                case DO:
                    compileDo();
                    break;
                case RETURN:
                    compileReturn();
                    break;
            }
            if (jackTokenizer.tokenType() != TokenTypeEnum.KEYWORD) {
                jackTokenizer.advance();
            }
        }
        outFile.write("</statements>\n");
    }

    //Compiles a let statement
    public void compileLet () throws Exception {
        outFile.write("<letStatement>\n");
        if (jackTokenizer.tokenType() != TokenTypeEnum.KEYWORD || jackTokenizer.keyWord() != KeywordsEnum.LET) {
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
        if (jackTokenizer.tokenType() != TokenTypeEnum.SYMBOL) {
            throw new Exception("Syntax Error");
        }
        outFile.write("<symbol> ");
        outFile.write(jackTokenizer.getSymbolString(jackTokenizer.symbol()));
        outFile.write(" </symbol>\n");
        jackTokenizer.advance();
        while (jackTokenizer.tokenType() != TokenTypeEnum.SYMBOL || jackTokenizer.symbol() != ';') {
            if (jackTokenizer.tokenType() == TokenTypeEnum.SYMBOL) {
                outFile.write("<symbol> ");
                outFile.write(jackTokenizer.getSymbolString(jackTokenizer.symbol()));
                outFile.write(" </symbol>\n");
                jackTokenizer.advance();
                continue;
            }
            compileExpression();
        }
        if (jackTokenizer.tokenType() != TokenTypeEnum.SYMBOL) {
            throw new Exception("Syntax Error");
        }
        outFile.write("<symbol> ");
        outFile.write(jackTokenizer.getSymbolString(jackTokenizer.symbol()));
        outFile.write(" </symbol>\n");
        outFile.write("</letStatement>\n");
    }

    //Compiles an if statement
    public void compileIf () throws Exception {
        outFile.write("<ifStatement>\n");
        if (jackTokenizer.tokenType() != TokenTypeEnum.KEYWORD || jackTokenizer.keyWord() != KeywordsEnum.IF) {
            throw new Exception("Syntax Error");
        }
        outFile.write("<keyword> ");
        outFile.write(jackTokenizer.keyWord().getType());
        outFile.write(" </keyword>\n");
        jackTokenizer.advance();
        if (jackTokenizer.tokenType() != TokenTypeEnum.SYMBOL || jackTokenizer.symbol() != '(') {
            throw new Exception("Syntax Error");
        }
        outFile.write("<symbol> ");
        outFile.write(jackTokenizer.getSymbolString(jackTokenizer.symbol()));
        outFile.write(" </symbol>\n");
        jackTokenizer.advance();
        compileExpression();
        if (jackTokenizer.tokenType() != TokenTypeEnum.SYMBOL || jackTokenizer.symbol() != ')') {
            throw new Exception("Syntax Error");
        }
        outFile.write("<symbol> ");
        outFile.write(jackTokenizer.getSymbolString(jackTokenizer.symbol()));
        outFile.write(" </symbol>\n");
        jackTokenizer.advance();
        if (jackTokenizer.tokenType() != TokenTypeEnum.SYMBOL || jackTokenizer.symbol() != '{') {
            throw new Exception("Syntax Error");
        }
        outFile.write("<symbol> ");
        outFile.write(jackTokenizer.getSymbolString(jackTokenizer.symbol()));
        outFile.write(" </symbol>\n");
        jackTokenizer.advance();
        compileStatements();
        if (jackTokenizer.tokenType() != TokenTypeEnum.SYMBOL || jackTokenizer.symbol() != '}') {
            throw new Exception("Syntax Error");
        }
        outFile.write("<symbol> ");
        outFile.write(jackTokenizer.getSymbolString(jackTokenizer.symbol()));
        outFile.write(" </symbol>\n");
        jackTokenizer.advance();
        if (jackTokenizer.tokenType() == TokenTypeEnum.KEYWORD && jackTokenizer.keyWord() == KeywordsEnum.ELSE) {
            outFile.write("<keyword> ");
            outFile.write(jackTokenizer.keyWord().getType());
            outFile.write(" </keyword>\n");
            jackTokenizer.advance();
            if (jackTokenizer.tokenType() != TokenTypeEnum.SYMBOL || jackTokenizer.symbol() != '{') {
                throw new Exception("Syntax Error");
            }
            outFile.write("<symbol> ");
            outFile.write(jackTokenizer.getSymbolString(jackTokenizer.symbol()));
            outFile.write(" </symbol>\n");
            jackTokenizer.advance();
            compileStatements();
            if (jackTokenizer.tokenType() != TokenTypeEnum.SYMBOL || jackTokenizer.symbol() != '}') {
                throw new Exception("Syntax Error");
            }
            outFile.write("<symbol> ");
            outFile.write(jackTokenizer.getSymbolString(jackTokenizer.symbol()));
            outFile.write(" </symbol>\n");
            jackTokenizer.advance();
        }
        outFile.write("</ifStatement>\n");
    }

    //Compiles while statement
    public void compileWhile () throws Exception {
        outFile.write("<whileStatement>\n");
        if (jackTokenizer.tokenType() != TokenTypeEnum.KEYWORD || jackTokenizer.keyWord() != KeywordsEnum.WHILE) {
            throw new Exception("Syntax Error");
        }
        outFile.write("<keyword> ");
        outFile.write(jackTokenizer.keyWord().getType());
        outFile.write(" </keyword>\n");
        jackTokenizer.advance();
        if (jackTokenizer.tokenType() != TokenTypeEnum.SYMBOL || jackTokenizer.symbol() != '(') {
            throw new Exception("Syntax Error");
        }
        outFile.write("<symbol> ");
        outFile.write(jackTokenizer.getSymbolString(jackTokenizer.symbol()));
        outFile.write(" </symbol>\n");
        jackTokenizer.advance();
        compileExpression();
        if (jackTokenizer.tokenType() != TokenTypeEnum.SYMBOL || jackTokenizer.symbol() != ')') {
            throw new Exception("Syntax Error");
        }
        outFile.write("<symbol> ");
        outFile.write(jackTokenizer.getSymbolString(jackTokenizer.symbol()));
        outFile.write(" </symbol>\n");
        jackTokenizer.advance();
        if (jackTokenizer.tokenType() != TokenTypeEnum.SYMBOL || jackTokenizer.symbol() != '{') {
            throw new Exception("Syntax Error");
        }
        outFile.write("<symbol> ");
        outFile.write(jackTokenizer.getSymbolString(jackTokenizer.symbol()));
        outFile.write(" </symbol>\n");
        jackTokenizer.advance();
        compileStatements();
        if (jackTokenizer.tokenType() != TokenTypeEnum.SYMBOL || jackTokenizer.symbol() != '}') {
            throw new Exception("Syntax Error");
        }
        outFile.write("<symbol> ");
        outFile.write(jackTokenizer.getSymbolString(jackTokenizer.symbol()));
        outFile.write(" </symbol>\n");
        outFile.write("</whileStatement>\n");
    }

    //Compiles do statement
    public void compileDo () throws Exception {
        outFile.write("<doStatement>\n");
        if (jackTokenizer.tokenType() != TokenTypeEnum.KEYWORD || jackTokenizer.keyWord() != KeywordsEnum.DO) {
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
        while (jackTokenizer.tokenType() == TokenTypeEnum.SYMBOL && jackTokenizer.symbol() == '.') {
            outFile.write("<symbol> ");
            outFile.write(jackTokenizer.getSymbolString(jackTokenizer.symbol()));
            outFile.write(" </symbol>\n");
            jackTokenizer.advance();
            if (jackTokenizer.tokenType() != TokenTypeEnum.IDENTIFIER) {
                throw new Exception("Syntax Error");
            }
            outFile.write("<identifier> ");
            outFile.write(jackTokenizer.identifier());
            outFile.write(" </identifier>\n");
            jackTokenizer.advance();
        }
        if (jackTokenizer.tokenType() != TokenTypeEnum.SYMBOL || jackTokenizer.symbol() != '(') {
            throw new Exception("Syntax Error");
        }
        outFile.write("<symbol> ");
        outFile.write(jackTokenizer.getSymbolString(jackTokenizer.symbol()));
        outFile.write(" </symbol>\n");
        compileExpressionList();
        if (jackTokenizer.tokenType() != TokenTypeEnum.SYMBOL || jackTokenizer.symbol() != ')') {
            throw new Exception("Syntax Error");
        }
        outFile.write("<symbol> ");
        outFile.write(jackTokenizer.getSymbolString(jackTokenizer.symbol()));
        outFile.write(" </symbol>\n");
        jackTokenizer.advance();
        if (jackTokenizer.tokenType() != TokenTypeEnum.SYMBOL || jackTokenizer.symbol() != ';') {
            throw new Exception("Syntax Error");
        }
        outFile.write("<symbol> ");
        outFile.write(jackTokenizer.getSymbolString(jackTokenizer.symbol()));
        outFile.write(" </symbol>\n");
        outFile.write("</doStatement>\n");
    }

    //Compiles a return statement
    public void compileReturn () throws Exception {
        outFile.write("<returnStatement>\n");
        if (jackTokenizer.tokenType() != TokenTypeEnum.KEYWORD || jackTokenizer.keyWord() != KeywordsEnum.RETURN) {
            throw new Exception("Syntax Error");
        }
        outFile.write("<keyword> ");
        outFile.write(jackTokenizer.keyWord().getType());
        outFile.write(" </keyword>\n");
        jackTokenizer.advance();
        if (jackTokenizer.tokenType() != TokenTypeEnum.SYMBOL && jackTokenizer.symbol() != ';') {
            compileExpression();
        }
        if (jackTokenizer.tokenType() != TokenTypeEnum.SYMBOL && jackTokenizer.symbol() != ';') {
            throw new Exception("Syntax Error");
        }
        outFile.write("<symbol> ");
        outFile.write(jackTokenizer.getSymbolString(jackTokenizer.symbol()));
        outFile.write(" </symbol>\n");
        outFile.write("</returnStatement>\n");
    }

    //Compiles an expression
    public void compileExpression () throws Exception {
        outFile.write("<expression>\n");
        while (jackTokenizer.tokenType() != TokenTypeEnum.SYMBOL || (jackTokenizer.symbol() != ')' && jackTokenizer.symbol() != ']')) {
            compileTerm();
            if (jackTokenizer.tokenType() == TokenTypeEnum.SYMBOL && jackTokenizer.symbol() != ';' && jackTokenizer.symbol() != ',' && jackTokenizer.symbol() != ')' && jackTokenizer.symbol() != ']') {
                outFile.write("<symbol> ");
                outFile.write(jackTokenizer.getSymbolString(jackTokenizer.symbol()));
                outFile.write(" </symbol>\n");
                jackTokenizer.advance();
                compileTerm();
            }
            if (jackTokenizer.symbol() == ';' || jackTokenizer.symbol() == ',') {
                break;
            }
        }
        outFile.write("</expression>\n");
    }

    //Compiles a term
    public void compileTerm () throws Exception {
        outFile.write("<term>\n");
        if (jackTokenizer.tokenType() == TokenTypeEnum.SYMBOL) {
            if (jackTokenizer.symbol() == '(') {
                outFile.write("<symbol> ");
                outFile.write(jackTokenizer.getSymbolString(jackTokenizer.symbol()));
                outFile.write(" </symbol>\n");
                jackTokenizer.advance();
                compileExpression();
                if (jackTokenizer.tokenType() != TokenTypeEnum.SYMBOL) {
                    throw new Exception("Syntax Error");
                }
                outFile.write("<symbol> ");
                outFile.write(jackTokenizer.getSymbolString(jackTokenizer.symbol()));
                outFile.write(" </symbol>\n");
                jackTokenizer.advance();

            } else if (jackTokenizer.tokenType() == TokenTypeEnum.SYMBOL && jackTokenizer.symbol() != ';' && jackTokenizer.symbol() != ',' && jackTokenizer.symbol() != ')') {
                outFile.write("<symbol> ");
                outFile.write(jackTokenizer.getSymbolString(jackTokenizer.symbol()));
                outFile.write(" </symbol>\n");
                jackTokenizer.advance();
                compileTerm();
            }
            ;
        } else if (jackTokenizer.tokenType() == TokenTypeEnum.KEYWORD) {
            if (jackTokenizer.keyWord() == KeywordsEnum.TRUE || jackTokenizer.keyWord() == KeywordsEnum.FALSE || jackTokenizer.keyWord() == KeywordsEnum.THIS || jackTokenizer.keyWord() == KeywordsEnum.NULL) {
                outFile.write("<keyword> ");
                outFile.write(jackTokenizer.keyWord().getType());
                outFile.write(" </keyword>\n");
                jackTokenizer.advance();
            }

        } else if (jackTokenizer.tokenType() == TokenTypeEnum.IDENTIFIER) {
            outFile.write("<identifier> ");
            outFile.write(jackTokenizer.identifier());
            outFile.write(" </identifier>\n");
            jackTokenizer.advance();
            while (jackTokenizer.tokenType() == TokenTypeEnum.SYMBOL && (jackTokenizer.symbol() == '.' || jackTokenizer.symbol() == '(' || jackTokenizer.symbol() == '[')) {
                if (jackTokenizer.symbol() == '.') {
                    outFile.write("<symbol> ");
                    outFile.write(jackTokenizer.getSymbolString(jackTokenizer.symbol()));
                    outFile.write(" </symbol>\n");
                    jackTokenizer.advance();
                    if (jackTokenizer.tokenType() != TokenTypeEnum.IDENTIFIER) {
                        throw new Exception("Syntax Error");
                    }
                    outFile.write("<identifier> ");
                    outFile.write(jackTokenizer.identifier());
                    outFile.write(" </identifier>\n");
                    jackTokenizer.advance();
                } else if (jackTokenizer.symbol() == '(' || jackTokenizer.symbol() == '[') {
                    outFile.write("<symbol> ");
                    outFile.write(jackTokenizer.getSymbolString(jackTokenizer.symbol()));
                    outFile.write(" </symbol>\n");
                    if (jackTokenizer.symbol() == '[') {
                        jackTokenizer.advance();
                        compileExpression();
                    } else {
                        compileExpressionList();
                    }
                    if (jackTokenizer.tokenType() != TokenTypeEnum.SYMBOL || (jackTokenizer.symbol() != ')' && jackTokenizer.symbol() != ']')) {
                        throw new Exception("Syntax Error");
                    }
                    outFile.write("<symbol> ");
                    outFile.write(jackTokenizer.getSymbolString(jackTokenizer.symbol()));
                    outFile.write(" </symbol>\n");
                    jackTokenizer.advance();

                }
            }
        } else if (jackTokenizer.tokenType() == TokenTypeEnum.INT_CONST) {
            outFile.write("<integerConstant> ");
            outFile.write("" + jackTokenizer.intVal());
            outFile.write(" </integerConstant>\n");
            jackTokenizer.advance();
            if (jackTokenizer.tokenType() == TokenTypeEnum.SYMBOL && jackTokenizer.isOp()) {
                outFile.write("<symbol> ");
                outFile.write(jackTokenizer.getSymbolString(jackTokenizer.symbol()));
                outFile.write(" </symbol>\n");

                jackTokenizer.advance();
                compileTerm();
            }
        } else if (jackTokenizer.tokenType() == TokenTypeEnum.STRING_CONST) {
            outFile.write("<stringConstant> ");
            outFile.write(jackTokenizer.stringVal());
            outFile.write(" </stringConstant>\n");
            jackTokenizer.advance();
        }
        outFile.write("</term>\n");
    }

    //Compiles a comma-separated list of expression
    public void compileExpressionList () throws Exception {
        outFile.write("<expressionList>\n");
        boolean isCommaLast = false;
        jackTokenizer.advance();
        while (jackTokenizer.tokenType() != TokenTypeEnum.SYMBOL || jackTokenizer.symbol() != ')') {
            compileExpression();
            isCommaLast = false;
            if (jackTokenizer.symbol() == ')') {
                outFile.write("</expressionList>\n");
                return;
            }
            if (jackTokenizer.tokenType() == TokenTypeEnum.SYMBOL && jackTokenizer.symbol() == ',') {
                if (isCommaLast) {
                    throw new Exception("Syntax Error");
                }
                outFile.write("<symbol> ");
                outFile.write(jackTokenizer.getSymbolString(jackTokenizer.symbol()));
                outFile.write(" </symbol>\n");
                isCommaLast = true;
                jackTokenizer.advance();
                if (jackTokenizer.tokenType() == TokenTypeEnum.SYMBOL && jackTokenizer.symbol() == ',') {
                    if (isCommaLast) {
                        throw new Exception("Syntax Error");
                    }
                }
            }
        }
        if (jackTokenizer.symbol() == ')') {
            outFile.write("</expressionList>\n");
        } else {
            throw new Exception("Syntax Error");
        }
    }

}
