
import java.util.Arrays;
import java.util.ArrayList;
import java.util.AbstractMap.SimpleEntry;
import java.util.LinkedHashMap;

// This class is the large database 
class HashMap {

  private LinkedHashMap<String, SimpleEntry[]> innerMap;

  // Initialize the map and set Load factor 1 for minimum collisions O(1)
  HashMap(int size) {
    this.innerMap = new LinkedHashMap<>(size, 1f);
  }

  // Return item at key O(1)
  SimpleEntry[] get(String key) {
    return this.innerMap.get(key);
  }
   
  // Returns true if key is is the map O(1)
  boolean contains(String key) {
    
    if (this.innerMap.get(key) == null)
      return false;

    boolean isEmpty = this.innerMap.get(key).length == 0;
    boolean isHistory = this.innerMap.get(key).length == 1
                 && this.innerMap.get(key)[0].getKey().equals("History");
    return !isEmpty && !isHistory;
  }
 
  // Returns sorted keys O(nlogn)
  String[] allKeys() {
    return quickSort(this.innerMap.keySet().toArray(new String[0]));
  }
  
  // Add key item pair O(1)
  void add(String key, SimpleEntry<String, Object> value) {
    
    if (value.getKey().equals("History"))
      throw new RuntimeException("\"History\" cannot be used as key."); // "History" is a keyword
      
    if (key == null || key.isEmpty())
      return;
      
    if (this.innerMap.get(key) != null) {
      ArrayList<SimpleEntry> data = new ArrayList<>(Arrays.asList(this.innerMap.get(key)));
      data.add(value);
      this.innerMap.remove(key);
      this.innerMap.put(key, data.toArray(new SimpleEntry[0]));
    } else {
      this.innerMap.put(key, new SimpleEntry[] { value });
    }
  }

  // Removes a key from the database and marks it as "History" O(1)
  void remove(String key) {

    SimpleEntry[] temp = this.innerMap.get(key);
    this.innerMap.remove(key);
    this.innerMap.put(key, new SimpleEntry[]{new SimpleEntry("History", temp)});
  }

  // Returns history O(1)
  SimpleEntry[] history(String key) {

    for (SimpleEntry pair : this.innerMap.get(key)) {
      if (pair.getKey().equals("History"))
        return ((SimpleEntry[]) pair.getValue());
    }
    return null;
  }

  // Return first key O(1)
  String firstKey() {
    return this.innerMap.keySet().toArray(new String[0])[0];
  }

  // Return next key O(n)
  String nextKey(String key) {
    
    var iter = this.innerMap.keySet().iterator();
    while (iter.hasNext()) {
      if (iter.next().equals(key))
        return iter.next();
    }
    return null;
  }

  // Return previous key O(n)
  String prevKey(String key) {
    
    var keys = this.innerMap.keySet().toArray(new String[0]);
    if (keys.length < 1 || keys[0].equals(key))
      return null;

    for (int i = 1; i < keys.length; i++) {
      if (keys[i].equals(key))
        return keys[i - 1];
    }
    return null;
  }

  // Check if empty O(1)
  boolean isEmpty() {
    return this.innerMap.isEmpty();
  }

  // The next few methods are used for QuickSort which puts all elements in order.
  // Recursion is used sort sections of the array.
  // This has a time complexity of O(nlogn) whic is much faster than 
  // the conventional sorting O(n^2).
  private String[] quickSort(String[] keys) {
    
    String pivot = keys[keys.length - 1];
    // Split the elements into left and right groups
    ArrayList<String>[] values = split(keys, pivot);
    // Return the sorted array
    return quickSortRecursive(values[0].toArray(new String[0]), values[1].toArray(new String[0]), pivot);
  }

  // QuickSort recursion
  private String[] quickSortRecursive(String[] left, String[] right, String pivot) {

    String[] smaller = left;
    String[] larger = right;
    
    if (left.length > 1) {
      String newPivot = smaller[left.length - 1];
      smaller = Arrays.copyOfRange(smaller, 0, smaller.length - 1);
      ArrayList<String>[] values = split(smaller, newPivot);
      smaller = quickSortRecursive(values[0].toArray(new String[0]), values[1].toArray(new String[0]), newPivot);
    }

    if (right.length > 1) {
      String newPivot = larger[larger.length - 1];
      larger = Arrays.copyOfRange(larger, 0, larger.length - 1);
      ArrayList<String>[] values = split(larger, newPivot);
      larger = quickSortRecursive(values[0].toArray(new String[0]), values[1].toArray(new String[0]), newPivot);
    }
    
    String[] result = new String[smaller.length + larger.length + 1];
    int pos = 0;
    for (String element : smaller) { 
      result[pos] = element;
      pos++;
    }

    result[pos] = pivot;
    pos++;
    for (String element : larger) {
      result[pos] = element;
      pos++;
    }
    return result;
  }

  // Splits ArrayList, used in quickSortRecursive
  private ArrayList<String>[] split(String[] keys, String pivot) {
    
    ArrayList<String> left = new ArrayList<>();
    ArrayList<String> right = new ArrayList<>();
    
    for (int i = 0; i < keys.length; i++) {
      if (i == keys.length - 1)
        break;
      if (compareStrings(keys[i], pivot))
        right.add(keys[i]);
      else
        left.add(keys[i]);
    }
    return new ArrayList[]{left, right};
  }
  
  // Compares which string is larger, used in split method above
  private boolean compareStrings(String a, String b) {
    
    for (int i = 0; i < a.length(); i++) {
      if (i >= b.length())
        return true;
      if (a.charAt(i) > b.charAt(i))
        return true;
      else if (a.charAt(i) < b.charAt(i))
        return false;
    }
    return a.length() < b.length();
  }

  // Optional method moves data from sequence to map
  // used in setThreshold O(n)
  void move(Sequence seq) {
    
    String[] keys = seq.allKeys();
    for (String key : keys) {
      this.innerMap.put(key, seq.get(key));
    }
  }
}