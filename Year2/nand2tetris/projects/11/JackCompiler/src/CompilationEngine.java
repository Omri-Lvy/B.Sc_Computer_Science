import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;

public class CompilationEngine {

    private JackTokenizer jackTokenizer;
    private VMWriter writer;
    private SymbolTable classTable;
    private SymbolTable subRutineTable;
    private String className;

    //Creates a new compilation engine with the given input and output
    public CompilationEngine (File input, FileWriter output) throws IOException {
        jackTokenizer = new JackTokenizer(input);
        writer = new VMWriter(output);
        classTable = new SymbolTable();
        subRutineTable = new SymbolTable();
    }

    //Compiles  a complete class
    public void compileClass () throws Exception {
        jackTokenizer.advance();
        if (jackTokenizer.tokenType() != TokenTypeEnum.KEYWORD || !jackTokenizer.keyWord().getType().equals("class")) {
            throw new Exception("Syntax Error");
        }
        jackTokenizer.advance();
        if (jackTokenizer.tokenType() != TokenTypeEnum.IDENTIFIER) {
            throw new Exception("Syntax Error");
        }
        className = jackTokenizer.identifier();
        jackTokenizer.advance();
        if (jackTokenizer.tokenType() != TokenTypeEnum.SYMBOL || jackTokenizer.symbol() != '{') {
            throw new Exception("Syntax Error");
        }
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
        writer.close();
    }

    //Compiles a static variable declaration or a field declaration
    public void compileClassVarDec () throws Exception {
        boolean isCommaLast = false;
        String kind;
        String type;
        String name;
        if (jackTokenizer.tokenType() != TokenTypeEnum.KEYWORD || (jackTokenizer.keyWord() != KeywordsEnum.FIELD && jackTokenizer.keyWord() != KeywordsEnum.STATIC)) {
            throw new Exception("Syntax Error");
        }
        kind = jackTokenizer.keyWord().getType();
        jackTokenizer.advance();
        if (jackTokenizer.tokenType() == TokenTypeEnum.IDENTIFIER) {
            type = jackTokenizer.identifier();
            jackTokenizer.advance();
        } else {
            type = jackTokenizer.keyWord().getType();
            jackTokenizer.advance();
        }
        if (jackTokenizer.tokenType() != TokenTypeEnum.IDENTIFIER) {
            throw new Exception("Syntax Error");
        }
        name = jackTokenizer.identifier();
        classTable.define(name,type,kind);
        jackTokenizer.advance();
        while (jackTokenizer.tokenType() != TokenTypeEnum.SYMBOL || jackTokenizer.symbol() != ';') {
            if (jackTokenizer.tokenType() == TokenTypeEnum.IDENTIFIER) {
                if (isCommaLast) {
                    name = jackTokenizer.identifier();
                    classTable.define(name, type, kind);
                    isCommaLast = false;
                } else {
                    throw new Exception("Syntax Error");
                }
            } else if (jackTokenizer.tokenType() == TokenTypeEnum.SYMBOL && jackTokenizer.symbol() == ',') {
                if (isCommaLast) {
                    throw new Exception("Syntax Error");
                } else {
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
    }

    //Compiles a complete method, function or constructor
    public void compileSubroutine () throws Exception {
        String functionName;
        KeywordsEnum funcType;
        if (jackTokenizer.tokenType() != TokenTypeEnum.KEYWORD ||
                (jackTokenizer.keyWord() != KeywordsEnum.CONSTRUCTOR &&
                        jackTokenizer.keyWord() != KeywordsEnum.METHOD &&
                        jackTokenizer.keyWord() != KeywordsEnum.FUNCTION)) {
            throw new Exception("Syntax Error");
        }
        subRutineTable.reset();
        funcType = jackTokenizer.keyWord();
        jackTokenizer.advance();
        if (jackTokenizer.tokenType() != TokenTypeEnum.KEYWORD &&
                jackTokenizer.isReturningIntBoolCharVoid() == null
                && !jackTokenizer.getToken().equals(className)) {
            throw new Exception("Syntax Error");
        }
        jackTokenizer.advance();
        functionName = jackTokenizer.identifier();
        jackTokenizer.advance();

        compileParameterList();

        if (funcType == KeywordsEnum.CONSTRUCTOR) {
            writer.writeFunction(className+"."+functionName, classTable.varCount("field") + classTable.varCount("static"));
            writer.writePush("constant",classTable.varCount("field"));
            writer.writeCall("Memory.alloc",1);
            writer.writePop("pointer",0);
        } else {
            if (funcType == KeywordsEnum.METHOD) {
                subRutineTable.define("this", className, SymbolKindEnum.ARG.getKind());
            }
            writer.writeFunction(className+"." + functionName,classTable.varCount("field") + classTable.varCount("static"));
            writer.writePush("argument",0);
            writer.writePop("pointer",0);
        }

        compileSubroutineBody();
    }

    //Compiles a parameter list
    public int compileParameterList () throws Exception {
        String value;
        String varType;
        boolean stillHasParams = true;
        int parameterNum = 0;
        if (jackTokenizer.tokenType() != TokenTypeEnum.SYMBOL || jackTokenizer.symbol() != '(') {
            throw new Exception("Syntax Error");
        }
        jackTokenizer.advance();

        value = jackTokenizer.getToken();
        while (stillHasParams && jackTokenizer.symbol() != ')'){
            parameterNum++;
            if (jackTokenizer.tokenType() != TokenTypeEnum.KEYWORD && jackTokenizer.tokenType() != TokenTypeEnum.IDENTIFIER) {
                throw new Exception("Syntax Error" + jackTokenizer.getToken());
            }
            
            varType = jackTokenizer.tokenType().getType();
            if (jackTokenizer.tokenType() == TokenTypeEnum.KEYWORD) {
                value = jackTokenizer.getToken();
				if (!(value).matches("int|char|boolean")) {
					throw new RuntimeException(
							"Syntax Error" + value);
				}
			} else if (jackTokenizer.tokenType() == TokenTypeEnum.IDENTIFIER) {
                value = jackTokenizer.getToken();
			} else {
				throw new RuntimeException("parameter type error!");
			}

            jackTokenizer.advance();
            subRutineTable.define(value, varType, "argument"); // XXXXX make sure im inserting correctly
            jackTokenizer.advance();
            if (jackTokenizer.tokenType() != TokenTypeEnum.SYMBOL || jackTokenizer.symbol() != ',') {
                stillHasParams = false;
            } else {
                jackTokenizer.advance();
            }
        }

        // im getting the identifier here for some reason XXXXX
        if (jackTokenizer.tokenType() != TokenTypeEnum.SYMBOL || jackTokenizer.symbol() != ')') {
            throw new RuntimeException("Syntax Error" + jackTokenizer.tokenType().getType() + value);
        }
        jackTokenizer.advance();
        return parameterNum;
    }

    //Compiles a subroutine's body
    public void compileSubroutineBody () throws Exception {
        if (jackTokenizer.tokenType() != TokenTypeEnum.SYMBOL || jackTokenizer.symbol() != '{') {
            throw new Exception("Syntax Error");
        };
        jackTokenizer.advance();
        while (jackTokenizer.tokenType() != TokenTypeEnum.SYMBOL || jackTokenizer.symbol() != '}') {
            if (jackTokenizer.tokenType() != TokenTypeEnum.KEYWORD) {
                throw new Exception("Syntax Error");
            }
            if (jackTokenizer.keyWord() == KeywordsEnum.VAR) {
                compileVarDec();
            } else if ((jackTokenizer.getToken()).matches("let|if|while|do|return")) {
                compileStatements();
            }
        }

        if (jackTokenizer.tokenType() != TokenTypeEnum.SYMBOL || jackTokenizer.symbol() != '}') {
            throw new RuntimeException("Syntax Error");
        }
    }

    //Compiles var declaration
    public void compileVarDec () throws Exception {
        String varName;
        String varType;
        if (jackTokenizer.tokenType() != TokenTypeEnum.KEYWORD) {
            throw new Exception("Syntax Error");
        }
        jackTokenizer.advance();
        if (jackTokenizer.tokenType() != TokenTypeEnum.KEYWORD ||
                (jackTokenizer.isReturningIntBoolCharVoid() == null ||
                        jackTokenizer.isReturningIntBoolCharVoid() == KeywordsEnum.VOID)) {
            if (jackTokenizer.tokenType() != TokenTypeEnum.IDENTIFIER) {
                throw new Exception("Syntax Error");
            }
        }
        if (jackTokenizer.tokenType() == TokenTypeEnum.IDENTIFIER) {
            varType = jackTokenizer.identifier();
        }
        else {
            varType = jackTokenizer.keyWord().getType();
        }
        jackTokenizer.advance();
        if (jackTokenizer.tokenType() != TokenTypeEnum.IDENTIFIER) {
            throw new Exception("Syntax Error");
        }
        varName = jackTokenizer.identifier();
        jackTokenizer.advance();
        subRutineTable.define(varName,varType,"local");
        while (jackTokenizer.tokenType() != TokenTypeEnum.SYMBOL || jackTokenizer.symbol() != ';') {
            if (jackTokenizer.tokenType() != TokenTypeEnum.KEYWORD) {
                throw new Exception("Syntax Error");
            }
            varType = jackTokenizer.keyWord().getType();
            jackTokenizer.advance();
            if (jackTokenizer.tokenType() != TokenTypeEnum.IDENTIFIER) {
                throw new Exception("Syntax Error");
            }
            varName = jackTokenizer.identifier();
            jackTokenizer.advance();
            subRutineTable.define(varName,varType,"local");
        }
        if (jackTokenizer.tokenType() != TokenTypeEnum.SYMBOL || jackTokenizer.symbol() != ';') {
            throw new Exception("Syntax Error");
        }
        jackTokenizer.advance();
    }

    //Compiles a sequence of statements
    public void compileStatements() throws Exception {
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
                default:
                    return;
            }
            if (jackTokenizer.tokenType() != TokenTypeEnum.KEYWORD) {
                jackTokenizer.advance();
            }
        }
    }

    //Compiles do statement
    public void compileDo () throws Exception {
        String callName = "";
		int index = 0;
        if (jackTokenizer.tokenType() != TokenTypeEnum.KEYWORD || jackTokenizer.keyWord() != KeywordsEnum.DO) {
            throw new Exception("Syntax Error");
        }

        jackTokenizer.advance();
        if (jackTokenizer.tokenType() != TokenTypeEnum.IDENTIFIER) {
            throw new Exception("Syntax Error");
        }

        callName = jackTokenizer.getToken();

        jackTokenizer.advance();
        while (jackTokenizer.tokenType() == TokenTypeEnum.SYMBOL && jackTokenizer.symbol() == '.') {
            jackTokenizer.advance();
            if (jackTokenizer.tokenType() != TokenTypeEnum.IDENTIFIER) {
                throw new Exception("Syntax Error");
            }
            callName = callName + "." + jackTokenizer.getToken();
            jackTokenizer.advance();
        }

        int dot = callName.indexOf(".");
		if (dot < 0) {
            writer.writePush("pointer",0);
			index++;
			callName = className + "." + callName;
		} else {
            String pre = callName.substring(0, dot);

			if (classTable.typeOf(pre) != null) {
                writer.writePush(classTable.kindOf(pre), classTable.indexOf(pre));
				index++;
                callName = callName.replace(pre, classTable.typeOf(pre));
			}
        }

        if (jackTokenizer.tokenType() != TokenTypeEnum.SYMBOL || jackTokenizer.symbol() != '(') {
            throw new Exception("Syntax Error");
        }

        index += compileExpressionList();
        if (jackTokenizer.tokenType() != TokenTypeEnum.SYMBOL || jackTokenizer.symbol() != ')') {
            throw new Exception("Syntax Error" + jackTokenizer.getToken());
        }

        jackTokenizer.advance();
        if (jackTokenizer.tokenType() != TokenTypeEnum.SYMBOL || jackTokenizer.symbol() != ';') {
            throw new Exception("Syntax Error" + jackTokenizer.getToken() + "111111111111111");
        }

        writer.writeCall(callName, index);
		writer.writePop("temp", 0);
    }

    //Compiles a let statement
    public void compileLet () throws Exception {
//        writer.write("<letStatement>\n");
        if (jackTokenizer.tokenType() != TokenTypeEnum.KEYWORD || jackTokenizer.keyWord() != KeywordsEnum.LET) {
            throw new Exception("Syntax Error");
        }
//        writer.write("<keyword> ");
//        writer.write(jackTokenizer.keyWord().getType());
//        writer.write(" </keyword>\n");
        jackTokenizer.advance();
        if (jackTokenizer.tokenType() != TokenTypeEnum.IDENTIFIER) {
            throw new Exception("Syntax Error");
        }
//        writer.write("<identifier> ");
//        writer.write(jackTokenizer.identifier());
//        writer.write(" </identifier>\n");
        jackTokenizer.advance();
        if (jackTokenizer.tokenType() != TokenTypeEnum.SYMBOL) {
            throw new Exception("Syntax Error");
        }
//        writer.write("<symbol> ");
//        writer.write(jackTokenizer.getSymbolString(jackTokenizer.symbol()));
//        writer.write(" </symbol>\n");
        jackTokenizer.advance();
        while (jackTokenizer.tokenType() != TokenTypeEnum.SYMBOL || jackTokenizer.symbol() != ';') {
            if (jackTokenizer.tokenType() == TokenTypeEnum.SYMBOL) {
//                writer.write("<symbol> ");
//                writer.write(jackTokenizer.getSymbolString(jackTokenizer.symbol()));
//                writer.write(" </symbol>\n");
                jackTokenizer.advance();
                continue;
            }
            compileExpression();
        }
        if (jackTokenizer.tokenType() != TokenTypeEnum.SYMBOL) {
            throw new Exception("Syntax Error");
        }
//        writer.write("<symbol> ");
//        writer.write(jackTokenizer.getSymbolString(jackTokenizer.symbol()));
//        writer.write(" </symbol>\n");
//        writer.write("</letStatement>\n");
    }

    //Compiles an if statement
    public void compileIf () throws Exception {
//        writer.write("<ifStatement>\n");
        if (jackTokenizer.tokenType() != TokenTypeEnum.KEYWORD || jackTokenizer.keyWord() != KeywordsEnum.IF) {
            throw new Exception("Syntax Error");
        }
//        writer.write("<keyword> ");
//        writer.write(jackTokenizer.keyWord().getType());
//        writer.write(" </keyword>\n");
        jackTokenizer.advance();
        if (jackTokenizer.tokenType() != TokenTypeEnum.SYMBOL || jackTokenizer.symbol() != '(') {
            throw new Exception("Syntax Error");
        }
//        writer.write("<symbol> ");
//        writer.write(jackTokenizer.getSymbolString(jackTokenizer.symbol()));
//        writer.write(" </symbol>\n");
        jackTokenizer.advance();
        compileExpression();
        if (jackTokenizer.tokenType() != TokenTypeEnum.SYMBOL || jackTokenizer.symbol() != ')') {
            throw new Exception("Syntax Error");
        }
//        writer.write("<symbol> ");
//        writer.write(jackTokenizer.getSymbolString(jackTokenizer.symbol()));
//        writer.write(" </symbol>\n");
        jackTokenizer.advance();
        if (jackTokenizer.tokenType() != TokenTypeEnum.SYMBOL || jackTokenizer.symbol() != '{') {
            throw new Exception("Syntax Error");
        }
//        writer.write("<symbol> ");
//        writer.write(jackTokenizer.getSymbolString(jackTokenizer.symbol()));
//        writer.write(" </symbol>\n");
        jackTokenizer.advance();
        compileStatements();
        if (jackTokenizer.tokenType() != TokenTypeEnum.SYMBOL || jackTokenizer.symbol() != '}') {
            throw new Exception("Syntax Error");
        }
//        writer.write("<symbol> ");
//        writer.write(jackTokenizer.getSymbolString(jackTokenizer.symbol()));
//        writer.write(" </symbol>\n");
        jackTokenizer.advance();
        if (jackTokenizer.tokenType() == TokenTypeEnum.KEYWORD && jackTokenizer.keyWord() == KeywordsEnum.ELSE) {
//            writer.write("<keyword> ");
//            writer.write(jackTokenizer.keyWord().getType());
//            writer.write(" </keyword>\n");
            jackTokenizer.advance();
            if (jackTokenizer.tokenType() != TokenTypeEnum.SYMBOL || jackTokenizer.symbol() != '{') {
                throw new Exception("Syntax Error");
            }
//            writer.write("<symbol> ");
//            writer.write(jackTokenizer.getSymbolString(jackTokenizer.symbol()));
//            writer.write(" </symbol>\n");
            jackTokenizer.advance();
            compileStatements();
            if (jackTokenizer.tokenType() != TokenTypeEnum.SYMBOL || jackTokenizer.symbol() != '}') {
                throw new Exception("Syntax Error");
            }
//            writer.write("<symbol> ");
//            writer.write(jackTokenizer.getSymbolString(jackTokenizer.symbol()));
//            writer.write(" </symbol>\n");
            jackTokenizer.advance();
        }
//        writer.write("</ifStatement>\n");
    }

    //Compiles while statement
    public void compileWhile () throws Exception {
//        writer.write("<whileStatement>\n");
        if (jackTokenizer.tokenType() != TokenTypeEnum.KEYWORD || jackTokenizer.keyWord() != KeywordsEnum.WHILE) {
            throw new Exception("Syntax Error");
        }
//        writer.write("<keyword> ");
//        writer.write(jackTokenizer.keyWord().getType());
//        writer.write(" </keyword>\n");
        jackTokenizer.advance();
        if (jackTokenizer.tokenType() != TokenTypeEnum.SYMBOL || jackTokenizer.symbol() != '(') {
            throw new Exception("Syntax Error");
        }
//        writer.write("<symbol> ");
//        writer.write(jackTokenizer.getSymbolString(jackTokenizer.symbol()));
//        writer.write(" </symbol>\n");
        jackTokenizer.advance();
        compileExpression();
        if (jackTokenizer.tokenType() != TokenTypeEnum.SYMBOL || jackTokenizer.symbol() != ')') {
            throw new Exception("Syntax Error");
        }
//        writer.write("<symbol> ");
//        writer.write(jackTokenizer.getSymbolString(jackTokenizer.symbol()));
//        writer.write(" </symbol>\n");
        jackTokenizer.advance();
        if (jackTokenizer.tokenType() != TokenTypeEnum.SYMBOL || jackTokenizer.symbol() != '{') {
            throw new Exception("Syntax Error");
        }
//        writer.write("<symbol> ");
//        writer.write(jackTokenizer.getSymbolString(jackTokenizer.symbol()));
//        writer.write(" </symbol>\n");
        jackTokenizer.advance();
        compileStatements();
        if (jackTokenizer.tokenType() != TokenTypeEnum.SYMBOL || jackTokenizer.symbol() != '}') {
            throw new Exception("Syntax Error");
        }
//        writer.write("<symbol> ");
//        writer.write(jackTokenizer.getSymbolString(jackTokenizer.symbol()));
//        writer.write(" </symbol>\n");
//        writer.write("</whileStatement>\n");
    }

    //Compiles a return statement
    public void compileReturn () throws Exception {
//        writer.write("<returnStatement>\n");
        if (jackTokenizer.tokenType() != TokenTypeEnum.KEYWORD || jackTokenizer.keyWord() != KeywordsEnum.RETURN) {
            throw new Exception("Syntax Error");
        }
//        writer.write("<keyword> ");
//        writer.write(jackTokenizer.keyWord().getType());
//        writer.write(" </keyword>\n");
        jackTokenizer.advance();
        if (jackTokenizer.tokenType() != TokenTypeEnum.SYMBOL && jackTokenizer.symbol() != ';') {
            compileExpression();
        }
        if (jackTokenizer.tokenType() != TokenTypeEnum.SYMBOL && jackTokenizer.symbol() != ';') {
            throw new Exception("Syntax Error");
        }
//        writer.write("<symbol> ");
//        writer.write(jackTokenizer.getSymbolString(jackTokenizer.symbol()));
//        writer.write(" </symbol>\n");
//        writer.write("</returnStatement>\n");
    }

    //Compiles an expression
    public void compileExpression () throws Exception {
//        writer.write("<expression>\n");
        while (jackTokenizer.tokenType() != TokenTypeEnum.SYMBOL || (jackTokenizer.symbol() != ')' && jackTokenizer.symbol() != ']')) {
            compileTerm();
            if (jackTokenizer.tokenType() == TokenTypeEnum.SYMBOL && jackTokenizer.symbol() != ';' && jackTokenizer.symbol() != ',' && jackTokenizer.symbol() != ')' && jackTokenizer.symbol() != ']') {
//                writer.write("<symbol> ");
//                writer.write(jackTokenizer.getSymbolString(jackTokenizer.symbol()));
//                writer.write(" </symbol>\n");
                jackTokenizer.advance();
                compileTerm();
            }
            if (jackTokenizer.symbol() == ';' || jackTokenizer.symbol() == ',') {
                break;
            }
        }
//        writer.write("</expression>\n");
    }

    //Compiles a term
    public void compileTerm () throws Exception {
//        writer.write("<term>\n");
        if (jackTokenizer.tokenType() == TokenTypeEnum.SYMBOL) {
            if (jackTokenizer.symbol() == '(') {
//                writer.write("<symbol> ");
//                writer.write(jackTokenizer.getSymbolString(jackTokenizer.symbol()));
//                writer.write(" </symbol>\n");
                jackTokenizer.advance();
                compileExpression();
                if (jackTokenizer.tokenType() != TokenTypeEnum.SYMBOL) {
                    throw new Exception("Syntax Error");
                }
//                writer.write("<symbol> ");
//                writer.write(jackTokenizer.getSymbolString(jackTokenizer.symbol()));
//                writer.write(" </symbol>\n");
                jackTokenizer.advance();

            } else if (jackTokenizer.tokenType() == TokenTypeEnum.SYMBOL && jackTokenizer.symbol() != ';' && jackTokenizer.symbol() != ',' && jackTokenizer.symbol() != ')') {
//                writer.write("<symbol> ");
//                writer.write(jackTokenizer.getSymbolString(jackTokenizer.symbol()));
//                writer.write(" </symbol>\n");
                jackTokenizer.advance();
                compileTerm();
            }
            ;
        } else if (jackTokenizer.tokenType() == TokenTypeEnum.KEYWORD) {
            if (jackTokenizer.keyWord() == KeywordsEnum.TRUE || jackTokenizer.keyWord() == KeywordsEnum.FALSE || jackTokenizer.keyWord() == KeywordsEnum.THIS || jackTokenizer.keyWord() == KeywordsEnum.NULL) {
//                writer.write("<keyword> ");
//                writer.write(jackTokenizer.keyWord().getType());
//                writer.write(" </keyword>\n");
                jackTokenizer.advance();
            }

        } else if (jackTokenizer.tokenType() == TokenTypeEnum.IDENTIFIER) {
//            writer.write("<identifier> ");
//            writer.write(jackTokenizer.identifier());
//            writer.write(" </identifier>\n");
            jackTokenizer.advance();
            while (jackTokenizer.tokenType() == TokenTypeEnum.SYMBOL && (jackTokenizer.symbol() == '.' || jackTokenizer.symbol() == '(' || jackTokenizer.symbol() == '[')) {
                if (jackTokenizer.symbol() == '.') {
//                    writer.write("<symbol> ");
//                    writer.write(jackTokenizer.getSymbolString(jackTokenizer.symbol()));
//                    writer.write(" </symbol>\n");
                    jackTokenizer.advance();
                    if (jackTokenizer.tokenType() != TokenTypeEnum.IDENTIFIER) {
                        throw new Exception("Syntax Error");
                    }
//                    writer.write("<identifier> ");
//                    writer.write(jackTokenizer.identifier());
//                    writer.write(" </identifier>\n");
                    jackTokenizer.advance();
                } else if (jackTokenizer.symbol() == '(' || jackTokenizer.symbol() == '[') {
//                    writer.write("<symbol> ");
//                    writer.write(jackTokenizer.getSymbolString(jackTokenizer.symbol()));
//                    writer.write(" </symbol>\n");
                    if (jackTokenizer.symbol() == '[') {
                        jackTokenizer.advance();
                        compileExpression();
                    } else {
                        compileExpressionList();
                    }
                    if (jackTokenizer.tokenType() != TokenTypeEnum.SYMBOL || (jackTokenizer.symbol() != ')' && jackTokenizer.symbol() != ']')) {
                        throw new Exception("Syntax Error");
                    }
//                    writer.write("<symbol> ");
//                    writer.write(jackTokenizer.getSymbolString(jackTokenizer.symbol()));
//                    writer.write(" </symbol>\n");
                    jackTokenizer.advance();

                }
            }
        } else if (jackTokenizer.tokenType() == TokenTypeEnum.INT_CONST) {
//            writer.write("<integerConstant> ");
//            writer.write("" + jackTokenizer.intVal());
//            writer.write(" </integerConstant>\n");
            jackTokenizer.advance();
            if (jackTokenizer.tokenType() == TokenTypeEnum.SYMBOL && jackTokenizer.isOp()) {
//                writer.write("<symbol> ");
//                writer.write(jackTokenizer.getSymbolString(jackTokenizer.symbol()));
//                writer.write(" </symbol>\n");

                jackTokenizer.advance();
                compileTerm();
            }
        } else if (jackTokenizer.tokenType() == TokenTypeEnum.STRING_CONST) {
//            writer.write("<stringConstant> ");
//            writer.write(jackTokenizer.stringVal());
//            writer.write(" </stringConstant>\n");
            jackTokenizer.advance();
        }
//        writer.write("</term>\n");
    }

    //Compiles a comma-separated list of expression
    public int compileExpressionList () throws Exception {
        int expCount = 0;
        boolean isCommaLast = false;
        jackTokenizer.advance();
        while (jackTokenizer.tokenType() != TokenTypeEnum.SYMBOL || jackTokenizer.symbol() != ')') {
            compileExpression();
            isCommaLast = false;
            if (jackTokenizer.symbol() == ')') {
                return 0;
            }
            if (jackTokenizer.tokenType() == TokenTypeEnum.SYMBOL && jackTokenizer.symbol() == ',') {
                if (isCommaLast) {
                    throw new Exception("Syntax Error");
                }

                isCommaLast = true;
                jackTokenizer.advance();
                if (jackTokenizer.tokenType() == TokenTypeEnum.SYMBOL && jackTokenizer.symbol() == ',') {
                    if (isCommaLast) {
                        throw new Exception("Syntax Error");
                    }
                }
            }
            expCount++;
        }
        if (jackTokenizer.symbol() == ')') {
            return expCount;
        } else {
            throw new Exception("Syntax Error");
        }
    }

}
