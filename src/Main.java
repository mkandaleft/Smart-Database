
import java.util.ArrayList;
import java.util.Random;

/*
 *####################################################################
 *
 * Smart Database
 * 
 * Written by Mark Kandaleft
 * 
 * This smart database will choose between a Sequence ADT implemented with ArrayList
 * and a LinkedHashMap ADT depending on the size of the input. 
 * For inputs below 50 000, the database will use a sequence where each node is an ArrayList for simplicity and a 
 * linked hash map for larger inputs.
 * 
 * The project is composed of the following files:
 * - Main which also containes a testing class
 * - Tools contains method performed on the database
 * - smartDB is the class that will assign either structure
 * - Hashmap and Sequence classes which contain their respective methods
 * - LinkedNode which is used by the sequence class
 * - 3 test files (2 small, 1 large) found under TestFiles
 * 
 * ####################################################################
 */

public class Main {
  public static void main(String[] args) {

    // STEP #1: Read the files
    testingClass.Tests.title("Reading Test Files");
    testingClass.Tests.readTestFiles();

    // STEP #2: Create and fill the SmartDBs
    testingClass.Tests.title("Creating and filling SmartDBs");
    testingClass.Tests.create();

    // STEP #3: Display all the keys sorted
    testingClass.Tests.title("Displaying all Keys Sorted");
    testingClass.Tests.getAllSortedKeys();

    // STEP #4: Remove a few items
    testingClass.Tests.title("Removing some elements");
    testingClass.Tests.removeSomeElements();

    // STEP #5: Adding removed items
    testingClass.Tests.title("Adding back removed element");
    testingClass.Tests.addRemovedElements();

    // STEP #6: Display removed items
    testingClass.Tests.title("Displaying removed elements");
    testingClass.Tests.getRemovedElements();

    // STEP #7: Remove a few items
    testingClass.Tests.title("Getting previous");
    testingClass.Tests.getPrevious();

    // STEP #8: Add new non-existing items (new keys)
    testingClass.Tests.title("Adding new items");
    testingClass.Tests.addUniqueItems();

    // STEP #9: Iterate forward by getting the next key
    testingClass.Tests.title("Iterating Forward");
    testingClass.Tests.iterateForward();

    // STEP #10: Iterate backwards by getting the previous key
    testingClass.Tests.title("Iterating Backwards");
    testingClass.Tests.iterateBackwards();
  }

}

// This class serves to test the different functions that are found in the Tools class
class testingClass {

  static int randomInt() {
    Random r = new Random();
    int result = r.nextInt(100-10) + 10;
    return result;
  }

  static class Tests extends Tools {
    
    // SmartDBs for each Test Files
    private static SmartDB DB_1;
    private static SmartDB DB_2;
    private static SmartDB DB_3;
    // Test Files names
    private static final String FILE_1 = "test_file1.txt";
    private static final String FILE_2 = "test_file2.txt";
    private static final String FILE_3 = "test_file3.txt";
    // Test Files Contents
    private static String[] FILE_1_CONTENT;
    private static String[] FILE_2_CONTENT;
    private static String[] FILE_3_CONTENT;
    // Removed Elements for functions
    private static ArrayList<String> REMOVED_1;
    private static ArrayList<String> REMOVED_2;
    private static ArrayList<String> REMOVED_3;

    // Read the contents of the test files
    public static void readTestFiles() {

      FILE_1_CONTENT = readFile(FILE_1);
      FILE_2_CONTENT = readFile(FILE_2);
      FILE_3_CONTENT = readFile(FILE_3);

      System.out.println("Files read");

      if (FILE_1_CONTENT.length == 0 || FILE_2_CONTENT.length == 0 || FILE_3_CONTENT.length == 0)
        System.out.println("One or more files could not be read");
    }
    
    // Create new SmartDB for Test Files then fill it with file content
    public static void create() {

      DB_1 = new SmartDB(FILE_1_CONTENT.length);
      fillDB(DB_1, FILE_1_CONTENT, 1);
      DB_2 = new SmartDB(FILE_2_CONTENT.length);
      fillDB(DB_2, FILE_2_CONTENT, 2);
      DB_3 = new SmartDB(FILE_3_CONTENT.length);
      fillDB(DB_3, FILE_3_CONTENT, 3);
    }
 
    // Show sorted keys of each SmartDB
    public static void getAllSortedKeys() {

      getSortedKeys(DB_1, 1);
      getSortedKeys(DB_2, 2);
      getSortedKeys(DB_3, 3);
    }

    // Removed 100 elements from the SmartDBs
    public static void removeSomeElements() {

      REMOVED_1 = removeRandom(DB_1);
      REMOVED_2 = removeRandom(DB_2);
      REMOVED_3 = removeRandom(DB_3);
      System.out.println("A few hundred elements were removed");
    }
    
    // Addeding back 100 removed elements for each SmartDB
    public static void addRemovedElements() {

      addElements(DB_1, REMOVED_1);
      addElements(DB_2, REMOVED_2);
      addElements(DB_3, REMOVED_3);
      System.out.println("Elements were added back");
    }

    // Display Re-Added elements for each SmartDB
    public static void getRemovedElements() {

      getRemoved(DB_1, REMOVED_1, randomInt());
      getRemoved(DB_2, REMOVED_2, randomInt());
      getRemoved(DB_3, REMOVED_3, randomInt());
    }
    
    // Display elements from the removed list for each SmartDB
    public static void getPrevious() {

      getHistory(DB_1, REMOVED_1, randomInt());
      getHistory(DB_2, REMOVED_2, randomInt());
      getHistory(DB_3, REMOVED_3, randomInt());
    }

    // Adds unique item to each SmartDB
    public static void addUniqueItems() {

      addNewItems(DB_1, 1);
      addNewItems(DB_2, 2);
      addNewItems(DB_3, 3);
    }
  
    // Display the forward elements
    public static void iterateForward() {
      
      moveForward(DB_1, 1);
      moveForward(DB_2, 2);
      moveForward(DB_3, 3);
    }

    // Display the backward elements
    public static void iterateBackwards() {

      moveBackwards(DB_1, 1);
      moveBackwards(DB_2, 2);
      moveBackwards(DB_3, 3);
    }
  }
}