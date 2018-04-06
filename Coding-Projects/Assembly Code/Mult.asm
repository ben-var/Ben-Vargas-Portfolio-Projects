// This file is part of www.nand2tetris.org
// and the book "The Elements of Computing Systems"
// by Nisan and Schocken, MIT Press.
// File name: projects/04/Mult.asm
// Written by Ben Vargas
// CS220: Prof. Nery Chapeton-Lamas

// Multiplies R0 and R1 and stores the result in R2.
// (R0, R1, R2 refer to RAM[0], RAM[1], and RAM[2], respectively.)

@2           // A = 2 
M = 0        // RAM[2] = 0 (Initializing RAM[2] to zero)
@1           // A = 1
D = M        // D = RAM[1]
@multiplier  // A = multiplier (new variable in memory to avoid modifying RAM[1])
M = D        // RAM[multiplier] = RAM[1]

// add R0 to R2 while multiplier is not zero (check at beginning)
(LOOP)
	@multiplier   // A = 1
	D = M         // D = RAM[1], (check for zero first before performing addition loop)
	@END          // A = END
	D;JEQ         // If D (i.e., multiplier) == 0, JUMP to END
	@0	      // A = 0
	D = M         // D = RAM[0]
	@2            // A = 2
	M = M + D     // RAM[2] = RAM[2] + RAM[0]
	@multiplier   // A = multiplier
	M = M - 1     // RAM[multiplier]--
	@LOOP         // A = LOOP
	0; JMP        // Unconditional Jump to LOOP
(END)
	@END          // A = END
	0; JMP        // Unconditional Jump to END (infinite loop required to end program)
