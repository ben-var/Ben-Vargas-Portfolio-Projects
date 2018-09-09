/**
 * Author: Ben Vargas
 * Course: CS220 - Computer Architecture and Assembly Language
 * Professor: Nery Chapeton-Lamas
 * Project: Assembler (HACK Architecture)
 */
import java.util.HashMap;

public class SymbolTable {
    public static final String INITIAL_VALID_CHARS = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ_.$:";
    public static final String ALL_VALID_CHARS = INITIAL_VALID_CHARS + "0123456789";

    // underlying data structure for SymbolTable wrapper class
    private HashMap<String, Integer> symbolTable;

    /**
     * Default Constructor for SymbolTable
     * <p>
     *     Initializes symbol HashMaps with pre-determined, hardcoded
     *     symbols (keys) and their corresponding reserved RAM locations
     *     (values).
     * </p>
     * Precondition: n/a
     * Postcondition: SymbolTable starts with reserved memory location.
     */
    public SymbolTable() {
        symbolTable = new HashMap<String, Integer>();
        symbolTable.put("SP", 0);
        symbolTable.put("LCL", 1);
        symbolTable.put("ARG", 2);
        symbolTable.put("THIS", 3);
        symbolTable.put("THAT", 4);

        symbolTable.put("R0", 0);
        symbolTable.put("R1", 1);
        symbolTable.put("R2", 2);
        symbolTable.put("R3", 3);
        symbolTable.put("R4", 4);
        symbolTable.put("R5", 5);
        symbolTable.put("R6", 6);
        symbolTable.put("R7", 7);
        symbolTable.put("R8", 8);
        symbolTable.put("R9", 9);
        symbolTable.put("R10", 10);
        symbolTable.put("R11", 11);
        symbolTable.put("R12", 12);
        symbolTable.put("R13", 13);
        symbolTable.put("R14", 14);
        symbolTable.put("R15", 15);
        symbolTable.put("SCREEN", 16384);
        symbolTable.put("KBD", 24576);
    }

    /**
     * addEntry
     * <p>
     *     Method used to add a symbol and address to the SymbolTable
     * </p>
     * Precondition: Symbol/address pair already in HashMap
     * Postcondition: Adds pair, returns true if added, false if illegal name/symbol/address
     * @param symbol - symbol to be added to the symbol table
     * @param address - int address to map the symbol to
     * @return - true if successful, false otherwise.
     * @throws IllegalArgumentException - if address passed into method is outside of acceptable range
     *                                    or attempts to override an existing value
     */
    public boolean addEntry(String symbol, int address) throws IllegalArgumentException {
        // checking for reserved addressees to avoid conflict
        if (address < 0 || address > 24576) {
            throw new IllegalArgumentException("Invalid address: " + Integer.toString(address));
        }

         if (!isValidName(symbol)) {
            System.err.print("An invalid name: " + symbol + " cannot be added to symbol table.");
            return false;
        }
        symbolTable.put(symbol, address);
        return true;
    }

    /**
     * contains
     * <p>
     *     Method returns a boolean for whether HashMap has the symbol.
     * </p>
     * Precondition: table has been initialized
     * Postcondition: returns boolean if arg is in table or not
     * @param symbol - symbol to lookup in the HashMap
     * @return - true if HashMap contains symbol, false otherwise
     */
    public boolean contains(String symbol) {
        return symbolTable.containsKey(symbol);
    }

    /**
     * getAddress
     * <p>
     *     Method returns address in HashMap of given symbol
     * </p>
     * Precondition: Symbol is in HashMap
     * Postcondition: returns boolean if arg is in table or not
     * @param symbol - symbol to look up in the symbol table for an address
     * @return - the address of the symbol
     * @throws IllegalArgumentException - if symbol is not in the table.
     */
    public int getAddress(String symbol) throws IllegalArgumentException {
        if (!this.contains(symbol)) {
            throw new IllegalArgumentException();
        }

        return symbolTable.get(symbol);
    }

    /**
     * isValidName
     * <p>
     *     Method used to check validity of identifiers for assembly code symbols.
     * </p>
     * Precondition: Starts with letters or "_.$:" only, numbers allowed after
     * Postcondition: returns true if valid identifier, false otherwise.
     * @param symbol - symbol to check the validity of
     * @return - true if valid symbol, false otherwise.
     * @throws IllegalArgumentException - if null is passed into method param
     */
    private boolean isValidName(String symbol) throws IllegalArgumentException {
        if (symbol == null) {
            throw new IllegalArgumentException();
        }

        String current;
        for(int i = 0; i < symbol.length(); i++) {
            current = symbol.substring(i, i+1);
            // first character must be valid and NON numeric
            if (i == 0 && !INITIAL_VALID_CHARS.contains(current)){
                return false;
            } else if (!ALL_VALID_CHARS.contains(current)){ // checking non-first char
                return false;
            }
        }
        return true;
    }

    /**
     * toString
     * <p>
     *     Standard toString that is a wrapper for the HashMap.toString() method
     * </p>
     * @return - string representation of underlying hashtable.
     * Precondition: symbolTable has been initialized
     * Postcondition: n/a
     */
    public String toString() {
        return symbolTable.toString();
    }
}
