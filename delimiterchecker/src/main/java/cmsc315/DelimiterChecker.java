package cmsc315;

// Class: Data Structures 315
// Author: Yelena Monson
// Date: January 21, 2024

// DelimiterChecker class has the DelimiterChecker constructor that throws an error if the file is not found. 
//It also has getNextChar method used to read every character excluding comments, string literals and 
// character literals. It also contains currentCharPosiiton method to print the currect position of 
// any given character with the exception listed above. 

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class DelimiterChecker {

    private BufferedReader bufferedReader;
    private int lineNumber = 1;
    private int charNumber;
    
    // Constructor
    public DelimiterChecker(File file) throws FileNotFoundException {

        try {
            FileReader fileReader = new FileReader(file);
            bufferedReader = new BufferedReader(fileReader);
        } catch (FileNotFoundException e) {
            throw e;
        }
    }
    
    public Character getNextChar() throws IOException {
        int charCode;
        boolean inBlockComment = false;
        boolean inLineComment = false;
        boolean inStringLiteral = false;
        boolean inCharLiteral = false;
        boolean escapeSequence = false;
    
        while ((charCode = bufferedReader.read()) != -1) {
            char currentChar = (char) charCode;
    
            if (currentChar == '\n') {
                lineNumber++;
                charNumber = 0;
                if (inLineComment) {
                    inLineComment = false;  // End of line comment.
                }
            } else {
                charNumber++;
            }
    
            if (escapeSequence) {
                escapeSequence = false;  // Next character after escape is always included.
            } else if (currentChar == '\\' && (inStringLiteral || inCharLiteral)) {
                escapeSequence = true;  // Handle escape sequences in string or char literals.
            } else if (inBlockComment) {
                if (currentChar == '*' && bufferedReader.ready() && (char)bufferedReader.read() == '/') {
                    inBlockComment = false;  // End of block comment.
                    continue;
                }
            } else if (inLineComment) {
                continue;  // Skip characters in line comment.
            } else if (inStringLiteral) {
                if (currentChar == '"') {
                    inStringLiteral = false;
                }
            } else if (inCharLiteral) {
                if (currentChar == '\'') {
                    inCharLiteral = false;
                }
            } else {
                if (currentChar == '/') {
                    bufferedReader.mark(1);
                    int nextChar = bufferedReader.read();
                    if (nextChar == '*') {
                        inBlockComment = true;
                        continue;
                    } else if (nextChar == '/') {
                        inLineComment = true;
                        continue;
                    } else {
                        bufferedReader.reset();  // It's not a comment, reset buffer.
                    }
                } else if (currentChar == '"') {
                    inStringLiteral = true;
                } else if (currentChar == '\'') {
                    inCharLiteral = true;
                } else {
                    return currentChar;  // Return character if not in comment or literal.
                }
            }
        }
    
        return null;  // End of file.
    }
    
    
    public String currentCharPosiiton() {
        return "line " + lineNumber + ", " + "character number " + charNumber;
    }

}