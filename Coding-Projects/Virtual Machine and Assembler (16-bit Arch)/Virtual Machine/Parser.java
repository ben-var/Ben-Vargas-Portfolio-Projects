/**
 * Author: Ben Vargas
 * Course: CS220 - Computer Architecture and Assembly Language
 * Professor: Nery Chapeton-Lamas
 * Project: Virtual Machine Translator (HACK Architecture)
 */

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * Object used to handle file INPUT (.vm files)
 */
public class Parser {

    // HOLDING VALID ARITHMETIC COMMANDS FOR EASY SEARCHING
    private static ArrayList<String> validArithmeticCommands;

    // FINAL STATIC VARIABLES FOR CLEAN COMMAND TYPING
    public static final int C_INVALIDCOMMAND = -1;
    public static final int C_NOCOMMAND = 0;
    public static final int C_ARITHMETIC = 1;
    public static final int C_PUSH = 2;
    public static final int C_POP = 3;
    public static final int C_LABEL = 4;
    public static final int C_GOTO = 5;
    public static final int C_IF = 6;
    public static final int C_FUNCTION = 7;
    public static final int C_RETURN = 8;
    public static final int C_CALL = 9;

    // VARIABLES RELATED TO FILE IO
    private Scanner inputFile;
    private int lineNumber;
    private String rawLine;


    // VARIABLES FROM LAST CALL TO ADVANCE
    private String cleanLine;
    private int currentCommandType;
    private String segment;
    private String rawArg2;
    private String keyword;
    /** variable name rawArg2's need to be converted to an int index for local, static, etc. */
    private int index;


    /**
     * Primary Constructor for Parser
     * <p>
     *     Constructor used to initialize the file IO on a .vm file.
     * </p>
     * Precondition: provided file is VM file
     * Postcondition: if file can't be opened, ends program w/error message
     * @param inFileName - the name of the file to attempt to open an input stream onto.
     */
    public Parser(String inFileName) {
        // opens input file stream and prepares to parse
        try {
            if (inFileName.length() < 4) {
                throw new IllegalArgumentException("Invalid file name. Must end in \".vm\" and have at least" +
                        " one character before the file extension.");
            }

            String fileExtension = inFileName.substring(inFileName.length() - 3);

            if (! (fileExtension.equals(".vm")) ) {
                throw new IllegalArgumentException("Invalid File Extension. Did not find  \".vm\"");
            }

            inputFile = new Scanner(new FileInputStream(inFileName));

            lineNumber = 0;
            initArithmeticCommands();
        } catch(IOException e) {
            System.out.println(e.getMessage());
            System.out.println("There was an issue opening your vm code file. Please check the filename.");
            System.exit(0);
        }
    }

    /**
     * initArithmeticCommands
     * <p>
     *     Method used to load all valid commands into the validArithmeticCommands
     *     ArrayList.
     * <p>
     * Precondition: n/a
     * Postcondition: All valid arithmetic commands will be loaded into the ArrayList
     */
    private void initArithmeticCommands() {
        validArithmeticCommands = new ArrayList<String>();

        validArithmeticCommands.add("add");
        validArithmeticCommands.add("sub");
        validArithmeticCommands.add("neg");
        validArithmeticCommands.add("eq");
        validArithmeticCommands.add("gt");
        validArithmeticCommands.add("lt");
        validArithmeticCommands.add("and");
        validArithmeticCommands.add("or");
        validArithmeticCommands.add("not");
    }

    /**
     * checkIfInputFileIsNonNull
     * <p>
     *     Simple helper method to verify if the Parser object is pointing to a file.
     * </p>
     * Precondition: n/a
     * Postcondition: n/a
     * @throws IllegalStateException - if inputFile is not initialized
     */
    private void checkIfInputFileIsNonNull() throws IllegalStateException {
        if (inputFile == null) {
            throw new IllegalStateException("The Scanner object was not properly initialized to a file.");
        }
    }

    /**
     * checkIfAdvanced
     * <p>
     *     Simple helper method to check whether advance() has been called at least once.
     * </p>
     * Precondition: n/a
     * Postcondition: n/a
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

            this.parse();
        }
        lineNumber++;
    }


    /**
     * parse
     * <p>
     *     Simple Method used to kickoff helper methods that
     *     clean the line and set the command type. Most of the
     *     handling of the command is performed in the CodeWriter class.
     * </p>
     * Precondition: A line has been read by the parser
     * Postcondition: the instance variables of the Parser have been filled
     */
    private void parse() {
        cleanLine();
        setCurrentCommandType();
    }

    /**
     * setCurrentCommandType
     * <p>
     *     Method used to classify a command to a specific type, as defined in the
     *     static variables at the top of the class.
     * </p>
     * Precondition: A valid command is generated by the compiler
     * Postcondition: 1 through 9 (integers) represent valid commands, 0 is no command, else -1
     */
    private void setCurrentCommandType() {
        checkIfAdvanced();

        /**
         * Setting command type for all possible values within valid command syntax of VM language
         */
        if (validArithmeticCommands.contains(keyword)) {
            currentCommandType = C_ARITHMETIC;
        } else if (cleanLine.contains("push")) {
            currentCommandType = C_PUSH;
        } else if (cleanLine.contains("pop")) {
            currentCommandType = C_POP;
        } else if (cleanLine.contains("label")) {
            currentCommandType = C_LABEL;
        } else if (cleanLine.contains("goto")){
            currentCommandType = C_GOTO;
        } else if (cleanLine.contains("if-goto")) {
            currentCommandType = C_IF;
        } else if (cleanLine.contains("function")) {
            currentCommandType = C_FUNCTION;
        } else if (cleanLine.contains("return")) {
            currentCommandType = C_RETURN;
        } else if (cleanLine.contains("call")) {
            currentCommandType = C_CALL;
        // blank command (usually generated from cleaned comment lines)
        } else if (cleanLine.equals("")){
            currentCommandType = C_NOCOMMAND;
        // if this case is reached, the command is invalid.
        } else {
            currentCommandType = C_INVALIDCOMMAND;
        }
    }

    /**
     * getCurrentCommandType
     * <p>
     *     Simple getter method for the current command type @ current line.
     * </p>
     * Precondition: advance and parse have been called
     * Postcondition: returns int of command type (static variables of Parser)
     * @return - int representation of the command type from last call to advance()
     */
    public int getCurrentCommandType() {
        if (currentCommandType == -1) {
            throw new IllegalStateException("An invalid instruction was sent to the translator." +
                    "\nCheck the code of the compiler :(.");
        }
        return currentCommandType;
    }

    /**
     * getSegment
     * <P>
     *     Standard getter for segment variable.
     * </P>
     * Precondition: advance() was called
     * Postcondition: n/a
     * @return - the segment part of a command (if any) of a parsed line
     */
    public String getSegment() {
        return segment;
    }

    /**
     * getIndex
     * <p>
     *     Standard getter for the index variable.
     * </p>
     * Precondition: advance() was called.
     * Postcondition: n/a
     * @return - the index member variable from a parsed line
     */
    public int getIndex() {
        return index;
    }

    /**
     * cleanLine
     * <p>
     *     Method used to clean a line (i.e., removing spaces, comments, and inline comments).
     *     Method also used to tokenize and store command values to individual parts (i.e., <b>keyword</b>,
     *     <b>command</b>, and <b>index</b>).
     * </p>
     * Precondition: line was parsed via a call to advance()
     * Postcondition: returned without comments and whitespace
     */
    private void cleanLine() {
        // removing comments
        int indexSlash;
        if(rawLine.contains("//")) {
            indexSlash = rawLine.indexOf("//");
            rawLine = rawLine.substring(0, indexSlash);
        }

        String[] tokens = rawLine.split(" ");
        keyword = tokens[0]; // always has a keyword (unless blank line)

        if (tokens.length == 3) { // length 3 signals a segment and index

            segment = tokens[1];
            rawArg2 = tokens[2];
            try {
                index = Integer.parseInt(rawArg2);
            } catch (InputMismatchException e) {
                System.err.println(e.getMessage());
            }
        }
        // removing spaces
        cleanLine = rawLine.replaceAll("\\s+", "");
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
     *     Simple accessor method for 'cleaned' line.
     * </p>
     * Precondition: advance and (subsequently) cleanLine() were called
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
