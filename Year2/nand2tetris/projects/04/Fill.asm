// This file is part of www.nand2tetris.org
// and the book "The Elements of Computing Systems"
// by Nisan and Schocken, MIT Press.
// File name: projects/04/Fill.asm

// Runs an infinite loop that listens to the keyboard input.
// When a key is pressed (any key), the program blackens the screen,
// i.e. writes "black" in every pixel;
// the screen should remain fully black as long as the key is pressed. 
// When no key is pressed, the program clears the screen, i.e. writes
// "white" in every pixel;
// the screen should remain fully clear as long as no key is pressed.

// Put your code here.

(LOOP)
   @24576
   D = M
   @LOOP_BLANKES
   D;JGT
   @LOOP_CLEAR
   D;JEQ
   @LOOP
   0;JMP
(LOOP_END)
(LOOP_BLANKES)
    @OFFSET
    M = 0
    (LOOP_SCREEN_BLANKS)
        @OFFSET
        D = M
        @8192
        D = A - D
        @LOOP_SCREEN_END
        D;JEQ
        @OFFSET
        D = M
        @SCREEN
        A = A + D
        M = -1
        @OFFSET
        M = M + 1
        @24576
        D = M
        @LOOP_CLEAR
        D;JEQ
        @LOOP_SCREEN_BLANKS
        0;JMP
    (LOOP_SCREEN_END)
    @LOOP
    0;JMP
(LOOP_BLANKES_END)
(LOOP_CLEAR)
    @OFFSET
    M = 0
    (LOOP_SCREEN_CLEAR)
        @OFFSET
        D = M
        @8192
        D = A - D
        @LOOP_SCREEN_END
        D;JEQ
        @OFFSET
        D = M
        @SCREEN
        A = A + D
        M = 0
        @OFFSET
        M = M + 1
        @24576
        D = M
        @LOOP_BLANKES
        D;JGT
        @LOOP_SCREEN_CLEAR
        0;JMP
    (LOOP_SCREEN_END)
    @LOOP
    0;JMP
(LOOP_CLEAR_END)
    

