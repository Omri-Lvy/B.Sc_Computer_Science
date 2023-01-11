import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class CompilationEngine {


    private JackTokenizer jackTokenizer;
    FileWriter outFile;

    //Creates a new compilation engine with the given input and output
    public CompilationEngine (File input, FileWriter output) {
        jackTokenizer = new JackTokenizer(input);
        outFile = output;
    }

    //Compiles  a complete class
    public void compileClass() throws IOException {
        outFile.write("<class>\n");
        while (jackTokenizer.hasMoreTokens()){
            TokenTypeEnum tokenType = jackTokenizer.tokenType();
            if (tokenType == TokenTypeEnum.KEYWORD) {
                if (jackTokenizer.keyWord() == KeywordsEnum.STATIC || jackTokenizer.keyWord() == KeywordsEnum.FIELD ) {
                    compileClassVarDec();
                }
                else if (jackTokenizer.keyWord() == KeywordsEnum.METHOD || jackTokenizer.keyWord() == KeywordsEnum.FUNCTION || jackTokenizer.keyWord() == KeywordsEnum.CONSTRUCTOR){
                    compileSubroutine();
                }




//                    case METHOD:
//                        break;
//                    case FUNCTION:
//                        break;
//                    case CONSTRUCTOR:
//                        break;
//                    case INT:
//                        break;
//                    case BOOLEAN:
//                        break;
//                    case CHAR:
//                        break;
//                    case VOID:
//                        break;
//                    case VAR:
//                        break;
//                    case LET:
//                        break;
//                    case DO:
//                        break;
//                    case IF:
//                        break;
//                    case ELSE:
//                        break;
//                    case WHILE:
//                        break;
//                    case RETURN:
//                        break;
//                    case TRUE:
//                        break;
//                    case FALSE:
//                        break;
//                    case NULL:
//                        break;
//                    case THIS:
//                        break;
                }
            }
        }
        outFile.write("</class>\n");
    }

    //Compiles a static variable declaration or a field declaration
    public void compileClassVarDec() {}

    //Compiles a complete method, function or constructor
    public void compileSubroutine() {}

    //Compiles a parameter list
    public void compileParameterList(){}

    //Compiles a subroutine's body
    public void compilerSubroutineBody() {}

    //Compiles var declaration
    public void compileVarDec(){}

    //Compiles a sequence of statements
    public void compileStatements(){}

    //Compiles a let statement
    public void compileLet() {}

    //Compiles an if statement
    public void compileIf() {}

    //Compiles while statement
    public void compileWhile() {}

    //Compiles do statement
    public void compileDo() {}

    //Compiles a return statement
    public void compileReturn() {}

    //Compiles an expression
    public void compileExpression() {}

    //Compiles a term
    public void compileTerm() {}

    //Compiles a comma-separated list of expression
    public void compileExpressionList() {}





}
