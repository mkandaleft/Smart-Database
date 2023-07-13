
import java.util.Arrays;
import java.util.ArrayList;
import java.util.AbstractMap.SimpleEntry;
import java.util.Random;
import java.io.IOException;
import java.nio.file.Paths;
import java.nio.file.Files;

// This class contains the functions

public class Tools {

  // Displays messages (Smiley faces used for positivity)
  public static void title(String text) {
    System.out.println("\n\u263A \u263A \u263A \u263A \u263A \u263A \u263A \u263A \u263A \u263A \u263A\n");
    System.out.println(text);
    System.out.println("-------------------------------------------");
  }                     
  
  // Read the file in the path package
  static String[] readFile(String file) {

    var path = Paths.get("TestFiles", file);
    try {
      return Files.readString(path).split("\\r?\\n");
    } catch (IOException e) {
      e.printStackTrace();
    }
    return new String[0];
  }

  // Fill SmartDB with file contents and display the number of elements
  static void fillDB(SmartDB db, String[] content, int i) {

    for (String elem : content) {
      db.add(elem, new SimpleEntry<>("Name", elem));
    }
    System.out.println("SmartDB #" + i + " has " + db.allKeys().length + " items");
    System.out.println("\n");
  }

  // Display sorted keys 
  static void getSortedKeys(SmartDB db, int i) {
    System.out.println("Test File #" + i + " Keys:");
    String outString = Arrays.toString(db.allKeys());
    // Print part of the sorted keys array
    if (outString.length() > 90)
      System.out.println(outString.substring(0, 60) + "...]"); //(set to 60 characters for simplicity)
    else
      System.out.println(outString);
    System.out.println("\n");
  }
  
  // Remove 100 random elements and store them in an array
  static ArrayList<String> removeRandom(SmartDB db) {
    
    ArrayList<String> removed = new ArrayList<>();
    String[] keys = db.allKeys();
    
    if (keys.length < 100) // The program bugs if there is less than 100 elements
      return null;
      
    for (int i = 0; i < 100; i++) {
      removed.add(keys[new Random().nextInt(keys.length)]);
      db.remove(removed.get(i));
    }
    return removed;
  }

  // Add elements into SmartDB and display them
  static void addElements(SmartDB db, ArrayList<String> elements) {
    
    if (elements == null)
      return;
    for (String element : elements) {
      int random = new Random().nextInt(10000);
      db.add(element, new SimpleEntry<>("New item ", random));
    }
  }

  // Display removed elements
  static void getRemoved(SmartDB db, ArrayList<String> elements, int i) {

    System.out.println("Added back Element " + i + ":");
    if (elements == null || elements.size() < 1)
      System.out.println("[]");
    else
      System.out.println(Arrays.toString(db.getValues(elements.get(0))));
    System.out.println("\n");
  }

  // Display history
  static void getHistory(SmartDB db, ArrayList<String> elements, int i) {

    System.out.println("Item History " + i + ":");
    if (elements == null || elements.size() < 1)
      System.out.println("[]");
    else
      System.out.println(Arrays.toString(db.prevItem(elements.get(0))));
    System.out.println("\n");
  }

  // Add unique item
  static void addNewItems(SmartDB db, int i) {

    System.out.println("Adding 50 items to SmartDB #" + i);
    String[] newKeys = db.generate(50);

    for (String key : newKeys) {
      int random = new Random().nextInt(10000);
      db.add(key, new SimpleEntry<>("Unique item ", random));
    }

    if (db.getValues(newKeys[0]) == null)
      System.out.println("Could not generate unique keys...");
    else
      System.out.println(Arrays.toString(db.getValues(newKeys[0])) + " ...");
    System.out.println("\n");
  }

  // Iterate forward
  static void moveForward(SmartDB db, int i) {

    System.out.println("\nIterating forward on SmartDB #" + i);
    String key = db.firstKey();

    for (int j = 0; j <= 2; j++) {
      if (key == null) {
        System.out.println("Could not iterate forward");
        return;
      }
      var value = db.getValues(key);
      System.out.print("Item " + j + "(" + value[value.length - 1] + (j == 4 ? ")\n" : "), "));
      key = db.nextKey(key);
    }
    System.out.println("\n");
  }

  // Iterate backwards
  static void moveBackwards(SmartDB db, int i) {

    System.out.println("Iterating backwards on SmartDB #" + i);
    String key = db.firstKey();
    
    for (int j = 0; j < 10; j++) { // Iterate forward by 10 to move backwards
      key = db.nextKey(key);
    }

    for (int j = 2; j >= 0; j--) {
      if (key == null) {
        System.out.println("Could not iterate backwards");
        return;
      }
      var value = db.getValues(key);
      System.out.print("Item " + j + "(" + value[value.length - 1] + (j == 0 ? ")\n" : "), "));
      key = db.prevKey(key);
    }
    System.out.println("\n");
  }
}