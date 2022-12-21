public class AssemblyCommands {

    private int labelNum = 0;

    public AssemblyCommands () {
    }

    public String getInit () {
        return "@256\n" +
                "D=A\n" +
                "@SP\n" +
                "M=D\n";
    }

    public String getArithmeticCommands (String command) throws Exception {
        // return the arithmetic command corresponding assembly code
        switch (command) {
            case "add":
                return getAddCommands();
            case "sub":
                return getSubCommands();
            case "neg":
                return getNegCommands();
            case "eq":
                return getEqCommands();
            case "gt":
                return getGtCommands();
            case "lt":
                return getLtCommands();
            case "and":
                return getAndCommands();
            case "or":
                return getOrCommands();
            case "not":
                return getNotCommands();
        }
        throw new Exception("Illegal command");
    }

    public String getPushPopCommands (String command, int index, String fileName) throws Exception {
        // return the push/pop command corresponding assembly code
        switch (command) {
            case "pushstatic":
                return getPushStaticCommands(index, fileName);
            case "popstatic":
                return getPopStaticCommands(index, fileName);
        }
        throw new Exception("Illegal command");
    }

    public String getPushPopCommands (String command, int index) throws Exception {
        // return the push/pop command corresponding assembly code
        switch (command) {
            case "pushconstant":
                return getPushConstantCommands(index);
            case "pushlocal":
                return getPushLocalCommands(index);
            case "poplocal":
                return getPopLocalCommands(index);
            case "pushargument":
                return getPushArgumentCommands(index);
            case "popargument":
                return getPopArgumentCommands(index);
            case "pushthis":
                return getPushThisCommands(index);
            case "popthis":
                return getPopThisCommands(index);
            case "pushthat":
                return getPushThatCommands(index);
            case "popthat":
                return getPopThatCommands(index);
            case "pushtemp":
                return getPushTempCommands(index);
            case "poptemp":
                return getPopTempCommands(index);
            case "pushpointer0":
                return getPushThisCommands(index);
            case "poppointer0":
                return getPopThisCommands(index);
            case "pushpointer1":
                return getPushThatCommands(index);
            case "poppointer1":
                return getPushThatCommands(index);
        }
        throw new Exception("Illegal command");
    }

    public String getPushPopCommands (String command) throws Exception {
        switch (command) {
            case "pushpointer0":
                return getPushPointer0Commands();
            case "poppointer0":
                return getPopPointer0Commands();
            case "pushpointer1":
                return getPushPointer1Commands();
            case "poppointer1":
                return getPopPointer1Commands();
        }
        throw new Exception("Illegal command");
    }

    public String getLabelCommand (String label) {
        return "(" + label + ")\n";
    }

    public String getGotoCommand (String label) {
        return "@" + label + "\n" +
                "0;JMP\n";
    }

    public String getIfCommand (String label) {
        return "@SP\n" +
                "AM=M-1\n" +
                "D=M\n" +
                "@" + label + "\n" +
                "D;JNE\n";
    }

    public String getCallCommand (String label,String returnLabel, int nArgs) {
        return  // push return address
                "@" + returnLabel + "\n" +
                        "D=A\n" +
                        "@SP\n" +
                        "A=M\n" +
                        "M=D\n" +
                        "@SP\n" +
                        "M=M+1\n" +
                        // Saves the caller’s LCL
                        "@LCL\n" +
                        "D=M\n" +
                        "@SP\n" +
                        "A=M\n" +
                        "M=D\n" +
                        "@SP\n" +
                        "M=M+1\n" +
                        // Saves the caller’s ARG
                        "@ARG\n" +
                        "D=M\n" +
                        "@SP\n" +
                        "A=M\n" +
                        "M=D\n" +
                        "@SP\n" +
                        "M=M+1\n" +
                        // Saves the caller’s THIS
                        "@THIS\n" +
                        "D=M\n" +
                        "@SP\n" +
                        "A=M\n" +
                        "M=D\n" +
                        "@SP\n" +
                        "M=M+1\n" +
                        // Saves the caller’s THAT
                        "@THAT\n" +
                        "D=M\n" +
                        "@SP\n" +
                        "A=M\n" +
                        "M=D\n" +
                        "@SP\n" +
                        "M=M+1\n" +
                        // Repositions ARG
                        "@SP\n"+
                        "D=M\n"+
                        "@" + nArgs + "\n" +
                        "D=D-A\n" +
                        "@5\n" +
                        "D=D-A\n"+
                        "@ARG\n" +
                        "M=D\n" +
                        // Repositions LCL
                        "@SP\n" +
                        "D=M\n" +
                        "@LCL\n" +
                        "M=D\n" +
                        getGotoCommand(label)+
                        "(" + returnLabel + ")\n";
    }

    public String getReturnCommand(){
        return  // gets the address at the frame’s end
                "@LCL\n"+
                "D=M\n"+
                "@END_FRAME\n"+
                "M=D\n"+
                // gets the return address
                "@END_FRAME\n" +
                "D=M\n" +
                "@5\n"+
                "A=D-A\n"+
                "D=M\n"+
                "@RET_ADDR\n"+
                "M=D\n"+
                // puts the return value for the caller
                getPopArgumentCommands(0) +
                // repositions SP
                "@ARG\n" +
                "D=M\n" +
                "@SP\n" +
                "M=D+1\n" +
                // restores THAT
                "@END_FRAME\n" +
                "D=M-1\n"+
                "@THAT\n"+
                "M=D\n"+
                // restores THIS
                "@END_FRAME\n" +
                "D=M\n" +
                "@2\n"+
                "D=D-A\n"+
                "@THIS\n" +
                "M=D\n" +
                // restores ARG
                "@END_FRAME\n" +
                "D=M\n" +
                "@3\n"+
                "D=D-A\n"+
                "@ARG\n" +
                "M=D\n" +
                // restores LCL
                "@END_FRAME\n" +
                "D=M\n" +
                "@4\n"+
                "D=D-A\n"+
                "@LCL\n" +
                "M=D\n" +
                "@RET_ADDR\n"+
                "A=M\n"+
                "0;JMP\n";
    }


    private String getPushConstantCommands (int index) {
        return  // get the value
                "@" + index + "\n" +
                        "D=A\n" +
                        // set RAM[SP] = constant
                        "@SP\n" +
                        "A=M\n" +
                        "M=D\n" +
                        // sp++
                        "@SP\n" +
                        "M=M+1\n";
    }

    private String getPushLocalCommands (int index) {
        return  // addr = LCL + index
                "@LCL\n" +
                        "D=M\n" +
                        "@" + index + "\n" +
                        "A=D+A\n" +
                        // RAM[SP] = RAM[addr]
                        "D=M\n" +
                        "@SP\n" +
                        "A=M\n" +
                        "M=D\n" +
                        // SP++
                        "@SP\n" +
                        "M=M+1\n";

    }

    private String getPopLocalCommands (int index) {
        return  // addr = LCL + index
                "@LCL\n" +
                        "D=M\n" +
                        "@" + index + "\n" +
                        "D=D+A\n" +
                        "@R13\n" +
                        "M=D\n" +
                        // SP--
                        "@SP\n" +
                        "AM=M-1\n" +
                        // RAM[addr] = RAM[SP]
                        "D=M\n" +
                        "@R13\n" +
                        "A=M\n" +
                        "M=D\n";
    }

    private String getPushArgumentCommands (int index) {
        return  // address = ARG + index
                "@ARG\n" +
                        "D=M\n" +
                        "@" + index + "\n" +
                        "A=D+A\n" +
                        // RAM[SP] = RAM[addr]
                        "D=M\n" +
                        "@SP\n" +
                        "A=M\n" +
                        "M=D\n" +
                        // SP++
                        "@SP\n" +
                        "M=M+1\n";

    }

    private String getPopArgumentCommands (int index) {
        return // addr = ARG + index
                "@ARG\n" +
                        "D=M\n" +
                        "@" + index + "\n" +
                        "D=D+A\n" +
                        "@R13\n" +
                        "M=D\n" +
                        // SP--
                        "@SP\n" +
                        "AM=M-1\n" +
                        // RAM[addr] = RAM[SP]
                        "D=M\n" +
                        "@R13\n" +
                        "A=M\n" +
                        "M=D\n";
    }

    private String getPushThisCommands (int index) {
        return  // address = THIS + index
                "@THIS\n" +
                        "D=M\n" +
                        "@" + index + "\n" +
                        "A=D+A\n" +
                        // RAM[SP] = RAM[addr]
                        "D=M\n" +
                        "@SP\n" +
                        "A=M\n" +
                        "M=D\n" +
                        // SP++
                        "@SP\n" +
                        "M=M+1\n";
    }

    private String getPopThisCommands (int index) {
        return  // addr = THIS + index
                "@THIS\n" +
                        "D=M\n" +
                        "@" + index + "\n" +
                        "D=D+A\n" +
                        "@R13\n" +
                        "M=D\n" +
                        // SP--
                        "@SP\n" +
                        "AM=M-1\n" +
                        // RAM[addr] = RAM[SP]
                        "D=M\n" +
                        "@R13\n" +
                        "A=M\n" +
                        "M=D\n";
    }

    private String getPushThatCommands (int index) {
        return  // addr = THAT + index
                "@THAT\n" +
                        "D=M\n" +
                        "@" + index + "\n" +
                        "A=D+A\n" +
                        // RAM[SP] = RAM[addr]
                        "D=M\n" +
                        "@SP\n" +
                        "A=M\n" +
                        "M=D\n" +
                        // SP++
                        "@SP\n" +
                        "M=M+1\n";
    }

    private String getPopThatCommands (int index) {
        return  // addr = THAT + index
                "@THAT\n" +
                        "D=M\n" +
                        "@" + index + "\n" +
                        "D=D+A\n" +
                        "@R13\n" +
                        "M=D\n" +
                        // SP--
                        "@SP\n" +
                        "AM=M-1\n" +
                        // RAM[addr] = RAM[SP]
                        "D=M\n" +
                        "@R13\n" +
                        "A=M\n" +
                        "M=D\n";
    }

    private String getPushStaticCommands (int index, String fileName) {
        return // addr = fileName.index
                "@" + fileName + "." + index + "\n" +
                        // RAM[SP] = RAM[addr]
                        "D=M\n" +
                        "@SP\n" +
                        "A=M\n" +
                        "M=D\n" +
                        // SP++
                        "@SP\n" +
                        "M=M+1\n";
    }

    private String getPopStaticCommands (int index, String fileName) {
        return  // SP--
                "@SP\n" +
                        "AM=M-1\n" +
                        // RAM[addr] = RAM[SP]
                        "D=M\n" +
                        "@" + fileName + "." + index + "\n" +
                        "M=D\n";
    }

    private String getPushTempCommands (int index) {
        return  // addr = 5 + index
                "@5\n" +
                        "D=A\n" +
                        "@" + index + "\n" +
                        "A=D+A\n" +
                        // RAM[SP] = RAM[addr]
                        "D=M\n" +
                        "@SP\n" +
                        "A=M\n" +
                        "M=D\n" +
                        // SP++
                        "@SP\n" +
                        "M=M+1\n";
    }

    private String getPopTempCommands (int index) {
        return  // addr = 5 + index
                "@5\n" +
                        "D=A\n" +
                        "@" + index + "\n" +
                        "D=D+A\n" +
                        "@R13\n" +
                        "M=D\n" +
                        // SP--
                        "@SP\n" +
                        "AM=M-1\n" +
                        // RAM[addr] = RAM[SP]
                        "D=M\n" +
                        "@R13\n" +
                        "A=M\n" +
                        "M=D\n";
    }

    private String getPushPointer0Commands () {
        return  // get THIS value
                "@THIS\n" +
                        "D=M\n" +
                        // RAM[SP] = THIS
                        "@SP\n" +
                        "A=M\n" +
                        "M=D\n" +
                        //SP++
                        "@SP\n" +
                        "M=M+1\n";
    }

    private String getPopPointer0Commands () {
        return  // THIS = RAM[SP]
                "@SP\n" +
                        "AM=M-1\n" +
                        "D=M\n" +
                        "@THIS\n" +
                        "M=D\n";
    }

    private String getPushPointer1Commands () {
        return  // get THAT value
                "@THAT\n" +
                        "D=M\n" +
                        // RAM[SP] = THIS
                        "@SP\n" +
                        "A=M\n" +
                        "M=D\n" +
                        //SP++
                        "@SP\n" +
                        "M=M+1\n";
    }

    private String getPopPointer1Commands () {
        return  // THAT = RAM[SP]
                "@SP\n" +
                        "AM=M-1\n" +
                        "D=M\n" +
                        "@THAT\n" +
                        "M=D\n";
    }

    private String getAddCommands () {
        return "@SP\n" +
                "AM=M-1\n" +        // decrease sp by 1 and go to first item in the stack
                "D=M\n" +           //set D to first item value
                "A=A-1\n" +     // move to 2nd items in the stack
                "M=D+M\n";       // add second item value to D and put result in stack
    }

    private String getSubCommands () {
        return "@SP\n" +
                "AM=M-1\n" +    // decrease sp by 1 and go to first item in the stack
                "D=M\n" +       //set D to first item value
                "A=A-1\n" +     // move to 2nd items in the stack
                "M=M-D\n";       // subtract second item value from D and put result in stack
    }

    private String getNegCommands () {
        return "@SP\n" +
                "A=M-1\n" +     // go to first item in the stack
                "M=-M\n";        // negate the value of it
    }

    private String getEqCommands () {
        labelNum++;
        return "@SP\n" +
                "AM=M-1\n" +    // decrease sp by 1 and go to first item in the stack
                "D=M\n" +       // set D to first item value
                "A=A-1\n" +     // go to second item in the stack
                "D=M-D\n" +     // subtract second item value from D
                "M=-1\n" +      // tentatively set result to true
                "@JUMPTO" + labelNum + "\n" +   // set label address to jump to if x == y
                "D;JEQ\n" +     // jump if equal
                "@SP\n" +       // go back to sp address
                "A=M-1\n" +     // go to first stack item
                "M=0\n" +       // put result in stack
                "(JUMPTO" + labelNum + ")\n";  // label to jump to if x == y
    }

    private String getGtCommands () {
        labelNum++;
        return "@SP\n" +
                "AM=M-1\n" +    // decrease sp by 1 and go to first item in the stack
                "D=M\n" +       // set D to first item value
                "A=A-1\n" +     // go to second item in the stack
                "D=M-D\n" +     // subtract second item value from D
                "M=-1\n" +      // tentatively set result to true
                "@JUMPTO" + labelNum + "\n" +   // set label address to jump to if x > y
                "D;JGT\n" +     // jump if equal
                "@SP\n" +       // go back to sp address
                "A=M-1\n" +     // go to first stack item
                "M=0\n" +       // put result in stack
                "(JUMPTO" + labelNum + ")\n";   // label to jump to if x > y      // add second item value to D and put result in stack
    }

    private String getLtCommands () {
        labelNum++;
        return "@SP\n" +
                "AM=M-1\n" +    // decrease sp by 1 and go to first item in the stack
                "D=M\n" +       // set D to first item value
                "A=A-1\n" +     // go to second item in the stack
                "D=M-D\n" +     // subtract second item value from D
                "M=-1\n" +      // tentatively set result to true
                "@JUMPTO" + labelNum + "\n" +   // set label address to jump to if x < y
                "D;JLT\n" +     // jump if equal
                "@SP\n" +       // go back to sp address
                "A=M-1\n" +     // go to first stack item
                "M=0\n" +       // put result in stack
                "(JUMPTO" + labelNum + ")\n";   // label to jump to if x < y
    }

    private String getAndCommands () {
        return "@SP\n" +
                "AM=M-1\n" +     // decrease sp by 1 and go to first item in the stack
                "D=M\n" +       // set D to first item value
                "A=A-1\n" +     // go to second item in the stack
                "M=D&M\n";       // put result in stack
    }

    private String getOrCommands () {
        return "@SP\n" +
                "AM=M-1\n" +    // decrease sp by 1 and go to first item in the stack
                "D=M\n" +       // set D to first item value
                "A=A-1\n" +     // go to second item in the stack
                "M=D|M\n";       // put result in stack
    }

    private String getNotCommands () {
        return "@SP\n" +
                "A=M-1\n" +     // go to first item in the stack
                "M=!M\n";        // set value to !M
    }


}
