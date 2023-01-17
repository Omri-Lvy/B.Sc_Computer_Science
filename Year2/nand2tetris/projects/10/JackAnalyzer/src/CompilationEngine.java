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
        jackTokenizer.advance();
        outFile = output;
        token = jackTokenizer.getToken();
        type = jackTokenizer.tokenType(token);
    }

    //Compiles  a complete class
    public void compileClass() throws Exception {
        outFile.write("<class>\n");
        if (type != TokenTypeEnum.KEYWORD || !token.equals("class")) {
            throw new Exception("Syntax Error");
        }
        outFile.write("<keyword> ");
        outFile.write(token);
        outFile.write(" </keyword>\n");
        jackTokenizer.advance();
        token = jackTokenizer.getToken();
        type = jackTokenizer.tokenType(token);
        if (type != TokenTypeEnum.IDENTIFIER) {
            throw new Exception("Syntax Error");
        }
        outFile.write("<identifier> ");
        outFile.write(token);
        outFile.write(" </identifier>\n");
        jackTokenizer.advance();
        token = jackTokenizer.getToken();
        type = jackTokenizer.tokenType(token);
        compileParameterList();
        jackTokenizer.advance();
        token = jackTokenizer.getToken();
        type = jackTokenizer.tokenType(token);
        if (type != TokenTypeEnum.SYMBOL || token.equals("{")) {
            throw new Exception("Syntax Error");
        }
        outFile.write("<symbol> ");
        outFile.write(token);
        outFile.write(" </symbol>\n");
        compileClassVarDec();

        outFile.write("</class>\n");
        outFile.close();
    }

    //Compiles a static variable declaration or a field declaration
    public void compileClassVarDec() {}

    //Compiles a complete method, function or constructor
    public void compileSubroutine() {}

    //Compiles a parameter list
    public void compileParameterList() throws Exception {
        jackTokenizer.advance();
        token = jackTokenizer.getToken();
        type = jackTokenizer.tokenType(token);
        if (type != TokenTypeEnum.SYMBOL || token.equals("(")) {
            throw new Exception("Syntax Error");
        }
        outFile.write("<symbol> ");
        outFile.write(token);
        outFile.write(" </symbol>\n");
        jackTokenizer.advance();
        token = jackTokenizer.getToken();
        while(!token.equals(")")) {

        }
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
