import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;

public class CompilationEngine {


    private JackTokenizer jackTokenizer;
    private FileWriter outFile;
    private String token;
    private TokenTypeEnum type;

    //Creates a new compilation engine with the given input and output
    public CompilationEngine (File input, FileWriter output) throws FileNotFoundException {
        jackTokenizer = new JackTokenizer(input);

        outFile = output;
    }

    //Compiles  a complete class
    public void compileClass() throws Exception {
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
        jackTokenizer.advance();
        if (jackTokenizer.tokenType() != TokenTypeEnum.SYMBOL || jackTokenizer.symbol() != '{') {
            throw new Exception("Syntax Error");
        }
        outFile.write("<symbol> ");
        outFile.write(jackTokenizer.symbol());
        outFile.write(" </symbol>\n");
        jackTokenizer.advance();
        while (jackTokenizer.tokenType() == TokenTypeEnum.KEYWORD) {
            if (jackTokenizer.keyWord().equals("static") || jackTokenizer.keyWord().equals("field")) {
                compileClassVarDec();
            }
            else if(jackTokenizer.keyWord().equals("constructor") || jackTokenizer.keyWord().equals("method") || jackTokenizer.keyWord().equals("function")) {
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
    public void compileClassVarDec() {
        System.out.println(jackTokenizer.getToken());
    }

    //Compiles a complete method, function or constructor
    public void compileSubroutine() {
        System.out.println(jackTokenizer.getToken());
    }

    //Compiles a parameter list
    public void compileParameterList() throws Exception {

    }

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
