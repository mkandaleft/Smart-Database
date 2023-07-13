
import java.util.ArrayList;
import java.util.AbstractMap.SimpleEntry;
import java.util.Random;

// This class will choose which type of database the file needs and will call
// the appropriate methods of each.
public class SmartDB {
  
  private int size;
  private int threshold = 50000; // The threshold defining if the structure is large or small
  private boolean isLarge;       // Flag for method calls
  private int keyLength = 12;
  private HashMap map;           // isLarge is True
  private Sequence seq;          // isLarge is False

  // Initiallize and select type depending on size O(1)
  public SmartDB(int size) {

    this.size = size;
    this.isLarge = this.size >= this.threshold;
    
    if (isLarge) {
      map = new HashMap(this.size);
      seq = null;
    } else {
      map = null;
      seq = new Sequence();
    }
  }

  // Generate a select number of keys O(n^2)
  public String[] generate(int n) {

    ArrayList<String> results = new ArrayList<>();

    for (int i = 0; i < n; i++) {
      String generated = generateRandomString();
      if (isLarge) {
        while (map.contains(generated)) {
          generated = generateRandomString();
        }
        results.add(generated);
      } else {
        while (seq.contains(generated)) {
          generated = generateRandomString();
        }
        results.add(generated);
      }
    }
    return results.toArray(new String[0]);
  }

  // Return all key O(1)
  public String[] allKeys() {
    
    if (isLarge) {
      return map.allKeys();
    } else {
      return seq.allKeys();
    }
  }

  // Add key value pair O(1)
  public void add(String key, SimpleEntry<String, Object> value) {

    if (isLarge) {
      map.add(key, value);
    } else {
      seq.add(key, value);
    }
  }

  // Remove key O(1)
  public void remove(String key) {

    if (isLarge) {
      map.remove(key);
    } else {
      seq.remove(key);
    }
  }

  // Return values O(1)
  public SimpleEntry[] getValues(String key) {

    if (isLarge) {
      return map.get(key);
    } else {
      return seq.get(key);
    } 
  }

  // Return first key O(1)
  public String firstKey() {

    if (isLarge) {
      return map.firstKey();
    } else {
      return seq.firstKey();
    }
  }

  // Return next key O(1)
  public String nextKey(String key) {

    if (isLarge) {
      return map.nextKey(key);
    } else {
      return seq.nextKey(key);
    }
  }

  // Return previous key O(1)
  public String prevKey(String key) {

    if (isLarge) {
      return map.prevKey(key);
    } else {
      return seq.prevKey(key);
    }
  }
  
  // Return history O(1)
  public SimpleEntry[] prevItem(String key) {

    if (isLarge) {
      return map.history(key);
    } else {
      return seq.history(key);
    }
  }

  // Generate a random string "item" O(n)
  private String generateRandomString() {

    final String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789"; // List of allowed characters
    StringBuilder builder = new StringBuilder();                      // String builder for convenience
    for (int i = 0; i < this.keyLength; i++) {
      int random = new Random().nextInt(characters.length());
      builder.append(characters.charAt(random));
    }
    return builder.toString();
  }

  // Optional method to reset the threshold during runtime O(1)
  public void setThreshold(int threshold) {
    
    boolean prevIsLarge = this.isLarge;
    this.threshold = threshold;
    this.isLarge = this.size >= this.threshold;
    
    if(prevIsLarge == this.isLarge)
      return;
    
    if (this.isLarge) {          // If isLarge changes, move data to new structure
      this.map.move(this.seq);
      this.seq = null;
    } else {
      this.seq.move(this.map);
      this.map = null;
    }
  }

  // Optional method to set Key length O(1)
  public void setKeyLength(int length) {
    
    if (length < 6 || length > 12)
      throw new RuntimeException("Key Length must be between 6 and 12 characters.");
    this.keyLength = length;
  }
}
