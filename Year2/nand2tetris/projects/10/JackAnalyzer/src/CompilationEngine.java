import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;

public class CompilationEngine {


    private JackTokenizer jackTokenizer;
    FileWriter outFile;

    //Creates a new compilation engine with the given input and output
    public CompilationEngine (File input, FileWriter output) throws FileNotFoundException {
        jackTokenizer = new JackTokenizer(input);
        jackTokenizer.advance();
        outFile = output;
    }

    //Compiles  a complete class
    public void compileClass() throws IOException {
        outFile.write("<class>\n");
        String token = jackTokenizer.getToken();
        TokenTypeEnum type = jackTokenizer.tokenType(token);


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
