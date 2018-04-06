/**
 * Author: Ben Vargas
 * Course: CS220 - Computer Architecture and Assembly Language
 * Professor: Nery Chapeton-Lamas
 * Project: Assembler (HACK Architecture)
 */

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Scanner;

public class Parser {
    // all possible command types
    public static final char NO_COMMAND = 'N';
    public static final char A_COMMAND = 'A';
    public static final char C_COMMAND = 'C';
    public static final char L_COMMAND = 'L';

    // VARIABLES RELATED TO FILE IO
    private Scanner inputFile;
    private int lineNumber;
    private String rawLine;

    // VARIABLES RELATED TO PARSING A LINE
    private String cleanLine;
    private char commandType;
    private String symbol;
    private String destMnemonic;
    private String compMnemonic;
    private String jumpMnemonic;

    /**
     * Primary Constructor for Parser
     * <p>
     *     Constructor used to initialize the file IO on an .asm file.
     * </p>
     * Precondition: provided file is ASM file
     * Postcondition: if file can't be opened, ends program w/error message
     * @param inFileName - the name of the file to attempt to open an input stream onto.
     */
    public Parser(String inFileName) {
        // opens input file stream and prepares to parse
        try {
            if (inFileName.length() < 5) {
                throw new IllegalArgumentException("Invalid file name. Must end in \".asm\" and have at least" +
                        " one character before the file extension.");
            }

            String fileExtension = inFileName.substring(inFileName.length() - 4);

            if (! (fileExtension.equals(".asm")) ) {
                throw new IllegalArgumentException("Invalid File Extension. Did not find  \".asm\"");
            }

            inputFile = new Scanner(new FileInputStream(inFileName));
        } catch(IOException e) {
            System.out.println(e.getMessage());
            System.out.println("There was an issue opening your assembly code file. Please check the filename.");
            System.exit(0);
        }
    }

    /**
     * checkIfInputFileIsNonNull
     * <p>
     *     Simple helper method to verify if the Parser object is pointing to a file.
     * </p>
     * @throws IllegalStateException - if inputFile is not initialized
     */
    private void checkIfInputFileIsNonNull() throws IllegalStateException {
        if (inputFile == null) {
            throw new IllegalStateException("The Scanner object was not properly initialized.");
        }
    }

    /**
     * checkIfAdvanced
     * <p>
     *     Simple helper method to check whether advance() has been called at least once.
     * </p>
     */
    private void checkIfAdvanced() {
        if (cleanLine == null) {
            throw new IllegalStateException();
        }
    }

    /**
     * hasMoreCommands
     * <p>
     *     Simple wrapper method that determines whether there are more lines to parse or not.
     * </p>
     * Precondition: file stream is open
     * Postcondition: returns true if more commands, else closes stream
     * @return - true if there is an additional command to parse, false otherwise.
     */
    public boolean hasMoreCommands() {
        checkIfInputFileIsNonNull(); // avoiding null pointer

        return inputFile.hasNextLine();
    }

    /**
     * advance
     * <p>
     *     PUBLIC method used by outside class in order to advance to the next line.
     *     Call to this method automatically cleans and parses the line that is returned
     *     from the nextLine method of Scanner called on the inputFile.
     * </p>
     * Precondition: file stream is open, called only if hasMoreCommands()
     * Postcondition: current instruction parts put into instance variables
     */
    public void advance() {
        checkIfInputFileIsNonNull(); // avoiding null pointer

        if (hasMoreCommands()) {
            rawLine = inputFile.nextLine();
            cleanLine();

            parse();
        }
    }

    /**
     * cleanLine
     * <p>
     *     Method used to clean a line (i.e., removing spaces, comments, and inline comments
     * </p>
     * Precondition: String parameter given (not null)
     * Postcondition: returned without comments and whitespace
     */
    private void cleanLine() {
        int indexSlash;
        if(rawLine.contains("//")) {
            indexSlash = rawLine.indexOf("//");
            rawLine = rawLine.substring(0, indexSlash);
        }
        cleanLine = rawLine.replaceAll("\\s+", "");
    }

    /**
     * parseCommandType
     * <p>
     *     Method used to determine the type of command that the current line
     *     of the parser is.
     * </p>
     * Precondition: String parameter is clean instruction
     * Postcondition: returns 'A' (A-instruction), 'C' (C-Instruction),
     *                'L' (Label), 'N' (no command)
     */
    private void parseCommandType() {
        if(cleanLine == null || cleanLine.length() == 0) {
            commandType = NO_COMMAND;
        } else if (cleanLine.charAt(0) == '@') {
            commandType = A_COMMAND;
        } else if (cleanLine.charAt(0) == '(') {
            commandType = L_COMMAND;
        } else {
            commandType = C_COMMAND;
        }
    }

    /**
     * parse
     * <p>
     *     Method is used to obtain and store crucial parts of an Assembly
     *     instruction (e.g., symbol for A instruction, dest/comp/jump for
     *     C instruction.
     * </p>
     * Precondition: advance() called so cleanLine has value
     * Postcondition: appropriate parts (instance vars) of instruction filled
     */
    private void parse() {
        checkIfAdvanced();

        parseCommandType();
        switch(commandType) {
            case A_COMMAND:
                parseSymbol();
                break;
            case L_COMMAND:
                parseSymbol();
                break;
            case C_COMMAND:
                parseDest();
                parseComp();
                parseJump();
                break;
            default:
                break;
        }

    }

    /**
     * parseSymbol
     * <p>
     *     Method used to obtain the symbol from the current clean line
     *     and store id to the instance variable inside the Parser object.
     *     This method is specifically for A-Instructions. The symbol
     *     does NOT contain the '(', ')', or '@' characters.
     * </p>
     * Precondition: advance() called so cleanLine has value, call for
     *               A- and L-commands only
     * Postcondition: symbol has appropriate value from instruction assigned
     */
    private void parseSymbol() {
        if(cleanLine.charAt(0) == '(') {
            if (cleanLine.length() < 3 || cleanLine.charAt(cleanLine.length() - 1) != ')') {
                System.err.println("Either missing closing parenthesis or missing label name: \"" + cleanLine + "\"");
                throw new IllegalArgumentException("Illegal expression: " + cleanLine);
            }
            symbol = cleanLine.substring(1, cleanLine.length() - 1);
        } else {
            symbol = cleanLine.substring(1);
        }
    }

    /**
     * parseDest
     * <p>
     *     Method used to obtain the dest mnemonic and store it to the instance
     *     variable inside the Parser object.
     *     This method is specifically for C-Instructions.
     * </p>
     * Precondition: advance() called so cleanLine has value, call for C-inst only
     * Postcondition: destMnemonic set to appropriate value from instruction
     */
    private void parseDest() {
        int indexOfEquals = cleanLine.indexOf("=");
        if (indexOfEquals == -1) {
            destMnemonic = "NULL";
        } else {
            destMnemonic = cleanLine.substring(0, indexOfEquals);
        }
    }

    /**
     * parseComp
     * <p>
     *     Method used to obtain the comp mnemonic and store it to the instance
     *     variable inside the Parser object.
     *     This method is specifically for C-Instructions.
     * </p>
     * Precondition: advance() called so cleanLine has value, call for C-inst only
     * Postcondition: compMnemonic set to appropriate value from instruction
     */
    private void parseComp() {
        int begin, end;

        // if cleanLine does NOT contain a "=", then indexOf returns -1, making begin 0 as intended.
        begin = cleanLine.indexOf("=") + 1;

        if(cleanLine.contains(";")) {
            end = cleanLine.indexOf(";");
        } else {
            end = cleanLine.length();
        }
        compMnemonic = cleanLine.substring(begin, end);
    }

    /**
     * parseJump
     * <p>
     *     Method used to obtain the jump mnemonic and store it to the instance
     *     variable inside the Parser object.
     *     This method is specifically for C-Instructions.
     * </p>
     * Precondition: advance() called so cleanLine has value, call for C-inst only
     * Postcondition: jumpMnemonic set to appropriate value from instruction
     */
    private void parseJump() {
        int begin;
        char lastChar = cleanLine.charAt(cleanLine.length() - 1);
        if(cleanLine.contains(";") && lastChar != ';'){
            begin = cleanLine.indexOf(";") + 1;
            jumpMnemonic = cleanLine.substring(begin);
        } else {
            jumpMnemonic = "NULL";
        }
    }

    /**
     * getCommandType
     * <p>
     *     Simple accessor method for the current command type @ current line.
     * </p>
     * Precondition: cleanLine has been parsed (advance was called)
     * Postcondition: returns char for command type (N/A/C/L)
     * @return - character representation of the command type from last call to advance()
     */
    public char getCommandType() {
        return commandType;
    }

    /**
     * getSymbol
     * <p>
     *     Simple getter for the currently stored symbol.
     * </p>
     * Precondition: cleanLine has been parsed (advance was called), call for
     *               labels only (use getCommandType())
     * Postcondition: returns string for symbol name
     * @return - current symbol from last A or L - Inst call to parse()
     */
    public String getSymbol() {
        return symbol;
    }

    /**
     * getDest
     * <p>
     *     Simple getter for the destMnemonic currently stored
     * </p>
     * Precondition: cleanLine has been parsed (advance was called),
     *               call for C-instructions only
     * Postcondition: returns mnemonic (ASM symbol) for dest part
     * @return - current compMnemonic from last C-Inst call to parse()
     */
    public String getDest() {
        return destMnemonic;
    }

    /**
     * getComp
     * <p>
     *     Simple getter for the compMnemonic currently stored
     * </p>
     * Precondition: cleanLine has been parsed (advance was called),
     *               call for C-instructions only
     * Postcondition: returns mnemonic (ASM symbol) for comp part
     * @return - current compMnemonic from last C-Inst call to parse()
     */
    public String getComp() {
        return compMnemonic;
    }

    /**
     * getJump
     * <p>
     *     Simple getter for the jumpMnemonic currently stored
     * </p>
     * Precondition: cleanLine has been parsed (advance was called),
     *               call for C-instructions only
     * Postcondition: returns mnemonic (ASM symbol) for jump part
     * @return - current jumpMnemonic from last C-Inst call to parse()
     */
    public String getJump() {
        return jumpMnemonic;
    }

    /**
     * getCommandTypeString (Debugging method)
     * <p>
     *     Simple accessor method for the current command type @ current line.
     *     This method is specifically for a String type of the command type.
     * </p>
     * Precondition: advance and parse have been called
     * Postcondition: returns string of command type
     * @return - String representation of the command type from last call to advance()
     */
    public String getCommandTypeString() {
        return Character.toString(commandType);
    }

    /**
     * getRawLine
     * <p>
     *     Simple accessor method for current 'raw' line.
     * </p>
     * Precondition: advance was called to put value from file in here
     * Postcondition: returns string of current original line from file
     * @return - current line BEFORE cleanLine() has been called in advance()
     */
    public String getRawLine() {
        return rawLine;
    }

    /**
     * getCleanLine
     * <p>
     *     Simple accessor method for current 'cleaned' line.
     * </p>
     * Precondition: advance and cleanLine were called
     * Postcondition: returns string of current clean instruction from file
     * @return - current line AFTER cleanLine() has been called in advance()
     */
    public String getCleanLine() {
        return cleanLine;
    }

    /**
     * getLineNumber (debugging)
     * <p>
     *     Simple accessor method for lineNumber.
     * </p>
     * Precondition: n/a
     * Postcondition: returns line number currently being processed from file
     * @return - current line number
     */
    public int getLineNumber() {
        return lineNumber;
    }

}
