public enum AsmCommandsEnum {

        add("@SP\n"+
        // Pop value into D
        "M=M+1\n"+
        "A=M\n" +
        "D=M\n" +
        // Set A as top of stack
        "A=A+1\n" +
        // Add D as top of stack
        "M=D+M\n"),
        sub("@SP\n"+
        // Pop value into D
        "M=M+1\n"+
        "A=M\n" +
        "D=M\n" +
        // Set A as top stack
        "A=A+1\n" +
        // Subtract D
        "M=M-D\n"),
        // get top address
        neg("@SP\n" +
        "A=M+1\n" +
        "M=-M\n"),
        eq("@SP\n"+
        // Pop value into D
        "M=M+1\n"+
        "A=M\n" +
        "D=M\n" +
        "A=A+1\n" +
        "D=M-D\n" +
        // now, D = 0 -> values are equal
        // if eq - skip to -1 (D)
        // else run bitwise
        "@SKIP\n" +
        "D;JEQ\n" +
        "D=-1 \n" +
        "D=!D\n" +
        // save new value
        "@SP\n" +
        "A=M+1\n" +
        "M=D\n"
        ),
        // gt("") ->>>>>>>>>>>>>>>>>>> fix,
        // lt("") ->>>>>>>>>>>>>>>>>>> fix,
        // get top address
        and("@SP\n"+
        // Pop value into D
        "M=M+1\n"+
        "A=M\n" +
        "D=M\n" +
        "A=A+1\n" +
        "M=D&M\n"),
        or("@SP\n"+
        // Pop value into D
        "M=M+1\n"+
        "A=M\n" +
        "D=M\n" +
        "A=A+1\n" +
        "M=D|M\n"),
        not("@SP\n" +
        "A=M+1\n" +
        // bitwise complement top of stack
        "M=!M\n");

        AsmCommandsEnum(String type) {
            this.asmCommands = type;
        };
        private String asmCommands;

        public String getAsmCommands () {
            return asmCommands;
        }
    }