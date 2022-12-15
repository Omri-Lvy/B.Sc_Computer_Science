public class AssemblyCommands {

    private int labelNum = 0;

    public AssemblyCommands(){}

    public String getArithmeticCommands (String command) {
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
        return null;
    }

    public String getPushPopCommands (String command, int index) {
        switch (command) {
            case "pushconstant":
                return getPushConstantCommands(index);
            case "popconstant":
                return getPopConstantCommands(index);
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
            case "pushstatic":
                return getPushStaticCommands(index);
            case "popstatic":
                return getPopStaticCommands(index);
            case "pushtemp":
                return getPushTemptCommands(index);
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
        return null;
    }

    private String getPushConstantCommands (int index) {
        return
            "@" + index + "\n" +
            "D=A\n" +                   //set value to given index
            "@SP\n" +
            "A=M\n" +                  // go to sp address
            "M=D\n" +                  // put value in tha stack
            "@SP\n" +                  // go to sp
            "M=M+1\n";                  // increase sp by 1
    }

    private String getPopConstantCommands (int index) {
        return
                "@SP\n"+
                "D=M\n"+
                "@" + index + "\n" +
                "D=D+A\n" +                    // get value from given index
                "@SP\n" +                   // go to sp
                "M=M-1\n";                   // decrease sp value by 1;
    }

    private String getPushLocalCommands (int index) {
        return
                "@LCL\n" +
                        "D=M\n" +                   // get LCL address
                        "@" + index + "\n" +   // get index value
                        "A=D+A\n" +                 // set address to relevant local address
                        "D=M\n" +                // get value
                        "@SP\n" +
                        "A=M\n" +                // go to sp address
                        "M=D\n" +                  // put value in the stack
                        "@SP\n" +                  // go to sp
                        "M=M+1\n";                // increase sp value by 1
    }

    private String getPopLocalCommands (int index) {
        return
                "@LCL\n" +
                "D=M\n" +                // get LCL address
                "@" + index + "\n" +    // get index value
                "A=D+A\n" +              // set address to relevant local address
                "D=M\n" +                // get value
                "@SP\n" +
                "M=M-1\n";                 // decrease sp value by 1
    }

    private String getPushArgumentCommands (int index) {
        return "@ARG\n" +
                "D=M\n" +                // get ARG address
                "@" + index + "\n" +    // get index value
                "A=D+A\n" +              // set address to relevant ARG address
                "D=M\n" +                // get value
                "@SP\n" +
                "A=M\n" +                // go to sp address
                "M=D\n" +                  // put value in the stack
                "@SP\n" +                  // go to sp
                "M=M+1\n";                 // increase sp value by 1

    }

    private String getPopArgumentCommands (int index) {
        return "@ARG\n" +
                "D=M\n" +                // get ARG address
                "@" + index + "\n" +    // get index value
                "A=D+A\n" +              // set address to relevant ARG address
                "D=M\n" +                // get value
                "@SP\n" +
                "M=M-1\n";                 // decrease sp value by 1
    }

    private String getPushThisCommands (int index) {
        return "@THIS\n" +
                "D=M\n" +                // get THIS address
                "@" + index + "\n" +    // get index value
                "A=D+A\n" +              // set address to relevant THIS address
                "D=M\n" +                // get value
                "@SP\n" +
                "A=M\n" +                // go to sp address
                "M=D\n" +                  // put value in the stack
                "@SP\n" +                  // go to sp
                "M=M+1\n";                 // increase sp value by 1
    }

    private String getPopThisCommands (int index) {
        return "@THIS\n" +
                "D=M\n" +                // get THIS address
                "@" + index + "\n" +    // get index value
                "A=D+A\n" +              // set address to relevant THIS address
                "D=M\n" +                // get value
                "@SP\n" +
                "M=M-1\n";                 // decrease sp value by 1
    }

    private String getPushThatCommands (int index) {
        return "@THAT\n" +
                "D=M\n" +                // get THAT address
                "@" + index + "\n" +    // get index value
                "A=D+A\n" +              // set address to relevant THAT address
                "D=M\n" +                // get value
                "@SP\n" +
                "A=M\n" +                // go to sp address
                "M=D\n" +                  // put value in the stack
                "@SP\n" +                  // go to sp
                "M=M+1\n";                 // increase sp value by 1
    }

    private String getPopThatCommands (int index) {
        return "@THAT\n" +
                "D=M\n" +                // get THAT address
                "@" + index + "\n" +    // get index value
                "A=D+A\n" +              // set address to relevant THAT address
                "D=M\n" +                // get value
                "@SP\n" +
                "M=M-1\n";                 // decrease sp value by 1
    }

    private String getPushStaticCommands (int index) {
        return "@16\n" +
                "D=M\n" +                // get first static variable address
                "@" + index + "\n" +    // get index value
                "A=D+A\n" +              // set address to relevant static variable address
                "D=M\n" +                // get value
                "@SP\n" +
                "A=M\n" +                // go to sp address
                "M=D\n" +                  // put value in the stack
                "@SP\n" +                  // go to sp
                "M=M+1\n";                 // increase sp value by 1;
    }

    private String getPopStaticCommands (int index) {
        return "@16\n" +
                "D=M\n" +                    // get first static variable address
                "@" + index + "\n" +        // get index value
                "A=D+A\n" +                  // set address to relevant static variable address
                "D=M\n" +                    // get value
                "@SP\n" +
                "M=M-1\n";                   // decrease sp value by 1;
    }

    private String getPushTemptCommands (int index) {
        return "@R5\n" +
                "D=M\n" +                    // get first temp variable address
                "@" + index + "\n" +        // get index value
                "A=D+A\n" +                  // set address to relevant temp variable address
                "D=M\n" +                    // get value
                "@SP\n" +
                "A=M\n" +                    // go to sp address
                "M=D\n" +                    // put value in the stack
                "@SP\n" +                    // go to sp
                "M=M+1\n";                   // increase sp value by 1;
    }

    private String getPopTempCommands (int index) {
        return "@R5\n" +
                "D=M\n" +                    // get first static temp address
                "@" + index + "\n" +        // get index value
                "A=D+A\n" +                  // set address to relevant temp variable address
                "D=M\n" +                    // get value
                "@SP\n" +
                "M=M-1\n";                   // decrease sp value by 1;
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
                "@jumpTo" + labelNum + "\n" +   // set label address to jump to if x == y
                "D;JEQ\n" +     // jump if equal
                "@SP\n" +       // go back to sp address
                "A=M-1\n" +     // go to first stack item
                "M=0\n" +       // put result in stack
                "(jumpTo" + labelNum + ")\n";  // label to jump to if x == y
    }

    private String getGtCommands () {
        labelNum++;
        return "@SP\n" +
                "AM=M-1\n" +    // decrease sp by 1 and go to first item in the stack
                "D=M\n" +       // set D to first item value
                "A=A-1\n" +     // go to second item in the stack
                "D=M-D\n" +     // subtract second item value from D
                "M=-1\n" +      // tentatively set result to true
                "@jumpTo" + labelNum + "\n" +   // set label address to jump to if x > y
                "D;JGT\n" +     // jump if equal
                "@SP\n" +       // go back to sp address
                "A=M-1\n" +     // go to first stack item
                "M=0\n" +       // put result in stack
                "(jumpTo" + labelNum + ")\n";   // label to jump to if x > y      // add second item value to D and put result in stack
    }

    private String getLtCommands () {
        labelNum++;
        return "@SP\n" +
                "AM=M-1\n" +    // decrease sp by 1 and go to first item in the stack
                "D=M\n" +       // set D to first item value
                "A=A-1\n" +     // go to second item in the stack
                "D=M-D\n" +     // subtract second item value from D
                "M=-1\n" +      // tentatively set result to true
                "@jumpTo" + labelNum + "\n" +   // set label address to jump to if x < y
                "D;JLT\n" +     // jump if equal
                "@SP\n" +       // go back to sp address
                "A=M-1\n" +     // go to first stack item
                "M=0\n" +       // put result in stack
                "(jumpTo" + labelNum + ")\n";   // label to jump to if x < y
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
