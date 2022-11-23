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

(LOOP) // infinite loop that listens to the keyboard input
   @24576
   D = M // set D = keyboard value
   @LOOP_BLANKES 
   D;JGT // if key is pressed go to blank loop
   @LOOP_CLEAR
   D;JEQ // if no key is pressed go to clear loop
   @LOOP
   0;JMP
(LOOP_END)
(LOOP_BLANKES)
    @OFFSET // reset offset to 0;
    M = 0
    (LOOP_SCREEN_BLANKS)
        @OFFSET
        D = M // set D = offset
        @8192
        D = A - D // check if reached the end of the screen
        @LOOP_SCREEN_END
        D;JEQ // if reached end of the screen end loop
        @OFFSET
        D = M // set D = offset
        @SCREEN
        A = A + D // set screen address to current address
        M = -1 // blank the current pixel's block 
        @OFFSET
        M = M + 1 // increment offset by 1
        @24576
        D = M // set D = keyboard value
        @LOOP_CLEAR
        D;JEQ // if key is no longer pressed go to clear loop
        @LOOP_SCREEN_BLANKS
        0;JMP // otherwise continue blank loop
    (LOOP_SCREEN_END)
    @LOOP
    0;JMP
(LOOP_BLANKES_END)
(LOOP_CLEAR)
    @OFFSET // reset offset to 0;
    M = 0 
    (LOOP_SCREEN_CLEAR)
        @OFFSET // set D = offset
        D = M
        @8192
        D = A - D // check if reached the end of the screen
        @LOOP_SCREEN_END
        D;JEQ // if reached end of the screen end loop
        @OFFSET
        D = M // set D = offset
        @SCREEN
        A = A + D // set screen address to current address
        M = 0 // blank the current pixel's block 
        @OFFSET
        M = M + 1 // increment offset by 1
        @24576
        D = M
        @LOOP_BLANKES
        D;JGT // if key is pressed go to blank loop
        @LOOP_SCREEN_CLEAR
        0;JMP // otherwise continue clear loop
    (LOOP_SCREEN_END)
    @LOOP
    0;JMP // continue infint loop
(LOOP_CLEAR_END)
    


