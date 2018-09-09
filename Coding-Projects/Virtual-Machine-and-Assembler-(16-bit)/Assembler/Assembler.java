/**
 * Author: Ben Vargas
 * Course: CS220 - Computer Architecture and Assembly Language
 * Professor: Nery Chapeton-Lamas
 * Project: Assembler (HACK Architecture)
 */
import java.io.FileOutputStream;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.io.PrintWriter;

public class Assembler {

    // ALGORITHM:
    // get input file name
    // create output file name and stream

    // create symbol table
    // do first pass to build symbol table (no output yet!)
    // do second pass to output translated ASM to HACK code

    // print out "done" message to user
    // close output file stream

    // variable is static because the helper methods need to access the ramAddress, and it's
    // state is required to be maintained
    private static int ramAddress;

    public static void main(String[] args) {

        String inputFileName, outputFileName;
        PrintWriter outputFile = null; //keep compiler happy
        SymbolTable symbolTable;

        //get input file name from command line or console input
        if(args.length == 1) {
            System.out.println("command line arg = " + args[0]);
            inputFileName = args[0];
        }
        else
        {
            Scanner keyboard = new Scanner(System.in);

            System.out.println("Please enter assembly file name you would like to assemble.");
            System.out.println("Don't forget the .asm extension: ");
            inputFileName = keyboard.nextLine();

            keyboard.close();
        }

        outputFileName = inputFileName.substring(0,inputFileName.lastIndexOf('.')) + ".hack";

        try {
            outputFile = new PrintWriter(new FileOutputStream(outputFileName));
        } catch (FileNotFoundException ex) {
            System.err.println("Could not open output file " + outputFileName);
            System.err.println("Run program again, make sure you have write permissions, etc.");
            System.exit(0);
        }

        symbolTable = new SymbolTable();

        // do first pass to build symbol table (no output yet!)
        firstPass(inputFileName, symbolTable);

        // do second pass to output translated ASM to HACK code
        ramAddress = 16;
        secondPass(inputFileName, symbolTable, outputFile);

        System.out.println("Congratulations, file has been successfully converted.");
        outputFile.close();
    }

    /**
     * firstPass
     * <p>
     *     Method used to ensure labels are properly stored to a hashtable, and that
     *     they map to the appropriate ROM instruction. This method should be called
     *     before secondPass to correctly translate an assembly file (.asm) to a .hack
     *     machine instruction file.
     * </p>
     * Precondition: Valid filename is passed to the method, and is an .asm file
     * Postcondition: The assembly code will still need to be converted to binary,
     *                this method is strictly for storing LABELS to the symbolTable.
     * @param inputFileName - the filename of the .asm file to parse
     * @param symbolTable - the symbol table maintained by the driver of the Assembler.
     */
    private static void firstPass(String inputFileName, SymbolTable symbolTable) {
        Parser parser = new Parser(inputFileName);

        char currentInstType;
        int romAddress = 0;
        while (parser.hasMoreCommands()) {
            parser.advance();

            // only A or C instructions count as ROM address lines
            currentInstType = parser.getCommandType();
            switch (currentInstType) {
                case Parser.A_COMMAND:
                    romAddress++;
                    break;
                case Parser.C_COMMAND:
                    romAddress++;
                    break;
                case Parser.L_COMMAND:
                    symbolTable.addEntry(parser.getSymbol(), romAddress);
                    break;
                case Parser.NO_COMMAND:
                    // do nothing, case here to illustrate all 'possible' cases.
                    break;
                default:
                    throw new IllegalStateException("Fatal error resulting from instruction type of parser.");
            }
        }
    }

    /**
     * secondPass
     * <p>
     *     Method used to translate assembly instructions into binary instructions. After first pass
     *     is run, L-Commands are no longer required to be handled. A instructions are built with the
     *     buildAInstruction helper method, and C instructions are built with buildCInstruction
     * </p>
     * Precondition: firstPass has been run
     * Postcondition: translates instructions into 16-bit instruction for HACK architecture
     * @param inputFileName - filename to pass to a new Parser that will go through the .asm file
     * @param symbolTable - symbolTable being maintained by the driver
     * @param outputFile - the file to write the final instructions to
     */
    private static void secondPass(String inputFileName, SymbolTable symbolTable, PrintWriter outputFile) {
        Parser parser = new Parser(inputFileName);
        Code coder = new Code();

        String machineInstruction = null;

        char currentInstType;
        while (parser.hasMoreCommands()) {
            parser.advance();

            currentInstType = parser.getCommandType();
            switch (currentInstType) {
                case Parser.A_COMMAND:
                    machineInstruction = buildAInstruction(parser, coder, symbolTable);
                    break;
                case Parser.C_COMMAND:
                    machineInstruction = buildCInstruction(parser, coder);
                    break;
                case Parser.L_COMMAND:
                    // do nothing, case here to illustrate all 'possible' cases.
                    break;
                case Parser.NO_COMMAND:
                    // do nothing, case here to illustrate all 'possible' cases.
                    break;
                default:
                    throw new IllegalStateException("Fatal error resulting from instruction type of parser.");
            }

            /**
             * To debug, add the following string to the println:  + "  |  " + parser.getCleanLine()
             *
             * This will add the assembly instruction to the .hack file for illustration purposes
             */
            if(currentInstType == 'A' || currentInstType == 'C') {
                outputFile.println(machineInstruction);
            }
        }
    }

    /**
     * buildAInstruction
     * <p>
     *     Builds an A instruction (machine code) that is composed of a 16-bit binary word.
     *     If the instruction uses a symbol, the symbol will be looked up in the passed
     *     symbol table. If the symbol does not exist in the table, then it will be added
     *     to the symbol table with the next available RAM address. If the symbol is a valid
     *     numeric symbol, the number will take the 'value' portion of the 16-bit instruction
     *
     *     A instruction: [0][v][v][v][v][v][v][v][v][v][v][v][v][v][v][v]
     *     v = value
     * </p>
     * Precondition: Valid instruction is passed to the method, parser points to valid .asm file.
     *               The method will also NEED to use the static ramAddress variable to know which
     *               address in RAM is the next available based off what has already been added.
     * Postcondition: 16-bit binary instruction (A-instruction) per HACK architecture is returned.
     * @param parser - parser object pointing to an .asm file
     * @param code - code object used to convert symbols into binary instructions
     * @return - binary string 16-bit A instruction
     */
    private static String buildAInstruction(Parser parser, Code code, SymbolTable symbolTable) {
        String aInstruction = "0";
        String currentSymbol = parser.getSymbol();
        int decimalAddress;
        String value;

        // if try is completed, no symbol necessary (integer literal)
        // otherwise, symbol is looked up and added if necessary
        try {
            decimalAddress = Integer.parseInt(currentSymbol);
            value = code.decimalToBinary(decimalAddress);
        } catch (NumberFormatException e) {
            if (!symbolTable.contains(currentSymbol)) {
                symbolTable.addEntry(currentSymbol, ramAddress);
                ramAddress++;
            }

            decimalAddress = symbolTable.getAddress(currentSymbol);
            value = code.decimalToBinary(decimalAddress);
        }

        value = value.substring(1, value.length()); // ensuring 15 bit for 'value' semantics

        aInstruction += value;
        return aInstruction;
    }

    /**
     * buildCInstruction
     * <p>
     *     Builds a C instruction (machine code) that is composed of a 16-bit binary word.
     *
     *     C instruction: [1][1][1][c1][c2][c3][c4][c5][c6][d1][d2][d3][j1][j2][j3]
     *     c - comp, d - dest, j - jump
     * </p>
     * Precondition: Valid instruction is passed to the method, parser points to valid .asm file
     * Postcondition: 16-bit binary instruction (C-instruction) per hack architecture is returned.
     * @param parser - parser object pointing to an .asm file
     * @param code - code object used to convert symbols into binary instructions
     * @return - binary string 16-bit C instruction
     */
    private static String buildCInstruction(Parser parser, Code code) {
        String cInstruction = "111";

        cInstruction += code.getComp(parser.getComp());
        cInstruction += code.getDest(parser.getDest());
        cInstruction += code.getJump(parser.getJump());

        return cInstruction;
    }

}