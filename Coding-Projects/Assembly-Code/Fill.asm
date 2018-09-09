// This file is part of www.nand2tetris.org
// and the book "The Elements of Computing Systems"
// by Nisan and Schocken, MIT Press.
// File name: projects/04/Fill.asm
// Written by Ben Vargas
// CS220: Prof. Nery Chapeton-Lamas

// Runs an infinite loop that listens to the keyboard input.
// When a key is pressed (any key), the program blackens the screen,
// i.e. writes "black" in every pixel;
// the screen should remain fully black as long as the key is pressed. 
// When no key is pressed, the program clears the screen, i.e. writes
// "white" in every pixel;
// the screen should remain fully clear as long as no key is pressed.

// Initializing pointer variable to address: SCREEN
@SCREEN			// A = SCREEN
D = A			// D = @SCREEN
@pointer		// A = pointer
M = D 			// RAM[pointer] = @pointer = @SCREEN

// Storing address for END_OF_SCREEN RAM address bound
@KBD			// A = KBD
D = A			// D = @KBD
@END_OF_SCREEN		// A = END_OF_SCREEN
M = D - 1		// RAM[END_OF_SCREEN] = @KBD - 1

// Storing address for BEFORE_SCREEN RAM address bound
@SCREEN			// A = SCREEN 	
D = A			// D = @SCREEN
@BEFORE_SCREEN		// A = BEFORE_SCREEN
M = D - 1		// RAM[BEFORE_SCREEN] = @SCREEN - 1

// CHECK looks in KBD to see whether to route to DRAW or ERASE
(CHECK)
	@KBD		// A = KBD
	D = M		// D = RAM[KBD]
	@DRAW		// A = DRAW
	D; JGT		// IF D > 0 (key is pressed), JUMP to DRAW
	@ERASE		// A = ERASE
	D; JLE		// IF D <= 0 (key is not pressed), JUMP to ERASE

	// Infinite loop; MUST go to DRAW or ERASE (KBD either >= 0 or > 0)

// DRAW will change pointer's pixel to black and increment pointer if in bounds
(DRAW)
	@pointer	// A = pointer
	D = M		// D = RAM[pointer]
	A = D		// A = RAM[pointer]
	M = -1		// RAM[RAM[pointer]] = BLACK (all 1's is true, 1111 1111 1111 1111 = -1)
	
	// Checking bound
	@END_OF_SCREEN	// A = END_OF_SCREEN
	D = D - M	// D = RAM[pointer] - RAM[END_OF_SCREEN]
	@CHECK		// A = CHECK
	D; JGE		// If D is > 0, JUMP to CHECK (don't increment)

	@pointer	// A = pointer
	M = M + 1	// RAM[pointer]++
	@CHECK		// A = CHECK
	0; JMP		// Unconditional JUMP to A
	
// ERASE will change pointer's pixel to white and decrement pointer if in bounds
(ERASE) 
	@pointer	// A = pointer
	D = M		// D = RAM[pointer]
	A = D		// A = RAM[pointer]
	M = 0		// RAM[RAM[pointer]] = WHITE (all 0's is false, 0000 0000 0000 0000 = 0)
	
	// Checking bound
	@BEFORE_SCREEN	// A = BEFORE_SCREEN
	D = D - M	// D = RAM[pointer] - RAM[BEFORE_SCREEN]
	@CHECK		// A = CHECK
	D; JLT		// If D <= 0, JUMP to CHECK (don't decrement)
	
	@pointer	// A = @pointer
	M = M - 1	// RAM[pointer]--
	@CHECK		// A = @CHECK
	0; JMP		// Unconditional Jump to CHECK
	
	

	
	