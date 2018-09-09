/**
 * Author: Ben Vargas
 * Course: CS220 - Computer Architecture and Assembly Language
 * Professor: Nery Chapeton-Lamas
 * Project: Assembler (HACK Architecture)
 */
import java.util.HashMap;

public class Code {
    /** HashMaps that are used to store the binary representation of mnemonics */
    private HashMap<String, String> compCodes;
    private HashMap<String, String> destCodes;
    private HashMap<String, String> jumpCodes;

    /**
     * Default Constructor for Code Object
     * <p>
     *     Initializes code HashMaps with pre-determined, hardcoded
     *     translations of code into their binary, machine code
     *     versions as defined within our HACK architecture.
     * </p>
     * Precondition: Comp codes = 7 bits (a and c1-c6), and dest/jump
     *               codes = 3 bits (d/j1-d/j3).
     * Postcondition: All hashmaps have lookups for valid codes
     */
    public Code() {
        destCodes = new HashMap<String, String>();
        destCodes.put("NULL","000");
        destCodes.put("M","001");
        destCodes.put("D","010");
        destCodes.put("MD","011");
        destCodes.put("A","100");
        destCodes.put("AM","101");
        destCodes.put("AD","110");
        destCodes.put("AMD","111");

        jumpCodes = new HashMap<String,String>();
        jumpCodes.put("NULL", "000");
        jumpCodes.put("JGT", "001");
        jumpCodes.put("JEQ", "010");
        jumpCodes.put("JGE", "011");
        jumpCodes.put("JLT","100");
        jumpCodes.put("JNE","101");
        jumpCodes.put("JLE","110");
        jumpCodes.put("JMP", "111");

        compCodes = new HashMap<String,String>();
        compCodes.put("0", "0101010");
        compCodes.put("1", "0111111");
        compCodes.put("-1","0111010");
        compCodes.put("D", "0001100");
        compCodes.put("A", "0110000");
        compCodes.put("!D", "0001101");
        compCodes.put("!A", "0110001");
        compCodes.put("-D", "0001111");
        compCodes.put("-A", "0110011");
        compCodes.put("D+1","0011111");
        compCodes.put("A+1","0110111");
        compCodes.put("D+A","0000010");
        compCodes.put("D-A","0010011");
        compCodes.put("A-D","0000111");
        compCodes.put("D&A","0000000");
        compCodes.put("D|A","0010101");
        compCodes.put("M","1110000");
        compCodes.put("!M","1110001");
        compCodes.put("-M","1110011");
        compCodes.put("M+1","1110111");
        compCodes.put("M-1","1110010");
        compCodes.put("D+M","1000010");
        compCodes.put("M+D","1000010");
        compCodes.put("D-M","1010011");
        compCodes.put("M-D","1000111");
        compCodes.put("D&M","1000000");
        compCodes.put("D|M","1010101");
        compCodes.put("D-1","0001110");
        compCodes.put("A-1","0110010");
    }

    /**
     * getComp
     * <p>
     *     Method used to convert a mnemonic to a string of bits (7).
     * </p>
     * Precondition: hashmaps are built with valid values
     * Postcondition: returns string of bits if valid, else returns null
     * @param mnemonic - comp mnemonic to convert to binary
     * @return - binary representation of the passed comp mnemonic
     */
    public String getComp(String mnemonic) {
        return compCodes.get(mnemonic);
    }

    /**
     * getDest
     * <p>
     *     Method used to convert a mnemonic to a string of bits (3).
     * </p>
     * Precondition: HashMaps are built with valid values
     * Postcondition: returns string of bits if valid, null otherwise
     * @param mnemonic - dest mnemonic to convert to binary
     * @return - binary representation of the passed dest mnemonic
     */
    public String getDest(String mnemonic) {
        return destCodes.get(mnemonic);
    }

    /**
     * getJump
     * <p>
     *     Method used to convert a mnemonic to a string of bits (3).
     * </p>
     * Precondition: HashMaps are built with valid values
     * Postcondition: Returns string of bits if valid, else returns null
     * @param mnemonic - jump mnemonic to convert to binary
     * @return - binary representation of the passed jump mnemonic
     */
    public String getJump(String mnemonic) {
        return jumpCodes.get(mnemonic);
    }

    /**
     * decimalToBinary
     * <p>
     *     Converts an integer parameter into a binary string using built in the built in Integer.toBinaryString
     *     method.
     * </p>
     * Precondition: valid range decimal value is passed to method per our architecture (HACK)
     * Postcondition: returns 16-bit string of binary digits (first char is MSB)
     * @param decimal - integer value to be converted to a binary string
     * @return - binary string representation of the decimal value, using 16 bits.
     * @throws IllegalArgumentException - if decimal is out of range.
     */
    public String decimalToBinary(int decimal) throws IllegalArgumentException {
        if (decimal > 32767 || decimal < -32768) {
            throw new IllegalArgumentException();
        }

        String binaryString = Integer.toBinaryString(decimal);

        // to account for leading zeros missed by the Integer.toBinaryString method
        while (binaryString.length() < 16) {
            binaryString = "0" + binaryString;
        }

        // removing overflow - should not occur because of the range check at the beginning of the method
        if (binaryString.length() > 16) {
            binaryString = binaryString.substring(binaryString.length() - 16, binaryString.length());
        }

        return binaryString;
    }
}
