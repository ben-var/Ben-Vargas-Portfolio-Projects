/**
 * Author: Ben Vargas
 * Course: CS220 - Computer Architecture and Assembly Language
 * Professor: Nery Chapeton-Lamas
 * Project: Virtual Machine Part 1 (HACK Architecture)
 */
import java.util.Scanner;

/**
 * Primary driver for Virtual Machine Translator.
 */
public class VirtualMachine {

    // ALGORITHM:
    // get input file name
    // create output file name and stream

    // translate VM to ASM code while .vm file has a next line.
    //     - parse command and (segment,  index) if any

    // print out "done" message to user
    // close output file stream

    public static void main(String[] args) {

        String inputFileName;
        String outputFileName = null; // outputFileName, parser, and codeWriter must be null for successful try/catch
        Parser parser = null;
        CodeWriter codeWriter = null;

        //get input file name from command line or console input
        if(args.length == 1) {
            System.out.println("command line arg = " + args[0]);
            inputFileName = args[0];
        } else {
            Scanner keyboard = new Scanner(System.in);

            System.out.println("Please enter VM code file name you would like to translate to assembly.");
            System.out.println("Don't forget the .vm extension: ");
            inputFileName = keyboard.nextLine();

            keyboard.close();
        }
        try {
            outputFileName = inputFileName.substring(0, inputFileName.lastIndexOf('.')) + ".asm";

        } catch (IndexOutOfBoundsException e) {
            System.err.println("Something went wrong trying to write to your file. \nPlease ensure the " +
                    "correct file name was entered, with the correct file extension (i.e., \".vm\")");
            System.exit(0);
        }

        parser = new Parser(inputFileName);
        codeWriter = new CodeWriter(outputFileName);

        String line, segment;
        int index, commandType;

        while(parser.hasMoreCommands()){
            parser.advance();

            line = parser.getCleanLine();
            segment = parser.getSegment();
            index = parser.getIndex();
            commandType = parser.getCurrentCommandType();

            // enable this line of code to view a debugging message for each line of the code.
            // printDebugLine(line, segment, index, parser);

            codeWriter.writeCommand(line, segment, index, commandType);
        }

        System.out.println("Congratulations, file has been successfully converted.");
        codeWriter.close();
    }

    /**
     * printDebugLine (Debugging method)
     * <p>
     *     Simple method to print out some diagnostic information about each line.
     * </p>
     * @param line - line last parsed with a call to Parser.advance()
     * @param segment - segment from last line parsed with call to Parser.advance()
     * @param index - index from last line parsed with call to Parser.advance()
     * @param parser - parser object that should be instantiated in main method
     * Precondition: parser was instantiated by body of code that called this method
     * Postcondition: n/a
     */
    public static void printDebugLine(String line, String segment, int index, Parser parser) {
        System.err.println("line number " + parser.getLineNumber() + ": "
                + line + " segment: " + segment + " index: " + index +
                " cmdType: " + parser.getCurrentCommandType());
    }

}
