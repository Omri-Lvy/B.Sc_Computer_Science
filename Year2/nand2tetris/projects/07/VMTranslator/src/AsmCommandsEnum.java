public enum AsmCommandsEnum {

        add("@SP\n"+
                "D=M\n"+
                ""),
        C_PUSH("C_PUSH"),
        C_POP("C_POP"),
        C_LABEL("C_LABEL"),
        C_GOTO("C_GOTO"),
        C_IF("C_IF"),
        C_FUNCTION("C_FUNCTION"),
        C_RETURN("C_RETURN"),
        C_CALL("C_CALL");
        AsmCommandsEnum(String type) {
            this.asmCommands = type;
        };
        private String asmCommands;

        public String getAsmCommands () {
            return asmCommands;
        }
    }