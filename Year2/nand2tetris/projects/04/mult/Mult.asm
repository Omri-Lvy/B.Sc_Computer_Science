// This file is part of www.nand2tetris.org
// and the book "The Elements of Computing Systems"
// by Nisan and Schocken, MIT Press.
// File name: projects/04/Mult.asm

// Multiplies R0 and R1 and stores the result in R2.
// (R0, R1, R2 refer to RAM[0], RAM[1], and RAM[2], respectively.)
//
// This program only needs to handle arguments that satisfy
// R0 >= 0, R1 >= 0, and R0*R1 < 32768.

// Put your code here.
// Set sum to 0
@R2
M = 0
//Set counter to R0
@R0
D = M
@COUNTER
M = D
// While counter > 0 add R1 to sum
(LOOP)
    @COUNTER
    D = M
    @LOOP_END
    D; JEQ
    @R1
    D = M // Set D = R1
    @R2
    M = M + D // add R1 to sum
    @COUNTER
    M = M - 1 // decrement counter by 1
    @LOOP
    0; JMP
(LOOP_END)
