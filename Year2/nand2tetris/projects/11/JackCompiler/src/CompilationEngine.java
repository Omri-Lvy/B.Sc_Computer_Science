import java.io.File;
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
        // XXXX recede
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
        writer.ifCounter = 0; // reset if Counter
		writer.whileCounter = 0; // reset whileConter
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
            if (jackTokenizer.symbol() == ',') {
                jackTokenizer.advance();
                if (jackTokenizer.tokenType() != TokenTypeEnum.IDENTIFIER) {
                    throw new Exception("Syntax Error");
                }
                varName = jackTokenizer.identifier();
            }
            else {
                if (jackTokenizer.tokenType() != TokenTypeEnum.KEYWORD) {
                    throw new Exception("Syntax Error");
                }
                varType = jackTokenizer.keyWord().getType();
                jackTokenizer.advance();
                if (jackTokenizer.tokenType() != TokenTypeEnum.IDENTIFIER) {
                    throw new Exception("Syntax Error");
                }
                varName = jackTokenizer.identifier();
            }
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
            throw new Exception("Syntax Error");
        }

        jackTokenizer.advance();
        if (jackTokenizer.tokenType() != TokenTypeEnum.SYMBOL || jackTokenizer.symbol() != ';') {
            throw new Exception("Syntax Error");
        }

        writer.writeCall(callName, index);
		writer.writePop("temp", 0);
    }

    //Compiles a let statement
    public void compileLet () throws Exception {
        Object value = null;
		String varName = null;
		boolean isArray = false;

        jackTokenizer.advance();
        if (jackTokenizer.tokenType() != TokenTypeEnum.KEYWORD || jackTokenizer.keyWord() != KeywordsEnum.LET) {
            throw new Exception("Syntax Error");
        }
        varName = jackTokenizer.getToken();

        if (jackTokenizer.tokenType() != TokenTypeEnum.IDENTIFIER) {
            throw new Exception("Syntax Error");
        }
        value = jackTokenizer.getToken();

        jackTokenizer.advance();
        if (jackTokenizer.tokenType() != TokenTypeEnum.SYMBOL) {
            throw new Exception("Syntax Error");
        }

		if ("[".equals(value)) {
			// [
            if (jackTokenizer.tokenType() != TokenTypeEnum.SYMBOL || jackTokenizer.symbol() != '[') {
                throw new Exception("Syntax Error");
            }

			// exp
			compileExpression();
			// ]
			jackTokenizer.advance();
            if (jackTokenizer.tokenType() != TokenTypeEnum.SYMBOL || jackTokenizer.symbol() != ']') {
                throw new Exception("Syntax Error");
            }
			writer.writePush(classTable.kindOf(varName),
                classTable.indexOf(varName));
			writer.writeArithmetic(jackTokenizer.symbol());
			isArray = true;

		}
		// =
		jackTokenizer.advance();
        if (jackTokenizer.tokenType() != TokenTypeEnum.SYMBOL || jackTokenizer.symbol() != '=') {
            throw new Exception("Syntax Error");
        }
            // exp
		compileExpression();
		if (!isArray) {
			writer.writePop(classTable.kindOf(varName),
					classTable.indexOf(varName));
		} else {
			writer.writePop("TEMP", 0);
			writer.writePop("POINTER", 1);
			writer.writePush("TEMP", 0);
			writer.writePop("THAT", 0);
			isArray = false;
		}

		// ;
		jackTokenizer.advance();
        if (jackTokenizer.tokenType() != TokenTypeEnum.SYMBOL || jackTokenizer.symbol() != ';') {
            throw new Exception("Syntax Error");
        }
    }

    //Compiles an if statement
    public void compileIf () throws Exception {
        Object value = null;
		String labelTrue = "IF_TRUE" + writer.ifCounter;
		String labelFalse = "IF_FALSE" + writer.ifCounter;
		String labelEnd = "IF_END" + writer.ifCounter++;
        jackTokenizer.advance();
        if (jackTokenizer.tokenType() != TokenTypeEnum.SYMBOL || jackTokenizer.symbol() != '(') {
            throw new Exception("Syntax Error");
        }
		// exp
		compileExpression();
		writer.writeIf(labelTrue);
		writer.writeGoto(labelFalse);
		// )
		jackTokenizer.advance();

        if (jackTokenizer.tokenType() != TokenTypeEnum.SYMBOL || jackTokenizer.symbol() != ')') {
            throw new Exception("Syntax Error");
        }
        // {
		jackTokenizer.advance();
        if (jackTokenizer.tokenType() != TokenTypeEnum.SYMBOL || jackTokenizer.symbol() != '{') {
            throw new RuntimeException("Syntax Error");
        }
        writer.writeLabel(labelTrue);
		// statements
		compileStatements();
		// }
		jackTokenizer.advance();
        if (jackTokenizer.tokenType() != TokenTypeEnum.SYMBOL || jackTokenizer.symbol() != '}') {
            throw new RuntimeException("Syntax Error");
        }
		// else
		jackTokenizer.advance();
		value = jackTokenizer.getToken();
		if ("else".equals(value)) {
			writer.writeGoto(labelEnd);
			writer.writeLabel(labelFalse);
			// else
			// {
			jackTokenizer.advance();
            if (jackTokenizer.tokenType() != TokenTypeEnum.SYMBOL || jackTokenizer.symbol() != '{') {
                throw new RuntimeException("Syntax Error");
            }			
            // statements
			compileStatements();
			// }
			jackTokenizer.advance();
            if (jackTokenizer.tokenType() != TokenTypeEnum.SYMBOL || jackTokenizer.symbol() != '}') {
                throw new RuntimeException("Syntax Error");
            }
			writer.writeLabel(labelEnd);
		} else {
			writer.writeLabel(labelFalse);
		}
    }

    //Compiles while statement
    public void compileWhile () throws Exception {
		String whileLabel = "WHILE_EXP" + writer.whileCounter;
		String endLabel = "WHILE_END" + writer.whileCounter++;

		// while
		writer.writeLabel(whileLabel);
		// (
		jackTokenizer.advance();
        if (jackTokenizer.tokenType() != TokenTypeEnum.SYMBOL || jackTokenizer.symbol() != '(') {
            throw new Exception("Syntax Error");
        }
        // exp
		compileExpression();
		writer.writeArithmetic('!');
		writer.writeIf(endLabel);
		// )
		jackTokenizer.advance();
        if (jackTokenizer.tokenType() != TokenTypeEnum.SYMBOL || jackTokenizer.symbol() != ')') {
            throw new Exception("Syntax Error");
        }
        // {
		jackTokenizer.advance();
        if (jackTokenizer.tokenType() != TokenTypeEnum.SYMBOL || jackTokenizer.symbol() != '{') {
            throw new RuntimeException("Syntax Error");
        }	
        // statements
		compileStatements();
		// }
		jackTokenizer.advance();
        if (jackTokenizer.tokenType() != TokenTypeEnum.SYMBOL || jackTokenizer.symbol() != '}') {
            throw new RuntimeException("Syntax Error");
        }	
		writer.writeGoto(whileLabel);
		writer.writeLabel(endLabel);
    }

    public void compileReturn () throws Exception {
        Object value = null;
		// return
		// exp
		jackTokenizer.advance();
		value = jackTokenizer.getToken();
		if (!";".equals(value)) {
			compileExpression();
		} else {
			writer.writePush("PUSH", 0);
		}
		// ;
		jackTokenizer.advance();
        if (jackTokenizer.tokenType() != TokenTypeEnum.SYMBOL || jackTokenizer.symbol() != ';') {
            throw new RuntimeException("Syntax Error");
        }	
		writer.writeReturn();
    }

    //Compiles an expression
    public void compileExpression () throws Exception {
        Object value = null;
		boolean opTag = false;
        boolean isSecondTerm = false;
		String op = null;
		do {
			// term
			compileTerm();
			if (isSecondTerm) {
				isSecondTerm = false;
				writer.writeArithmetic(op);
				op = null;
			}
			// op
			jackTokenizer.advance();
			value = jackTokenizer.getToken();
			if (value.toString().matches("\\+|-|\\*|/|\\&|\\||<|=|>")) {
				opTag = true;
				op = value.toString();
			} else {
				opTag = false;
			}
			isSecondTerm = true;
		} while (opTag);
    }

    //Compiles a term
    public void compileTerm () throws Exception {
		TokenTypeEnum tokenType = null;
		String tvalue = null;

		// read token to confirm the structure of term
		jackTokenizer.advance();
		tvalue = jackTokenizer.getToken();
		tokenType = jackTokenizer.tokenType();
		//( exp )
		if ("(".equals(tvalue)) {
			compileExpression();
			jackTokenizer.advance();
            if (jackTokenizer.tokenType() != TokenTypeEnum.SYMBOL || jackTokenizer.symbol() != ')') {
                throw new Exception("Syntax Error");
            }
        } else if (tvalue.matches("\\-|~")) { //~|- term
			// - ~
			String op = tvalue;
			// term
			compileTerm();
			writer.writeArithmetic(op);
		} else if (TokenTypeEnum.INT_CONST.equals(tokenType)) { // intConst
			tvalue = jackTokenizer.getToken();
			writer.writePush("constant", Integer.parseInt(tvalue));
		} else if (TokenTypeEnum.STRING_CONST.equals(tokenType)) { // String
			tvalue = jackTokenizer.getToken();
			writer.writePush("constant", tvalue.length());
			writer.writeCall("String.new", 1);
			for (char c : ((String) tvalue).toCharArray()) {
				writer.writePush("constant", c);
				writer.writeCall("String.appendChar", 2);
			}
		} else if (TokenTypeEnum.KEYWORD.equals(tokenType)) { // keyword
			tvalue = jackTokenizer.getToken();
			if ("true".equals(tvalue)) {
				writer.writePush("constant", 0);
				writer.writeArithmetic("not");
			} else if ("false".equals(tvalue) || "null".equals(tvalue)) {
				writer.writePush("constant", 0);
			} else if ("this".equals(tvalue)) {
				writer.writePush("pointer", 0);
			} else if ("that".equals(tvalue)) {
				writer.writePush("pointer", 1);
			}
		} else if (TokenTypeEnum.IDENTIFIER.equals(tokenType)) {
			// varName
			String varName = jackTokenizer.getToken();

			jackTokenizer.advance();
			tvalue = jackTokenizer.getToken();
			// varName[exp]
			if ("[".equals(tvalue)) {
				// [
				// exp
				compileExpression();
				writer.writePush(classTable.kindOf(varName),
						classTable.indexOf(varName));
				writer.writeArithmetic("add");
				writer.writePop("pointer", 1);
				writer.writePush("that", 0);
				// ]
				jackTokenizer.advance();
                if (jackTokenizer.tokenType() != TokenTypeEnum.SYMBOL || jackTokenizer.symbol() != ']') {
                    throw new Exception("Syntax Error");
                }
			} else if ("(".equals(tvalue)) { // TYPE_9 subroutineName(expList)
				// (
				// expList
				compileExpressionList();
				// )
				jackTokenizer.advance();
                if (jackTokenizer.tokenType() != TokenTypeEnum.SYMBOL || jackTokenizer.symbol() != ')') {
                    throw new Exception("Syntax Error");
                }
				// TYPE_10 className|varName.subroutineNmae(expList)
				int argsNum = 0;
				if (classTable.kindOf(varName) != null) {
					//vmWriter.writePush(Segment.THIS, table.indexOf(varName));
					writer.writePush(classTable.kindOf(varName), classTable.indexOf(varName));
					argsNum++;
					varName = classTable.typeOf(varName);
				}

				// .
				varName += jackTokenizer.getToken();
				if (classTable.kindOf(varName) != null) {
					writer.writePush("this", 0);
					varName = classTable.typeOf(varName);
				}
				// sub
				jackTokenizer.advance();
				varName += jackTokenizer.getToken();
				// (
				jackTokenizer.advance();
                if (jackTokenizer.tokenType() != TokenTypeEnum.SYMBOL || jackTokenizer.symbol() != '(') {
                    throw new Exception("Syntax Error");
                }
				// expList
				argsNum += compileExpressionList();
				// )
				jackTokenizer.advance();
                if (jackTokenizer.tokenType() != TokenTypeEnum.SYMBOL || jackTokenizer.symbol() != ')') {
                    throw new Exception("Syntax Error");
                }

				writer.writeCall(varName, argsNum);
			} else {
				writer.writePush(classTable.kindOf(varName),
                classTable.indexOf(varName));
			}
		}
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
