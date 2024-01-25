package cmsc315;

import org.junit.Before;
import org.junit.After;
import org.junit.Test;
import static org.junit.Assert.*;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class DelimiterCheckerTest {

    private File tempFile;

    @Before
    public void setUp() throws IOException {
        tempFile = createTempFile();
    }

    @After
    public void tearDown() {
        //clean up
        tempFile.delete();
    }

    private File createTempFile() throws IOException {
    File tempFile = File.createTempFile("test", ".txt");  
    String fileContent = "import java.util.*;\n"
                            + "public class testFile {\n"
                            + " static final int N = 5000;\n"
                            + " public static void main(String[] args) {\n"
                            + "   // Add numbers 0, 1, 2, ..., N - 1 to the array list\n"
                            + "   List<Integer> list = new ArrayList<>(];\n"
                            + "   for (int i = 0; i < N; i++)\n"
                            + "     list.add(i);\n"
                            + "     Collections.shuffle(list); // Shuffle the array list\n"
                            + "   }"
                            + "}";

        try (FileWriter writer = new FileWriter(tempFile)) {
            writer.write(fileContent);
        }
        return tempFile;
    }

    @Test
    public void testGetFirstCharacter() throws IOException  {
        // Initialize DelimiterChecker and call getNextChar
        DelimiterChecker checker = new DelimiterChecker(tempFile);

         // Call getNextChar and capture the result
        char actualChar = checker.getNextChar();
        
        char expectedChar = 'i';

        // Assert the expected behavior
        assertEquals(expectedChar, actualChar);
    }

    @Test
    public void testGet5thCharacter() throws IOException  {
        // Initialize DelimiterChecker and call getNextChar
        DelimiterChecker checker = new DelimiterChecker(tempFile);

         // Call getNextChar and capture the result
         char actualChar = '\0';
         for (int i = 0; i < 5; i++) { 
             actualChar = checker.getNextChar();
         }
         char expectedChar = 'r'; 
         assertEquals(expectedChar, actualChar);

        // Assert the expected behavior
        assertEquals(expectedChar, actualChar);
    }

    @Test
    public void testCharactersSkippingCommentsAndLiterals() throws IOException {
        // Initialize DelimiterChecker with the file
        DelimiterChecker checker = new DelimiterChecker(tempFile);

        List<Character> characters = new ArrayList<>();

        // Read characters and add them to the list
        Character ch;
        while ((ch = checker.getNextChar()) != null) {
            characters.add(ch);
        }

        // Convert the list to a String 
        String result = characters.stream()
                                .map(c -> c.toString()) // Convert Character to String
                                .collect(Collectors.joining());

        System.out.println("Characters read (excluding comments and literals):");
        System.out.println(result);
    }

}